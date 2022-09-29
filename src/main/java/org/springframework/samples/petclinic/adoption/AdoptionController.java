package org.springframework.samples.petclinic.adoption;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController 
{
	private static final String ADOPT = "adoptions/createForm";
	
	private AdoptionService adoptionService;
	private PetService petService;
	private OwnerService ownerService;
	
	@Autowired
	public AdoptionController(AdoptionService adoptionService, OwnerService ownerService, PetService petService) 
	{
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}
	
	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {
		List<Pet> pets = this.petService.findPetsByOwner(ownerService.findWhoEnters().getId()).stream().collect(Collectors.toList());
		List<Adoption> petsInAdoption = (List<Adoption>) this.adoptionService.findAll();
		pets.removeAll(petsInAdoption.stream().map(x -> x.getPet()).collect(Collectors.toList()));
		return pets;
	}
	
	@GetMapping()
	public String showAdoptions(ModelMap modelMap) 
	{
		String view = "adoptions/adoptionList";
		List<Adoption> adoptions = (List<Adoption>) adoptionService.findAll();
		adoptions = adoptions.stream().filter(x -> ownerService.findWhoEnters().getClinic() == x.getPet().getOwner().getClinic()).collect(Collectors.toList());
		modelMap.addAttribute("adoptions", adoptions);
		modelMap.addAttribute("owner",ownerService.findWhoEnters().getId());
		return view;
	}
	
	@GetMapping(path="/new")
	public String initCreationForm(ModelMap model) {
		Adoption adoption = new Adoption();
		model.addAttribute("adoption", adoption);
		if(this.populatePets().size() == 0) 
		{
			model.addAttribute("message","No tienes mascotas que poner en adopción.");
		}
		return ADOPT;
	}
	
	@PostMapping(path = "/new")
	public String processCreationForm(@Valid Adoption adoption, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("adoption",adoption);
			return ADOPT;
		}
		else {
				modelMap.addAttribute("message","Se ha añadido la solicitud de adopción correctamente");
				adoptionService.save(adoption);
		}
		return "welcome";
	}
	
	@GetMapping(path="{adoptionId}/{petId}")
	public String proccessAdoption(@PathVariable("petId") int petid,@PathVariable("adoptionId") int adid, ModelMap model) throws DataAccessException, DuplicatedPetNameException 
	{
		Adoption a = adoptionService.findById(adid);
		adoptionService.delete(a);
		Pet pet = petService.findPetById(petid);
		Owner oldOwner = pet.getOwner();
		Owner newOwner = ownerService.findWhoEnters();
		newOwner.addPet(pet);
		oldOwner.removePet(pet);
		pet.setOwner(newOwner);
		petService.savePet(pet);
		ownerService.saveOwner(newOwner);
		ownerService.saveOwner(oldOwner);
		model.addAttribute("message","Has adoptado una mascota ♥");
		return "welcome";
	}
}
