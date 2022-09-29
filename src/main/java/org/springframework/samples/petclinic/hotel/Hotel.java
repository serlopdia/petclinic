package org.springframework.samples.petclinic.hotel;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity{
	
	@Min(1)
	@Max(30)
	@NotNull()
	private Integer room;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull()
    private LocalDate startDate;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull()
    private LocalDate finishDate;
	
	@OneToOne
	private Pet pet;
	
	@OneToOne	
	private Owner owner;
	
}
