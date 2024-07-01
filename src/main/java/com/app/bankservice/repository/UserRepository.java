package com.app.bankservice.repository;

import com.app.bankservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserFirstName(String firstName);
}
