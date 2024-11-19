package ru.tecocrm.site.synchronizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SynchronousKafkaService {
    private final Map<String, Waiter<String>> waitersByMessageId = new HashMap<>();
    private final KafkaTemplate<String, String> sender;
    @Autowired
    public SynchronousKafkaService(KafkaTemplate<String, String> sender) {
        this.sender = sender;
    }

    @SneakyThrows
    public <T> CompletableFuture<Optional<KafkaMessage<T>>> sendAndGet(KafkaMethod method, JacksonTypeBuilder<T> type) {
        KafkaMessage<KafkaMethod> message = new KafkaMessage<>(method);
        sender.send("", new ObjectMapper().writeValueAsString(method));
        Waiter<String> waiter = new Waiter<>();
        waitersByMessageId.put(message.messageId(), waiter);
        return CompletableFuture.supplyAsync(() -> waiter.get().map(s -> {
            try {
                return new ObjectMapper().readValue(s, new JacksonTypeBuilder<>(KafkaMessage.class, type).build());
            } catch (JsonProcessingException e) {
                log.warn("", e);
                return null;
            }
        }));
    }
    public void send(KafkaMethod method) {
        KafkaMessage<KafkaMethod> message = new KafkaMessage<>(method);
    }
    @KafkaListener(topics = {"hueta"}, groupId = "qwe")
    public void listen(String data, Acknowledgment ack) throws JsonProcessingException {
        KafkaMessage message = new ObjectMapper().readValue(data, KafkaMessage.class);
        waitersByMessageId.get(message.messageId()).set(data);
        ack.acknowledge();
    }
}
