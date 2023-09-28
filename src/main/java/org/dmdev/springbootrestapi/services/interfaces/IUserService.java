package org.dmdev.springbootrestapi.services.interfaces;

import org.dmdev.springbootrestapi.models.ResponseModel;
import org.dmdev.springbootrestapi.models.UserModel;

import java.time.LocalDate;

public interface IUserService {
    ResponseModel create(UserModel userModel);
    ResponseModel update(UserModel userModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
    ResponseModel getInBirthRange(LocalDate from, LocalDate to);

    ResponseModel updateEmail(Long id, String email);
}
