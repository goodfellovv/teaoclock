package com.dabramov.teaoclock.server;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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
    public Mono<AddresseeDto[]> getAllAddressees() {
        return webClient.get()
                .uri("addressees")
                .retrieve()
                .bodyToMono(AddresseeDto[].class);
    }

    @Override
    public Mono<AddresseeDto[]> getAllAddresseesByIds(String ids) {
        return webClient.get()
                .uri(builder -> builder.path("addressees").queryParam("ids", ids).build())
                .retrieve()
                .bodyToMono(AddresseeDto[].class);
    }

    @Override
    public Mono<AddresseeDto> saveAddressee(Mono<AddresseeDto> addressee) {
        return webClient.post()
                .uri("addressees")
                .bodyValue(addressee)
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
    public Mono<MessageDto[]> getAllMessages() {
        return webClient.get()
                .uri("messages")
                .retrieve()
                .bodyToMono(MessageDto[].class);
    }

    @Override
    public Mono<MessageDto[]> getAllMessagesByIds(String ids) {
        return webClient.get()
                .uri(builder -> builder.path("messages").queryParam("ids", ids).build())
                .retrieve()
                .bodyToMono(MessageDto[].class);
    }

    public Mono<MessageDto> saveMessage(Mono<MessageDto> message) {
        return webClient.post()
                .uri("messages")
                .bodyValue(message)
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
