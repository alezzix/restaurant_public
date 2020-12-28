package com.restaurant.converter;

import org.springframework.stereotype.Component;

import com.restaurant.dto.NewTableInsertDTO;
import com.restaurant.dto.TableDTO;
import com.restaurant.model.TableEntity;

@Component
public class TableConverter {

	public TableDTO convertEntityToDTO(TableEntity entity) {

		TableDTO tableDTO = new TableDTO(entity.getSize(), entity.getTotalFreeSeats(), entity.getId());
		tableDTO.setId(entity.getId());
		tableDTO.setTotalFreeSeats(entity.getTotalFreeSeats());

		return tableDTO;

	}

	public TableEntity convertDTOtoEntity(NewTableInsertDTO dto) {

		TableEntity tableEntity = new TableEntity();
		tableEntity.setSize(dto.getSize());
		tableEntity.setTotalFreeSeats(dto.getTotalFreeSeats());

		return tableEntity;

	}

}
