package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.User;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 使用MongoTemplate对数据库进行增删改查操作
 *
 * @author pjmike
 * @create 2018-01-29 23:16
 **/
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void insertUser(User user) {
        mongoTemplate.insert(user,"users");
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = mongoTemplate.findAll(User.class,"users");
        return users;
    }

    @Override
    public User findUserByName(String username) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("username").is(username)), User.class,"users");
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("email").is(email)), User.class,"users");
        return user;
    }
    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        User user = mongoTemplate.findOne(Query.query(Criteria.where("email").is(email).and("password").is(password)),User.class,"users");
        return user;
    }
    @Override
    public WriteResult updateUser(Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, User.class,"users");
    }
}
