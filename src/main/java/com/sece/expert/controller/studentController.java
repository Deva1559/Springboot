package com.sece.expert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sece.expert.entity.studententity;
import com.sece.expert.repository.studentrepository;

@RestController
public class studentController {

    @Autowired
    studentrepository repository;

    // GET - Get all students
    @GetMapping("/student")
    public List<studententity> getStudent() {
        return repository.findAll();
    }

    // POST - Add multiple students
    @PostMapping("/student")
    public List<studententity> addStudents(
            @RequestBody List<studententity> students) {

        return repository.saveAll(students);
    }

    // PUT - Update one student
    @PutMapping("/student")
    public List<studententity> updateStudents(
        @RequestBody List<studententity> students) {

    for (studententity student : students) {

        studententity oldStudent =
                repository.findById(student.getId()).orElse(null);

        if (oldStudent != null) {
            oldStudent.setName(student.getName());
            oldStudent.setDepartment(student.getDepartment());
            oldStudent.setAge(student.getAge());

            repository.save(oldStudent);
        }
    }

    return repository.findAll();
    }

    // DELETE - Delete one student
    @DeleteMapping("/student/{id}")
    public String deleteStudent(@PathVariable int id) {

        studententity student =
                repository.findById(id).orElse(null);

        if (student != null) {
            repository.deleteById(id);
            return "Student deleted successfully!";
        }

        return "Student not found!";
    }

    // DELETE - Delete multiple students
    @DeleteMapping("/student")
    public String deleteStudents(@RequestBody List<Integer> ids) {

        repository.deleteAllById(ids);

        return "Students deleted successfully!";
    }
}