package eg.kafka.template.listeners;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
//    /**
//     * We must (for now) register the aspect that looks for the @EgKafkaSecurity annotation.
//     * In a later version of the framework, this would be automatic.
//     */
//    @Bean
//    public EgKafkaSecurityAspect egKafkaSecurityAspect(){
//        return new EgKafkaSecurityAspect();
//    }
//
//    /**
//     * We must decide how to handle messages from kafka where the provided EG-DOMAIN header points to a non-existing domain.
//     * This can happen because messages in Kafka can "live forever", or at least with a given retention time.
//     * In this case, we just create a empty handler, which will allow the messages to be consumed and passed on to the listener.
//     */
//    @Bean
//    public UnknownDomainHandler doNothingOnMissingDomainHandler(){
//        return new UnknownDomainHandler() {
//            @Override
//            public void handleUnknownDomain(String domain) {
//
//            }
//        };
//    }
}
