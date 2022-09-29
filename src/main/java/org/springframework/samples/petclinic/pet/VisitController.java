/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.pet;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.clinic.ClinicService;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class VisitController {

	private final PetService petService;
	
	private final ClinicService clinicService;
	
	private final OwnerService ownerService;

	@Autowired
	public VisitController(PetService petService, ClinicService clinicService, OwnerService ownerService) {
		this.petService = petService;
		this.clinicService = clinicService;
		this.ownerService = ownerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		Visit visit = new Visit();
		Pet pet = this.petService.findPetById(petId);
		pet.addVisit(visit);
		model.put("visit", visit);
		return "pets/createOrUpdateVisitForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result,@PathVariable("petId") int petId,ModelMap modelMap) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		else {
			
			Clinic c = this.ownerService.findWhoEnters().getClinic();
			
			List<Owner> owners = this.clinicService.findOwnerClinic(c);
			int res = 0;
			for(Owner o : owners) 
			{
				res = res + o.getPets().stream().mapToInt(x -> x.getVisits().size()).sum();
			}
			
			if(c.getLevel() == 0 && res == 1000 || c.getLevel() == 1 && res == 5000) 
			{
				modelMap.addAttribute("message","la cuota de visitas ha sido superada.");
				return "pets/createOrUpdateVisitForm";	
			}
			Pet pet = this.petService.findPetById(petId);
			visit.setPet(pet);
			this.petService.saveVisit(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/visits")
	public String showVisits(@PathVariable("petId") int petId, Map<String, Object> model) {
		model.put("visits", this.petService.findPetById(petId).getVisits());
		return "visitList";
	}
	
	@GetMapping(value = "/owners/{petId}/{ownerId}/delete/visit/{visitId}")
    public String deleteVisit(@PathVariable("visitId") int visitId, ModelMap modelMap) 
    {
    	Optional<Visit> visit = petService.findVisitById(visitId);
    	if(visit.isPresent()) 
    	{
    		petService.deleteVisit(visit.get());
    		modelMap.addAttribute("message", "la visita se borró satisfactoriamente");
    	}else 
    	{
    		modelMap.addAttribute("message", "la visita no ha sido encontrada :(");
    	}
    	return "redirect:/owners/{ownerId}";
    }
}
