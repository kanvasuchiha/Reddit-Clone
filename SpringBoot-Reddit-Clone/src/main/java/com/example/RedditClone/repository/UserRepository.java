package com.example.RedditClone.repository;

import com.example.RedditClone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
