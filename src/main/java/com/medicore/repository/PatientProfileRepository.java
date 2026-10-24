package com.medicore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;


public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

	boolean existsByUser(User user);
}
