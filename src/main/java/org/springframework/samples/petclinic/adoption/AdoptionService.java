package org.springframework.samples.petclinic.adoption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {

	private AdoptionRepository AdoptionRepo;
	
	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepo) 
	{
		this.AdoptionRepo = adoptionRepo;
	}
	
	public Iterable<Adoption> findAll()
	{
		return AdoptionRepo.findAll();
	}
	
	public void save(Adoption a) 
	{
		AdoptionRepo.save(a);
	}
	public void delete(Adoption a) 
	{
		AdoptionRepo.delete(a);
	}
	public Adoption findById(int adId) 
	{
		return AdoptionRepo.findById(adId).orElse(null);
	}
}
