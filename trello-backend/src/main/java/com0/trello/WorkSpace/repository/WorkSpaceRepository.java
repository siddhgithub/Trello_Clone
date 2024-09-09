package com0.trello.WorkSpace.repository;

import com0.trello.WorkSpace.model.WorkSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpace, Integer>{

}
