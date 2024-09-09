package com0.trello;

import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import com0.trello.User.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void createUserTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.OK,user1.getStatusCode(),"User not created");

        verify(userRepository).save(user);
    }

    @Test
    public void createUserWithEmptyUserNameTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST,user1.getStatusCode(),"User not created");

    }

    @Test
    public void createUserWithEmptyEmailAddressTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("harsh1");
        user.setUserEmailAddress("");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST,user1.getStatusCode(),"User not created");

    }

    @Test
    public void createUserWithEmptyPasswordTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("harsh1");
        user.setUserEmailAddress("harsh2@gmail.com");
        user.setUserPassword("");
        user.setSecurityAnswer("User1");

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST,user1.getStatusCode(),"User not created");

    }

    @Test
    public void createUserWithWrongPasswordRequirementTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("harsh1");
        user.setUserEmailAddress("harsh2@gmail.com");
        user.setUserPassword("harsh22");
        user.setSecurityAnswer("User1");

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.UNAUTHORIZED,user1.getStatusCode(),"User not created");

    }

    @Test
    public void createUserWithEmptySecurityAnswerTest()
    {
        User user = new User();
        user.setId(1);
        user.setUserName("harsh1");
        user.setUserEmailAddress("harsh2@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("");

        ResponseEntity<User> user1= this.userService.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST,user1.getStatusCode(),"User not created");

    }


    @Test
	public void checkUserExistsWithValidEmailTest()
	{
		User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

		when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<User> user1= this.userService.createUser(user);

		when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);
		User userFound=this.userService.findUserByEmail("User1@gmail.com");

		assertEquals(user1.getBody(),user,"Mismatch");
		verify(userRepository).save(user);
		verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());
	}

    @Test
    public void loginUserTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<User> user1= this.userService.createUser(user);

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);
        ResponseEntity<User> user2 = userService.validateEmailPassword(user.getUserEmailAddress(),user.getUserPassword());

        assertEquals(HttpStatus.OK,user2.getStatusCode(),"Not working properly");

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());
    }


    @Test
    public void loginUserWithWrongPasswordTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<User> user1= this.userService.createUser(user);

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);
        ResponseEntity<User> user2 = userService.validateEmailPassword(user.getUserEmailAddress(),"Harsh@2244");

        assertEquals(HttpStatus.BAD_REQUEST,user2.getStatusCode(),"Not working properly");

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());
    }

    @Test
    public void loginWithNoUserTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@1234");
        user.setSecurityAnswer("User1");

        when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<User> user1= this.userService.createUser(user);

        ResponseEntity<User> user2 = userService.validateEmailPassword("harsh@ymail.com","Harsh@1234");

        assertEquals(HttpStatus.BAD_REQUEST,user2.getStatusCode(),"Not working properly");

    }


    @Test
    public void validPasswordTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("har123");
        user.setSecurityAnswer("User1");


        Boolean response = userService.isValidPassword(user.getUserPassword());

        assertFalse(response);

    }

    @Test
    public void findDuplicateUserTest()
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

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);
        Boolean response = userService.findDuplicateUserByEmail(user2.getUserEmailAddress());

        assertTrue(response);
        verify(userRepository).findAll();
    }

    @Test
    public void checkUpdateUserTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));

        User user1=new User();
        user1.setId(1);
        user1.setUserName("Harsh2");

        String response = userService.updateUser(user1);
        assertEquals("User information updated successfully!",response,"User is not updated successfully");

        verify(userRepository).findById(1);
    }

    @Test
    public void checkFindUserByIdTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user));

        User user1 = userService.findUserById(user.getId());

        assertEquals(user1.getUserName(),user.getUserName());

        verify(userRepository).findById(1);

    }

    @Test
    public void checkFindUserByEmailTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);

        User user1 = userService.findUserByEmail(user.getUserEmailAddress());

        assertEquals(user1.getUserName(),user.getUserName());

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());

    }

    @Test
    public void resetPasswordTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);

        User user1 = new User();
        user1.setId(1);
        user1.setUserEmailAddress("User1@gmail.com");
        user1.setSecurityAnswer("User1");
        user1.setUserPassword("Harsh@12345");

        String response = userService.resetPassword(user1.getUserEmailAddress(),user1.getSecurityAnswer(),user1.getUserPassword());

        assertEquals("Password reset done",response);

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());

    }

    @Test
    public void resetPasswordWithoutSameSecurityAnswerTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);

        User user1 = new User();
        user1.setId(1);
        user1.setUserEmailAddress("User1@gmail.com");
        user1.setSecurityAnswer("User3");
        user1.setUserPassword("Harsh@12345");

        String response = userService.resetPassword(user1.getUserEmailAddress(),user1.getSecurityAnswer(),user1.getUserPassword());

        assertEquals("Invalid Security Answer",response);

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());

    }

    @Test
    public void resetPasswordWithoutProperNewPasswordPatternTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        when(userRepository.findByUserEmailAddress(user.getUserEmailAddress())).thenReturn(user);

        User user1 = new User();
        user1.setId(1);
        user1.setUserEmailAddress("User1@gmail.com");
        user1.setSecurityAnswer("User1");
        user1.setUserPassword("Harsh12345");

        String response = userService.resetPassword(user1.getUserEmailAddress(),user1.getSecurityAnswer(),user1.getUserPassword());

        assertEquals("New Password must satisfy the requirements",response);

        verify(userRepository).findByUserEmailAddress(user.getUserEmailAddress());

    }

    @Test
    public void resetPasswordWithoutUserEmailTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        User user1 = new User();
        user1.setId(1);
        user1.setUserEmailAddress("User12@gmail.com");
        user1.setSecurityAnswer("User1");
        user1.setUserPassword("Harsh12345");

        String response = userService.resetPassword(user1.getUserEmailAddress(),user1.getSecurityAnswer(),user1.getUserPassword());

        assertEquals("User not found",response);

    }

    @Test

    public void deleteUserByIdTest()
    {
        User user=new User();
        user.setId(1);
        user.setUserName("Harsh1");
        user.setUserEmailAddress("User1@gmail.com");
        user.setUserPassword("Harsh@123");
        user.setSecurityAnswer("User1");

        doNothing().when(userRepository).deleteById(1);

        String result = userService.deleteUserById(1);

        assertEquals("User Information deleted successfully!", result);
        verify(userRepository).deleteById(1);
    }


}
