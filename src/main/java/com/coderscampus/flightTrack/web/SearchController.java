package com.coderscampus.flightTrack.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.coderscampus.flightTrack.domain.Search;
import com.coderscampus.flightTrack.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping("/arrivalSearchRequests")
	public String getallSearchRequests(ModelMap model) {
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
		// searchService.initiateSearch(search);
		return "redirect:/search/" + search.getId();
	}

	@PostMapping("/search/delete/{id}")
	public String deleteSearch(@PathVariable("id") Long id) {
		searchService.delete(id);
		return "redirect:/arrivalSearchRequests";
	}

	@PostMapping("/arrivalSearch/deleteAllRequests")
	public String deleteAllRequests() {
		searchService.deleteAllRequests();
		return "redirect:/arrivalSearchRequests";
	}

	@PostMapping("/airportArrivalSearch")
	public String saveSearch(@RequestBody Search search) throws Exception {
		// Set the search type and airport as you were doing
		String type = search.getSearchType();

		// Save the search object
		searchService.save(search);

		// Continue with your logic for redirection and search initiation
		String url;
		if (type.equals("arrival")) {
			url = "arrivals";
		} else {
			url = "departures";
		}
		searchService.initiateSearch(search);

		return "redirect:/" + url;
	}
	
//	@PostMapping("/airportArrivalSearch")
//    public String handleSearchRequest(@RequestBody Search search, Model model) {
//        try {
//            // Set the search type and airport as you were doing
//            String type = search.getSearchType();
//
//            // Save the search object
//            searchService.save(search);
//
//            // Continue with your logic for redirection and search initiation
//            String url;
//            if (type.equals("arrival")) {
//                url = "arrivals";
//            } else {
//                url = "departures";
//            }
//            searchService.initiateSearch(search);
//
//            return "redirect:/" + url;
//        } catch (Exception e) {
//            // Handle any exceptions
//            model.addAttribute("errorMessage", "An error occurred");
//            return "404error"; // Return an error view
//        }
//    }

}