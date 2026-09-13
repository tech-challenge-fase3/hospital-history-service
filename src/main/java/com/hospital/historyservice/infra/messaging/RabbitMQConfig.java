package com.hospital.historyservice.infra.messaging;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    static final String APPOINTMENT_EXCHANGE = "hospital.appointments";
    static final String HISTORY_CREATED_QUEUE = "hospital.history.appointments.created";
    static final String HISTORY_UPDATED_QUEUE = "hospital.history.appointments.updated";

    @Bean
    TopicExchange appointmentExchange() {
        return new TopicExchange(APPOINTMENT_EXCHANGE, true, false);
    }

    @Bean
    Queue historyCreatedQueue() {
        return new Queue(HISTORY_CREATED_QUEUE, true);
    }

    @Bean
    Queue historyUpdatedQueue() {
        return new Queue(HISTORY_UPDATED_QUEUE, true);
    }

    @Bean
    Binding historyCreatedBinding(
            @Qualifier("historyCreatedQueue") Queue historyCreatedQueue,
            TopicExchange appointmentExchange) {
        return BindingBuilder.bind(historyCreatedQueue).to(appointmentExchange).with("appointment.created");
    }

    @Bean
    Binding historyUpdatedBinding(
            @Qualifier("historyUpdatedQueue") Queue historyUpdatedQueue,
            TopicExchange appointmentExchange) {
        return BindingBuilder.bind(historyUpdatedQueue).to(appointmentExchange).with("appointment.updated");
    }

    @Bean
    JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setDefaultRequeueRejected(true);
        return factory;
    }
}
