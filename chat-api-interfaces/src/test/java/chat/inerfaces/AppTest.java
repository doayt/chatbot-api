package chat.inerfaces;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {



   @Test
    public void questionGet() throws IOException {
       //使用HttpClient获取接口信息
       CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //设置Get请求参数
       HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88882124251842/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie","_c_WBKFRo=UQHP1NtYupQJTc3ZRcogvGqAOXpalR4m0FfN9Pxd; _nb_ioWEgULi=; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22412814142252188%22%2C%22first_id%22%3A%2219d66155fabe35-030c4a727ae878c-4c657b58-1622400-19d66155fac13e2%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTlkNjYxNTVmYWJlMzUtMDMwYzRhNzI3YWU4NzhjLTRjNjU3YjU4LTE2MjI0MDAtMTlkNjYxNTVmYWMxM2UyIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNDEyODE0MTQyMjUyMTg4In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22412814142252188%22%7D%2C%22%24device_id%22%3A%2219d66155fabe35-030c4a727ae878c-4c657b58-1622400-19d66155fac13e2%22%7D; zsxq_access_token=A7CC55D0-0A60-429A-ABBB-3A1A37F4D535_9598769BB52690B2");
        get.addHeader("Content-Type","application/json; charset=UTF-8");
        //发送请求并获取response
       CloseableHttpResponse response = httpClient.execute(get);

       //如果响应状态码是200,代表成功,使用EntityUtils转换响应体信息
       if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
           String res = EntityUtils.toString(response.getEntity());
           System.out.println(res);
       }else{
           //如果不是200，则打印响应状态码
           System.out.println(response.getStatusLine().getStatusCode());
       }


   }

    @Test
    public void answerPost() throws IOException {
       CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //测试得出回答的接口几乎一样，只有Post请求和topicid这部分需要修改
        HttpPost post=new HttpPost("https://api.zsxq.com/v2/topics/82255512588514112/answer");
        post.addHeader("cookie","_c_WBKFRo=UQHP1NtYupQJTc3ZRcogvGqAOXpalR4m0FfN9Pxd; _nb_ioWEgULi=; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22412814142252188%22%2C%22first_id%22%3A%2219d66155fabe35-030c4a727ae878c-4c657b58-1622400-19d66155fac13e2%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTlkNjYxNTVmYWJlMzUtMDMwYzRhNzI3YWU4NzhjLTRjNjU3YjU4LTE2MjI0MDAtMTlkNjYxNTVmYWMxM2UyIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiNDEyODE0MTQyMjUyMTg4In0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22412814142252188%22%7D%2C%22%24device_id%22%3A%2219d66155fabe35-030c4a727ae878c-4c657b58-1622400-19d66155fac13e2%22%7D; zsxq_access_token=A7CC55D0-0A60-429A-ABBB-3A1A37F4D535_9598769BB52690B2");
        post.addHeader("Content-Type","application/json;charset=UTF-8");

        //获取测试时的接口负载信息(reload)，查看源后复制

        String paramJson="{\"req_data\":{\"text\":\"开个小号配合，别告诉别人\\n\",\"image_ids\":[]}}";

        //Post请求需要入参信息(传入负载信息和content-type),然后传给Httppost对象
        StringEntity entity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);

//如果响应状态码是200,代表成功,使用EntityUtils转换响应体信息
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            //如果不是200，则打印响应状态码
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }


}
