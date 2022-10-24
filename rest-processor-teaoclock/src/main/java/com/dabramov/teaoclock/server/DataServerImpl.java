package com.dabramov.teaoclock.server;

import com.dabramov.teaoclock.dto.AddresseeDto;
import com.dabramov.teaoclock.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataServerImpl implements DataServer {
    private final RestTemplate restTemplate;
    @Value("${data.server.url}")
    private String dataServerUrl;

    @Override
    @SneakyThrows(URISyntaxException.class)
    public AddresseeDto getAddressee(String id) {
        URI url = new URI(dataServerUrl).resolve("addressees/" + id);
        ResponseEntity<AddresseeDto> response = restTemplate.getForEntity(url, AddresseeDto.class);
        return response.getBody();
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public List<AddresseeDto> getAllAddressees() {
        URI url = new URI(dataServerUrl).resolve("addressees");
        ResponseEntity<AddresseeDto[]> response = restTemplate.getForEntity(url, AddresseeDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public List<AddresseeDto> getAllAddresseesByIds(List<String> ids) {
        URI url = new URI(dataServerUrl).resolve("addressees");
        Map<String, String> params = new HashMap<>();
        params.put("ids", String.join(",", ids));
        ResponseEntity<AddresseeDto[]> response = restTemplate.getForEntity(url.toString(), AddresseeDto[].class, params);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public AddresseeDto saveAddressee(AddresseeDto addressee) {
        URI url = new URI(dataServerUrl).resolve("addressees");
        ResponseEntity<AddresseeDto> response = restTemplate.postForEntity(url, addressee, AddresseeDto.class);
        return response.getBody();
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public void deleteAddressee(String id) {
        URI url = new URI(dataServerUrl).resolve("addressees/" + id);
        restTemplate.delete(url);
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public MessageDto getMessage(String id) {
        URI url = new URI(dataServerUrl).resolve("messages/" + id);
        ResponseEntity<MessageDto> response = restTemplate.getForEntity(url, MessageDto.class);
        return response.getBody();
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public List<MessageDto> getAllMessages() {
        URI url = new URI(dataServerUrl).resolve("messages");
        ResponseEntity<MessageDto[]> response = restTemplate.getForEntity(url, MessageDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public List<MessageDto> getAllMessagesByIds(List<String> ids) {
        URI url = new URI(dataServerUrl).resolve("messages");
        Map<String, String> params = new HashMap<>();
        params.put("ids", String.join(",", ids));
        ResponseEntity<MessageDto[]> response = restTemplate.getForEntity(url.toString(), MessageDto[].class, params);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public MessageDto saveMessage(MessageDto message) {
        URI url = new URI(dataServerUrl).resolve("messages");
        ResponseEntity<MessageDto> response = restTemplate.postForEntity(url, message, MessageDto.class);
        return response.getBody();
    }

    @Override
    @SneakyThrows(URISyntaxException.class)
    public void deleteMessage(String id) {
        URI url = new URI(dataServerUrl).resolve("messages/" + id);
        restTemplate.delete(url);
    }
}
