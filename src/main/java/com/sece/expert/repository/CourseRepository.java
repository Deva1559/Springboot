package com.sece.expert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sece.expert.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
