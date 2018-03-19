package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.dao.SelectDao;
import cn.pjmike.xuebei.web.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-03-19 21:31
 */
@Repository
public class SelectDaoImpl implements SelectDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<User> selectUserByLike(String name) {
        Query query = TextQuery.queryText(new TextCriteria().matchingAny(name)).sortByScore();
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

    @Override
    public List<UserGroup> selectGroupByLike(String name) {
        Query query = TextQuery.queryText(new TextCriteria().matchingAny(name)).sortByScore();
        List<UserGroup> userGroups = mongoTemplate.find(query, UserGroup.class);
        return userGroups;
    }
}
