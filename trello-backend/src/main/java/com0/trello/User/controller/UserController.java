package com0.trello.User.controller;

import com0.trello.User.model.User;
import com0.trello.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(@RequestParam int id) {
        User user = userService.findUserById(id);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/loginInformationConfirmation")
        public ResponseEntity<User> loginConfirmation(@RequestParam String email, @RequestParam String password) {
        return userService.validateEmailPassword(email,password);
    }

    @PostMapping("/passwordReset")
    public String resetPassword(@RequestParam String email,@RequestParam String securityAnswer, @RequestParam String newPassword){
        return userService.resetPassword(email,securityAnswer,newPassword);
    }

    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return userService.createUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id) {

        return userService.deleteUserById(id);
    }
}
