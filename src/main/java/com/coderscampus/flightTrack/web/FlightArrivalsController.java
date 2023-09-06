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

import com.coderscampus.flightTrack.domain.OpenSkyResponseArrival;
import com.coderscampus.flightTrack.service.ArrivalService;
import com.coderscampus.flightTrack.util.EpochConverter;

@Controller
public class FlightArrivalsController {

    @Autowired
    private ArrivalService arrivalService;
    
    private EpochConverter epochConverter = new EpochConverter();

    // GET mapping for displaying the flight arrival list
    @GetMapping("/arrivals")
    public String getArrivalList(Model model) {
        // Logic to retrieve the arrivals data and pass it to the view
        
        List<OpenSkyResponseArrival> arrivals = arrivalService.getArrivalList();
        model.addAttribute("arrivals", arrivals);
        return "arrivals";
    }

    @GetMapping("/arrivals/{id}")
    public String getArrivalById(@PathVariable Long id, Model model) {
        OpenSkyResponseArrival arrival = arrivalService.getArrivalById(id);

        // Convert epoch timestamps to human-readable dates
        String firstSeenFormatted = epochConverter.epochToHumanReadable(arrival.getFirstSeen());
        String lastSeenFormatted = epochConverter.epochToHumanReadable(arrival.getLastSeen());

        // Add the arrival and formatted dates to the model
        model.addAttribute("arrival", arrival);
        model.addAttribute("firstSeenFormatted", firstSeenFormatted);
        model.addAttribute("lastSeenFormatted", lastSeenFormatted);

        return "arrival";
    }

    @PostMapping("/arrivals/update/{id}")
    public String updateArrival(@PathVariable Long id, @ModelAttribute OpenSkyResponseArrival updatedArrival) {
        arrivalService.updateArrival(id, updatedArrival);
        return "redirect:/arrivals";
    }

    @PostMapping("/arrivals/delete/{id}")
    public String deleteArrival(@PathVariable("id") Long id) {
        arrivalService.deleteArrival(id);
        return "redirect:/arrivals";
    }
}
