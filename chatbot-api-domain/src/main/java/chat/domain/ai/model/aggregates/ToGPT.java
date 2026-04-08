package chat.domain.ai.model.aggregates;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ToGPT
{
    private String model;

    private List<ToGPTEntity> messages;


}