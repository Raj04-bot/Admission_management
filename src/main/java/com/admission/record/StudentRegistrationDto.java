package com.admission.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record StudentRegistrationDto(
		
		Long studentRegistrationId,
		@NotBlank String program,
	    @NotBlank String firstName,
	    String middleName,
	    @NotBlank String lastName,

	    @Pattern(regexp = "^[6-9]\\d{9}$",message = "Invalid mobile number")
	    String mobileNo,

	    @Email(message = "Invalid email")
	    String email,


	    @NotNull String dateOfBirth
	    
		) {}
