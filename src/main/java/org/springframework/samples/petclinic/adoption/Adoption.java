package org.springframework.samples.petclinic.adoption;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Adoption extends BaseEntity
{
	@OneToOne
	private Pet pet;
	
	private String description;	
}
