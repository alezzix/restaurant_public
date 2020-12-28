package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.model.ClientsGroup;

@Repository
public interface ClientGroupRepository extends JpaRepository<ClientsGroup, Long> {

	List<ClientsGroup> findByTableIdOrderByIdAsc(long tableId);

}