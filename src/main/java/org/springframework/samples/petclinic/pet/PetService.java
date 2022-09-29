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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.hotel.Hotel;
import org.springframework.samples.petclinic.hotel.HotelRepository;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private PetRepository petRepository;
	private OwnerRepository ownerRepository;
	private VisitRepository visitRepository;
	private HotelRepository hotelRepository;

	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository, HotelRepository hotelRepository, OwnerRepository ownerRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
		this.ownerRepository = ownerRepository;
		this.hotelRepository = hotelRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
			if(pet.getOwner()!=null){
				Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            	if (StringUtils.hasLength(pet.getName()) &&  (otherPet!= null && !otherPet.getId().equals(pet.getId()))) {            	
            		throw new DuplicatedPetNameException();
            	}else
                	petRepository.save(pet);                
			}else
				petRepository.save(pet);
	}

	@Transactional
	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}
	
	@Transactional
	public Collection<Pet> findPetsByOwner(int ownerId)
	{
		return petRepository.findPetsByOwner(ownerId);
	}
	
	@Transactional
	public Optional<Visit> findVisitById(int visitId)
	{
		return visitRepository.findById(visitId);
	}
	
	@Transactional
	public void deleteVisit(Visit visit) 
	{
		visit.getPet().getVisitsInternal().remove(visit);
		visit.setPet(null);
		visitRepository.delete(visit);
	}
	
	@Transactional
	public Pet getPetByName(String text) {
		return petRepository.findByName(text);
	}
	
	@Transactional
	public void delete(Pet pet) {
		List<Hotel> hotel = (List<Hotel>) hotelRepository.findAll();
		for(Hotel h: hotel) 
		{
			if(h.getPet().equals(pet)) 
			{
				hotelRepository.delete(h);
			}
		}
		List<Visit> visits = (List<Visit>) visitRepository.findAll();
		for(Visit v: visits) 
		{
			if(v.getPet().equals(pet)) 
			{
				deleteVisit(v);
			}
		}
		List<Owner> owners = (List<Owner>) ownerRepository.findAll();
		for(Owner o: owners) 
		{
			if(o.getPets().contains(pet))
			{
				o.removePet(pet);
			}
		}
		pet.setVisitsInternal(null);
		pet.setOwner(null);
		pet.setType(null);
		petRepository.delete(pet);
	}
}
