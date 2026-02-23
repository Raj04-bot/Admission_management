package com.admission.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
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
            studentRegistrationRepository.findByEmail(dto.getEmail())
						                    .filter(existing -> !Objects.equals(existing.getStudentRegistrationId(), dto.getStudentRegistrationId()))
						                    .ifPresent(e -> {
						                        throw new BusinessException("Email already registered");
						                    });

            // Mobile uniqueness
            studentRegistrationRepository.findByMobileNo(dto.getMobileNo())
						                    .filter(existing -> !Objects.equals(existing.getStudentRegistrationId(), dto.getStudentRegistrationId()))
						                    .ifPresent(e -> {
						                        throw new BusinessException("Mobile number already registered");
						                    });

            StudentRegistration student = Optional.ofNullable(dto.getStudentRegistrationId())
								                    .flatMap(studentRegistrationRepository::findById)
								                    .orElseGet(StudentRegistration::new);

            BeanUtils.copyProperties(dto, student);
            student.setDateOfBirth(Validation.parseDateOfBirth(dto.getDateOfBirth()));
            
            StudentRegistration saved = studentRegistrationRepository.save(student);
            
            Optional.ofNullable(dto.getStudentRegistrationId())
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
            log.warn("Exception Occurred at saveOrUpdate on StudentRegistrationServiceImpl: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            log.error("Exception Occurred at saveOrUpdate on StudentRegistrationServiceImpl: {}", ex);
            throw new ApplicationException("Unable to save student details");
        }
    }

	@Override
    public List<StudentRegistrationDto> getAllStudentData(Boolean isActive) {
    	List<StudentRegistration> students = new ArrayList<>();
    	List<StudentRegistrationDto> studentsDto = new ArrayList<>();
    	try {
    		students = isActive ? studentRegistrationRepository.findAllByIsActive(isActive) :studentRegistrationRepository.findAll();
    		
    		studentsDto = students.stream()
				    		        .map(student -> {
				    		            StudentRegistrationDto dto = new StudentRegistrationDto();
				    		            BeanUtils.copyProperties(student, dto);
				    		            return dto;
				    		        })
				    		        .collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Exception Occurred at getAllStudentData on StudentRegistrationServiceImpl: {}", isActive, e);
	        throw new ServiceException("Unable to fetch student data. Please try again later.");
		}
        return studentsDto;
    }



}
