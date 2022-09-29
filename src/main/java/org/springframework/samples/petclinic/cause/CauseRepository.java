package org.springframework.samples.petclinic.cause;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CauseRepository extends CrudRepository<Cause,Integer>{

	@Query("SELECT c FROM Cause c WHERE c.owner.id = :ownerId")
	Iterable<Cause> findCausesByOwnerId(@Param("ownerId") Integer ownerId);
	
	@Query("SELECT c FROM Cause c")
	Iterable<Cause> findAll();

}
