package org.dmdev.springbootrestapi.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.dmdev.springbootrestapi.entities.User;
import org.dmdev.springbootrestapi.exceptions.IllegalBirthdate;
import org.dmdev.springbootrestapi.exceptions.IllegalDateRange;
import org.dmdev.springbootrestapi.models.ResponseModel;
import org.dmdev.springbootrestapi.models.UserModel;
import org.dmdev.springbootrestapi.repositories.UserDao;
import org.dmdev.springbootrestapi.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;

    public ResponseModel create(UserModel userModel) {
        User user = User.builder()
                .email(userModel.getEmail())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .birthdate(userModel.getBirthdate())
                .address(userModel.getAddress())
                .number(userModel.getNumber())
                .build();
        userDao.save(user);
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("User %s Created", user.getFirstname()))
                .build();
    }

    public ResponseModel update(UserModel userModel) {
        Optional<User> userOptional
                = userDao.findById(userModel.getId());
        if (userOptional.isPresent()) {
            User user = User.builder()
                    .email(userModel.getEmail())
                    .firstname(userModel.getFirstname())
                    .lastname(userModel.getLastname())
                    .birthdate(userModel.getBirthdate())
                    .address(userModel.getAddress())
                    .number(userModel.getNumber())
                    .build();
            userDao.save(user);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("User %s Updated", user.getFirstname()))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("User #%d Not Found", userModel.getId()))
                    .build();
        }
    }

    public ResponseModel updateEmail(Long id, String email) {
        Optional<User> userOptional
                = userDao.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(email);
            userDao.save(user);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("User %s Email Updated", user.getFirstname()))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("User #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel getAll() {
        List<User> users = userDao.findAll(Sort.by("id").descending());
        List<UserModel> userModels =
                users.stream().map(u ->
                        UserModel.builder()
                                .id(u.getId())
                                .email(u.getEmail())
                                .firstname(u.getFirstname())
                                .lastname(u.getLastname())
                                .address(u.getAddress())
                                .birthdate(u.getBirthdate())
                                .number(u.getNumber())
                                .build()
                ).toList();
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .data(userModels)
                .build();
    }

    public ResponseModel delete(Long id){
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            userDao.delete(user);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("User #%d Deleted", id))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("User #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel getInBirthRange(LocalDate from, LocalDate to){
        if(from.isAfter(to)){
            throw new IllegalDateRange("The start date cannot be later than the end date.");
        }
        List<User> users = userDao.findInBirthdateRange(from, to);
        List<UserModel> userModels =
                users.stream().map(u ->
                        UserModel.builder()
                                .id(u.getId())
                                .email(u.getEmail())
                                .firstname(u.getFirstname())
                                .lastname(u.getLastname())
                                .address(u.getAddress())
                                .birthdate(u.getBirthdate())
                                .number(u.getNumber())
                                .build()
                ).toList();
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .data(userModels)
                .build();
    }


}
