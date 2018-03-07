package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.dao.UserRelationDao;
import cn.pjmike.xuebei.web.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pjmike
 * @create 2018-03-07 19:35
 */
@Service
public class UserRelationServiceImpl implements UserRelationService{
    @Autowired
    private UserRelationDao userRelationDao;
    @Override
    public void addFriend(UserRelation userRelation) {
        userRelationDao.addFriend(userRelation);
    }
    @Override
    public void comfirmFriend(UserRelation userRelation) {
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
