package org.springframework.samples.petclinic.clinic;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicService {
	
	private ClinicRepository repo;
	
	private OwnerService ownerService;
	
	private UserService userService;
	
	private VetService vetService;
	
	@Autowired
	public ClinicService(ClinicRepository repo, OwnerService ownerService, UserService userService,
			VetService vetService) 
	{
		this.repo = repo;
		this.ownerService = ownerService;
		this.userService = userService;
		this.vetService = vetService;
	}
	
	@Transactional
	public Iterable<Clinic> findClinicByOwner(Integer ownerId)
	{
		return this.repo.findClinicByOwner(ownerId);
	}
	
	@Transactional
	public ClinicOwner findWhoEnters() 
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return this.repo.findClinicOwner(auth.getName());
	}

	@Transactional
	public void save(@Valid Clinic clinic) {
		this.repo.save(clinic);
		
	}

	@Transactional
	public Clinic findClinicById(Integer clinicId) {
		return this.repo.findClinicById(clinicId);
	}
	
	@Transactional
	public List<Vet> findVetByClinic(Integer id)
	{
		return this.repo.findVetByClinic(id);
	}

	@Transactional
	public Clinic findClinicByName(String name) {
		return this.repo.findClinicByName(name);
	}

	@Transactional
	public void delete(Clinic c) {
		List<Owner> owners = this.repo.findOwnerByClinic(c.getId());
		for(Owner o:owners) 
		{
			ownerService.delete(o);
		}
		List<Vet> vets = this.repo.findVetByClinic(c.getId());
		for(Vet v:vets) 
		{
			vetService.delete(v);
		}
		List<User> users = this.repo.findUserByClinic(c.getId());
		for(User u:users) 
		{
			userService.delete(u);
		}
		this.repo.delete(c);
	}
	
	@Transactional
	public List<Owner> findOwnerClinic(Clinic c)
	{
		return this.repo.findOwnerByClinic(c.getId());
	}
}
