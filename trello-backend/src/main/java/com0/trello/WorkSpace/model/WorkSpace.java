package com0.trello.WorkSpace.model;

import com0.trello.User.model.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class WorkSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String workSpaceName;
    private String workSpaceDetail;

    @ManyToMany(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")

    private List<User> users = new ArrayList<>();

    public WorkSpace()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkSpaceName() {
        return workSpaceName;
    }

    public void setWorkSpaceName(String workSpaceName) {
        this.workSpaceName = workSpaceName;
    }

    public String getWorkSpaceDetail() {
        return workSpaceDetail;
    }

    public void setWorkSpaceDetail(String workSpaceDetail) {
        this.workSpaceDetail = workSpaceDetail;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
