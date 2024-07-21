package mts.homework.sivelkaev.application.check.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mts.homework.sivelkaev.application.check.kafka.service.ClientCheckEventService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientCheckEventConsumer {
    private final ClientCheckEventService clientCheckEventService;

    @KafkaListener(topics = "application.client.check",
                   groupId = "application")
    public void consume(String message) {
        log.info("Принято сообщение из Kafka {}.", message);

        clientCheckEventService.consume(message);
    }
}
