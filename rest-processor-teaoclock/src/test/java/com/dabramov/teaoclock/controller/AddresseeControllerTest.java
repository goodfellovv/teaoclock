package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.server.DataServer;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AddresseeController.class)
class AddresseeControllerTest {
    private final String BASE_URL = "/addressees";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DataServer dataServer;

    @Test
    void getAllAddresses() throws Exception {
        List<AddresseeDto> addressees = Arrays.asList(new AddresseeDto(), new AddresseeDto());
        Mockito.when(dataServer.getAllAddressees()).thenReturn(addressees);
        MvcResult response = mockMvc.perform(get(BASE_URL + "/getAll")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressees));
    }

    @Test
    void saveAddressee() throws Exception {
        AddresseeDto addressees = new AddresseeDto();
        Mockito.when(dataServer.saveAddressee(addressees)).thenReturn(addressees);
        MvcResult response = mockMvc.perform(post(BASE_URL + "/save").contentType("application/json").content(objectMapper.writeValueAsString(addressees))).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(addressees));
    }

    @Test
    void deleteAddressee() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
    }
}