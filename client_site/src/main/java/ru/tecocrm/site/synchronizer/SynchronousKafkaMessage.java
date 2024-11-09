package ru.tecocrm.site.synchronizer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SynchronousKafkaMessage {
    private final String messageId;
}
