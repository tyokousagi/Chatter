package com.example.chatter;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
@Component
public class DiscordMessenger {

    private final WebClient webClient;
    private final String userId = "1064492923854524447"; // メンションしたいユーザーのID

    public DiscordMessenger(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void sendDiscordMessage(String webhookUrl, String message) {
        if (webhookUrl == null || webhookUrl.isEmpty() || message == null || message.isEmpty()) {
            System.err.println("Webhook URL or message is null or empty.");
            return;
        }

        String mention = "";
        if (userId != null && !userId.isEmpty()) {
            mention = "<@" + userId + "> "; // ユーザーメンション
            // または
            // mention = "<@&" + roleId + "> "; // ロールメンション (roleId を取得する必要あり)
        }

        String content = String.format("{\"content\": \"%s%s\"}", mention, message); // メンションをメッセージの先頭に追加

        

        // Discord のリクエストボディの形式に合わせる
        //String content = String.format("{\"content\": \"%s\"}", message);

        webClient.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(content))
                .retrieve()
                .bodyToMono(String.class) // レスポンスボディをStringとして受け取る
                .subscribe(
                        response -> System.out.println("Discord message sent: " + response),
                        error -> System.err.println("Error sending Discord message: " + error.getMessage())
                );
    }
}
