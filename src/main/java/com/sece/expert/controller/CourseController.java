package com.sece.expert.controller;

import java.util.List;
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
import com.sece.expert.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // GET - Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }

    // GET - Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST - Create new course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    // PUT - Update existing course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course courseDetails) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            Course existingCourse = course.get();
            existingCourse.setCourseName(courseDetails.getCourseName());
            existingCourse.setDepartment(courseDetails.getDepartment());
            existingCourse.setDuration(courseDetails.getDuration());
            existingCourse.setFees(courseDetails.getFees());
            Course updatedCourse = courseRepository.save(existingCourse);
            return ResponseEntity.ok(updatedCourse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE - Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
