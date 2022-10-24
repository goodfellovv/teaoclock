package com.dabramov.teaoclock.handler;

import com.dabramov.teaoclock.dto.MessageDto;
import com.dabramov.teaoclock.server.DataServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MessageHandlerTest {
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ApplicationContext context;
    @MockBean
    private DataServer dataServer;

    @Test
    public void testGetAllMessages() {
        MessageDto[] data = {new MessageDto(), new MessageDto(), new MessageDto()};
        Mono<MessageDto[]> mono = Mono.just(data);
        Mockito.when(dataServer.getAllMessages()).thenReturn(mono);
        this.webClient.get()
                .uri("http://localhost/messages/getAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MessageDto[].class)
                .isEqualTo(data);
    }

    @Test
    public void testGetAllMessagesByIds() {
        MessageDto[] data = {new MessageDto(), new MessageDto(), new MessageDto()};
        Mono<MessageDto[]> mono = Mono.just(data);
        Mockito.when(dataServer.getAllMessagesByIds("1,2")).thenReturn(mono);
        this.webClient.get()
                .uri("http://localhost/messages/getAll?ids=1,2")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MessageDto[].class)
                .isEqualTo(data);
    }

    @Test
    public void testSaveMessage() {
        MessageDto data = new MessageDto();
        Mono<MessageDto> mono = Mono.just(data);
        Mockito.when(dataServer.saveMessage(Mockito.any())).thenReturn(mono);
        this.webClient.post()
                .uri("http://localhost/messages/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(data))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(MessageDto.class)
                .isEqualTo(data);
    }

    @Test
    public void testDeleteMessage() {
        Mockito.when(dataServer.deleteMessage(Mockito.anyString())).thenReturn(Mono.empty());
        this.webClient.delete()
                .uri("http://localhost/messages" + "/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
