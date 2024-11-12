package ru.tecocrm.site.synchronizer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transferring object Для отправки в kafka указания другому микросервису что делать.
 * Должен быть преобразован в json строку.
 */
@Getter
public class KafkaMethod {

    /**
     * Имя метода. Желательно полное повторение имени целевого java-метода.
     */
    private final String name;

    /**
     * Аргументы с которыми будет вызван метод.
     */
    private final List<KafkaMethodArgument> arguments = new ArrayList<>();

    public KafkaMethod(String name) {
        this.name = name;
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
