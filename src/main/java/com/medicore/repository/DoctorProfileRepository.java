package com.medicore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.DoctorProfile;
import com.medicore.entity.User;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {

	boolean existsByUser(User user);

}
