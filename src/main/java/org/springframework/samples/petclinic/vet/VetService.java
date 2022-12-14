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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.clinic.Clinic;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.OwnerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class VetService {

	private VetRepository vetRepository;
	
	private OwnerService ownerService;
	
	

	@Autowired
	public VetService(VetRepository vetRepository, OwnerService ownerService) {
		this.vetRepository = vetRepository;
		this.ownerService = ownerService;
	}		

	@Transactional(readOnly = true)	
	public Collection<Vet> findVets() {
		Owner o = this.ownerService.findWhoEnters();
		List<Vet> v =  (List<Vet>) vetRepository.findAll();
		if(o != null) {
		 v =  v.stream().filter(x -> x.getClinic() == o.getClinic()).collect(Collectors.toList());
		}else 
		{
			v = v.stream().filter(x -> x.getClinic() == findClinicUser()).collect(Collectors.toList());
		}
		return v;
	}
	
	@Transactional
	public Clinic findClinicUser() 
	{
		return this.vetRepository.findClinicUser(SecurityContextHolder.getContext().getAuthentication().getName());
	}
	
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		vetRepository.save(vet);
	}
	
	@Transactional
	public Optional<Vet> getById(Integer id) throws DataAccessException {
		return vetRepository.findById(id);
	}

	@Transactional
	public void delete(Vet vet) 
	{
		vetRepository.delete(vet);
	}
}
