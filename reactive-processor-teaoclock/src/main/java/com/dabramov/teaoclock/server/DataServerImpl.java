package com.dabramov.teaoclock.server;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataServerImpl implements DataServer {
    @Autowired
    @Qualifier("dataClient")
    private WebClient webClient;

    @Override
    public Mono<AddresseeDto> getAddressee(String id) {
        return webClient.get()
                .uri("addressees/" + id)
                .retrieve()
                .bodyToMono(AddresseeDto.class);
    }

    @Override
    public Flux<AddresseeDto> getAllAddressees() {
        return webClient.get()
                .uri("addressees")
                .retrieve()
                .bodyToFlux(AddresseeDto.class);
    }

    @Override
    public Flux<AddresseeDto> getAllAddresseesByIds(String ids) {
        return webClient.get()
                .uri(builder -> builder.path("addressees").queryParam("ids", ids).build())
                .retrieve()
                .bodyToFlux(AddresseeDto.class);
    }

    @Override
    public Mono<AddresseeDto> saveAddressee(Mono<AddresseeDto> addressee) {
        return webClient.post()
                .uri("addressees")
                .body(addressee, AddresseeDto.class)
                .retrieve()
                .bodyToMono(AddresseeDto.class);
    }

    @Override
    public Mono<Void> deleteAddressee(String id) {
        return webClient.delete()
                .uri("addressees/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .then();
    }

    public Mono<MessageDto> getMessage(String id) {
        return webClient.get()
                .uri("messages/" + id)
                .retrieve()
                .bodyToMono(MessageDto.class);
    }

    @Override
    public Flux<MessageDto> getAllMessages() {
        return webClient.get()
                .uri("messages")
                .retrieve()
                .bodyToFlux(MessageDto.class);
    }

    @Override
    public Flux<MessageDto> getAllMessagesByIds(String ids) {
        return webClient.get()
                .uri(builder -> builder.path("messages").queryParam("ids", ids).build())
                .retrieve()
                .bodyToFlux(MessageDto.class);
    }

    public Mono<MessageDto> saveMessage(Mono<MessageDto> message) {
        return webClient.post()
                .uri("messages")
                .body(message, MessageDto.class)
                .retrieve()
                .bodyToMono(MessageDto.class);
    }

    public Mono<Void> deleteMessage(String id) {
        return webClient.delete()
                .uri("messages/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .then();
    }
}
