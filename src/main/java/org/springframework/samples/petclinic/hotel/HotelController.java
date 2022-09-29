package org.springframework.samples.petclinic.hotel;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {
	private static final String BOOKING = "bookings/createForm";
	private static final String BADREQUEST = "badRequest";
	private static final String HOTEL = "hotel";
	private static final String MESSAGE = "message";
	
	private final OwnerService ownerService;
	private final HotelService hotelService;
	private final PetService petService;
	
	@Autowired
	public HotelController(HotelService hotelService, OwnerService ownerService, PetService petService) 
	{
		this.hotelService = hotelService;
		this.ownerService = ownerService;
		this.petService = petService;
	}
	
	@GetMapping()
	public String showBookingOwner(ModelMap modelMap) 
	{
		String view = "bookings/BookingList";
		Iterable<Hotel> bookingsById = hotelService.findBookingsByOwnerId(ownerService.findWhoEnters().getId());
		modelMap.addAttribute("bookings", bookingsById);
		return view;
	}
	
	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		return this.petService.findPetsByOwner(ownerService.findWhoEnters().getId());
	}
	
	@GetMapping(path="/new")
	public String initCreationForm(ModelMap model) {
		if(ownerService.findWhoEnters().getCity().equalsIgnoreCase("rusia")) {
			model.addAttribute(MESSAGE,"Por diversas razones, actualmente carece de derecho a reserva. NO A LA INVASIÓN");
			return "welcome";
		}
		Hotel hotel = new Hotel();
		model.addAttribute(HOTEL, hotel);
		return BOOKING;
	}
	
	@PostMapping(path = "/new")
	public String processCreationForm(@Valid Hotel hotel, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute(HOTEL,hotel);
			return BOOKING;
		}
		else {
			Integer r = hotelService.checkRoomAvailability(hotel.getStartDate(), hotel.getFinishDate(), hotel.getRoom(), ownerService.findWhoEnters().getClinic());
			Integer petId = hotel.getPet().getId();
			if(Boolean.TRUE.equals(hotelService.checkDate(hotel.getStartDate(), hotel.getFinishDate()))) 
			{
				result.rejectValue("startDate", BADREQUEST, "The start date must be before the finish date");
				result.rejectValue("finishDate", BADREQUEST, "Or the finish date must be after the start date");
				modelMap.addAttribute(HOTEL,hotel);
				return BOOKING;
			}
			else if(Boolean.TRUE.equals(hotelService.checkPetAvailability(hotel.getStartDate(), hotel.getFinishDate(), petId))){
				result.rejectValue("pet", BADREQUEST, "The selected pet already has a book for these dates");
				modelMap.addAttribute(HOTEL,hotel);
				return BOOKING;
			}
			else if(!hotel.getRoom().equals(r)) {
				if(!r.equals(0)) {
					result.rejectValue("room", BADREQUEST, "The selected room is occupied on that date, suggestion: " + r.toString());
				}else {
					result.rejectValue("room", BADREQUEST, "There are no rooms available on these dates");
				}
				modelMap.addAttribute(HOTEL,hotel);
				return BOOKING;
			} else {
				hotel.setOwner(ownerService.findWhoEnters());
				hotelService.save(hotel);
				return "redirect:/welcome";
			}
		}
	}
	
	@GetMapping(path= "/delete/{hotelId}")
	public String deleteBooking(@PathVariable("hotelId") int hotelid, ModelMap modelMap) 
	{
		Optional<Hotel> hotel = hotelService.findBookingById(hotelid);
		if(hotel.isPresent()) 
		{
			hotelService.delete(hotel.get());
			modelMap.addAttribute(MESSAGE,"La reserva se ha borrado satisfactoriamente.");
			
		}else 
		{
			modelMap.addAttribute(MESSAGE,"La reserva no ha sido encontrada :(");
		}
		return "welcome";
	}
	
}
