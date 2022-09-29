package org.springframework.samples.petclinic.hotel;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService 
{
	private HotelRepository hotelRepository;
	
	@Autowired
	public HotelService(HotelRepository hotelRepository) 
	{
		this.hotelRepository = hotelRepository;
	}
	
	@Transactional
	public void save(Hotel hotel) 
	{
		hotelRepository.save(hotel);
	}
	
	@Transactional
	public Optional<Hotel> findBookingById(Integer hotelId)
	{
		return hotelRepository.findById(hotelId);
	}
	
	@Transactional
	public void delete(Hotel hotel) 
	{
		hotelRepository.delete(hotel);
	}	
	
	@Transactional
	public Iterable<Hotel> findBookingsByOwnerId(Integer ownerId) {
		return hotelRepository.findBookingsByOwnerId(ownerId);
	}
	
	public Boolean checkDate(LocalDate sd, LocalDate fd) 
	{
		return sd.isAfter(fd);
	}

	public Integer checkRoomAvailability(LocalDate sd, LocalDate fd, Integer r,Clinic c) {
		Collection<Integer> occupiedRooms = hotelRepository.checkRoomAvailability(sd,fd,c);
		List<Integer> availableRooms = IntStream.range(1, 31).filter(x -> !occupiedRooms.contains(x)).boxed().collect(Collectors.toList());
		if(occupiedRooms.contains(r)) {
			return !availableRooms.isEmpty() ? availableRooms.get(0) : 0;
		}
		return r;
	}
	
	public Boolean checkPetAvailability(LocalDate sd, LocalDate fd, Integer petId) {
		Collection<Integer> unavailablePets = hotelRepository.checkPetAvailability(sd,fd);
		return unavailablePets.contains(petId);
	}
}
