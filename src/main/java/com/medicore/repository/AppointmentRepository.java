package com.medicore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.Appointment;
import com.medicore.entity.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findAllByDoctor(User doctor);

	List<Appointment> findAllByPatient(User authUser);
}
