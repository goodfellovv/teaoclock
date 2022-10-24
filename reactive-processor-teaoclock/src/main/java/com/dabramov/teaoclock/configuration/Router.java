package com.dabramov.teaoclock.configuration;

import com.dabramov.teaoclock.handler.AddresseeHandler;
import com.dabramov.teaoclock.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Router {
    @Bean
    public RouterFunction<ServerResponse> addresseeRoute(AddresseeHandler addresseeHandler) {
        return RouterFunctions.route(GET("/addressees/getAll"), addresseeHandler::getAll)
                .andRoute(POST("/addressees/save"), addresseeHandler::save)
                .andRoute(DELETE("/addressees/{id}"), addresseeHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> messageRoute(MessageHandler messageHandler) {
        return RouterFunctions.route(GET("/messages/getAll"), messageHandler::getAll)
                .andRoute(POST("/messages/save"), messageHandler::save)
                .andRoute(DELETE("/messages/{id}"), messageHandler::delete);
    }
}
