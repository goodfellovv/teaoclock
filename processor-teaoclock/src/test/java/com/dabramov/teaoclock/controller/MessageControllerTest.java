package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.server.DataServer;
import com.dabramov.teaoclock.dto.MessageDto;
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
@WebMvcTest(MessageController.class)
class MessageControllerTest {
    private String BASE_URL = "/messages";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DataServer dataServer;

    @Test
    void getAllMessages() throws Exception {
        List<MessageDto> messages = Arrays.asList(new MessageDto(), new MessageDto());
        Mockito.when(dataServer.getAllMessages()).thenReturn(messages);
        MvcResult response = mockMvc.perform(get(BASE_URL + "/getAll")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(messages));
    }

    @Test
    void saveMessage() throws Exception {
        MessageDto message = new MessageDto();
        Mockito.when(dataServer.saveMessage(message)).thenReturn(message);
        MvcResult response = mockMvc.perform(post(BASE_URL + "/save").contentType("application/json").content(objectMapper.writeValueAsString(message))).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(message));
    }

    @Test
    void deleteMessage() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
    }
}