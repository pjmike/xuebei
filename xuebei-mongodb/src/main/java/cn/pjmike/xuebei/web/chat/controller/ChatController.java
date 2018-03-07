package cn.pjmike.xuebei.web.chat.controller;

import cn.pjmike.xuebei.web.chat.Model.BaseMessage;
import cn.pjmike.xuebei.web.chat.Model.ChatMessage;
import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Silence on 2017/4/21.
 */
@Controller
public class ChatController {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private UserService userService;

	@MessageMapping("/all")
	public void all(Principal principal, String message) {
		ChatMessage chatMessage = createMessage(principal.getName(), message);
		// 参数说明 principal 当前登录的用户， message 客户端发送过来的内容
		// principal.getName() 可获得当前用户的username


		// 发送消息给订阅 "/topic/notice" 且在线的用户
		template.convertAndSend("/topic/notice", JSON.toJSONString(chatMessage));
	}

	@MessageMapping("/chat")
	public void chat(Principal principal, String message) {
		BaseMessage baseMessage = JSON.parseObject(message, BaseMessage.class);
		baseMessage.setSender(principal.getName());
		this.send(baseMessage);
	}
	@MessageMapping("/addFriend")
	public void addFriend(@RequestBody UserRelation userRelation) {
		template.convertAndSendToUser(userRelation.getUserAlias(), "/queue/friends", userRelation.getMsg());
	}
	private void send(BaseMessage message) {
		message.setDate(new Date());
		ChatMessage chatMessage = createMessage(message.getSender(), message.getContent());
		template.convertAndSendToUser(message.getReceiver(), "/topic/chat", JSON.toJSONString(chatMessage));
	}

	private ChatMessage createMessage(String username, String message) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(username);
		chatMessage.setContent(message);
		chatMessage.setSendTime(simpleDateFormat.format(new Date()));
		return chatMessage;
	}
}
