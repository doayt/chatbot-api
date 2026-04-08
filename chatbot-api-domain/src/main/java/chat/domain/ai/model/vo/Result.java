package chat.domain.ai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Result
{
    private String id;

    private List<Choices> choices;


}