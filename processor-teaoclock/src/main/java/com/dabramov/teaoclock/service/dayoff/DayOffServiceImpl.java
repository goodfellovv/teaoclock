package com.dabramov.teaoclock.service.dayoff;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;

@Service
@Profile({"quartz", "scheduled"})
@RequiredArgsConstructor
public class DayOffServiceImpl implements DayOffService {
    private byte[] dayOffCalendar = new byte[] {};
    private final RestTemplate restTemplate;

    @EventListener(ApplicationReadyEvent.class)
    @SneakyThrows
    private void fillDayOffCache() {
        this.dayOffCalendar = loadWorkCalendar();
    }

    private byte[] loadWorkCalendar() throws URISyntaxException {
        byte[] dayOffCalendar = new byte[] {};
        URI url = new URI("https://isdayoff.ru/api/getdata?delimeter=%2C&year=" + Year.now());
        String response = restTemplate.getForEntity(url, String.class).getBody();
        if(Objects.nonNull(response)){
            String[] numberStr = response.split(",");
            dayOffCalendar = new byte[numberStr.length];
            for (int i = 0; i < numberStr.length; i++) {
                dayOffCalendar[i] = Byte.parseByte(numberStr[i]);
            }
        }
        return dayOffCalendar;
    }

    @Override
    public Boolean isDayOff(int dayOfYear) {
        return dayOffCalendar.length >= dayOfYear && dayOffCalendar[dayOfYear] == 1;
    }
}
