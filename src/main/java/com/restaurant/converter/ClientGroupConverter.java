package com.restaurant.converter;

import org.springframework.stereotype.Component;

import com.restaurant.dto.ClientGroupDTO;
import com.restaurant.dto.NewlyArrivedClientGroupDTO;
import com.restaurant.model.ClientsGroup;

@Component
public class ClientGroupConverter {

	public ClientGroupDTO convertEntityToDTO(ClientsGroup entity) {

		ClientGroupDTO clientGroupDTO = new ClientGroupDTO(entity.getId(), entity.getSize(), entity.getTableId());	

		return clientGroupDTO;

	}

	public ClientsGroup convertNewlyArrivedDTOtoEntity(NewlyArrivedClientGroupDTO dto) {

		ClientsGroup clientsGroup = new ClientsGroup();
		clientsGroup.setSize(dto.getSize());

		return clientsGroup;

	}
	
	public ClientsGroup convertClientGroupDTOtoEntity(ClientGroupDTO dto) {

		ClientsGroup clientsGroup = new ClientsGroup();
		clientsGroup.setSize(dto.getSize());
		clientsGroup.setTableId(dto.getTableId());

		return clientsGroup;

	}

}
