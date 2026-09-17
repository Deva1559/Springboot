package com.sece.expert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int markId;

    private int studentId;

    private int courseId;

    private String examName;

    private int marks;

    private int totalMarks;

    // Default constructor
    public Marks() {
    }

    // Parameterized constructor
    public Marks(int studentId, int courseId, String examName, int marks, int totalMarks) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.examName = examName;
        this.marks = marks;
        this.totalMarks = totalMarks;
    }

    // Getters and Setters

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
}
