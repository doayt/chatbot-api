package chat.application.job;


import chat.domain.ai.IOpenAI;
import chat.domain.zsxq.IZsxqApi;
import chat.domain.zsxq.model.aggregates.UnansweredQuestionAggregates;
import chat.domain.zsxq.model.vo.Topics;
import chat.interfaces.config.ApiProperties;
import chat.interfaces.config.GPTProperties;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@EnableScheduling
@Configuration
public class ChatBotSchedule {


    @Resource
    private GPTProperties gptProperties;

    @Resource
    private IOpenAI openAI;

    @Resource
    private ApiProperties apiProperties;

    @Resource
    private IZsxqApi zsxqApi;





    @Scheduled(cron="0 0/1 * * * ?  ")
    public void scheduled() {

        try{
            if(new Random().nextBoolean()){
                log.info("随机打烊中...");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            int hour = now.getHour();
            if (hour >22||hour<7){
                log.info("超过晚上十点或者小于早上七点，打烊时间不工作...");
                return;
            }


            String groupId = apiProperties.getGroupId();
            String cookie = apiProperties.getCookie();

            UnansweredQuestionAggregates unansweredQuestionAggregates = zsxqApi.queryUnansweredQuestionTopicId(groupId, cookie);
            log.info("测试结果：{}", JSON.toJSONString(unansweredQuestionAggregates));
            if (unansweredQuestionAggregates == null) {
                log.info("未能找到数据...");
                return;
            }

            List<Topics> topics=unansweredQuestionAggregates.getResp_data().getTopics();
            if (topics==null||topics.isEmpty()) {
                log.info("未能获取话题信息...");
                return;
            }

            //不用一次性全回答，不然会被风控检测
            Topics topic=topics.get(0);

                String topicId = topic.getTopic_id();
                String txt=topic.getQuestion().getText();
                log.info("topicId:{},text:{}",topicId,txt);

                String text = openAI.doChatGPT(txt.trim(), gptProperties.getUri(), gptProperties.getApiKey(), gptProperties.getModel());
                log.info("回答的结果:{}",text);
                zsxqApi.answer(groupId,cookie,topicId,text.trim(),false);


//            for(Topics topic:topics){
//                String topicId = topic.getTopic_id();
//                String txt=topic.getQuestion().getText();
//                log.info("topicId:{},text:{}",topicId,txt);
//
//                String text = openAI.doChatGPT(txt, gptProperties.getUri(), gptProperties.getApiKey(), gptProperties.getModel());
//                log.info("回答的结果:{}",text);
//                zsxqApi.answer(groupId,cookie,topicId,text,false);
//            }
        }catch (Exception e){
            log.error("自动回答问题异常",e);
        }


    }
}
