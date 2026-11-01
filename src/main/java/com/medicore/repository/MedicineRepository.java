package com.medicore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicore.entity.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

}
