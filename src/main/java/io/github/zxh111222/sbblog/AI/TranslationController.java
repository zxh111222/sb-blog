package io.github.zxh111222.sbblog.AI;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ai")
public class TranslationController {

    private final ChatClient chatClient;

    public TranslationController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/translate-chinese")
    public Map<String, String> translateChineseToEnglish(@RequestParam String input) {
        Prompt prompt = new Prompt("将以下这段中文文本翻译为英文(只管翻译，不需任何交流，不需要其他多余语言):" + input);


        return Map.of(
                "translatedText",
                chatClient.prompt(prompt)
                        .call().content()
        );
    }

}
