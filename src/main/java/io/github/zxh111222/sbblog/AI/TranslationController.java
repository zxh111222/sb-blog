package io.github.zxh111222.sbblog.AI;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class TranslationController {

    private final ChatClient chatClient;

    public TranslationController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/translate-chinese")
    public Flux<String> translateChineseToEnglish(@RequestParam String input) {
        Prompt prompt = new Prompt("将以下这段中文文本翻译为英文:" + input);
        return this.chatClient.prompt(prompt)
                .stream().content();
    }
}
