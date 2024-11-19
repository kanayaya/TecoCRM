package ru.tecocrm.site.synchronizer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.stream.Stream;

public class JacksonTypeBuilder<T> {
    private final Class<? extends T> type;
    private final JacksonTypeBuilder<?>[] parameters;

    public JacksonTypeBuilder(Class<? extends T> type, JacksonTypeBuilder<?>... parameters) {
        this.type = type;
        this.parameters = parameters;
    }
    public JavaType build() {
        return new ObjectMapper().getTypeFactory().constructParametricType(
                type,
                Stream.of(parameters)
                .map(JacksonTypeBuilder::build)
                .toArray(JavaType[]::new));
    }
}
