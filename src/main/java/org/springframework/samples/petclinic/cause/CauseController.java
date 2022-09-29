package org.springframework.samples.petclinic.cause;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/causes")
@Controller
public class CauseController {
	private static final String VIEWS_CAUSES_CREATE_OR_UPDATE_FORM = "causes/createOrUpdateCauseForm";
	
	private final OwnerService ownerService;
	private final CauseService causeService;
	private final AuthoritiesService authoritiesService;
	
	@Autowired
	public CauseController(CauseService causeService, OwnerService ownerService, AuthoritiesService authoritiesService) 
	{
		this.causeService = causeService;
		this.ownerService = ownerService;
		this.authoritiesService = authoritiesService;
	}
	
	@GetMapping()
	public String showCauseOwner(ModelMap modelMap) 
	{
		String view = "causes/causesList";
		List<Cause> causesById = (List<Cause>) causeService.findAll();
		causesById = causesById.stream().filter(x -> x.getOwner().getClinic() == ownerService.findWhoEnters().getClinic()).collect(Collectors.toList());
		modelMap.addAttribute("causes", causesById);
		return view;
	}
	
	@GetMapping(path="/new")
	public String initCreationForm(ModelMap model) {
		Cause cause = new Cause();
		model.addAttribute("cause", cause);
		return VIEWS_CAUSES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(path="/new")
	public String processCreationForm(@Valid Cause cause, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("cause",cause);
			return VIEWS_CAUSES_CREATE_OR_UPDATE_FORM;
		}
		else {
			cause.setOwner(ownerService.findWhoEnters());
			causeService.save(cause);
			modelMap.addAttribute("message","Formulario enviado correctamente.");
		    return "redirect:/causes";
		}
	}
	
	@GetMapping(value="/{causeId}")
	public String showCause(@PathVariable("causeId") int causeId, Model model) {
		Cause cause=causeService.findCauseById(causeId).get();
		model.addAttribute("cause", cause);
		model.addAttribute("username", authoritiesService.getActualUsername());
		model.addAttribute("authority", authoritiesService.getUserAuthority());
		
		return "causes/causeDetails";
	}

	@GetMapping(value = "/{causeId}/edit")
	public String initUpdateCauseForm(@PathVariable("causeId") int causeId, Model model) {
		Cause cause = this.causeService.findCauseById(causeId).get();
		if(cause.getOwner().getUser().getUsername().equals(authoritiesService.getActualUsername()) 
			|| authoritiesService.getUserAuthority().equals("admin")) {
			
			model.addAttribute(cause);
			return VIEWS_CAUSES_CREATE_OR_UPDATE_FORM;
		}
		model.addAttribute("message","Una causa solo puede ser editada por su creador.");
		return "welcome";
	}

	@PostMapping(value = "/{causeId}/edit")
	public String processUpdateCauseForm(@Valid Cause cause, BindingResult result, ModelMap modelMap,
			@PathVariable("causeId") int causeId) {
		if (result.hasErrors()) {
			return VIEWS_CAUSES_CREATE_OR_UPDATE_FORM;
		}
		else {
			cause.setId(causeId);
			this.causeService.save(cause);
			modelMap.addAttribute("message","La causa ha sido editada satisfactoriamente.");
			return "welcome";
		}
	}
	
	@GetMapping(path="/{causeId}/delete")
	public String deleteCause(@PathVariable("causeId") int causeId, ModelMap modelMap) {
		Optional<Cause> cause = causeService.findCauseById(causeId);
		if(cause.isPresent()) 
		{
			if(cause.get().getOwner().getUser().getUsername().equals(authoritiesService.getActualUsername()) 
				|| authoritiesService.getUserAuthority().equals("admin")) {
				
				causeService.delete(cause.get());
				modelMap.addAttribute("message","La causa se ha borrado satisfactoriamente.");
			} else {
				modelMap.addAttribute("message","Una causa solo puede ser eliminada por su creador.");
			}
		} else {
			modelMap.addAttribute("message","La causa a eliminar no ha sido encontrada.");
		}
		return "welcome";
	}
	
}
