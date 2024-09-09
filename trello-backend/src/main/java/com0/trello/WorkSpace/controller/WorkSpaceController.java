package com0.trello.WorkSpace.controller;

import com0.trello.Board.model.Board;
import com0.trello.Board.service.BoardService;
import com0.trello.User.model.User;
import com0.trello.User.service.UserService;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/workspace")
public class WorkSpaceController {

    @Autowired
    WorkSpaceService workSpaceService;

    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;

    @GetMapping("/getBoards")
    public ResponseEntity<List<Board>> getAllBoardsInWorkspace(@RequestParam Integer workSpaceId) {
        return ResponseEntity.ok(boardService.getAllBoardsInWorkspace(workSpaceId));
    }

    @GetMapping("/getWorkSpace")
    public ResponseEntity<WorkSpace> getWorkSpaceById(@RequestParam int id) {
        WorkSpace workSpace = workSpaceService.findWorkSpaceById(id);
        if(workSpace != null){
            return ResponseEntity.ok(workSpace);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<WorkSpace> updateWorkSpace(@RequestBody WorkSpace workSpace) {
        return ResponseEntity.ok(workSpaceService.updateWorkSpace(workSpace));
    }

    @PostMapping("/save")
    public ResponseEntity<WorkSpace> createWorkSpace(@RequestParam String workSpaceName, @RequestParam String workSpaceDetail) {
        try {
            WorkSpace workSpace = new WorkSpace();
            workSpace.setWorkSpaceName(workSpaceName);
            workSpace.setWorkSpaceDetail(workSpaceDetail);
            return ResponseEntity.ok(workSpaceService.createWorkSpace(workSpace));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/allWorkSpaces")
    public ResponseEntity<List<WorkSpace>> getAllWorkSpaces()
    {
        return ResponseEntity.ok(workSpaceService.getAllWorkSpaces());
    }

    @GetMapping("/membersName")
    public List<User> displayAllUsersNames(@RequestParam int workSpaceId)
    {
        return workSpaceService.getAllMembersName(workSpaceId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteWorkSpaceById(@PathVariable("id") int id) {

        return workSpaceService.deleteWorkSpaceById(id);
    }

        @PostMapping("/addMemberToWorkSpace")
    public String addMemberToWorkSpace(@RequestParam int workSpace_Id, @RequestParam String email)
    {
        workSpaceService.addMemberToWorkSpace(userService.findUserByEmail(email),workSpace_Id);
        return "Added Member";
    }


}
