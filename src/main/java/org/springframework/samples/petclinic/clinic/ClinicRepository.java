package org.springframework.samples.petclinic.clinic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.vet.Vet;

public interface ClinicRepository extends CrudRepository<Clinic,Integer>{
	
	@Query("SELECT c FROM Clinic c WHERE c.co.id = :id")
	Iterable<Clinic> findClinicByOwner(@Param("id") Integer ownerId);
	
	@Query("SELECT o FROM ClinicOwner o WHERE o.user.username = :name")
	ClinicOwner findClinicOwner(@Param("name") String name);

	@Query("SELECT c FROM Clinic c WHERE c.id = :id")
	Clinic findClinicById(@Param("id") Integer clinicId);
	
	@Query("SELECT v FROM Vet v WHERE v.clinic.id = :id")
	List<Vet> findVetByClinic(@Param("id") Integer id);

	@Query("SELECT c FROM Clinic c WHERE c.name = :name")
	Clinic findClinicByName(@Param("name") String name);
	
	@Query("SELECT o FROM Owner o WHERE o.clinic.id = :id")
	List<Owner> findOwnerByClinic(@Param("id") Integer id);
	
	@Query("SELECT u FROM User u WHERE u.clinic.id = :id")
	List<User> findUserByClinic(@Param("id") Integer id);
}
