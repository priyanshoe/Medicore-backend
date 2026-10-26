package com.medicore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
