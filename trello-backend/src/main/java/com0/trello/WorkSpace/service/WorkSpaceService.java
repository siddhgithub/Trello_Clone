package com0.trello.WorkSpace.service;

import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import com0.trello.User.service.UserService;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WorkSpaceService {

    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public WorkSpace createWorkSpace(WorkSpace workSpace)
    {
        List<User> userList = userRepository.findAll();
        if(userList.size() == 0)
        {
            return null;
        }
        return workSpaceRepository.save(workSpace);
    }

    public WorkSpace findWorkSpaceById(int id)
    {
        WorkSpace workSpace = workSpaceRepository.findById(id).orElse(null);

        System.out.println("Successfully fetched workSpace information " + workSpace);
        return workSpace;

    }

    public WorkSpace updateWorkSpace(WorkSpace workSpace)
    {
        WorkSpace workSpace1 = workSpaceRepository.findById(workSpace.getId()).orElse(null);

        if (workSpace1 == null) {
            return null;
        }

        workSpace1.setWorkSpaceName(workSpace.getWorkSpaceName());
        workSpace1.setWorkSpaceDetail(workSpace.getWorkSpaceDetail());
        return workSpaceRepository.save(workSpace1);
    }

    public String deleteWorkSpaceById(int id)
    {
        workSpaceRepository.deleteById(id);

        return "Workspace deleted Successfully";
    }

    public String addMemberToWorkSpace(User user, int workSpace_Id)
    {
        WorkSpace workSpace = workSpaceRepository.findById(workSpace_Id).orElse(null);

        List<User> allMembers = workSpace.getUsers();
        for (int i = 0; i <allMembers.size() ; i++) {
            if (allMembers.get(i).getId()==user.getId()){
                return "Member should not be added";
            }
        }
        allMembers.add(user);
        workSpace.setUsers(allMembers);

        workSpaceRepository.save(workSpace);

        return "Member Added Successfully";

    }

    public List<WorkSpace> getAllWorkSpaces (){
        List<WorkSpace> allWorkSpaces = workSpaceRepository.findAll();

        return allWorkSpaces;
    }

    public List<User> getAllMembersName(int workSpaceId)
    {
        WorkSpace workSpace = findWorkSpaceById(workSpaceId);

        return workSpace.getUsers();
    }
}
