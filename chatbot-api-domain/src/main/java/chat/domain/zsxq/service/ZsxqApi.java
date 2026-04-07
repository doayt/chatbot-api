package chat.domain.zsxq.service;

import chat.domain.zsxq.IZsxqApi;
import chat.domain.zsxq.model.aggregates.UnansweredQuestionAggregates;
import chat.domain.zsxq.model.req.AnswerReq;
import chat.domain.zsxq.model.req.ReqData;
import chat.domain.zsxq.model.req.ReqDataPro;
import chat.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ZsxqApi implements IZsxqApi {
    @Override
    public UnansweredQuestionAggregates queryUnansweredQuestionTopicId(String groupId, String cookie) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
      //将原来整体的uri拆分并根据传入的参数组装
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/"+groupId+"/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie",cookie);

        get.addHeader("Content-Type","application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(get);


        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("拉取提问数据,groupId:{},jsonStr:{}",groupId,jsonStr);
            //原来的直接输出改为返回对象
            return JSONObject.parseObject(jsonStr, UnansweredQuestionAggregates.class);
        }else{
        throw new IOException(response.getStatusLine().getStatusCode() + ":" + response.getStatusLine().getStatusCode());

        }



    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //一样，替换
        HttpPost post=new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/answer");
        post.addHeader("cookie",cookie);

        post.addHeader("Content-Type","application/json;charset=UTF-8");
        //多设置一个标头User-Agent,因为是程序侵入，可能会被识别为危险请求，表明自己是从浏览器进入的良民
        post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36 Edg/146.0.0.0");

     //   String paramJson="{\"req_data\":{\"text\":\"自己百度，丰衣足食\\n\",\"image_ids\":[]}}";



        //可能需要封装，也可能是模拟前端只传文本信息+是否私有两个属性参数
      //  ReqData reqData = new ReqData(text, silenced);
        AnswerReq answerReq=new AnswerReq(new ReqDataPro(text));
        String paramJson = net.sf.json.JSONObject.fromObject(answerReq).toString();


        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);

        //自动回答不需要知道多余的返回信息，只需要知道是否成功即可
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            log.info("回答问题的结果,groupId:{},topicId:{},jsonStr:{}", groupId, topicId, jsonStr);
            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
                return answerRes.getSucceeded();
        }else{

           throw new IOException("异常状态码为" + ":" + response.getStatusLine().getStatusCode());
        }





    }
}
