package org.dmdev.springbootrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String email;
    private String firstname;
    private String lastname;
    @Adult
    private LocalDate birthdate;
    private String address;
    private long number;
}
