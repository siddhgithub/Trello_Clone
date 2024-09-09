package com0.trello.Task.Controller;

import com0.trello.Board.model.Board;
import com0.trello.Board.repository.BoardRepository;
import com0.trello.Task.Service.TaskService;
import com0.trello.Task.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tasks")

public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    BoardRepository boardRepository;

    @GetMapping("/getTask")
    public ResponseEntity<Task> getTaskById(@RequestParam int id) {
        Task task = taskService.findTaskById(id);
        if(task != null){
            return ResponseEntity.ok(task);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allTasks")
    public List<Task> getAllTask(){
       return taskService.getAllTasks();
    }

    @PutMapping("/update")
    public String updateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @PostMapping("/save")
    public Task createTask(@RequestBody Task task, @RequestParam Integer boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);

        task.setBoard(board);
        return taskService.createTask(task);
    }

    @PutMapping("/changeTaskStatus")
    public String changeTaskStatus(@RequestParam int id, @RequestParam String newStatus)
    {
        return taskService.changeStatus(id,newStatus);
    }

    @PutMapping("/changeEndDate")
    public String changeEndDate(@RequestParam int id, @RequestParam LocalDate newEndDate)
    {
        return taskService.changeEndDate(id,newEndDate);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable("id") int id) {

        return taskService.deleteTaskById(id);
    }

    @PostMapping("/assignMember")
    public String assignMemberToTask(@RequestParam String emailAddress,@RequestParam int taskId)
    {
        return taskService.assignMemberToTask(emailAddress,taskId);
    }
}
