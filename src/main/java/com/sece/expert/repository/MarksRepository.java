package com.sece.expert.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sece.expert.entity.Marks;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {
    List<Marks> findByStudentId(int studentId);
    List<Marks> findByCourseId(int courseId);
    List<Marks> findByStudentIdAndCourseId(int studentId, int courseId);
}
