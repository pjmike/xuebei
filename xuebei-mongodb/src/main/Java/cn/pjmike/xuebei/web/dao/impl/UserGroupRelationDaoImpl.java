package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserGroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.UserGroupRelationDao;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-27 11:53
 */
@Repository
public class UserGroupRelationDaoImpl implements UserGroupRelationDao {
    @Autowired
    private MongoTemplate template;

    @Override
    public void insertBatch(List<UserGroupRelation> groupRelations) {
        template.insert(groupRelations, "groupRelation");
    }

    @Override
    public List<UserTemp> selectGroupUser(UserGroup group) {
        BasicDBObject object = new BasicDBObject();
        object.put("groupId", group.getGroupId());
        //指定返回字段
        BasicDBObject fieldObject = new BasicDBObject();
        fieldObject.put("userList", true);
        Query query = new BasicQuery(object, fieldObject);

        List<UserGroupRelation> userGroupRelations = template.find(Query.query(Criteria.where("groupId").is(group.getGroupId())), UserGroupRelation.class);
        List<UserTemp> users = new ArrayList<UserTemp>();
        for (UserGroupRelation groupRelation : userGroupRelations) {
            users.add(groupRelation.getUser());
        }
        return users;
    }

    @Override
    public WriteResult deleteUserGroupRelation(String groupId, String uuid) {
        return template.remove(Query.query(Criteria.where("groupId").is(groupId).and("uuid").is(uuid)), UserGroupRelation.class, "groupRelation");
    }
    @Override
    public UserGroupRelation selectByUuidAndGroupId(String uuid,String groupId) {
        return template.findOne(Query.query(Criteria.where("groupId").is(groupId).and("uuid").is(uuid)),
                UserGroupRelation.class, "groupRelation");
    }
}
