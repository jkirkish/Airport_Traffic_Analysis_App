package com.coderscampus.flightTrack.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.flightTrack.domain.OpenSkyResponseDeparture;
import com.coderscampus.flightTrack.repository.AirportOfFlightDeparturesRepository;
import com.coderscampus.flightTrack.util.EpochConverter;

@Service
public class DepartureService {
	
	
	private final AirportOfFlightDeparturesRepository departuresRepository;
	
	private EpochConverter epochConvert = new EpochConverter();
	
	@Autowired
    public DepartureService(AirportOfFlightDeparturesRepository departuresRepository) {
        this.departuresRepository = departuresRepository;
    }
	public List<OpenSkyResponseDeparture> getDepartureList() {
	        return departuresRepository.findAll();
	}
	public OpenSkyResponseDeparture getDepartureById(Long id) {
	        return departuresRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid departure ID: " + id));
	}
	public void createDeparture(OpenSkyResponseDeparture departure) {

        departuresRepository.save(departure);
    }
	public void updateDeparture(Long id, OpenSkyResponseDeparture updatedDeparture) {
        OpenSkyResponseDeparture existingDeparture = departuresRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid departure ID: " + id));

        departuresRepository.save(existingDeparture);
    }
	  public void deleteDeparture(Long id) {
	        departuresRepository.deleteById(id);
	    }
	  public void deleteAllDepartures() {
		  departuresRepository.deleteAll(); 
	    }
}
