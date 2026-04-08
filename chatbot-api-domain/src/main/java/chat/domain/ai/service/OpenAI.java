package chat.domain.ai.service;

import chat.domain.ai.IOpenAI;
import chat.domain.ai.model.aggregates.ToGPT;
import chat.domain.ai.model.aggregates.ToGPTEntity;
import chat.domain.ai.model.vo.Choices;
import chat.domain.ai.model.vo.Result;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class OpenAI implements IOpenAI {

    /**
     * 把问题文本交给ai然后返回ai给出的结果
     * @param text
     * @return
     * @throws IOException
     */
    @Override
    public String doChatGPT(String text,String uri,String apiKey,String model) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post=new HttpPost(uri);

        post.addHeader("Authorization",apiKey);
        post.addHeader("Content-Type","application/json; charset=UTF-8");

//        String paramJson="{\n" +
//                "    \"model\": \"gpt-3.5-turbo\",\n" +
//                "    \"messages\": [\n" +
//                "      {\n" +
//                "        \"role\": \"system\",\n" +
//                "        \"content\": \"You are a helpful assistant.\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"role\": \"user\",\n" +
//                "        \"content\": \"Hello!\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }";

        ToGPTEntity toGPTEntity = new ToGPTEntity(text);
        List<ToGPTEntity> entities= Arrays.asList(toGPTEntity);
        ToGPT toGPT=new ToGPT(model,entities);

        String paramJson =net.sf.json.JSONObject.fromObject(toGPT).toString();

        StringEntity entity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            if (jsonStr == null) {
                return "没有返回的信息！";
            }

            Result result = JSON.parseObject(jsonStr, Result.class);

            List<Choices> choices = result.getChoices();

            for (Choices choice : choices) {
               return choice.getMessage().getContent();
            }


        }else{

            System.out.println(response.getStatusLine().getStatusCode());
            return "请求失败";
        }


    return "未知错误！";


    }
}
