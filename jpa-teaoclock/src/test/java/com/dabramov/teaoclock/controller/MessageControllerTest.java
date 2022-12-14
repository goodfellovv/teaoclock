package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.entity.Message;
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
@WebMvcTest(MessageController.class)
class MessageControllerTest {
    private String BASE_URL = "/api/messages";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageRepository repository;

    @Test
    public void getMessage() throws Exception {
        Optional<Message> message = Optional.of(new Message());
        Mockito.when(repository.findById(1L)).thenReturn(message);
        MvcResult response = mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(message));
    }

    @Test
    public void getMessages() throws Exception {
        List<Message> messages = Arrays.asList(new Message(), new Message());
        Mockito.when(repository.findAll()).thenReturn(messages);
        MvcResult response = mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(messages));
    }

    @Test
    public void getMessagesByIds() throws Exception {
        List<Message> messages = Arrays.asList(new Message(), new Message(), new Message());
        Mockito.when(repository.findAllById(Arrays.asList(1L, 2L))).thenReturn(messages.subList(0, 2));
        MvcResult response = mockMvc.perform(get(BASE_URL).param("ids", "1,2")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(messages.subList(0, 2)));
    }

    @Test
    public void saveMessage() throws Exception {
        Message message = new Message();
        Mockito.when(repository.save(message)).thenReturn(message);
        MvcResult response = mockMvc.perform(post(BASE_URL).contentType("application/json").content(objectMapper.writeValueAsString(message))).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(message));
    }

    @Test
    public void deleteMessage() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
    }
}