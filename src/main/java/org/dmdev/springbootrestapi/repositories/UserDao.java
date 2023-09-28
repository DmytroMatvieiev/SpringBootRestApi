package org.dmdev.springbootrestapi.repositories;

import org.dmdev.springbootrestapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u Where u.birthdate > :from and u.birthdate < :to")
    public List<User> findInBirthdateRange(@Param("from") LocalDate from,
                                               @Param("to") LocalDate to);
}

