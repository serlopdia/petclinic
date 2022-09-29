package org.springframework.samples.petclinic.hotel;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.clinic.Clinic;

public interface HotelRepository extends CrudRepository<Hotel,Integer>{
	
	@Query("SELECT h FROM Hotel h WHERE h.owner.id = :ownerId")
	Iterable<Hotel> findBookingsByOwnerId(@Param("ownerId") Integer ownerId);

	/*
	 * Devuelve las habitaciones ocupadas en un intervalo de tiempo
	 */
	
	@Query("SELECT h.room FROM Hotel h WHERE (:sd >= h.startDate and :sd <= h.finishDate) or (:fd >= h.startDate and :fd <= h.finishDate) or (:sd <= h.startDate and :fd >= h.finishDate) and h.owner.clinic = :c")
	Collection<Integer> checkRoomAvailability(@Param("sd") LocalDate sd, @Param("fd") LocalDate fd, @Param("c") Clinic c);
	
	@Query("SELECT h.pet.id FROM Hotel h WHERE (:sd >= h.startDate and :sd <= h.finishDate) or (:fd >= h.startDate and :fd <= h.finishDate) or (:sd <= h.startDate and :fd >= h.finishDate)")
	Collection<Integer> checkPetAvailability(@Param("sd") LocalDate sd, @Param("fd") LocalDate fd);

}
