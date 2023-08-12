package com.coderscampus.flightTrack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/register"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/errorLogin"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/index"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/registerConfirmation"))
            .andExpect(status().isOk());

        
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminEndpoints() throws Exception {
        mockMvc.perform(get("/adminPage"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/arrivalSearchRequests"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk());

       
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUserEndpoints() throws Exception {
        mockMvc.perform(get("/airportArrivalSearch"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/arrival"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/arrivals"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/user"))
            .andExpect(status().isOk());

       
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testAuthenticatedEndpoints() throws Exception {
        mockMvc.perform(get("/departure.html"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/departures"))
            .andExpect(status().isOk());

        mockMvc.perform(get("/editSearch"))
            .andExpect(status().isOk());

     
    }
}

