package com0.trello;

import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import com0.trello.WorkSpace.model.WorkSpace;
import com0.trello.WorkSpace.repository.WorkSpaceRepository;
import com0.trello.WorkSpace.service.WorkSpaceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkSpaceTest {
    @Mock
    WorkSpaceRepository workSpaceRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    WorkSpaceService workSpaceService;

    @Test
    public void getAllWorkSpaceTest()
    {
        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        WorkSpace workSpace1=new WorkSpace();
        workSpace1.setId(2);
        workSpace1.setWorkSpaceName("harsh2");
        workSpace1.setWorkSpaceDetail("hello2");

        List<WorkSpace> workSpaceList = new ArrayList<>();
        workSpaceList.add(workSpace);
        workSpaceList.add(workSpace1);

        when(workSpaceRepository.findAll()).thenReturn(workSpaceList);

        List<WorkSpace> workSpaceList1 = workSpaceService.getAllWorkSpaces();

        assertEquals(2,workSpaceList1.size());

        verify(workSpaceRepository).findAll();
    }

    @Test
    public void getAllMemberTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        User user2=new User();
        user2.setId(2);
        user2.setUserName("Harsh2");
        user2.setUserEmailAddress("User2@gmail.com");
        user2.setUserPassword("Harsh@1234");
        user2.setSecurityAnswer("User2");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");
        workSpace.setUsers(userList);

        when(workSpaceRepository.findById(1)).thenReturn(java.util.Optional.of(workSpace));

        List<User> userList2 = workSpaceService.getAllMembersName(1);

        assertEquals(2,userList2.size());
    }

    @Test
    public void addMemberToWorkSpaceTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        when(workSpaceRepository.findById(1)).thenReturn(java.util.Optional.of(workSpace));

        String response =  workSpaceService.addMemberToWorkSpace(user,1);

        assertEquals("Member Added Successfully",response);
    }

    @Test
    public void createWorkSpaceTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");


        List<User> userList = new ArrayList<>();
        userList.add(user);

        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");
        workSpace.setUsers(userList);

        when(workSpaceRepository.save(workSpace)).thenReturn(workSpace);
        when(userRepository.findAll()).thenReturn(userList);
        WorkSpace workSpace1= workSpaceService.createWorkSpace(workSpace);

        assertEquals(workSpace,workSpace1,"WorkSpace not created");
        verify(workSpaceRepository).save(workSpace);
        verify(userRepository).findAll();

    }

    @Test
    public void checkFindWorkSpaceByIdTest()
    {
        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        when(workSpaceRepository.findById(1)).thenReturn(java.util.Optional.of(workSpace));

        WorkSpace workSpace1 = workSpaceService.findWorkSpaceById(workSpace.getId());

        assertEquals(workSpace.getWorkSpaceName(),workSpace1.getWorkSpaceName());

        verify(workSpaceRepository).findById(1);

    }

    @Test
    public void updateWorkSpaceTest()
    {
        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        when(workSpaceRepository.findById(1)).thenReturn(java.util.Optional.of(workSpace));
        WorkSpace workSpace1=new WorkSpace();
        workSpace1.setId(1);
        workSpace1.setWorkSpaceName("Harsh2");

        WorkSpace response = workSpaceService.updateWorkSpace(workSpace1);
        assertEquals(workSpace.getWorkSpaceName(),workSpace1.getWorkSpaceName());
        verify(workSpaceRepository).findById(1);
    }

    @Test
    public void deleteWorkSpaceByIdTest()
    {
        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        doNothing().when(workSpaceRepository).deleteById(1);

        String result = workSpaceService.deleteWorkSpaceById(1);

        assertEquals("Workspace deleted Successfully", result);
        verify(workSpaceRepository).deleteById(1);
    }

    @Test
    public void updateNullWorkSpaceTest()
    {
        WorkSpace workSpace=new WorkSpace();
        workSpace.setId(1);
        workSpace.setWorkSpaceName("harsh1");
        workSpace.setWorkSpaceDetail("hello");

        WorkSpace workSpace1=new WorkSpace();
        workSpace1.setId(2);
        workSpace1.setWorkSpaceName("Harsh2");

        WorkSpace response = workSpaceService.updateWorkSpace(workSpace1);
        Assertions.assertNull(response);
    }
}
