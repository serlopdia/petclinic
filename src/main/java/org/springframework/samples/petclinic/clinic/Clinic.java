package org.springframework.samples.petclinic.clinic;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Clinic extends NamedEntity
{
	
	private Integer level;
	
	private Integer vet;
	
	@ManyToOne()
	private ClinicOwner co;
	

}
