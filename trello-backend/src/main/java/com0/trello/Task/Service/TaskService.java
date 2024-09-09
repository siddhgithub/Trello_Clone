package com0.trello.Task.Service;

import com0.trello.Board.repository.BoardRepository;
import com0.trello.Task.Repository.TaskRepository;
import com0.trello.Task.model.Task;
import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    WorkSpaceRepository workSpaceRepository;
    public Task createTask(Task task)
    {
        if(task.getTaskName() == null || task.getEndDate().isBefore(task.getStartDate()))
        {
            return null;
        }

        Task task1 = taskRepository.save(task);
        System.out.println(task1);

        return task1;
    }

    public Task findTaskById(int id) {
        Task task = taskRepository.findById(id).orElse(null);
        System.out.println("Successfully fetched task information " + task);
        return task;
    }

    public String updateTask(Task task) {
        Task task1 = taskRepository.findById(task.getId()).orElse(null);
        if (task1 == null) {
            return "No User found with given Id";
        }
        task1.setTaskName(task.getTaskName());
        task1.setTaskStatus(task.getTaskStatus());
        task1.setStartDate(task.getStartDate());
        task1.setEndDate(task.getEndDate());
        taskRepository.save(task1);
        return "Task information updated successfully!";
    }

    public List<Task> getAllTasks() {
        List<Task> tasks=taskRepository.findAll();
        return tasks;
    }

    public String deleteTaskById(int id) {
        taskRepository.deleteById(id);
        return "Task Information deleted successfully!";
    }

    public String changeStatus(int taskId, String newStatus)
    {
        Task task = taskRepository.findById(taskId).orElse(null);

        if(task == null)
        {
            return "No task found of given id";
        }

        if(newStatus == null)
        {
            return "Please enter valid status";
        }

        task.setTaskStatus(newStatus);
        taskRepository.save(task);

        return "Changed Task Status Successfully";

    }

    public String changeEndDate(int taskId, LocalDate newEndDate)
    {
        Task task = taskRepository.findById(taskId).orElse(null);

        if(task == null)
        {
            return "No task found of given id";
        }

        if(newEndDate == null)
        {
            return "Please enter valid due date";
        }

        if(newEndDate.isBefore(task.getStartDate()))
        {
            return "Enter proper End date";
        }

        task.setEndDate(newEndDate);
        taskRepository.save(task);

        return "Changed due date Successfully";
    }

    public String assignMemberToTask(String userEmail, int taskId)
    {
        Task task = taskRepository.findById(taskId).orElse(null);
        if(task==null){
            return "Member cannot be assigned in empty task";
        }
        int workspaceId= task.getBoard().getWorkSpace().getId();
        WorkSpace workSpace=workSpaceRepository.findById(workspaceId).orElse(null);
        List<User> userList = workSpace.getUsers();
        for(int i=0;i<userList.size();i++)
        {
            if(userList.get(i).getUserEmailAddress().equals(userEmail))
            {
                User user=userRepository.findByUserEmailAddress(userEmail);
                List<User> users=new ArrayList<>();
                users.add(user);
//                task.setMemberList(users);
                taskRepository.save(task);
                return "Member Added successfully";
            }
        }

        return "Member Not Added successfully";
    }

}
