package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.ClientGroupDTO;
import com.restaurant.dto.NewTableInsertDTO;
import com.restaurant.dto.NewlyArrivedClientGroupDTO;
import com.restaurant.dto.TableDTO;

public interface RestManager {
	
	 List<String> onArrive(NewlyArrivedClientGroupDTO newlyArrivedClientGroupDTO);
	 
	 List<String> onLeave(ClientGroupDTO dto);
	 
	 List<ClientGroupDTO> findAllGroups();
	 
	 List<TableDTO> findAllTables();	 
	 
	 //just for tests
	 List<TableDTO> inserTables(List<NewTableInsertDTO> listNewTableInsertDTO);

}
