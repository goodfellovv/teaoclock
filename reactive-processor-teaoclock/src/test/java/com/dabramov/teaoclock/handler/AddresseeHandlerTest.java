package com.dabramov.teaoclock.handler;

import com.dabramov.teaoclock.dto.AddresseeDto;
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
public class AddresseeHandlerTest {
    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ApplicationContext context;
    @MockBean
    private DataServer dataServer;

    @Test
    public void testGetAllAddresses() {
        AddresseeDto[] data = {new AddresseeDto(), new AddresseeDto(), new AddresseeDto()};
        Mono<AddresseeDto[]> mono = Mono.just(data);
        Mockito.when(dataServer.getAllAddressees()).thenReturn(mono);
        this.webClient.get()
                .uri("http://localhost/addressees/getAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AddresseeDto[].class)
                .isEqualTo(data);
    }

    @Test
    public void testGetAllAddressesByIds() {
        AddresseeDto[] data = {new AddresseeDto(), new AddresseeDto(), new AddresseeDto()};
        Mono<AddresseeDto[]> mono = Mono.just(data);
        Mockito.when(dataServer.getAllAddresseesByIds("1,2")).thenReturn(mono);
        this.webClient.get()
                .uri("http://localhost/addressees/getAll?ids=1,2")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AddresseeDto[].class)
                .isEqualTo(data);
    }

    @Test
    public void testSaveAddressee() {
        AddresseeDto data = new AddresseeDto();
        Mono<AddresseeDto> mono = Mono.just(data);
        Mockito.when(dataServer.saveAddressee(Mockito.any())).thenReturn(mono);
        this.webClient.post()
                .uri("http://localhost/addressees/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(data))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(AddresseeDto.class)
                .isEqualTo(data);
    }

    @Test
    public void testDeleteAddressee() {
        Mockito.when(dataServer.deleteAddressee(Mockito.anyString())).thenReturn(Mono.empty());
        this.webClient.delete()
                .uri("http://localhost/addressees" + "/1")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
