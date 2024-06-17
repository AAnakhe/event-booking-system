package com.musalasoft.dev.eventbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musalasoft.dev.eventbookingapp.dto.TicketRequest;
import com.musalasoft.dev.eventbookingapp.enums.Category;
import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.model.Reservation;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.repository.ReservationRepository;
import com.musalasoft.dev.eventbookingapp.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private User userTest;

    @MockBean
    private Event eventTest;

    @MockBean
    private Reservation reservationTest;

    @BeforeEach
    public void setUp() {
        userTest = new User();
        userTest.setId(1);
        userTest.setName("Abundance A");
        userTest.setEmail("id3velope@gmail.com");

        eventTest = new Event();
        eventTest.setId(1);
        eventTest.setName("Night of Bliss");
        eventTest.setDate(LocalDate.parse("2024-08-04"));
        eventTest.setAvailableAttendeesCount(1000);
        eventTest.setEventDescription("A night the King of Kings");
        eventTest.setCategory(Category.valueOf("Concert".toUpperCase()));

        reservationTest = new Reservation();
        reservationTest.setId(1);
        reservationTest.setUser(userTest);
        reservationTest.setEvent(eventTest);
        reservationTest.setAttendeesCount(30);

        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(reservationTest);

    }


    @Test
    @WithMockUser
    public void reserveTickets_Returns200() throws Exception {
        Integer eventID = 1;

        Mockito.when(reservationService.saveReservation(Mockito.anyInt(), Mockito.any(TicketRequest.class)))
                .thenReturn(reservationTest);

        mockMvc.perform(post("/events/{eventId}/tickets", eventID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationTest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Successfully reserved tickets"))
                .andExpect(jsonPath("$.data.id").value(reservationTest.getId()))
                .andExpect(jsonPath("$.data.attendeesCount").value(reservationTest.getAttendeesCount()))
                .andExpect(jsonPath("$.data.user.id").value(userTest.getId()))
                .andExpect(jsonPath("$.data.user.name").value(userTest.getName()))
                .andExpect(jsonPath("$.data.user.email").value(userTest.getEmail()))
                .andExpect(jsonPath("$.data.event.id").value(eventTest.getId()))
                .andExpect(jsonPath("$.data.event.name").value(eventTest.getName()))
                .andExpect(jsonPath("$.data.event.date").value(eventTest.getDate().toString()))
                .andExpect(jsonPath("$.data.event.availableAttendeesCount").value(eventTest.getAvailableAttendeesCount()))
                .andExpect(jsonPath("$.data.event.eventDescription").value(eventTest.getEventDescription()))
                .andExpect(jsonPath("$.data.event.category").value(eventTest.getCategory().toString()));
    }


    @Test
    @WithMockUser
    public void viewBookedEvents_returns200() throws Exception {

        List<Reservation> reservationList = new ArrayList<>();

        Mockito.when(reservationService.viewBookedEvents()).thenReturn(reservationList);
        mockMvc.perform(get("/events/my-events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Booked events retrieved successfully"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @WithMockUser
    public void cancelReservations_returns200() throws Exception {
        Integer reservationId = 1;

        mockMvc.perform(delete("/events/{reservationID}/tickets", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Reservation cancelled successfully"));

    }

}