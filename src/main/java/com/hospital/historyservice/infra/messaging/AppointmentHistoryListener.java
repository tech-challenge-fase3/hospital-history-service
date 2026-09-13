package com.hospital.historyservice.infra.messaging;

import java.io.IOException;

import com.hospital.historyservice.infra.persistence.AppointmentEvent;
import com.hospital.historyservice.infra.persistence.AppointmentHistory;
import com.hospital.historyservice.infra.persistence.AppointmentHistoryRepository;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class AppointmentHistoryListener {

    private final AppointmentHistoryRepository repository;

    public AppointmentHistoryListener(AppointmentHistoryRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConfig.HISTORY_CREATED_QUEUE)
    public void onCreated(AppointmentEvent event, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        process(event, channel, deliveryTag);
    }

    @RabbitListener(queues = RabbitMQConfig.HISTORY_UPDATED_QUEUE)
    public void onUpdated(AppointmentEvent event, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        process(event, channel, deliveryTag);
    }

    private void process(AppointmentEvent event, Channel channel, long deliveryTag)
            throws IOException {
        try {
            var appointmentId = event.appointmentId() != null ? event.appointmentId() : event.id();
            if (appointmentId == null || event.updatedAt() == null) {
                channel.basicReject(deliveryTag, false);
                return;
            }

            repository.findById(appointmentId).ifPresentOrElse(
                    history -> {
                        if (history.getUpdatedAt() == null
                        || !history.getUpdatedAt().isAfter(event.updatedAt())) {
                            history.update(event.patientId(), event.doctorId(), event.appointmentDate(),
                                    event.status(), event.notes(), event.createdAt(), event.updatedAt());
                            repository.save(history);
                        }
                    },
                    () -> repository.save(new AppointmentHistory(appointmentId, event))
            );
            channel.basicAck(deliveryTag, false);
        } catch (RuntimeException exception) {
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
