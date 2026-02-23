package com.admission.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admission.entity.StudentRegistration;
import com.admission.record.StudentRegistrationDto;
import com.admission.service.StudentRegistrationService;
import com.admission.utils.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentRegistrationController {

    private final StudentRegistrationService studentRegistrationService;

    @PostMapping("/student")
    public ResponseEntity<ApiResponse<StudentRegistration>> saveOrUpdate(@Valid @RequestBody StudentRegistrationDto dto) {

        StudentRegistration student = studentRegistrationService.saveOrUpdate(dto);

        String message = dto.getStudentRegistrationId() == null ? "Student registered successfully": "Student updated successfully";

        return ResponseEntity.ok(ApiResponse.success(student, message));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<StudentRegistrationDto>>> getAllStudentData() {
        return ResponseEntity.ok(ApiResponse.success(studentRegistrationService.getAllStudentData(false), "Student data fetched successfully"));
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StudentRegistration> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(studentRegistrationService.getById(id));
//    }

    
}

