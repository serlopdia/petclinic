package org.springframework.samples.petclinic.contact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.clinic.ClinicOwner;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService 
{
	private ContactRepository repo;
	
	@Autowired
	public ContactService(ContactRepository repo) 
	{
		this.repo = repo;
	}
	
	@Transactional
	public Owner findWhoOwnerEnters() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return repo.findOwnerByUsername(auth.getName()).orElse(null);
	}
	
	@Transactional
	public ClinicOwner findWhoClinicOEnters() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.repo.findClinicOwner(auth.getName()).orElse(null);
	}
	
	@Transactional
	public List<Clinic> findClinicByClinicOwner(int id)
	{
		return this.repo.findClinicByClinicOwner(id);
	}
	
	@Transactional
	public User findWhoAdminEnters() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.repo.findWhoAdminEnter(auth.getName()).orElse(null);
	}
	
}
