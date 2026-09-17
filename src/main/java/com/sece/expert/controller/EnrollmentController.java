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
import com.sece.expert.entity.Enrollment;
import com.sece.expert.entity.studententity;
import com.sece.expert.repository.CourseRepository;
import com.sece.expert.repository.EnrollmentRepository;
import com.sece.expert.repository.studentrepository;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private studentrepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // GET - Get all enrollments
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return ResponseEntity.ok(enrollments);
    }

    // GET - Get enrollment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable int id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            return ResponseEntity.ok(enrollment.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST - Create new enrollment
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEnrollment(@RequestBody Enrollment enrollment) {
        Map<String, Object> response = new HashMap<>();

        // Check if student exists
        Optional<studententity> student = studentRepository.findById(enrollment.getStudentId());
        if (!student.isPresent()) {
            response.put("status", "error");
            response.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if course exists
        Optional<Course> course = courseRepository.findById(enrollment.getCourseId());
        if (!course.isPresent()) {
            response.put("status", "error");
            response.put("message", "Course not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if student is already enrolled in the same course
        Optional<Enrollment> existingEnrollment = enrollmentRepository
                .findByStudentIdAndCourseId(enrollment.getStudentId(), enrollment.getCourseId());
        if (existingEnrollment.isPresent()) {
            response.put("status", "error");
            response.put("message", "Student already enrolled");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Save the enrollment
        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        response.put("status", "success");
        response.put("message", "Enrollment created successfully");
        response.put("enrollment", savedEnrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT - Update existing enrollment
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateEnrollment(@PathVariable int id,
            @RequestBody Enrollment enrollmentDetails) {
        Map<String, Object> response = new HashMap<>();

        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);
        if (enrollment.isPresent()) {
            Enrollment existingEnrollment = enrollment.get();
            existingEnrollment.setStudentId(enrollmentDetails.getStudentId());
            existingEnrollment.setCourseId(enrollmentDetails.getCourseId());
            existingEnrollment.setEnrollmentDate(enrollmentDetails.getEnrollmentDate());
            existingEnrollment.setStatus(enrollmentDetails.getStatus());

            Enrollment updatedEnrollment = enrollmentRepository.save(existingEnrollment);
            response.put("status", "success");
            response.put("message", "Enrollment updated successfully");
            response.put("enrollment", updatedEnrollment);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Enrollment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // DELETE - Delete enrollment
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEnrollment(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();

        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            response.put("status", "success");
            response.put("message", "Enrollment deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Enrollment not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
