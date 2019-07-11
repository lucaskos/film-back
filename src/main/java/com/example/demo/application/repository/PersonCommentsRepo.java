package com.example.demo.application.repository;

import com.example.demo.application.model.comments.PersonComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonCommentsRepo extends JpaRepository<PersonComment, Long> {
}
