package com.restaurant.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.ClientGroupDTO;
import com.restaurant.dto.NewTableInsertDTO;
import com.restaurant.dto.NewlyArrivedClientGroupDTO;
import com.restaurant.dto.TableDTO;
import com.restaurant.service.RestManager;

@RestController
@Validated
@RequestMapping("/")

public class RestaurantController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

	@Autowired
	private RestManager service;

	@PostMapping(value = "/onArrive", produces = { "application/json" })
	public ResponseEntity<List<String>> onArrive(
			@RequestBody @Valid NewlyArrivedClientGroupDTO newlyArrivedClientGroupDTO) {
		LOGGER.info("### onArrive ");
		return new ResponseEntity<>(service.onArrive(newlyArrivedClientGroupDTO), HttpStatus.CREATED);
	}

	@PutMapping(value = "/onLeave", produces = { "application/json" })
	public ResponseEntity<List<String>> onLeave(@RequestBody @Valid ClientGroupDTO clientGroupDTO) {
		LOGGER.info("### onLeave ");
		return new ResponseEntity<>(service.onLeave(clientGroupDTO), HttpStatus.OK);
	}

	@GetMapping(value = "/groups", produces = { "application/json" })
	public ResponseEntity<List<ClientGroupDTO>> findAllGroups() {
		LOGGER.info("### findAllGroups ");
		return new ResponseEntity<>(service.findAllGroups(), HttpStatus.OK);
	}

	@GetMapping(value = "/tables", produces = { "application/json" })
	public ResponseEntity<List<TableDTO>> findAllTables() {
		LOGGER.info("### findAllTables ");
		return new ResponseEntity<>(service.findAllTables(), HttpStatus.OK);
	}

	// just for tests, to fill the tables
	@PostMapping(value = "/tables", produces = { "application/json" })
	public ResponseEntity<List<TableDTO>> insertTables(
			@RequestBody @Valid List<NewTableInsertDTO> listNewTableInsertDTO) {
		LOGGER.info("### insertTables ");
		return new ResponseEntity<>(service.inserTables(listNewTableInsertDTO), HttpStatus.CREATED);
	}
}
