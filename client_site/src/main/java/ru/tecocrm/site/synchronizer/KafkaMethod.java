package ru.tecocrm.site.synchronizer;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * Data transferring object Для отправки в kafka указания другому микросервису что делать.
 * Должен быть преобразован в json строку.
 */
@Getter
@Builder
public class KafkaMethod {
    private final String topic;

    /**
     * Имя метода. Желательно полное повторение имени целевого java-метода.
     */
    private final String name;

    /**
     * Аргументы с которыми будет вызван метод.
     */
    @Singular("addArgument")
    private final List<KafkaMethodArgument> arguments;

    public KafkaMethod(String topic, String name, List<KafkaMethodArgument> arguments) {
        this.topic = topic;
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * Метод для добавления аргумента. Принимает любой объект в качестве значения аргумента.
     * @param name Имя нового аргумента
     * @param value Значение нового аргумента может быть любого типа, сложные объекты будут преобразованы в json строку
     */
    public void addArgument(String name, Object value) {
        arguments.add(new KafkaMethodArgument(name, value));
    }
}
