package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.model.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long>  {
  
  @Query(value = "select t from TableEntity t where t.size = t.totalFreeSeats Order By t.totalFreeSeats Asc")
  List<TableEntity> findAllFreeOrderByTotalFreeSeatsAsc();
  
  @Query(value = "select t from TableEntity t where t.size > t.totalFreeSeats and t.totalFreeSeats > 0  Order By t.totalFreeSeats Asc")
  List<TableEntity> findAllOccupiedOrderByTotalFreeSeatsAsc();

}