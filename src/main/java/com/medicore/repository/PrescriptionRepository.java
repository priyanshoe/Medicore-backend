package com.medicore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.Appointment;
import com.medicore.entity.Prescription;
import com.medicore.entity.User;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

	List<Prescription> findAllByPatient(User patient);

	List<Prescription> findAllByDoctor(User doctor);

	boolean existsByAppointment(Appointment appointment);

}
