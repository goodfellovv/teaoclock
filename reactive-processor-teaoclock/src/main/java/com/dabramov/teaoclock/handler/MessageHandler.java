package com.dabramov.teaoclock.handler;

import com.dabramov.teaoclock.dto.MessageDto;
import com.dabramov.teaoclock.server.DataServer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageHandler {
    @Autowired
    private final DataServer dataServer;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        Optional<String> ids = serverRequest.queryParam("ids");
        ServerResponse.BodyBuilder bodyBuilder = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON);
        return ids.map(s -> bodyBuilder.body(dataServer.getAllMessagesByIds(s), MessageDto.class))
                .orElseGet(() -> bodyBuilder.body(dataServer.getAllMessages(), MessageDto.class));
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        Mono<MessageDto> messageDtoMono = serverRequest.bodyToMono(MessageDto.class);
        ServerResponse.BodyBuilder bodyBuilder = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON);
        return bodyBuilder.body(dataServer.saveMessage(messageDtoMono), MessageDto.class);
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        ServerResponse.BodyBuilder bodyBuilder = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON);
        return bodyBuilder.build(dataServer.deleteMessage(id));
    }
}
