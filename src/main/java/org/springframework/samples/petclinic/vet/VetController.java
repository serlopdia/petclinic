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
package org.springframework.samples.petclinic.vet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.petclinic.owner.OwnerService;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {
	
	private static final String VIEWS_VET_CREATE_OR_UPDATE = "vets/createOrUpdateVetForm";

	private VetService vetService;
	
	public SpecialtyService specialtyService;
	
	public OwnerService ownerService;

	@Autowired
	public VetController(VetService clinicService, OwnerService ownerService, SpecialtyService specialtyService) {
		this.vetService = clinicService;
		this.ownerService = ownerService;
		this.specialtyService = specialtyService;
	}

	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}

	@GetMapping(value="/vets/create")
	public String initCreationForm(Map<String, Object> model) {
		String view = VIEWS_VET_CREATE_OR_UPDATE;
		Vet vet = new Vet();
		model.put("vet", vet);
		List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
		model.put("specialties", specialtiesList);
		return view;
	}
	
	
	@PostMapping(value="/vets/new")
	public String processCreationForm(@RequestBody MultiValueMap<String, String> form, @Valid Vet vet, BindingResult result, Map<String, Object> model) {
		List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
		if(this.vetService.findVets().size() + 1 > this.vetService.findClinicUser().getVet()) 
		{
			model.put("message", "Has consumido todos los veterinarios del nivel de Servicio");
			return "welcome";
		}
		
		if(result.hasErrors()) {
			model.put("vet", vet);
			model.put("specialties", specialtiesList );
			return VIEWS_VET_CREATE_OR_UPDATE;
		} else {
			HashSet<Specialty> specialtySet = new HashSet<>();
			
			for(int i=0; i<specialtiesList.size(); i++) {
				String specialtyStrId = form.getFirst(specialtiesList.get(i).getName());
				if(specialtyStrId!=null)specialtySet.add(specialtyService.getById(Integer.parseInt(specialtyStrId)).get());
			}			
			vet.setSpecialtiesInternal(specialtySet);
			vet.setClinic(this.vetService.findClinicUser());
			vetService.saveVet(vet);
			
		}
		return "redirect:/vets";
	}
	
	@GetMapping(value="/vets/edit/{vetId}")
	public String showEditVet(@PathVariable("vetId") Integer vetId, Map<String, Object> model) {
		
		Vet vet=vetService.getById(vetId).get();
		List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
		
		model.put("vet", vet);
		model.put("specialties", specialtiesList );
		return VIEWS_VET_CREATE_OR_UPDATE;
	
		
	}
	
	@GetMapping(value="/vets/delete/{vetId}")
	public String deleteVet(@PathVariable("vetId") Integer vetId, ModelMap modelmap) 
	{
		Optional<Vet> vet = vetService.getById(vetId);
		if(vet.isPresent()) 
		{
			vetService.delete(vet.get());
			modelmap.addAttribute("message","El veterinario se borró satisfactoriamente");
		}else 
		{
			modelmap.addAttribute("message","El veterinario no ha sido encontrado.");
		}
		return "welcome";
	}
	
	@GetMapping(value="/vets/delete/spec/{specId}")
	public String deleteSpec(@PathVariable("specId") Integer specId, ModelMap modelmap) 
	{
		Optional<Specialty> spec = specialtyService.getById(specId);
		if(spec.isPresent()) 
		{
			specialtyService.delete(spec.get());
			modelmap.addAttribute("message","La especialidad se borró satisfactoriamente");
		}else 
		{
			modelmap.addAttribute("message","La especialidad no ha sido encontrada.");
		}
		return "welcome";
	}

}
