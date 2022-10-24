package com.dabramov.teaoclock.service.dayoff;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Objects;

@Service
@Profile({"quartz", "scheduled"})
public class DayOffServiceImpl implements DayOffService {
    private byte[] dayOffCalendar = new byte[]{};

    @Autowired
    @Qualifier("dayOffClient")
    private WebClient webClient;

    @EventListener(ApplicationReadyEvent.class)
    @SneakyThrows
    private void fillDayOffCache() {
        this.dayOffCalendar = loadWorkCalendar();
    }

    private byte[] loadWorkCalendar() throws URISyntaxException {
        byte[] dayOffCalendar = new byte[]{};
        String response = webClient.get().retrieve().bodyToMono(String.class).block();
        if (Objects.nonNull(response)) {
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
