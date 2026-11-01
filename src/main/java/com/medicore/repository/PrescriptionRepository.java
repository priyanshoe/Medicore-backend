package com.medicore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

}
