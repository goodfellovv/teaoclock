package com.dabramov.teaoclock.server;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.dto.MessageDto;

import java.util.List;

public interface DataServer {
    AddresseeDto getAddressee(String id);

    List<AddresseeDto> getAllAddressees();

    List<AddresseeDto> getAllAddresseesByIds(List<String> ids);

    AddresseeDto saveAddressee(AddresseeDto addressee);

    void deleteAddressee(String id);

    MessageDto getMessage(String id);

    List<MessageDto> getAllMessages();

    List<MessageDto> getAllMessagesByIds(List<String> ids);

    MessageDto saveMessage(MessageDto message);

    void deleteMessage(String id);
}
