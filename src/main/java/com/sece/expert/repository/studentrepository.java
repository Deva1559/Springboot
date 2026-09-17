package com.sece.expert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sece.expert.entity.studententity;

@Repository 
public interface studentrepository extends JpaRepository<studententity, Integer> {
    Optional<studententity> findByUsername(String username);
}
