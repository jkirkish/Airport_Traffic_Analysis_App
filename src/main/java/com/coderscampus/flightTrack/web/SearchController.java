package com.coderscampus.flightTrack.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coderscampus.flightTrack.domain.OpenSkyResponseArrival;
import com.coderscampus.flightTrack.domain.OpenSkyResponseDeparture;
import com.coderscampus.flightTrack.domain.Search;
import com.coderscampus.flightTrack.domain.User;
import com.coderscampus.flightTrack.service.SearchService;
import com.coderscampus.flightTrack.service.UserService;

@Controller
@RequestMapping("/api/v1/searches")
public class SearchController {
	
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/arrivalSearchRequests")
	public String getallSearchRequests(ModelMap model){
		List<Search> search = searchService.findAll();
		model.put("arrivalSearch", search);
		return "arrivalSearchRequests";
	}
	
	@GetMapping("/airportArrivalSearch")
	public String getAirportArrivalSearches(ModelMap model) {
		model.put("arrivalSearch", new Search());
		return "airportArrivalSearch";
	}
	@GetMapping("/search/{id}")
	public String viewSearch(@PathVariable("id") Long id, Model model) {
	   Search search = searchService.findById(id);
	   model.addAttribute("search", search);
	   return "editSearch"; 
	    }
	@PostMapping("/search/save/{id}")
	public String saveChanges(@PathVariable("id") Long id, Search search) throws Exception {
	    search.setId(id);
	    searchService.save(search);
	    searchService.initiateSearch(search);
	    return "redirect:/search/" + search.getId();
	}

	@PostMapping("/search/delete/{id}")
	public String deleteSearch(@PathVariable("id") Long id) {
	    searchService.delete(id);
	    return "redirect:/arrivalSearchRequests";
	}
	
	@PostMapping("/airportArrivalSearch")
	public String saveSearch(Search search) throws Exception {
		searchService.save(search);
		searchService.initiateSearch(search);
		return "redirect:/airportArrivalSearch";
	}

			
	
	}


