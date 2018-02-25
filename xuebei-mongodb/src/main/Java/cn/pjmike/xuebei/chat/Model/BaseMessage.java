package cn.pjmike.xuebei.chat.Model;


import java.util.Date;

/**
 * 基本消息类型 Created by Silence on 2017/4/21.
 */
public class BaseMessage {

	// 消息IDi
	// @Id
	// private String id;

	// 消息类型
	private String type;

	// 消息内容
	private String content;

	// 发送者
	private String sender;

	// 接受者 类型
	private String toType;

	// 接受者
	private String receiver;

	// 发送时间
	private Date date;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
