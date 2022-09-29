package org.springframework.samples.petclinic.cause;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService 
{
	private CauseRepository causeRepository;
	
	@Autowired
	public CauseService(CauseRepository causeRepository) 
	{
		this.causeRepository = causeRepository;
	}
	
	@Transactional
	public void save(Cause cause) 
	{
			causeRepository.save(cause);
	}
	
	@Transactional
	public Iterable<Cause> findAll()
	{
		return causeRepository.findAll();
	}
	
	@Transactional
	public Optional<Cause> findCauseById(Integer causelId)
	{
		return causeRepository.findById(causelId);
	}
	
	@Transactional
	public void delete(Cause cause) 
	{
		causeRepository.delete(cause);
	}	
	
	@Transactional
	public Iterable<Cause> findCausesByOwnerId(Integer causeId) 
	{
		return causeRepository.findCausesByOwnerId(causeId);
	}
	
}
