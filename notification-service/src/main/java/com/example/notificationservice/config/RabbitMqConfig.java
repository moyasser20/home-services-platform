package com.example.notificationservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String EXCHANGE = "booking.events.exchange";
    public static final String CONFIRMED_QUEUE = "booking.confirmed.queue";
    public static final String FAILED_QUEUE = "booking.failed.queue";
    public static final String CONFIRMED_ROUTING_KEY = "booking.confirmed";
    public static final String FAILED_ROUTING_KEY = "booking.failed";

    @Bean
    public DirectExchange bookingExchange() {
        return new DirectExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue bookingConfirmedQueue() {
        return new Queue(CONFIRMED_QUEUE, true);
    }

    @Bean
    public Queue bookingFailedQueue() {
        return new Queue(FAILED_QUEUE, true);
    }

    @Bean
    public Binding confirmedBinding(Queue bookingConfirmedQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(bookingConfirmedQueue).to(bookingExchange).with(CONFIRMED_ROUTING_KEY);
    }

    @Bean
    public Binding failedBinding(Queue bookingFailedQueue, DirectExchange bookingExchange) {
        return BindingBuilder.bind(bookingFailedQueue).to(bookingExchange).with(FAILED_ROUTING_KEY);
    }
}
