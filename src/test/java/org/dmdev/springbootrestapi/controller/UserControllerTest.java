package org.dmdev.springbootrestapi.controller;

import jakarta.validation.ValidationException;
import org.dmdev.springbootrestapi.controllers.UserController;
import org.dmdev.springbootrestapi.entities.User;
import org.dmdev.springbootrestapi.exceptions.IllegalDateRangeException;
import org.dmdev.springbootrestapi.exceptions.UserExceptionHandler;
import org.dmdev.springbootrestapi.models.ResponseModel;
import org.dmdev.springbootrestapi.models.UserModel;
import org.dmdev.springbootrestapi.repositories.UserDao;
import org.dmdev.springbootrestapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDate;
import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    private static final User USER_EXAMPLE = User.builder()
            .id(1L)
            .firstname("Dima")
            .lastname("Matvieiev")
            .email("best@email.com")
            .birthdate(LocalDate.of(2004, 2, 24))
            .build();
    private static final UserModel USER_MODEL_EXAMPLE = UserModel.builder()
            .id(1L)
            .firstname("Dima")
            .lastname("Matvieiev")
            .email("best@email.com")
            .birthdate(LocalDate.of(2004, 2, 24))
            .build();

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private UserService service;

    @MockBean
    private UserDao repo;

    @Autowired
    UserController userController;


    @Test
    public void getAll_whenListOfUserReturned_thenCorrect() throws Exception {

        List<User> userList = Collections.singletonList(USER_EXAMPLE);

        when(repo.findAll((Sort) any())).thenReturn(userList);

        var rm = userController.getAll().getBody();

        assertNotNull(rm);
        assertEquals(Collections.singletonList(USER_MODEL_EXAMPLE), rm.getData());
    }

    @Test
    public void createUser_whenResponseModelReturned_thenCorrect() {
        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("User %s Created", USER_EXAMPLE.getFirstname()))
                .build();
        when(service.create(USER_MODEL_EXAMPLE)).thenReturn(rm);

        var re = userController.create(USER_MODEL_EXAMPLE);

        assertEquals(rm, re.getBody());
    }

    @Test
    public void createYoungUser_whenIllegalBirthdateExceptionThrows_thenCorrect() {
        final UserModel YOUNG_USER_MODEL_EXAMPLE = UserModel.builder()
                .id(1L)
                .email("12312")
                .birthdate(LocalDate.of(2010, 12, 12))
                .build();

        ResponseModel rm = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message("Users under the age of 18 are not allowed to register")
                .build();
//        when(service.create(YOUNG_USER_MODEL_EXAMPLE)).thenReturn(rm);

        assertThrows(ValidationException.class, () ->
            userController.create(YOUNG_USER_MODEL_EXAMPLE)
        );


    }

    @Test
    public void updateUser_whenUserSaved_thenCorrect() {
        when(repo.findById(1L)).thenReturn(Optional.of(USER_EXAMPLE));
        when(repo.save(USER_EXAMPLE)).thenReturn(USER_EXAMPLE);

        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("User %s Updated", USER_EXAMPLE.getFirstname()))
                .build();

        var re = userController.update(USER_MODEL_EXAMPLE);

        assertEquals(rm, re.getBody());
    }

    @Test
    public void updateNotExistUser_whenUserNotFount_thenCorrect() {
        when(repo.findById(1L)).thenReturn(Optional.empty());
        when(repo.save(USER_EXAMPLE)).thenReturn(USER_EXAMPLE);

        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.FAIL_STATUS)
                .message(String.format("User #%d Not Found", 1L))
                .build();

        var re = userController.update(USER_MODEL_EXAMPLE);


        assertEquals(rm, re.getBody());
    }

    @Test
    public void updateEmail_whenResponseModelReturned_thenCorrect(){
        String newEmail = "new@email.com";
        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("User %s Email Updated", USER_EXAMPLE.getFirstname()))
                .build();
        User newUser = User.builder()
                .id(1L)
                .firstname("Dima")
                .lastname("Matvieiev")
                .email("best@email.com")
                .birthdate(LocalDate.of(2004, 2, 24))
                .build();

        when(repo.findById(1L)).thenReturn(Optional.of(newUser));
        when(repo.save(newUser)).thenReturn(newUser);

        var re = userController.updateEmail(1L, newEmail);

        assertEquals(rm, re.getBody());
    }

    @Test
    public void delete_whenUserDeleted_thenCorrect() {
        when(repo.findById(1L)).thenReturn(Optional.of(USER_EXAMPLE));
        doNothing().when(repo).delete(USER_EXAMPLE);

        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("User #%d Deleted", 1L))
                .build();

        var re = userController.delete(1L);

        assertEquals(rm, re.getBody());
    }

    @Test
    public void deleteNotExistUser_whenUserNotFound_thenCorrect() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        ResponseModel rm = ResponseModel.builder()
                .status(ResponseModel.FAIL_STATUS)
                .message(String.format("User #%d Not Found", 2L))
                .build();

        var re = userController.delete(2L);

        assertEquals(rm, re.getBody());
    }

    @Test
    public void getInBirthRange_whenListOfUser_thenCorrect() {
        LocalDate from = LocalDate.of(1994, 12, 12);
        LocalDate to = LocalDate.of(2000, 12, 12);

        when(repo.findInBirthdateRange(from, to)).thenReturn(Collections.singletonList(USER_EXAMPLE));

        var re = userController.getInBirthRange(from, to);

        assertNotNull(re.getBody());
        assertEquals(Collections.singletonList(USER_MODEL_EXAMPLE), re.getBody().getData());
    }

    @Test
    public void getInBirthRange_whenValidationExceptionThrows_thenCorrect() {
        ResponseModel rm = ResponseModel.builder()
                .status("Bad Request: " + HttpStatus.BAD_REQUEST.value())
                .message("The start date cannot be later than the end date.")
                .build();
        LocalDate to = LocalDate.of(1994, 12, 12);
        LocalDate from = LocalDate.of(2000, 12, 12);

        assertThrows(IllegalDateRangeException.class, () -> userController.getInBirthRange(from, to));


    }

}
