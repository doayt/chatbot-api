package chat.domain.zsxq.model.req;
//
import chat.domain.zsxq.model.dto.Answer;
import chat.domain.zsxq.model.dto.Topic;
import chat.domain.zsxq.model.vo.Topics;

import java.util.List;
public class ReqData
{
    private Topic topic=new Topic();

    public ReqData(Topic topic) {
        this.topic = topic;

    }

    public ReqData(String text,boolean silenced){
        Answer answer=new Answer();
        answer.setText(text);
      //错误写法
//        Topic topic=new Topic();
//        topic.setSilenced(silenced);
        this.topic.setAnswer(answer);
        this.topic.setSilenced(silenced);

    }


    public void setTopics(List<Topics> topics){
        this.topic = topic;
    }
    public Topic getTopics(){
        return this.topic;
    }
}