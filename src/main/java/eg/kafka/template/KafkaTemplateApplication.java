package eg.kafka.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eg.kafka.template.senders.TestSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaTemplateApplication {
    private static ConfigurableApplicationContext context;


    public static void main(String[] args) {

        context = SpringApplication.run(KafkaTemplateApplication.class, args);
        context.start();

        ObjectMapper objectMapper = new ObjectMapper();
        TestSender testSender = (TestSender) context.getBean("testSender");
        String item152038 = "{\"clientId\":\"client1\",\"genericId\":{\"id\":11301447,\"name\":\"PINE 12X120 CLASS 2\",\"products\":[{\"itemId\":\"152038\",\"source\":{\"system\":\"NOBB\",\"systemId\":\"24401689\"}},{\"itemId\":\"152690\",\"source\":{\"system\":\"NOBB\",\"systemId\":\"24401697\"}}]}}";
        String item152039 = "{\"clientId\":\"client1\",\"genericId\":{\"id\":11301448,\"name\":\"PINE 24X240 CLASS 2\",\"products\":[{\"itemId\":\"152039\",\"source\":{\"system\":\"NOBB\",\"systemId\":\"24401689\"}},{\"itemId\":\"152691\",\"source\":{\"system\":\"NOBB\",\"systemId\":\"24401697\"}}]}}";

        try {
            JsonNode jsonNode1 = objectMapper.readTree(item152038);
            testSender.sendTestMessage(jsonNode1, "1");
            JsonNode jsonNode2 = objectMapper.readTree(item152039);
            testSender.sendTestMessage(jsonNode2, "2");
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

}
