package org.springframework.samples.petclinic.clinic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.hotel.Hotel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinic")
public class ClinicController {
	
	private ClinicService service;
	
	@Autowired
	public ClinicController(ClinicService service) 
	{
		this.service = service;
	}
	
	@GetMapping()
	public String listClinic(ModelMap modelMap) 
	{
		String view = "clinic/clinicList";
		Iterable<Clinic> res = service.findClinicByOwner(service.findWhoEnters().getId());
		modelMap.addAttribute("clinic",res);
		return view;
	}
	
	@GetMapping(path = "/price")
	public String showDetails(ModelMap modelMap) 
	{
		List<Clinic> aux = (List<Clinic>) service.findClinicByOwner(service.findWhoEnters().getId());
		int l = 0;
		if(aux.size() > 0) 
		{
			l = aux.get(0).getLevel();
		}
		modelMap.addAttribute("clinic", l);
		return "clinic/show";
	}
	
	@GetMapping(path="/new")
	public String initCreationForm(ModelMap model) {
		Clinic clinic = new Clinic();
		model.addAttribute("clinic", clinic);
		return "clinic/createForm";
	}
	
	@GetMapping(path= "/change/{level}")
	public String changeLevel(@PathVariable("level") Integer level,ModelMap model) 
	{
		List<Clinic> clinicas = (List<Clinic>) this.service.findClinicByOwner(service.findWhoEnters().getId());
		Integer limit = 80000;
		if(level == 0) {limit = 1;}else if(level == 1) {limit = 4;}
		
		for(Clinic c:clinicas) 
		{
			if(clinicas.size() > 1 && level == 0) 
			{
				model.addAttribute("message","para cambiar el plan deseado debe usted disminuir su número de clinicas");
				return "welcome";
			}
			if(limit < this.service.findVetByClinic(c.getId()).size()) 
			{
				model.addAttribute("message","para cambiar al plan deseado debe usted disminuir su número de veterinarios");
				return "welcome";
			}else {
				c.setVet(limit);c.setLevel(level);
				service.save(c);
			}
		}
		return "redirect:/clinic/price";
	}
	
	@PostMapping(path = "/new")
	public String processCreationForm(@Valid Clinic clinic, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("clinic",clinic);
			return "clinic/createForm";
		}
		else {
			
			List<Clinic> aux = (List<Clinic>) service.findClinicByOwner(service.findWhoEnters().getId());
			if(aux.size() == 0 || aux.get(0).getLevel() != 0) {
				modelMap.addAttribute("message","Se ha añadido la clinica correctamente");
				clinic.setCo(service.findWhoEnters());
				clinic.setLevel(0);
				clinic.setVet(1);
				service.save(clinic);
			}else{modelMap.addAttribute("message","solo puedes tener una clinica con tu plan");}
		}
		return "welcome";
	}
	
	@GetMapping(path = "/del/{clinicId}")
	public String deleteClinic(@PathVariable("clinicId") Integer clinicId,ModelMap modelMap) 
	{
		Clinic c = service.findClinicById(clinicId);
		if(c != null) 
		{
			service.delete(c);
			modelMap.addAttribute("message","La clinica ha sido satisfactoriamente borrada");
		}
		else 
		{
			modelMap.addAttribute("message","La clinica no ha sido encontrada");
		}
		return "welcome";
	}
	

}
