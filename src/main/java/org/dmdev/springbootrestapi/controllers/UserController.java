package org.dmdev.springbootrestapi.controllers;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.dmdev.springbootrestapi.models.ResponseModel;
import org.dmdev.springbootrestapi.models.UserModel;
import org.dmdev.springbootrestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseModel> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseModel> create(@RequestBody @Valid UserModel userModel) {
        return new ResponseEntity<>(userService.create(userModel), HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<ResponseModel> update( @RequestBody @Valid UserModel userModel){
        return new ResponseEntity<>(userService.update(userModel), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ResponseModel> updateEmail(@PathVariable Long id, @RequestBody String email){
        return new ResponseEntity<>(userService.updateEmail(id, email), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseModel> delete(@PathVariable Long id){
        return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/users/range")
    public ResponseEntity<ResponseModel> getInBirthRange(@RequestParam LocalDate from,
                                                         @RequestParam LocalDate to){
        return new ResponseEntity<>(userService.getInBirthRange(from,to),HttpStatus.OK);
    }
}
