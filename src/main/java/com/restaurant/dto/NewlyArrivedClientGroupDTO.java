package com.restaurant.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewlyArrivedClientGroupDTO {

	@NotNull(message = "Group size may not be empty or null")
	@Range(min = 1, message = "Group size may not be less than 1")
	public Integer size; // number of clients

}
