package com.designthink.controller;

import com.designthink.domain.User;
import com.designthink.service.UserService;
import com.designthink.util.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    public static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    /**
     * Injecting service dependency to access the dao and perform the business logic
     */
    @Autowired
    UserService userService;

    /**
     * This method return all list of user available.
     * @return List<User>
     */

    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllBy();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * This method return the persisted user for the given ID
     * @param id
     * @return
     */

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        User user = userService.findById(id);
        if (user == null) {
            logger.error("User with given id {} not found.", id);
            return new ResponseEntity(new CustomException("User with given id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * This method is used to add the new user in the system
     * @param user
     * @param ucBuilder
     * @return
     */

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userService.existsById(user.getId())) {
            return new ResponseEntity(new CustomException("Unable to add the user. A User with name " +
                    user.getName() + " already exist."), HttpStatus.CONFLICT);
        }
        User persistedUser = userService.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(persistedUser.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    /**
     * This method update the user for the give user ID
     * @param id
     * @param user
     * @return The updated user
     */

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {

        User currentUser = userService.findById(id);

        if (currentUser == null) {
            logger.error(" User with id {} not found.", id);
            return new ResponseEntity(new CustomException("User not found with User id " + id + " Please provide a valid user id"),
                    HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        return new ResponseEntity<User>(userService.save(currentUser), HttpStatus.OK);
    }

    /**
     * This method is delete the user for the give ID
     * @param id
     * @return
     */

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomException("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method delete all user form the repository
     * @return ResponseEntity<User>
     */

    @DeleteMapping("/users")
    public ResponseEntity<User> deleteAllUsers() {
        logger.info("Deleting All Users");
        userService.deleteAll();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

}
    
