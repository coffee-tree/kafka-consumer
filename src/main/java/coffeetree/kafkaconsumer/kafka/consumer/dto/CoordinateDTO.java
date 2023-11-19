package coffeetree.kafkaconsumer.kafka.consumer.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Getter
public class CoordinateDTO {
    private String id;
    private Double x;
    private Double y;

    public CoordinateDTO(String id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}
