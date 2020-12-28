package com.restaurant.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientGroupDTO {

	@NotNull(message = "Group id may not be empty or null")
	private Long id;
	@NotNull(message = "Group size may not be empty or null")
	public Integer size; // number of clients	
	private long tableId;

}
