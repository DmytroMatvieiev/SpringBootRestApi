package org.dmdev.springbootrestapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,
            unique = true,
            length = 64)
    private String email;
    @Column(nullable = false,
            length = 32)
    private String firstname;
    @Column(nullable = false,
            length = 32)
    private String lastname;
    @Column(nullable = false)
    private LocalDate birthdate;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private long number;
}
