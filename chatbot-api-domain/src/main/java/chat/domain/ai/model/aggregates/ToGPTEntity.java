package chat.domain.ai.model.aggregates;


//messages
public class ToGPTEntity
{
    private String role="user";

    private String content;

    public ToGPTEntity(String content){
        this.content=content;
    }


    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
}
