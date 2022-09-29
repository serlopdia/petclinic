package org.springframework.samples.petclinic.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.clinic.ClinicOwner;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contact")
public class ContactController {
	
	public ContactService contactService;
	
	@Autowired
	public ContactController(ContactService contactService) 
	{
		this.contactService = contactService;
	}
	
	@GetMapping("")
	public String getContacto() 
	{
		return "contact/show";
	}
	@GetMapping("ca")
	public String getCA(ModelMap model)
	{
		Integer level = 4;
		Owner o = this.contactService.findWhoOwnerEnters();
		ClinicOwner c = this.contactService.findWhoClinicOEnters();
		User u = this.contactService.findWhoAdminEnters();
		if( o != null) 
		{
			level = o.getClinic().getLevel();
		}else if(c != null)
		{
			List<Clinic> aux  = this.contactService.findClinicByClinicOwner(c.getId());
			if(aux.size() == 0) 
			{
				return "contact/CA";
			}else 
			{
				level = aux.get(0).getLevel();
			}
		}else if(u != null) 
		{
			level = u.getClinic().getLevel();
		}
		
		if(level == 0) {
			return "contact/CA0";
		}else if(level == 1) {
			return "contact/CA1";
		}else if(level == 2) {
			return "contact/CA2";
		}
		return "contact/CA";
	}

}
