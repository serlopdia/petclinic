package org.springframework.samples.petclinic.contact;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.clinic.ClinicOwner;
import org.springframework.samples.petclinic.donation.Donation;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.user.User;

public interface ContactRepository extends CrudRepository<Donation,Integer>{
	
	@Query("SELECT o FROM Owner o WHERE o.user.username = :username")
	Optional<Owner> findOwnerByUsername(@Param("username") String name);

	@Query("SELECT o FROM ClinicOwner o WHERE o.user.username = :username")
	Optional<ClinicOwner> findClinicOwner(@Param("username") String name);

	@Query("SELECT u FROM User u WHERE u.username = :username")
	Optional<User> findWhoAdminEnter(@Param("username") String name);

	@Query("SELECT c FROM Clinic c WHERE c.co.id = :id")
	List<Clinic> findClinicByClinicOwner(@Param("id") Integer id);
	
	

}
