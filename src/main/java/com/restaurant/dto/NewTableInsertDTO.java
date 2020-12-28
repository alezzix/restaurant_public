package com.restaurant.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewTableInsertDTO {

	@NotNull(message= "Table size may not be empty or null")
	@Range(min = 2, message= "Table size may not be less than 2")
	private Integer size; 
	@NotNull(message= "Table totalFreeSeats may not be empty or null")
	@Range(min = 2, message= "Table totalFreeSeats may less than 1")
	private Integer totalFreeSeats;

}
