package mts.homework.sivelkaev.application.check.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mts.homework.sivelkaev.application.check.helper.JsonMarshallingHelper;
import mts.homework.sivelkaev.application.check.kafka.KafkaProducer;
import mts.homework.sivelkaev.application.check.kafka.dto.request.ClientCheckRequest;
import mts.homework.sivelkaev.application.check.kafka.dto.response.ClientCheckResponse;
import mts.homework.sivelkaev.application.check.model.repository.ClientRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientCheckEventService {
    private final JsonMarshallingHelper jsonMarshallingHelper;
    private final ClientRepository clientRepository;
    private final KafkaProducer kafkaProducer;

    public void consume(String message) {
        ClientCheckRequest req = unmarshallMessage(message);

        var client = clientRepository.findByPassportNumber(req.getPassportNumber());

        ClientCheckResponse res;
        if (client.isEmpty()) {
            res = ClientCheckResponse.builder()
                    .applicationId(req.getApplicationId())
                    .status(1)
                    .statusDesc("Клиент не найден.")
                    .build();
        } else {
            res = ClientCheckResponse.builder()
                    .applicationId(req.getApplicationId())
                    .clientId(client.get().getId())
                    .status(0)
                    .build();
        }

        var resMessage = jsonMarshallingHelper.marshall(res);
        kafkaProducer.send("application.client.check.response", resMessage);
    }

    private ClientCheckRequest unmarshallMessage(String message) {
        if (StringUtils.isBlank(message)) {
            log.error("Отсутствует входящее сообщение.");
            throw new IllegalArgumentException();
        }

        try {
            return jsonMarshallingHelper.unmarshall(ClientCheckRequest.class, message);
        } catch (Exception e) {
            log.error("Ошибка десериализации сообщения типа {}.", ClientCheckRequest.class.getSimpleName(), e);
            throw new IllegalArgumentException(e);
        }
    }
}
