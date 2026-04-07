package chat.domain.zsxq.model.req;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReqDataPro {

    private String text;
    private String[] image_ids=new String[]{};



    public ReqDataPro(String text) {
        this.text=text;

    }
}
