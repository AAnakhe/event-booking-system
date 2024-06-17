package com.ajavacode.dev.eventbookingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ajavacode.dev.eventbookingapp.dto.EventDto;
import com.ajavacode.dev.eventbookingapp.enums.Category;
import com.ajavacode.dev.eventbookingapp.model.Event;
import com.ajavacode.dev.eventbookingapp.repository.EventRepository;
import com.ajavacode.dev.eventbookingapp.service.EventService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser
    public void testCreateEvent_returns200() throws Exception {
    EventDto payload = new EventDto("International Chess Challenge", "2024-08-03",
            159, "Chess competition", "Game");

        Event event = new Event();
        event.setName(payload.name());
        event.setDate(LocalDate.parse(payload.date()));
        event.setAvailableAttendeesCount(payload.availableAttendeesCount());
        event.setEventDescription(payload.description());
        event.setCategory(Category.valueOf(payload.category().toUpperCase()));

        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(event);
        Mockito.when(eventService.createEvent(Mockito.any(EventDto.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Event created successfully"))
                .andExpect(jsonPath("$.data.name").value(event.getName()))
                .andExpect(jsonPath("$.data.availableAttendeesCount").value(event.getAvailableAttendeesCount()))
                .andExpect(jsonPath("$.data.eventDescription").value(event.getEventDescription()))
                .andExpect(jsonPath("$.data.category").value(event.getCategory().toString()));

    }

    @Test
    @WithMockUser
    public void testSearchEvents_returns200() throws Exception {
         String name = "Night of Bliss";
         LocalDate startDate = LocalDate.parse("2024-08-04");
         String endDate = "2025-06-10";
         Integer availableAttendeesCount = 1000;
         String category = "Concert";
         String description = "A night with Jesus";


        List<Event> events = new ArrayList<>();
        Event event = new Event();
            event.setName(name);
            event.setDate(startDate);
            event.setAvailableAttendeesCount(availableAttendeesCount);
            event.setEventDescription(description);
            event.setCategory(Category.valueOf(category.toUpperCase()));
            events.add(event);

        Mockito.when(eventService.searchEvent(name, startDate.toString(), endDate, category)).thenReturn(events);

        mockMvc.perform(get("/events")
                        .param("name", name)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate)
                        .param("category", category)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Events retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(event.getId()))
                .andExpect(jsonPath("$.data[0].name").value(event.getName()))
                .andExpect(jsonPath("$.data[0].date").value(event.getDate().toString()))
                .andExpect(jsonPath("$.data[0].availableAttendeesCount").value(event.getAvailableAttendeesCount()))
                .andExpect(jsonPath("$.data[0].eventDescription").value(event.getEventDescription()))
                .andExpect(jsonPath("$.data[0].category").value(event.getCategory().toString()));

    }
}
