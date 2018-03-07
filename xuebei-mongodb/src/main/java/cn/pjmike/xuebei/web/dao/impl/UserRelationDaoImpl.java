package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.dao.UserRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author pjmike
 * @create 2018-03-07 19:40
 */
@Repository
public class UserRelationDaoImpl implements UserRelationDao{
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void addFriend(UserRelation userRelation) {
        mongoTemplate.insert(userRelation,"userRelations");
    }
}
