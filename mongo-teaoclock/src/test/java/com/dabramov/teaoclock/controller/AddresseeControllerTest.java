package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.entity.Addressee;
import com.dabramov.teaoclock.repository.AddresseeRepository;
import com.dabramov.teaoclock.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddresseeController.class)
class AddresseeControllerTest {
    private final String BASE_URL = "/api/addressees";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddresseeRepository addresseeRepository;
    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void testGetAddressee() throws Exception {
        Optional<Addressee> addressee = Optional.of(new Addressee());
        Mockito.when(addresseeRepository.findById("1")).thenReturn(addressee);
        MvcResult response = mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressee));
    }

    @Test
    public void testGetAllAddresses() throws Exception {
        List<Addressee> addressees = Arrays.asList(new Addressee(), new Addressee());
        Mockito.when(addresseeRepository.findAll()).thenReturn(addressees);
        MvcResult response = mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressees));
    }

    @Test
    public void testGetAllAddressesByIds() throws Exception {
        List<Addressee> addressees = Arrays.asList(new Addressee(), new Addressee(), new Addressee());
        Mockito.when(addresseeRepository.findAllById(Arrays.asList("1", "2"))).thenReturn(addressees.subList(0, 2));
        MvcResult response = mockMvc.perform(get(BASE_URL).param("ids", "1,2")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressees.subList(0, 2)));
    }

    @Test
    public void testSaveAddressee() throws Exception {
        Addressee addressee = new Addressee();
        Mockito.when(addresseeRepository.save(addressee)).thenReturn(addressee);
        MvcResult response = mockMvc.perform(post(BASE_URL).contentType("application/json").content(objectMapper.writeValueAsString(addressee))).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressee));
    }

    @Test
    public void testDeleteAddressee() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
    }
}