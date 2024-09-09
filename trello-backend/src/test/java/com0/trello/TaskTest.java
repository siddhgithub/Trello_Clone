package com0.trello;

import com0.trello.Board.model.Board;
import com0.trello.Task.Repository.TaskRepository;
import com0.trello.Task.Service.TaskService;
import com0.trello.Task.model.Task;
import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.repository.WorkSpaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskTest {

    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    WorkSpaceRepository workSpaceRepository;

    @Test
    public void createTaskTest()
    {
        Board board = new Board(1,"kalu",null);
        Task task = new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,21));
        task.setBoard(board);
        task.setUser(null);

        when(taskRepository.save(task)).thenReturn(task);
        Task task1 = taskService.createTask(task);

        assertEquals(task.getId(),task1.getId(),"Task not created properly");
        verify(taskRepository).save(task);
    }

    @Test
    public void createTaskWithoutNameTest()
    {
        Task task = new Task();
        task.setId(1);
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        Task task1 = taskService.createTask(task);

        assertNull(task1,"Task Cannot be created without name");
    }

    @Test
    public void statusChangeTest()
    {
        Task task = new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));
        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskStatus("Doing");

        String response = taskService.changeStatus(1,task1.getTaskStatus());

        assertEquals("Doing",task.getTaskStatus(),"Task Status has not been changed");

        verify(taskRepository).findById(1);

    }

    @Test
    public void statusChangeWithoutAssignedTaskTest()
    {
        Task task = new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        Task task1 = new Task();
        task1.setId(1);
        task1.setTaskStatus("Doing");

        String response = taskService.changeStatus(1,task1.getTaskStatus());
        boolean check;
        if(task.getTaskStatus().equals("Doing"))
        {
            check =true;
        }
        else
        {
            check = false;
        }
        assertFalse(check);

    }

    @Test
    public void changeDueDateTest()
    {
        Task task = new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));
        Task task1 = new Task();
        task1.setId(1);
        task1.setEndDate(LocalDate.of(2023,07,29));

        String response = taskService.changeEndDate(1,task1.getEndDate());

        assertEquals(LocalDate.of(2023,07,29),task.getEndDate(),"Task due date is not changed");

        verify(taskRepository).findById(1);
    }

    @Test
    public void checkValidDueDateTest()
    {
        Task task = new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));
        Task task1 = new Task();
        task1.setId(1);
        task1.setEndDate(LocalDate.of(2023,07,10));

        String response = taskService.changeEndDate(1,task1.getEndDate());

        assertEquals("Enter proper End date",response,"Enter proper Due date, it should be after start date");

        verify(taskRepository).findById(1);
    }

    @Test
    public void addUserTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("Harsh");
        user.setUserEmailAddress("harsh@dal.ca");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("Hello1234");

        User user1 = new User();
        user1.setId(2);
        user1.setUserName("Harsh1");
        user1.setUserEmailAddress("harsh1@dal.ca");
        user1.setUserPassword("Harsh1@123");
        user1.setSecurityAnswer("Hello12345");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        WorkSpace w1 = new WorkSpace();
        w1.setId(1);
        w1.setWorkSpaceName("workspace1");
        w1.setWorkSpaceDetail("detail11");
        w1.setUsers(userList);

        Board b1= new Board();
        b1.setId(1);
        b1.setBoardName("Board1");
        b1.setWorkSpace(w1);


        Task task=new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(b1);
        task.setUser(user);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));
        when(workSpaceRepository.findById(1)).thenReturn(java.util.Optional.of(w1));
        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);

        String response = taskService.assignMemberToTask(user.getUserEmailAddress(),1);

        assertEquals("Member Added successfully",response,"Member is not added");

        verify(taskRepository).findById(1);
        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());
        verify(workSpaceRepository).findById(1);
    }

    @Test
    public void addUserToNonExistingTask()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("Harsh");
        user.setUserEmailAddress("harsh@dal.ca");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("Hello1234");

        String response = taskService.assignMemberToTask(user.getUserEmailAddress(),1);
        assertEquals("Member cannot be assigned in empty task",response,"Member cannot added to non existing task");

    }

    @Test
    public void updateTaskTest()
    {
        Task task=new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));

        Task task1=new Task();
        task1.setId(1);
        task1.setTaskName("Harsh2");

        String response = taskService.updateTask(task1);
        assertEquals("Task information updated successfully!",response,"Task is not updated successfully");

        verify(taskRepository).findById(1);
    }

    @Test
    public void checkFindTaskByIdTest()
    {
        Task task=new Task();
        task.setId(1);
        task.setTaskName("Harsh1");
        task.setTaskStatus("To-Do");
        task.setStartDate(LocalDate.of(2023,07,19));
        task.setEndDate(LocalDate.of(2023,07,25));
        task.setBoard(null);
        task.setUser(null);

        when(taskRepository.findById(1)).thenReturn(java.util.Optional.of(task));

        Task task1 = taskService.findTaskById(1);

        assertEquals(task1.getTaskName(),task.getTaskName());

        verify(taskRepository).findById(1);

    }
}
