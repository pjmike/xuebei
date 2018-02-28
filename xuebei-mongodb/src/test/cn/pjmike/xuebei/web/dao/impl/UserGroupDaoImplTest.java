package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import com.mongodb.BasicDBObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml","classpath:springmvc.xml"})
public class UserGroupDaoImplTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void insertGroup() throws Exception {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupName("群里爱");
        mongoTemplate.insert(userGroup, "group");
        /**
         * UserGroup{groupId='5a94fa7776f4d1513e367853', groupName='群里爱', owner='null', annoucement='null', date=null, status=0, password=null, location=null}

         */
        //拿到id
        System.out.println(userGroup);
    }

    @Test
    public void insertTestDBRef() {
        GroupRelation groupRelation = new GroupRelation();
        groupRelation.setAlias("sdf");
        User user = new User();
        user.setUsername("sfsdf");
        user.setEmail("1173447453@qq.com");
        mongoTemplate.save(user);
        List<User> users = new ArrayList<User>();
        users.add(user);
//        groupRelation.setUser(user);
        mongoTemplate.insert(groupRelation);
        System.out.println(groupRelation.getUser());
    }

    /**
     * 查询指定字段
     */
    @Test
    public void testDBObject() {
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.put("username", "pjmike");
        BasicDBObject fieldObject = new BasicDBObject();
        fieldObject.put("username", true);
        fieldObject.put("password", true);
        Query query = new BasicQuery(dbObject, fieldObject);
        User user = mongoTemplate.findOne(query, User.class);
        System.out.println(user);
    }
}