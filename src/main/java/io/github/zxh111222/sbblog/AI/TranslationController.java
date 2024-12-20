package io.github.zxh111222.sbblog.AI;


import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/ai")
public class TranslationController {

    private final ChatClient chatClient;

    public TranslationController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping("/translate-chinese")
    public Map<String, String> translateChineseToEnglish(@RequestBody Map<String, String> input) {
        String text = input.get("input");
        Prompt prompt = new Prompt("翻译为简洁英文(只管翻译，不需任何交流，不需要其他多余语言)保留的原本的格式和特殊字符(最开始的：除外)，从这个冒号开始后全部都是：" + text);

        Map<String, String> translatedText = Map.of(
                "translatedText",
                chatClient.prompt(prompt)
                        .call().content());


        return translatedText;
    }

    @PostMapping("/translate_img")
    public Map<String, String> translateImg(@RequestBody Map<String, String> input) throws NoApiKeyException, UploadFileException {
        String imageUrl = input.get("input");
        String baseLocalPath = "E://JAVAP/sb-blog/upload";
        // 提取 URL 路径部分
        String relativePath = imageUrl.replace("http://localhost:8080/", "");
        String image = baseLocalPath + "/" + relativePath;
        System.out.println(image);

        MultiModalConversation conv = new MultiModalConversation();

        Map<String, Object> map = new HashMap<>();
        map.put("image", image);
        map.put("max_pixels", "1003520");
        map.put("min_pixels", "3136");
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(Arrays.asList(
                        map,
                        Collections.singletonMap("text", "读取图片中的文字"))).build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey("sk-bb1ab19daf794061826870031fe6276d")
                .model("qwen-vl-ocr")
                .message(userMessage)
                .topP(0.01)
                .temperature(0.1f)
                .maxLength(2000)
                .build();
        MultiModalConversationResult result = conv.call(param);
        String chinese_text = (String) result.getOutput().getChoices().get(0).getMessage().getContent().get(0).get("text");
        Prompt prompt = new Prompt("翻译为简洁英文(只管翻译，不需任何交流，不需要其他多余语言)保留原本的格式和特殊字符，从这个冒号开始后都是:" + chinese_text);

        return Map.of("translatedText",chatClient.prompt(prompt)
                .call().content());

    }

//    private String loadImage(String url){
//        String baseLocalPath = "E://JAVAP/sb-blog/upload";
//        // 提取 URL 路径部分
//        String relativePath = url.replace("http://localhost:8080/", "");
//        // 拼接本地根目录和相对路径
//        System.out.println(baseLocalPath + "/" + relativePath);
////        return "file://" + baseLocalPath + File.separator + relativePath.replace("/", File.separator);
//        return baseLocalPath + "/" + relativePath;
//    }


    @PostMapping("/translate-code-comments")
    public Map<String, String> translateCodeComments(@RequestBody Map<String, String> request) {
        String code = request.get("code");


        // 提取注释
        String commentRegex = "//[^\n]*|/\\*[\\s\\S]*?\\*/";
        Pattern pattern = Pattern.compile(commentRegex);
        Matcher matcher = pattern.matcher(code);

        StringBuilder translatedCode = new StringBuilder();

        while (matcher.find()) {
            String comment = matcher.group();
            Prompt prompt = new Prompt("翻译为简洁英文(只管翻译，不需任何交流，不需要其他多余语言)保留原本的格式和特殊字符，从这个冒号开始后都是:" + comment);
            String translatedComment = chatClient.prompt(prompt)
                    .call().content();
            matcher.appendReplacement(translatedCode, translatedComment);
        }
        matcher.appendTail(translatedCode);

        return Map.of("translatedCode", translatedCode.toString());
    }



}
