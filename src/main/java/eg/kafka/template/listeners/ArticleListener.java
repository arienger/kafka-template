package eg.kafka.template.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eg.kafka.template.domain.GenericArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implement ConsumerSeekAware if "playing" with the offset is necessary.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleListener implements ConsumerSeekAware {
    private final ObjectMapper objectMapper;
    /**
     * To get properties dynamically.
     */
    private final Environment environment;

    /**
     * Keep an instance of the consumer seek callback in case we want to be able to set the offsets for a group/topic interactively without stopping the consumer.
     * It is not possible to set the offset with console on an active group, so it might be a good idea.
     * It makes less sense to implement this functionality if this service is only batch oriented, since a short downtime is usually not a problem.
     */
    private ConsumerSeekCallback consumerSeekCallback;
    /**
     * List of active partitions for this group
     */
    private Set<TopicPartition> topicPartitions = new HashSet<>();


    /**
     * If we want to play with offsets at later time, we MUST catch the ConsumerSeekCallback in this method (NOT the onPartitionsAssigned)
     */
    @Override
    public void registerSeekCallback(ConsumerSeekCallback callback) {
        this.consumerSeekCallback = callback;
    }

    /**
     * Maybe we want to be able to interactively set offsets based on time? Something like this.
     * Now we can control the location in the topic based on WHEN the message was added to Kafka.
     */
    public void setOffsets(Date toTime) {
        consumerSeekCallback.seekToTimestamp(topicPartitions, toTime.getTime());
    }

    /**
     * In here we can play with offsets, but ONLY when a partition is assigned. This callback is thread-specific, and will NOT work outside the consumer thread.
     * We keep a reference to any registered partitions for later use.
     */
    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        topicPartitions.addAll(assignments.keySet());

        //So, we can perhaps force the offsets to start at beginning every time the application starts like this. This CAN
        //be useful for in-memory-cached data. Just read the data from start every time the app starts, and cache in hashmap or something
        boolean resetAtBeginningWanted = true;
        if (resetAtBeginningWanted) {
            for (TopicPartition topicPartition : assignments.keySet()) {
                callback.seekToBeginning(topicPartition.topic(), topicPartition.partition());
                log.info("Set offset to beginning for {}", topicPartition);
            }
        }

    }

    /**
     * Remove partitions from internal list
     */
    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        partitions.forEach(topicPartition -> this.topicPartitions.remove(topicPartition));
    }

    /**
     * Register the kafka listener for one or many topics.
     */
    @KafkaListener(topics = "NewTopic")
    public void receiveArticle(@Payload ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {

            final JsonNode rootNode = objectMapper.readTree(record.value());
            GenericArticle genericArticle = objectMapper.treeToValue(rootNode, GenericArticle.class);

            //Do something with the record. Call services or whatever.

            //When done, acknowledge in order to commit the group's offset in Kafka
            acknowledgment.acknowledge();
        } catch (Exception e) {
            //On exception, catch it and handle logging yourself. If exception is thrown back to the listener container,
            // there will be some "retry X times" stuff as default. We do NOT usually want to lose messages, so better handle the acknowledgement ourselves..
            log.error(e.getMessage(), e);
            acknowledgment.acknowledge();
            //acknowledgment.nack(Duration.ofSeconds(3));
        }
    }
}
