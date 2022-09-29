package org.springframework.samples.petclinic.vet;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository extends CrudRepository<Specialty, Integer>{
	
@Query("SELECT s FROM Specialty s")
List<Specialty> findAllSpecialties() throws DataAccessException;	

}
