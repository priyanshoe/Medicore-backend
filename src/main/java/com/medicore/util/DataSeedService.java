package com.medicore.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.medicore.ENUM.Gender;
import com.medicore.ENUM.UserRole;
import com.medicore.entity.DoctorProfile;
import com.medicore.entity.PatientProfile;
import com.medicore.entity.User;
import com.medicore.repository.DoctorProfileRepository;
import com.medicore.repository.PatientProfileRepository;
import com.medicore.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DataSeedService {


	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final DoctorProfileRepository doctorProfileRepo;
	private final PatientProfileRepository patientProfileRepo;
	
	@Transactional
	public void seedUser() {
		
		if(userRepo.count() == 0) {
			List<User> users = new ArrayList<User>();
			
			// create admin
			User admin = new User();
			admin.setName("Admin generated");
			admin.setEmail("admin@medicore.com");
			admin.setPassword(passwordEncoder.encode("adminpass"));
			admin.setRole(UserRole.ADMIN);
			users.add(admin);

			
			// create doctor 1
			User doctor1 = new User();
			doctor1.setName("doctor1 generated");
			doctor1.setEmail("doctor1@medicore.com");
			doctor1.setPassword(passwordEncoder.encode("doctor1pass"));
			doctor1.setRole(UserRole.DOCTOR);
			users.add(doctor1);
			// create doctor profile
			DoctorProfile doctorProfile1 = new DoctorProfile();
			doctorProfile1.setUser(doctor1);
			doctorProfile1.setName(doctor1.getName());
			doctorProfile1.setAvailability("Mon, Wed, Fri - 11:00 - 18:00");
			doctorProfile1.setConsultationFee(1800.00);
			doctorProfile1.setExperience(5);
			doctorProfile1.setProfileImage("https://img.magnific.com/free-photo/female-doctor-hospital-with-stethoscope_23-2148827774.jpg?semt=ais_hybrid&w=740&q=80");
			doctorProfile1.setPhone("+54 945-578-39");
			doctorProfile1.setQualification("MBBS, MD");
			doctorProfile1.setSpecialization("Cardiology");
			
			// create doctor 2
			User doctor2 = new User();
			doctor2.setName("doctor2 generated");
			doctor2.setEmail("doctor2@medicore.com");
			doctor2.setPassword(passwordEncoder.encode("doctor2pass"));
			doctor2.setRole(UserRole.DOCTOR);
			users.add(doctor2);
			// create doctor profile
			DoctorProfile doctorProfile2 = new DoctorProfile();
			doctorProfile2.setUser(doctor2);
			doctorProfile2.setName(doctor2.getName());
			doctorProfile2.setAvailability("Mon, Wed, Fri - 11:00 - 18:00");
			doctorProfile2.setConsultationFee(1500.00);
			doctorProfile2.setExperience(3);
			doctorProfile2.setProfileImage("https://img.magnific.com/free-photo/portrait-experienced-professional-therapist-with-stethoscope-looking-camera_1098-19305.jpg");
			doctorProfile2.setPhone("+54 655-956-55");
			doctorProfile2.setQualification("MBBS");
			doctorProfile2.setSpecialization("General Medicine");
			
			
			// create patient 1
			User patient1 = new User();
			patient1.setName("patient1 generated");
			patient1.setEmail("patient1@medicore.com");
			patient1.setPassword(passwordEncoder.encode("patient1pass"));
			patient1.setRole(UserRole.PATIENT);
			users.add(patient1);
			// create patient profile
			PatientProfile patientProfile1 = new PatientProfile();
			patientProfile1.setUser(patient1);
			patientProfile1.setName(patient1.getName());
			patientProfile1.setPhone("+58 564-231-98");
			patientProfile1.setGender(Gender.MALE);
			patientProfile1.setAddress("124 Park Avenue, Metro City, New York");
			patientProfile1.setDateOfBirth(Date.valueOf("1998-05-09"));

			// create patient 2
			User patient2 = new User();
			patient2.setName("patient2 generated");
			patient2.setEmail("patient2@medicore.com");
			patient2.setPassword(passwordEncoder.encode("patient2pass"));
			patient2.setRole(UserRole.PATIENT);
			users.add(patient2);
			// create patient profile
			PatientProfile patientProfile2 = new PatientProfile();
			patientProfile2.setUser(patient2);
			patientProfile2.setName(patient2.getName());
			patientProfile2.setPhone("+42 659-445");
			patientProfile2.setGender(Gender.FEMALE);
			patientProfile2.setAddress("124 High Streets, Bungking Tower, London");
			patientProfile2.setDateOfBirth(Date.valueOf("2007-09-16"));
			
			
			// save users
			userRepo.saveAll(users);
			
			// save doctor profiles
			doctorProfileRepo.saveAll(Arrays.asList(doctorProfile1,doctorProfile2));
			
			// save patient profiles
			patientProfileRepo.saveAll(Arrays.asList(patientProfile1, patientProfile2));
		}
	};
}

