package ru.tecocrm.site.synchronizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class SynchronousKafkaService {
    private final Map<String, Waiter<String>> jsonsFromKafka = new HashMap<>();
    public <T> CompletableFuture<Optional<KafkaMessage<T>>> sendAndGet(KafkaMethod method, JacksonTypeBuilder<T> type) {
        KafkaMessage<KafkaMethod> message = new KafkaMessage<>(method);
        Waiter<String> waiter = new Waiter<>();
        jsonsFromKafka.put(message.messageId(), waiter);
        return CompletableFuture.supplyAsync(() -> waiter.get().map(s -> {
            try {
                return new ObjectMapper().readValue(s, new JacksonTypeBuilder<>(KafkaMessage.class, type).build());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }));
    }
    public void send(KafkaMethod method) {
        KafkaMessage<KafkaMethod> message = new KafkaMessage<>(method);
    }
    @KafkaListener(topics = {"hueta"}, groupId = "qwe")
    public void listen(String data, Acknowledgment ack) throws JsonProcessingException {
        KafkaMessage message = new ObjectMapper().readValue(data, KafkaMessage.class);
        jsonsFromKafka.get(message.messageId()).set(data);
        ack.acknowledge();
    }
}
