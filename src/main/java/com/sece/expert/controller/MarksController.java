package com.sece.expert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sece.expert.entity.Course;
import com.sece.expert.entity.Marks;
import com.sece.expert.entity.studententity;
import com.sece.expert.repository.CourseRepository;
import com.sece.expert.repository.MarksRepository;
import com.sece.expert.repository.studentrepository;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private studentrepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // GET - Get all marks
    @GetMapping
    public ResponseEntity<List<Marks>> getAllMarks() {
        List<Marks> marks = marksRepository.findAll();
        return ResponseEntity.ok(marks);
    }

    // GET - Get marks by ID
    @GetMapping("/{id}")
    public ResponseEntity<Marks> getMarksById(@PathVariable int id) {
        Optional<Marks> marks = marksRepository.findById(id);
        if (marks.isPresent()) {
            return ResponseEntity.ok(marks.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST - Create new marks record
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMarks(@RequestBody Marks marks) {
        Map<String, Object> response = new HashMap<>();

        // Check if student exists
        Optional<studententity> student = studentRepository.findById(marks.getStudentId());
        if (!student.isPresent()) {
            response.put("status", "error");
            response.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if course exists
        Optional<Course> course = courseRepository.findById(marks.getCourseId());
        if (!course.isPresent()) {
            response.put("status", "error");
            response.put("message", "Course not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validate marks are not negative
        if (marks.getMarks() < 0) {
            response.put("status", "error");
            response.put("message", "Marks cannot be negative");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Validate marks are not greater than totalMarks
        if (marks.getMarks() > marks.getTotalMarks()) {
            response.put("status", "error");
            response.put("message", "Marks cannot be greater than total marks");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Save the marks record
        Marks savedMarks = marksRepository.save(marks);
        response.put("status", "success");
        response.put("message", "Marks recorded successfully");
        response.put("marks", savedMarks);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT - Update existing marks
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMarks(@PathVariable int id, @RequestBody Marks marksDetails) {
        Map<String, Object> response = new HashMap<>();

        Optional<Marks> marks = marksRepository.findById(id);
        if (marks.isPresent()) {
            Marks existingMarks = marks.get();

            // Validate marks are not negative
            if (marksDetails.getMarks() < 0) {
                response.put("status", "error");
                response.put("message", "Marks cannot be negative");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Validate marks are not greater than totalMarks
            if (marksDetails.getMarks() > marksDetails.getTotalMarks()) {
                response.put("status", "error");
                response.put("message", "Marks cannot be greater than total marks");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            existingMarks.setStudentId(marksDetails.getStudentId());
            existingMarks.setCourseId(marksDetails.getCourseId());
            existingMarks.setExamName(marksDetails.getExamName());
            existingMarks.setMarks(marksDetails.getMarks());
            existingMarks.setTotalMarks(marksDetails.getTotalMarks());

            Marks updatedMarks = marksRepository.save(existingMarks);
            response.put("status", "success");
            response.put("message", "Marks updated successfully");
            response.put("marks", updatedMarks);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Marks record not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // DELETE - Delete marks record
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMarks(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();

        if (marksRepository.existsById(id)) {
            marksRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "Marks record deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Marks record not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
