package chat.inerfaces;


import chat.domain.ai.IOpenAI;
import chat.domain.zsxq.IZsxqApi;
import chat.domain.zsxq.model.aggregates.UnansweredQuestionAggregates;
import chat.domain.zsxq.model.vo.Topics;
import chat.interfaces.ApiApplication;
import chat.interfaces.config.ApiProperties;
import chat.interfaces.config.GPTProperties;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ApiTest {

    @Resource
    private GPTProperties gptProperties;

    @Resource
    private IOpenAI openAI;

    @Resource
    private ApiProperties apiProperties;

    @Resource
    private IZsxqApi zsxqApi;


    @Test
    public void test() throws IOException {
        String groupId = apiProperties.getGroupId();
        String cookie = apiProperties.getCookie();

        UnansweredQuestionAggregates unansweredQuestionAggregates = zsxqApi.queryUnansweredQuestionTopicId(groupId, cookie);
        log.info("测试结果：{}", JSON.toJSONString(unansweredQuestionAggregates));
        if (unansweredQuestionAggregates == null) {
            log.info("请求的服务失败...");
            return;
        }

        List<Topics> topics=unansweredQuestionAggregates.getResp_data().getTopics();
        if (topics==null||topics.isEmpty()) {
            log.info("请求的服务失败,未能获取话题信息");
            return;
        }

        for(Topics topic:topics){
            String topicId = topic.getTopic_id();
            String txt=topic.getQuestion().getText();
            log.info("topicId:{},text:{}",topicId,txt);

            String text = openAI.doChatGPT(txt, gptProperties.getUri(), gptProperties.getApiKey(), gptProperties.getModel());
            log.info("回答的结果:{}",text);
            zsxqApi.answer(groupId,cookie,topicId,text,false);
        }

    }


}
