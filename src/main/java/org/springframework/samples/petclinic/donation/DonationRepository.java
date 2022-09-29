package org.springframework.samples.petclinic.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DonationRepository extends CrudRepository<Donation,Integer>{

	@Query("SELECT d FROM Donation d WHERE d.cause.id = :causeId")
	Iterable<Donation> findDonationsByCauseId(@Param("causeId") Integer causeId);

}
