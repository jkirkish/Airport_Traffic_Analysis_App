package com.coderscampus.flightTrack;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
//https://openskynetwork.github.io/opensky-api/rest.html
public class OpenSkyIntegration {
	@Test
	public void callOpenSkyExample() {
		
		RestTemplate rt = new RestTemplate();
	    URI uri = UriComponentsBuilder.fromHttpUrl("https://opensky-network.org/api/states/all")
	    					 .build()
	    					 .toUri();
	    
	    ResponseEntity<String>response = rt.getForEntity(uri, String.class);
	    System.out.println(response);
	}@Test
	public void callOpenSkyExampleAllflights() throws JsonMappingException, JsonProcessingException {
//		begin
//
//		integer
//
//		Start of time interval to retrieve flights for as Unix time (seconds since epoch)
//
//		end
//      https://www.epochconverter.com/
//		integer
//		End of time interval to retrieve flights for as Unix time (seconds since epoch)
		RestTemplate rt = new RestTemplate();
	    URI uri = UriComponentsBuilder.fromHttpUrl("https://opensky-network.org/api/flights/all")
	    					 .queryParam("begin","1685991864")
	    					 .queryParam("end","1685999064")
	    					 .build()
	    					 .toUri();
	    
	    ResponseEntity<String>response = rt.getForEntity(uri, String.class);
    	System.out.println(response);
	}
	
@Test
public void openskyExampleArrivalAirport() throws IOException{
		/*Retrieve flights for a certain airport which arrived within a given time interval [begin, end]. 
		 * If no flights are found for the given period, HTTP stats 404 - Not found is returned with an 
		 * empty response body.
		 * The given time interval must not be larger than seven days!
		 */
		RestTemplate rt = new RestTemplate();
	    URI uri = UriComponentsBuilder.fromHttpUrl("https://opensky-network.org/api/flights/arrival")
	    					 .queryParam("airport","KIAD")
	    					 .queryParam("begin","1685832444")
	    					 .queryParam("end","1686005244")
	    					 .build()
	    					 .toUri();
	    
	    
	   
	    	ResponseEntity<String>response = rt.getForEntity(uri, String.class);
	    	System.out.println(response);
	}
@Test
public void openskyExampleDepartureAirport() throws IOException{
		/*Retrieve flights for a certain airport which departure within a given time interval [begin, end]. 
		 * If no flights are found for the given period, HTTP stats 404 - Not found is returned with an 
		 * empty response body.
		 * The given time interval must not be larger than seven days!
		 */
		RestTemplate rt = new RestTemplate();
	    URI uri = UriComponentsBuilder.fromHttpUrl("https://opensky-network.org/api/flights/departure")
	    					 .queryParam("airport","kCMH")
	    					 .queryParam("begin","1686174444")
	    					 .queryParam("end","1686178044")
	    					 .build()
	    					 .toUri();
	    
	    
	    ResponseEntity<String>response = rt.getForEntity(uri, String.class);
    	System.out.println(response);
	}
@Test
public void openskyExampleforAircraft() throws IOException{
		RestTemplate rt = new RestTemplate();
		URI uri = UriComponentsBuilder.fromHttpUrl("https://opensky-network.org/api/flights/aircraft")
									  .queryParam("icao24","a09a01")
									  .queryParam("begin","1685898028")
									  .queryParam("end","1685984428")
									  .build()
									  .toUri();
		ResponseEntity<String>response = rt.getForEntity(uri, String.class);
    	System.out.println(response);
}
}