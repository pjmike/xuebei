package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.web.chat.Model.UserRelation;
import cn.pjmike.xuebei.web.dao.UserRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    @Override
    public void deleteFriend(String userId,String friendId) {
        mongoTemplate.remove(Query.query(Criteria.where("friendUuid").is(friendId).and("userUuid").is(userId)), UserRelation.class);
    }
    @Override
    public void confirmFriend(UserRelation relation) {
        Query query = Query.query(Criteria.where("userUuid").is(relation.getUserUuid()).and("friendUuid").is(relation.getFriendUuid()));
        Update update = new Update();
        update.set("status", 0);
        mongoTemplate.upsert(query, update, UserRelation.class);
    }
}
