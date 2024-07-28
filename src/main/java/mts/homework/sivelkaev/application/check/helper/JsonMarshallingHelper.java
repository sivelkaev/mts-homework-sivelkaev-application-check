package mts.homework.sivelkaev.application.check.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonMarshallingHelper {
    private final ObjectMapper mapper;

    @NonNull
    @SneakyThrows
    public <T> String marshall(@NotNull T javaMessage) {
        try {
            return mapper.writeValueAsString(javaMessage);
        } catch (Exception e) {
            log.error("Ошибка маршаллинга сообщения class={}", javaMessage.getClass(), e);
            throw e;
        }
    }

    @NonNull
    @SneakyThrows
    public <T> T unmarshall(Class<T> clazz, @NotNull String javaMessage) {
        try {
            return mapper.readValue(javaMessage, clazz);
        } catch (Exception e) {
            log.error("class: {} - Ошибка десериализации сообщения.", clazz.getName(), e);
            throw e;
        }
    }
}
