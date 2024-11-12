package ru.tecocrm.site.synchronizer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KafkaMethodArgument {
    private final String name;
    private final String value;

    public KafkaMethodArgument(String name, Object value) {
        this.name = name;
        try {
            this.value = new ObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
