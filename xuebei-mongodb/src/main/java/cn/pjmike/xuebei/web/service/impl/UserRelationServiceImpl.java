package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.dao.UserRelationDao;
import cn.pjmike.xuebei.web.service.UserRelationService;
import io.rong.RongCloud;
import io.rong.messages.TxtMessage;
import io.rong.messages.VoiceMessage;
import io.rong.methods.message._private.Private;
import io.rong.methods.message.chatroom.Chatroom;
import io.rong.methods.message.discussion.Discussion;
import io.rong.methods.message.group.Group;
import io.rong.methods.message.history.History;
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
    private static final TxtMessage txtMessage = new TxtMessage();
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
        txtMessage.setContent(userRelation.getUserAlias() + "请求添加"+userRelation.getFriendAlias()+"为好友");
        SystemMessage systemMessage = new SystemMessage()
                .setSenderUserId(userRelation.getUserUuid())
                .setTargetId(targetIds)
                .setObjectName(txtMessage.getType())
                .setContent(txtMessage)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setContentAvailable(0);

        ResponseResult result = system.send(systemMessage);
        System.out.println("send system message:  " + result.toString());
        userRelationDao.addFriend(userRelation);
    }
    @Override
    public void comfirmFriend(UserRelation userRelation) {
        RongCloud rongCloud = RongCloud.getInstance();

        MsgSystem system = rongCloud.message.system;


        //更新好友状态，status,确认添加好友
        userRelation.setStatus(0);
        //更新被请求人关系
        UserRelation userRelation1 = new UserRelation();
        userRelation1.setUserUuid(userRelation.getFriendUuid());
        userRelation1.setFriendUuid(userRelation.getUserUuid());

    }
    @Override
    public void rejectFriend(String userUuid, String friendUuid) {

    }
}
