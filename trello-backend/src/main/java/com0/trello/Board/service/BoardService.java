package com0.trello.Board.service;

import com0.trello.Board.model.Board;
import com0.trello.Board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public Board createBoard(Board board)
    {
        if(board == null)
        {
            return null;
        }
        return boardRepository.save(board);
    }

    public String updateBoard(Board board)
    {
        Board board1 = boardRepository.findById(board.getId()).orElse(null);
        if(board1 == null)
        {
            return "No board Assigned with this ID";
        }
        board1.setBoardName(board.getBoardName());
        board1.setWorkSpace(board.getWorkSpace());
        boardRepository.save(board);

        return "Board Updated Successfully";
    }

    public String deleteBoardById(int id){
        boardRepository.deleteById(id);

        return "Board deleted Successfully";
    }

    public List<Board> getAllBoardsInWorkspace(Integer workSpaceId) {
        List<Board> allBoards = boardRepository.findAll();
        List<Board> boardsInGivenWorkspace = new ArrayList<>();

        for (Board board : allBoards) {
            if (board.getWorkSpace().getId() == workSpaceId) {
                boardsInGivenWorkspace.add(board);
            }
        }

        return boardsInGivenWorkspace;
    }
}
