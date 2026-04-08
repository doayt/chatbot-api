package chat.domain.ai;


import java.io.IOException;

/**
 * ChatGPT OpenAI接口
 */
public interface IOpenAI {

    String doChatGPT(String text,String uri,String apiKey,String model) throws IOException;
}
