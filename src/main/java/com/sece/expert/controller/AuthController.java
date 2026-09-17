package com.sece.expert.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sece.expert.entity.studententity;
import com.sece.expert.repository.studentrepository;

@RestController
public class AuthController {

    @Autowired
    studentrepository repository;

    // Register endpoint
    @PostMapping("/auth/register")
    public Map<String, String> register(@RequestBody studententity student) {
        Map<String, String> response = new HashMap<>();

        // Check if username already exists
        Optional<studententity> existingStudent = repository.findByUsername(student.getUsername());

        if (existingStudent.isPresent()) {
            response.put("status", "error");
            response.put("message", "Username already exists");
            return response;
        }

        // Save the new student
        repository.save(student);
        response.put("status", "success");
        response.put("message", "Student registered successfully");
        return response;
    }

    // Login endpoint
    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        Map<String, String> response = new HashMap<>();

        String username = credentials.get("username");
        String password = credentials.get("password");

        // Check if username exists
        Optional<studententity> student = repository.findByUsername(username);

        if (!student.isPresent()) {
            response.put("status", "error");
            response.put("message", "Username not found");
            return response;
        }

        // Check if password is correct
        if (!student.get().getPassword().equals(password)) {
            response.put("status", "error");
            response.put("message", "Invalid Password");
            return response;
        }

        // Login successful
        response.put("status", "success");
        response.put("message", "Login Successful");
        response.put("studentName", student.get().getName());
        return response;
    }
}
