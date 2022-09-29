package org.springframework.samples.petclinic.donation;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.cause.CauseService;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes/{causeId}")
public class DonationController {

	private static final String VIEWS_DONATIONS_CREATE_OR_UPDATE_FORM = "donations/donationForm";
	
	private final DonationService donationService;
    private final OwnerService ownerService;
    private final CauseService causeService;
    private final AuthoritiesService authoritiesService;

	@Autowired
	public DonationController(DonationService donationService, CauseService causeService, OwnerService ownerService, AuthoritiesService authoritiesService) {
		this.donationService = donationService;
		this.causeService = causeService;
		this.ownerService = ownerService;
		this.authoritiesService = authoritiesService;
	}
	
	public Cause findCause(@PathVariable("causeId") int causeId) {
		return this.causeService.findCauseById(causeId).get();
	}
	
	/*@ModelAttribute("owner")
	            
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("donation")
	public void initDonationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new DonationValidator());
	} */
	
	@GetMapping(value = "/donations/new")
	public String initCreationForm(Cause cause, ModelMap model) {
		Donation donation = new Donation();
		cause.addDonation(donation);
		model.put("donation", donation);
		return VIEWS_DONATIONS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/donations/new")
	public String processCreationForm(Cause cause, @Valid Donation donation, @PathVariable("causeId") int causeId, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("donation", donation);
			return VIEWS_DONATIONS_CREATE_OR_UPDATE_FORM;
		}
		else {
			Cause actualCause = findCause(causeId);
			Integer idealQuantity =  actualCause.getTarget()-actualCause.getDonationsTotal();
			Integer difference = idealQuantity -donation.getAmount();
			if (difference<0) {
				model.put("donation", donation);
				model.addAttribute("message","Esta donación supera la cantidad máxima requerida para alcanzar el objetivo. Para llegar al objetivo, faltan: "+idealQuantity+" euros.");
				return VIEWS_DONATIONS_CREATE_OR_UPDATE_FORM;
			}
			donation.setOwner(ownerService.findWhoEnters());
			donation.setCause(actualCause);
			donation.setDate(LocalDate.now());
			donationService.save(donation);
			model.addAttribute("message","La donación se ha realizado satisfactoriamente.");
		    return "redirect:/causes";
		}
	}
	
	@GetMapping("/delete/{donationId}")
	public String deletePet(@PathVariable("donationId") int donationId, ModelMap modelMap) 
	{
		Donation donation = donationService.findDonationById(donationId).get();
		
		if(donation != null) 
		{
			if(donation.getOwner().getUser().getUsername().equals(authoritiesService.getActualUsername()) 
				|| authoritiesService.getUserAuthority().equals("admin") ) {
				
				donationService.delete(donation);
				modelMap.addAttribute("message", "La donación ha sido eliminada satisfactoriamente.");
			} else {
				modelMap.addAttribute("message","Una donación solo puede ser eliminada por quién la realizó.");
			}
		}else 
		{
			modelMap.addAttribute("message","La donación no ha sido encontrada.");
		}
		return "welcome";
	}
	}