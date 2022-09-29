package org.springframework.samples.petclinic.clinic;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class ClinicFormatter implements Formatter<Clinic> {
	
	private final ClinicService clinicService;
	
	@Autowired
	public ClinicFormatter(ClinicService clinicService) 
	{
		this.clinicService = clinicService;
	}

	@Override
	public String print(Clinic object, Locale locale) {
		return object.getName();
	}

	@Override
	public Clinic parse(String text, Locale locale) throws ParseException {
		Clinic clinic = clinicService.findClinicByName(text);
		if(clinic != null) 
		{
			return clinic;
		}else 
		{
			throw new ParseException("Clinic not found: " + text,0);
		}
	}

}
