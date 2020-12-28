package com.restaurant.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.converter.ClientGroupConverter;
import com.restaurant.converter.TableConverter;
import com.restaurant.dto.ClientGroupDTO;
import com.restaurant.dto.NewTableInsertDTO;
import com.restaurant.dto.NewlyArrivedClientGroupDTO;
import com.restaurant.dto.TableDTO;
import com.restaurant.model.ClientsGroup;
import com.restaurant.model.TableEntity;
import com.restaurant.repository.ClientGroupRepository;
import com.restaurant.repository.TableRepository;

@Service
public class RestManagerImpl implements RestManager {

	@Autowired
	private ClientGroupRepository clientGroupRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private ClientGroupConverter clientGroupConverter;

	@Autowired
	private TableConverter tableConverter;

	private static final String PUT_GROUP = "Put group ";
	private static final String WITH_ID = " with id: ";
	private static final String AND_SIZE = " and size: ";
	private static final String OND_THE_TAB = " on the table ";
	private static final String THERE_ARE_NO_TABLE_OR_SEATS_ARRIVE = "There is no empty table or seats for the newly arrived group.";
	private static final String THERE_ARE_NO_TABLE_OR_SEATS_LEAVE = "There is no empty table or seats for any groups in the queue.";
	

	// new client(s) show up
	@Override
	public List<String> onArrive(NewlyArrivedClientGroupDTO newlyArrivedClientGroupDTO) {
		List<String> listResults = new ArrayList<String>();
		List<TableEntity> listFreeTables = tableRepository.findAllFreeOrderByTotalFreeSeatsAsc();

		ClientsGroup clientsGroup = clientGroupRepository
				.save(clientGroupConverter.convertNewlyArrivedDTOtoEntity(newlyArrivedClientGroupDTO));

		// find free table
		listResults.addAll(verifyFreeTableOrFreeSeatsToGroup(Arrays.asList(clientsGroup), listFreeTables));
		if (listResults.isEmpty()) {
			// find free seats in occupied table
			List<TableEntity> listOccupiedTables = tableRepository.findAllOccupiedOrderByTotalFreeSeatsAsc();
			listResults.addAll(verifyFreeTableOrFreeSeatsToGroup(Arrays.asList(clientsGroup), listOccupiedTables));
		}

		if (listResults.isEmpty()) {
			listResults.add(THERE_ARE_NO_TABLE_OR_SEATS_ARRIVE);
		}

		return listResults;
	}

	// client(s) leave, either served or simply abandoning the queue
	@Override
	public List<String> onLeave(ClientGroupDTO dto) {
		List<String> listResults = new ArrayList<String>();
		if (dto.getTableId() != 0) {
			TableEntity tableEntity = updateFreeSeats(dto);
			List<ClientsGroup> listGroups = clientGroupRepository.findByTableIdOrderByIdAsc(0);
			List<TableEntity> listFreeTables = tableRepository.findAllFreeOrderByTotalFreeSeatsAsc();

			if (tableEntity.getSize() == tableEntity.getTotalFreeSeats()) {
				// find free table
				listResults.addAll(verifyFreeTableOrFreeSeatsToGroup(listGroups, listFreeTables));
			} else {
				// find free seats in occupied table
				List<TableEntity> listOccupiedTables = tableRepository.findAllOccupiedOrderByTotalFreeSeatsAsc();
				listResults.addAll(verifyFreeTableOrFreeSeatsToGroup(listGroups, listOccupiedTables));
			}
		}
		Optional<ClientsGroup> clientsGroup = clientGroupRepository.findById(dto.getId());
		if (clientsGroup.isEmpty()) {
			clientGroupRepository.delete(clientsGroup.get());
		}

		if (listResults.isEmpty()) {
			listResults.add(THERE_ARE_NO_TABLE_OR_SEATS_LEAVE);
		}

		return listResults;

	}

	private List<String> verifyFreeTableOrFreeSeatsToGroup(List<ClientsGroup> listGroups, List<TableEntity> listTables) {

		List<String> listString = new ArrayList<String>();
		for (ClientsGroup group : listGroups) {
			for (TableEntity tableEntity : listTables) {
				if (tableEntity.getTotalFreeSeats() >= group.getSize()) {
					group.setTableId(tableEntity.getId());
					clientGroupRepository.save(group);
					tableEntity.setTotalFreeSeats(tableEntity.getTotalFreeSeats() - group.getSize());
					tableRepository.save(tableEntity);
					listString.add(PUT_GROUP + WITH_ID + group.getId() + AND_SIZE + group.getSize() + OND_THE_TAB
							+ WITH_ID + tableEntity.getId() + AND_SIZE + tableEntity.getSize());
					break;
				}

			}
		}
		return listString;
	}


	private TableEntity updateFreeSeats(ClientGroupDTO dto) {
		Optional<TableEntity> tableOptional = tableRepository.findById(dto.getTableId());
		if (tableOptional.isPresent()) {
			TableEntity tableEntity = tableOptional.get();
			tableEntity.setTotalFreeSeats(dto.size + tableEntity.getTotalFreeSeats());
			tableRepository.save(tableEntity);
			return tableEntity;
		}
		return null;
	}

	// return table where a given client group is seated,
	// or null if it is still queuing or has already left
	public TableDTO lookup(ClientGroupDTO clientGroupDTO) {
		Long tableId = findTableIdByGroup(clientGroupDTO);
		if (tableId != 0) {
			return findTableById(tableId);
		} else {
			return null;
		}

	}

	private long findTableIdByGroup(ClientGroupDTO clientGroupDTO) {
		Optional<ClientsGroup> clientsGroupOptional = clientGroupRepository.findById(clientGroupDTO.getId());
		if (clientsGroupOptional.isPresent()) {
			return clientsGroupOptional.get().getTableId();
		}
		return 0;
	}

	private TableDTO findTableById(Long tableId) {
		Optional<TableEntity> tableOptional = tableRepository.findById(tableId);
		if (tableOptional.isPresent()) {
			return tableConverter.convertEntityToDTO(tableOptional.get());
		} else {
			return null;
		}

	}

	@Override
	public List<ClientGroupDTO> findAllGroups() {
		List<ClientsGroup> listEntity = clientGroupRepository.findAll();
		List<ClientGroupDTO> listDTO = new ArrayList<ClientGroupDTO>();
		for (ClientsGroup entity : listEntity) {
			listDTO.add(clientGroupConverter.convertEntityToDTO(entity));
		}
		return listDTO;
	}

	@Override
	public List<TableDTO> findAllTables() {
		List<TableEntity> listEntity = tableRepository.findAll();
		List<TableDTO> listDTO = new ArrayList<TableDTO>();
		for (TableEntity entity : listEntity) {
			listDTO.add(tableConverter.convertEntityToDTO(entity));
		}
		return listDTO;
	}

	@Override
	public List<TableDTO> inserTables(List<NewTableInsertDTO> listNewTableInsertDTO) {

		List<TableDTO> listTableDTO = new ArrayList<TableDTO>();
		for (NewTableInsertDTO dto : listNewTableInsertDTO) {
			TableEntity tableEntity = new TableEntity();
			tableEntity = tableConverter.convertDTOtoEntity(dto);
			tableEntity = tableRepository.save(tableEntity);
			listTableDTO.add(tableConverter.convertEntityToDTO(tableEntity));
		}

		return listTableDTO;

	}

}
