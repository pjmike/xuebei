package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.dao.UserRelationDao;
import cn.pjmike.xuebei.web.service.UserRelationService;
import io.rong.RongCloud;
import io.rong.messages.ContactNtfMessage;
import io.rong.messages.VoiceMessage;

import io.rong.methods.message.system.MsgSystem;
import io.rong.models.message.SystemMessage;
import io.rong.models.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pjmike
 * @create 2018-03-07 19:35
 */
@Service
public class UserRelationServiceImpl implements UserRelationService{
    /**
     * 消息内容,系统消息
     */
    private static final VoiceMessage voiceMessage = new VoiceMessage("hello", "helloExtra", 20L);

    @Autowired
    private UserRelationDao userRelationDao;
    @Override
    public void addFriend(UserRelation userRelation) throws Exception {

        RongCloud rongCloud = RongCloud.getInstance();

        MsgSystem system = rongCloud.message.system;

        /**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/system.html#send
         *
         * 发送添加好友系统消息
         *
         */
        String[] targetIds = {userRelation.getFriendUuid()};

        ContactNtfMessage contactNtfMessage = new ContactNtfMessage("add", "", userRelation.getUserUuid(), userRelation.getFriendUuid(), userRelation.getRemark());

        SystemMessage systemMessage = new SystemMessage()
                .setSenderUserId(userRelation.getUserUuid())
                .setTargetId(targetIds)
                .setObjectName(contactNtfMessage.getType())
                .setContent(contactNtfMessage)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setContentAvailable(0);

        ResponseResult result = system.send(systemMessage);
        System.out.println("send system message:  " + result.toString());
        userRelationDao.addFriend(userRelation);
    }
    @Override
    public void comfirmFriend(UserRelation userRelation) throws Exception {
        RongCloud rongCloud = RongCloud.getInstance();

        MsgSystem system = rongCloud.message.system;

        String[] targetIds = {userRelation.getUserUuid()};
        ContactNtfMessage contactNtfMessage = new ContactNtfMessage("confirm", "", userRelation.getFriendUuid(), userRelation.getUserUuid(), userRelation.getRemark());

        SystemMessage systemMessage = new SystemMessage()
                .setSenderUserId(userRelation.getFriendUuid())
                .setTargetId(targetIds)
                .setObjectName(contactNtfMessage.getType())
                .setContent(contactNtfMessage)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setContentAvailable(0);

        ResponseResult result = system.send(systemMessage);
        System.out.println("send system message:  " + result.toString());
        //更新好友状态，status,确认添加好友
        userRelation.setStatus(0);
        userRelationDao.confirmFriend(userRelation);
        //更新被请求人关系
        UserRelation userRelation1 = new UserRelation();
        userRelation1.setUserUuid(userRelation1.getFriendUuid());
        userRelation1.setFriendUuid(userRelation1.getUserUuid());
        userRelationDao.addFriend(userRelation1);
    }
    @Override
    public void rejectFriend(String userUuid, String friendUuid) throws Exception {

        RongCloud rongCloud = RongCloud.getInstance();

        MsgSystem system = rongCloud.message.system;

        /**
         * API 文档: http://rongcloud.github.io/server-sdk-nodejs/docs/v1/message/system.html#send
         *
         * 发送添加好友系统消息
         *
         */
        String[] targetIds = {userUuid};

        ContactNtfMessage contactNtfMessage = new ContactNtfMessage("reject", "", friendUuid, userUuid,"");

        SystemMessage systemMessage = new SystemMessage()
                .setSenderUserId(friendUuid)
                .setTargetId(targetIds)
                .setObjectName(contactNtfMessage.getType())
                .setContent(contactNtfMessage)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setContentAvailable(0);

        ResponseResult result = system.send(systemMessage);
        System.out.println("send system message:  " + result.toString());
        //TODO,本地数据库删除
        userRelationDao.deleteFriend(userUuid,friendUuid);
    }
}
