package com.dabramov.teaoclock.controller;

import com.dabramov.teaoclock.entity.Message;
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
@WebMvcTest(MessageController.class)
class MessageControllerTest {
    private String BASE_URL = "/api/messages";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AddresseeRepository addresseeRepository;
    @MockBean
    private MessageRepository messageRepository;

    @Test
    public void testGetMessage() throws Exception {
        Optional<Message> message = Optional.of(new Message());
        Mockito.when(messageRepository.findById("1")).thenReturn(message);
        MvcResult response = mockMvc.perform(get(BASE_URL + "/1")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(message));
    }

    @Test
    public void testGetMessages() throws Exception {
        List<Message> messages = Arrays.asList(new Message(), new Message());
        Mockito.when(messageRepository.findAll()).thenReturn(messages);
        MvcResult response = mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(messages));
    }

    @Test
    public void testGetMessagesByIds() throws Exception {
        List<Message> messages = Arrays.asList(new Message(), new Message(), new Message());
        Mockito.when(messageRepository.findAllById(Arrays.asList("1", "2"))).thenReturn(messages.subList(0, 2));
        MvcResult response = mockMvc.perform(get(BASE_URL).param("ids", "1,2")).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(messages.subList(0, 2)));
    }

    @Test
    public void testSaveMessage() throws Exception {
        Message message = new Message();
        Mockito.when(messageRepository.save(message)).thenReturn(message);
        MvcResult response = mockMvc.perform(post(BASE_URL).contentType("application/json").content(objectMapper.writeValueAsString(message))).andExpect(status().isOk()).andReturn();
        String stringContent = response.getResponse().getContentAsString();
        assertThat(stringContent).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(message));
    }

    @Test
    public void testDeleteMessage() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1")).andExpect(status().isOk());
    }
}