package com.dabramov.teaoclock.server;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.dto.MessageDto;
import reactor.core.publisher.Mono;

public interface DataServer {
    Mono<AddresseeDto> getAddressee(String id);

    Mono<AddresseeDto[]> getAllAddressees();

    Mono<AddresseeDto[]> getAllAddresseesByIds(String ids);

    Mono<AddresseeDto> saveAddressee(Mono<AddresseeDto> addressee);

    Mono<Void> deleteAddressee(String id);

    Mono<MessageDto> getMessage(String id);

    Mono<MessageDto[]> getAllMessages();

    Mono<MessageDto[]> getAllMessagesByIds(String ids);

    Mono<MessageDto> saveMessage(Mono<MessageDto> message);

    Mono<Void> deleteMessage(String id);
}
