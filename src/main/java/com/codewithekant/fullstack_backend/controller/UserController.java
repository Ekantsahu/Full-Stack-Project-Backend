package com.codewithekant.fullstack_backend.controller;

import com.codewithekant.fullstack_backend.exception.UserNotFoundException;
import com.codewithekant.fullstack_backend.model.User;
import com.codewithekant.fullstack_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")

public class UserController {
   @Autowired
   private UserRepository userRepository;

   @PostMapping("/user")
   User newUser(@RequestBody User newUser){
       return userRepository.save(newUser);
   }
   @GetMapping("/users")
   List<User> getAllUsers(){
      return userRepository.findAll();
   }

   @GetMapping("/user/{id}")
   User getUserById(@PathVariable long id){
      return  userRepository.findById(id)
              .orElseThrow(()->new UserNotFoundException(id));
   }

   @PutMapping("/user/{id}")
   User updateUser(@RequestBody User newUser,@PathVariable Long id){
      return userRepository.findById(id)
              .map(user -> {
                 user.setUsername(newUser.getUsername());
                 user.setName(newUser.getName());
                 user.setEmail(newUser.getEmail());
                 return userRepository.save(user);
              }).orElseThrow(()->new UserNotFoundException(id));
   }

   @DeleteMapping("/user/{id}")
   String deleteUser(@PathVariable Long id){
      if(!userRepository.existsById(id)){
         throw new UserNotFoundException(id);
      }
      userRepository.deleteById(id);
      return "User with ID "+id+" has been deleted successfully";
   }
}
