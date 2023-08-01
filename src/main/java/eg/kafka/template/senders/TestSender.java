package eg.kafka.template.senders;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * Just an example for sending messages to Kafka.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TestSender {
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Send a testmessage. Topics in Kafka can be either "compacted" or "non compacted". Compacted topics must have a key,
     * and the messages will live forever in latest version.
     * In a "non compacted" topic messages will have a ttl, and the key has no effect other than information
     */
    public void sendTestMessage(JsonNode someMessage, String key) {
        ProducerRecord<String, String> record = new ProducerRecord<>("NewTopic", key, someMessage.toString());
        try {
            //Sending messages is async, so we must either wait for the future or somehow register a listener on it (unless delivery of the message is very unimportant)
            kafkaTemplate.send(record).get();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
    }

}
