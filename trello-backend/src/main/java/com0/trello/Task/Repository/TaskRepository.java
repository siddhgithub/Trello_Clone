package com0.trello.Task.Repository;

import com0.trello.Task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    //The TaskRepository interface extends the JpaRepository interface,
    // which provides a set of predefined methods for basic CRUD operations.
}
