package io.github.zxh111222.sbblog.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SummarizationTest {

    public static String summarize(String text) throws Exception {
        String apiUrl = "http://127.0.0.1:5000/summarize";  // Flask服务地址
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        // 手动转义特殊字符
        String escapedText = text.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");

        // 创建请求体，发送给Python服务
        String jsonInputString = "{\"text\":\"" + escapedText + "\"}";
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 获取Python服务的响应
            try (InputStream is = connection.getInputStream()) {
                byte[] responseBytes = is.readAllBytes();
                return new String(responseBytes, StandardCharsets.UTF_8);
            }
        } else {
            // 处理错误响应
            try (InputStream errorStream = connection.getErrorStream()) {
                if (errorStream != null) {
                    byte[] errorResponse = errorStream.readAllBytes();
                    System.out.println("Error Response: " + new String(errorResponse, StandardCharsets.UTF_8));
                }
            }
            throw new Exception("Failed to get a valid response from the Flask service. HTTP Code: " + responseCode);
        }
    }


    public static void main(String[] args) throws Exception {
        String article = """
                近年来，人工智能（AI）技术取得了飞速发展，从语音助手到自动驾驶，AI 正逐渐渗透到人类生活的方方面面。然而，随着这一技术的普及，也带来了许多机遇和挑战。
                首先，AI 在医疗领域的应用已经展现出巨大的潜力。例如，基于机器学习的算法可以快速分析医疗影像，帮助医生更早地发现癌症等重大疾病。除此之外，AI 还可以通过分析病人的病历和基因数据，为其量身定制最合适的治疗方案。这不仅提高了医疗效率，还降低了医疗成本。
                其次，AI 技术在工业生产中的应用也在不断扩大。例如，在制造业中，AI 驱动的机器人能够完成复杂的组装任务，极大地提高了生产效率。此外，AI 系统还可以预测设备的故障时间，从而减少停机时间并降低维护成本。这些进步正在重塑传统行业的面貌。
                然而，AI 的发展也伴随着许多社会问题和伦理挑战。一个显著的问题是失业风险。随着 AI 系统的广泛应用，许多传统岗位正逐渐被取代。例如，自动驾驶汽车可能会导致大量卡车司机失业，而智能客服系统可能会取代人类客服人员。如何在技术进步与就业保障之间找到平衡，成为一个亟待解决的问题。
                另一个挑战是隐私问题。AI 系统需要大量数据来训练，而这些数据通常涉及个人隐私。例如，智能家居设备可能会收集用户的生活习惯数据，而社交媒体平台则可能利用用户数据来推送广告。这些行为引发了广泛的隐私担忧，也促使人们呼吁加强对数据使用的监管。
                此外，AI 技术的滥用也可能带来严重后果。例如，深度伪造技术（Deepfake）可以伪造视频和音频，可能被用来传播虚假信息，从而引发社会不安定。因此，如何制定合理的法律和伦理规范，防止 AI 技术的滥用，成为一个亟待解决的问题。
                总之，人工智能是一把“双刃剑”，它在带来巨大便利的同时，也提出了许多社会和伦理上的挑战。为了让 AI 更好地为人类服务，我们需要政府、企业和学术界的通力合作，共同制定规范与政策，确保 AI 技术的可持续发展。""";
        String summary = summarize(article);
        System.out.println("生成的摘要: " + summary);
    }
}


