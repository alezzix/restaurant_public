package com.restaurant.dto;

import lombok.Data;

@Data
public class TableDTO {
	
	public TableDTO(int size, int totalFreeSeats, Long id) {
		this.size = size;
		this.totalFreeSeats = totalFreeSeats;
		this.id = id;
	}


	private Long id;
	private final int size;	
	private int totalFreeSeats;

}
