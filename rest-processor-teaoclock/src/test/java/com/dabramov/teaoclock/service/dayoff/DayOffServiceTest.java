package com.dabramov.teaoclock.service.dayoff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.Objects;

@SpringBootTest
@RunWith(SpringRunner.class)
class DayOffServiceTest {
    @Autowired
    private DayOffService dayOffService;

    @Test
    void isDayOff() throws URISyntaxException {
        Assertions.assertTrue(dayOffService.isDayOff(1));
    }
}