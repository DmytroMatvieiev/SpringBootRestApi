package org.dmdev.springbootrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.dmdev.springbootrestapi.models.validations.Adult;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {
    private Long id;
    @Email
    private String email;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Adult
    private LocalDate birthdate;
    @NotNull
    private String address;
    @NotNull
    private long number;
}
