package chat.domain.zsxq;

import chat.domain.zsxq.model.aggregates.UnansweredQuestionAggregates;

import java.io.IOException;

/**
 * 聚合对象
 */
public interface IZsxqApi {
    /**
     *返回提问信息的接口
     * @param groupId
     * @param cookie
     * @return
     * @throws IOException
     */
    UnansweredQuestionAggregates queryUnansweredQuestionTopicId(String groupId,String cookie) throws IOException;


    /**
     * 自动回答问题的接口
     */
    boolean answer(String groupId,String cookie,String topicId,String text,boolean silenced) throws IOException;
}
