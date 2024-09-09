package com0.trello.Board.controller;

import com0.trello.Board.model.Board;
import com0.trello.Board.repository.BoardRepository;
import com0.trello.Board.service.BoardService;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    WorkSpaceService workSpaceService;

    @GetMapping("/getBoard")
    public ResponseEntity<Board> getBoardById(@RequestParam int id) {
        Board board = boardRepository.findById(id).orElse(null);
        if(board != null){
            return ResponseEntity.ok(board);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public String updateBoard(@RequestBody Board board) {
        return boardService.updateBoard(board);
    }

    @PostMapping("/save")
    public ResponseEntity<Board> createBoard(@RequestParam String boardName, @RequestParam Integer workSpaceId) {

        WorkSpace workspace = workSpaceService.findWorkSpaceById(workSpaceId);

        if (workspace == null) {
            return ResponseEntity.internalServerError().build();
        }

        Board board = new Board();
        board.setWorkSpace(workspace);
        board.setBoardName(boardName);
        return ResponseEntity.ok(boardService.createBoard(board));
    }

    @DeleteMapping("/delete")
    public boolean deleteBoardById(@RequestParam Integer id) {
        try {
            boardService.deleteBoardById(id);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
