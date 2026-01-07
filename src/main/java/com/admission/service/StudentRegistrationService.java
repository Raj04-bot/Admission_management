package com.admission.service;

import com.admission.entity.StudentRegistration;
import com.admission.record.StudentRegistrationDto;

import jakarta.validation.Valid;

public interface StudentRegistrationService {

	StudentRegistration saveOrUpdate(@Valid StudentRegistrationDto dto);

	

}
