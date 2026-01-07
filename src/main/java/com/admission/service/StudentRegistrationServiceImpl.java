package com.admission.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.admission.entity.StudentRegistration;
import com.admission.exception.ApplicationException;
import com.admission.exception.BusinessException;
import com.admission.exception.ResourceNotFoundException;
import com.admission.record.StudentRegistrationDto;
import com.admission.repository.StudentRegistrationRepository;
import com.admission.utils.ApplicationConstants;
import com.admission.utils.UserCreationUtil;
import com.admission.utils.Validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentRegistrationServiceImpl implements StudentRegistrationService {
	
	private final StudentRegistrationRepository studentRegistrationRepository;
	private final UserCreationUtil userCreationUtil;
	
	@Override
	@Transactional
    public StudentRegistration saveOrUpdate(StudentRegistrationDto dto) {

        try {
        	// Email uniqueness
            studentRegistrationRepository.findByEmail(dto.email())
						                    .filter(existing -> !Objects.equals(existing.getStudentRegistrationId(), dto.studentRegistrationId()))
						                    .ifPresent(e -> {
						                        throw new BusinessException("Email already registered");
						                    });

            // Mobile uniqueness
            studentRegistrationRepository.findByMobileNo(dto.mobileNo())
						                    .filter(existing -> !Objects.equals(existing.getStudentRegistrationId(), dto.studentRegistrationId()))
						                    .ifPresent(e -> {
						                        throw new BusinessException("Mobile number already registered");
						                    });

            StudentRegistration student = Optional.ofNullable(dto.studentRegistrationId())
								                    .flatMap(studentRegistrationRepository::findById)
								                    .orElseGet(StudentRegistration::new);

            BeanUtils.copyProperties(dto, student);
            student.setDateOfBirth(Validation.parseDateOfBirth(dto.dateOfBirth()));
            
            StudentRegistration saved = studentRegistrationRepository.save(student);
            
            Optional.ofNullable(dto.studentRegistrationId())
			            .ifPresent(id ->
			                userCreationUtil.createUserWithRole(
			                    student.getEmail(),
			                    student.getFirstName(),
			                    student.getMobileNo(),
			                    student.getEmail(),
			                    student.getMobileNo(),
			                    ApplicationConstants.ROLE_STUDENT,
			                    id
			                )
			            );


            return saved;

        } catch (BusinessException | ResourceNotFoundException ex) {
            log.warn("Validation error while saving student: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error while saving student", ex);
            throw new ApplicationException("Unable to save student details");
        }
    }

//    public List<StudentRegistration> getAll() {
//        return repository.findAll();
//    }
//
//    public StudentRegistration getById(Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Student not found"));
//    }
//
//    public StudentRegistration update(Long id, StudentRegistrationDto dto) {
//
//        StudentRegistration student = getById(id);
//
//        student.setProgram(dto.program());
//        student.setFirstName(dto.firstName());
//        student.setMiddleName(dto.middleName());
//        student.setLastName(dto.lastName());
//        student.setMobileNo(dto.mobileNo());
//        student.setEmail(dto.email());
//        student.setDateOfBirth(dto.dateOfBirth());
//
//        return repository.save(student);
//    }
//
//    public void delete(Long id) {
//        repository.deleteById(id);
//    }

}
