package com.medicore.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.DoctorProfile;
import com.medicore.entity.User;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {

	boolean existsByUser(User user);

	Optional<DoctorProfile> findByUser(User user);

	List<DoctorProfile> findAllByUser_IdIn(Collection<Long> userIds);

}
