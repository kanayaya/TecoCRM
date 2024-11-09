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
public class KafkaWaiterService {
    private final Map<String, Waiter<JsonNode>> jsonsFromKafka = new HashMap<>();
    public CompletableFuture<Optional<JsonNode>> getFromKafka(String messageId) {
        Waiter<JsonNode> waiter = new Waiter<>();
        jsonsFromKafka.put(messageId, waiter);
        return CompletableFuture.supplyAsync(waiter::get);
    }
    @KafkaListener
    public void listen(String data, Acknowledgment ack) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(data);
        jsonsFromKafka.get(jsonNode.findValuesAsText("message_id").get(0)).set(jsonNode);
        ack.acknowledge();
    }
}
