package com0.trello.User.service;

import com0.trello.User.model.User;
import com0.trello.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    User loggedInUser;

    public ResponseEntity<User> createUser(User user) {
        if(user.getUserName().isEmpty() || user.getUserName() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if(user.getUserPassword().isEmpty() || user.getUserPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if(user.getUserEmailAddress().isEmpty() || user.getUserEmailAddress() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if(user.getSecurityAnswer().isEmpty() || user.getSecurityAnswer() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if(!isValidPassword(user.getUserPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        if(findDuplicateUserByEmail(user.getUserEmailAddress())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
        userRepository.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);}

    public ResponseEntity<User> validateEmailPassword(String email, String password) {
        User user = findUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        if (!user.getUserPassword().equals(password)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
        loggedInUser=user;
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    public boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return Pattern.matches(passwordPattern,password);}

    public Boolean findDuplicateUserByEmail(String email) {
        List<User> existingUsers = userRepository.findAll();
        for (User existingUser : existingUsers) {
            if (existingUser.getUserEmailAddress().equals(email)) {
                return true;}
        }
        return false;}

    public User findUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        System.out.println("Successfully fetched user information " + user);
        return user;}

    public User findUserByEmail(String email) {
        return userRepository.findByUserEmailAddress(email);}

    public String updateUser(User user) {
        User user1 = userRepository.findById(user.getId()).orElse(null);
        if (user1 == null) {
            return "No User found with given Id";}
        user1.setUserEmailAddress(user.getUserEmailAddress());
        user1.setUserPassword(user.getUserPassword());
        user1.setUserName(user.getUserName());
        user1.setSecurityAnswer(user.getSecurityAnswer());
        userRepository.save(user1);
        return "User information updated successfully!";}

    public String deleteUserById(int id) {
        userRepository.deleteById(id);
        return "User Information deleted successfully!";}

    public String resetPassword(String email, String securityAnswer, String newPassword){
        User user = findUserByEmail(email);
        if(user == null) {
            return "User not found";}
        if (!user.getSecurityAnswer().equals(securityAnswer)) {
            return "Invalid Security Answer";}
        if(isValidPassword(newPassword)) {
            user.setUserPassword(newPassword);}
        else {return "New Password must satisfy the requirements";}
        updateUser(user);
        return "Password reset done";}}
