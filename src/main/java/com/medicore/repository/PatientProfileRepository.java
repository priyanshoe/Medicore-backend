package com.medicore.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;


public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

	boolean existsByUser(User user);
	
	Optional<PatientProfile> findByUser(User user);
	
	List<PatientProfile> findAllByUser_IdIn(Collection<Long> userIds);
}
