package com0.trello.Board.model;

import com0.trello.WorkSpace.model.WorkSpace;
import jakarta.persistence.*;

@Entity
public class Board{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String boardName;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private WorkSpace workSpace;

    public Board(int id, String boardName, WorkSpace workSpace) {
        this.id = id;
        this.boardName = boardName;
        this.workSpace = workSpace;
    }

    public Board()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public WorkSpace getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(WorkSpace workSpace) {
        this.workSpace = workSpace;
    }
}
