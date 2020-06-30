package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class LineBotApplication {

  @Value("${line.bot.channelToken}")
  private String channelAccessToken;
  
  @EventMapping
  public TextMessage handleDefaultMessageEvent(MessageEvent<TextMessageContent> event) {
      System.err.println(event);
      String userId = event.getSource().getUserId();
      String text = event.getMessage().getText();
      String richMenuId = "richmenu-7c0282614719d4bb8ae372da5317615d";
      
      if("給我選單".equals(text)) {
        LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
        client.linkRichMenuIdToUser(userId, richMenuId);
        return TextMessage.builder().text("Ok, I have given you the rich menu.").build();
      }
      
      if("移除選單".equals(text)) {
        LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();
        client.linkRichMenuIdToUser(userId, richMenuId);
        return TextMessage.builder().text("Ok, I delete the rich menu, now.").build();
      }
      
      return TextMessage.builder().text(event.getMessage().getText()).build();
  }
  
  @EventMapping
  public TextMessage handleDefaultMessageEvent(Event event) {
      System.err.println(event);
      return TextMessage.builder().text("I don't know what you want to express.").build();
  }
  
  @EventMapping
  public List<TextMessage> handlePostbackEvent(PostbackEvent event) {
    String data = event.getPostbackContent().getData();
    return Arrays.asList(
        TextMessage.builder().text("This Button hidden the postback action in back.").build(),
        TextMessage.builder().text("And The Button hide message is ".concat("< "+data+" >")).build()
    );
  }
  
}
