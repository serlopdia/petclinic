package org.springframework.samples.petclinic.vet;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecialtyService {
	
	private SpecialtyRepository specialtyRepository;
	private VetRepository vetRepository;
	
	@Autowired
	public SpecialtyService(SpecialtyRepository specialtyRepository, VetRepository vetRepository) {
		this.specialtyRepository = specialtyRepository;
		this.vetRepository = vetRepository;
	}
	
	@Transactional
	public List<Specialty> getAllSpecialties() throws DataAccessException {
		return specialtyRepository.findAllSpecialties();
	}
	
	@Transactional
	public Optional<Specialty> getById(Integer id) throws DataAccessException {
		return specialtyRepository.findById(id);
	}

	public void delete(Specialty specialty) {
		Collection<Vet> vets = vetRepository.findAll();
		for(Vet v:vets) 
		{
			if(v.getSpecialtiesInternal().contains(specialty)) 
			{
				v.getSpecialtiesInternal().remove(specialty);
			}
		}
		specialtyRepository.delete(specialty);
	}

}
