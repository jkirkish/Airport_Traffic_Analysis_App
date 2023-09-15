package com.coderscampus.flightTrack.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coderscampus.flightTrack.domain.OpenSkyResponseDeparture;
import com.coderscampus.flightTrack.service.DepartureService;
import com.coderscampus.flightTrack.util.EpochConverter;

@Controller
public class FlightDeparturesController {

	@Autowired
	private DepartureService departService;
	
	private EpochConverter epochConverter = new EpochConverter();
	
    // GET mapping for displaying the flight departure list
    @GetMapping("/departures")
    public String getDepartureList(Model model) {
        // Logic to retrieve the departures data and pass it to the view
        // Example:
        List<OpenSkyResponseDeparture> departures = departService.getDepartureList();
        model.addAttribute("departures", departures);
        return "departures";
    }

    @GetMapping("/departures/{id}")
    public String getDepartureById(@PathVariable Long id, Model model) {
    	OpenSkyResponseDeparture departure = departService.getDepartureById(id);

        // Convert epoch timestamps to human-readable dates
        String firstSeenFormatted = epochConverter.epochToHumanReadable(departure.getFirstSeen());
        String lastSeenFormatted = epochConverter.epochToHumanReadable(departure.getLastSeen());

        // Add the departure and formatted dates to the model
        model.addAttribute("departure", departure);
        model.addAttribute("firstSeenFormatted", firstSeenFormatted);
        model.addAttribute("lastSeenFormatted", lastSeenFormatted);

        return "departure"; 
    }

    @PostMapping("/departures/update/{id}")
    public String updateDeparture(@PathVariable Long id,@ModelAttribute OpenSkyResponseDeparture updatedDeparture) {
        
        departService.updateDeparture(id,updatedDeparture);
        return "redirect:/departures"; 
    }

   
    
    @PostMapping("/departures/delete/{id}")
    public String deleteDeparture(@PathVariable("id") Long id) {
       
        departService.deleteDeparture(id);
        return "redirect:/departures"; 
    }
    @PostMapping("/departures/deleteAll")
    public String deleteAllDepartures() {
        departService.deleteAllDepartures(); 
        return "redirect:/departures"; 
    }
}

    

   

