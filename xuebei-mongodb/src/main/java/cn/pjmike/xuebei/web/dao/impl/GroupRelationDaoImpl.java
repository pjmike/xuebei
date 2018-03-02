package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.GroupRelationDao;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-27 11:53
 */
@Repository
public class GroupRelationDaoImpl implements GroupRelationDao {
    @Autowired
    private MongoTemplate template;

    @Override
    public void insertBatch(List<GroupRelation> groupRelations) {
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

        List<GroupRelation> groupRelations = template.find(Query.query(Criteria.where("groupId").is(group.getGroupId())), GroupRelation.class);
        List<UserTemp> users = new ArrayList<UserTemp>();
        for (GroupRelation groupRelation : groupRelations) {
            users.add(groupRelation.getUser());
        }
        return users;
    }

    @Override
    public WriteResult deleteGroupRelationByuidAndGid(String groupId, String uuid) {
        return template.remove(Query.query(Criteria.where("groupId").is(groupId).and("uuid").is(uuid)), GroupRelation.class, "groupRelation");
    }
    @Override
    public GroupRelation selectByUuidAndGroupId(String uuid, String groupId) {
        return template.findOne(Query.query(Criteria.where("groupId").is(groupId).and("uuid").is(uuid)),
                GroupRelation.class, "groupRelation");
    }
    @Override
    public WriteResult changeGroupAlias(GroupRelation groupRelation) {
        Query query = Query.query(Criteria.where("groupId").is(groupRelation.getGroupId()).and("uuid").is(groupRelation.getUuid()));
        Update update = new Update();
        update.set("user.$.alias", groupRelation.getAlias());
        return template.updateFirst(query, update, "groupRelation");
    }
    @Override
    public long selectGroupRelationCount(String groupId) {
        return template.count(Query.query(Criteria.where("groupId").is(groupId)),
                GroupRelation.class, "groupRelation");
    }
    @Override
    public WriteResult updateGroupRelationWithIsManager(GroupRelation groupRelation) {
        Query query = Query.query(Criteria.where("groupId").is(groupRelation.getGroupId()).and("uuid").is(groupRelation.getUuid()));
        Update update = new Update();
        update.set("isManager",groupRelation.getIsManager());
        return template.updateFirst(query, update, "groupRelation");
    }
}
