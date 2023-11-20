package coffeetree.kafkaconsumer.kafka.consumer;

import coffeetree.kafkaconsumer.kafka.consumer.dto.CoordinateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Data
@RequiredArgsConstructor
public class KafkaConsumer {

    Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "coffee", groupId = "coffee-tree", containerFactory = "kafkaListenerContainerFactory")
    public void receiveRecord(ConsumerRecord<String, String> consumerRecord){
        try{
            String encodedMessage = consumerRecord.value().replace("\"", "");
            //LOGGER.info("raw value {} ", encodedMessage);
            byte[] decodedMessage = Base64.getDecoder().decode(encodedMessage);
            String json = new String(decodedMessage, StandardCharsets.UTF_8);

            String key = consumerRecord.key();
            CoordinateDTO payload = objectMapper.readValue(json, CoordinateDTO.class);
            LOGGER.info("[receive record] {} : {}", key, payload);
            simpMessagingTemplate.convertAndSend("/topic/container", payload);
        }catch (Exception exception){
            LOGGER.error("[receiveRecord] {} : {}", exception.getClass(), exception.getMessage());
        }

        simpMessagingTemplate.convertAndSend("/topic/container", "FAIL");

    }
}
