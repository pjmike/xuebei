package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.dao.UserGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


/**
 * @author pjmike
 * @create 2018-02-25 22:53
 */
@Repository
public class UserGroupDaoImpl implements UserGroupDao {
    @Autowired
    private MongoTemplate template;

    @Override
    public UserGroup findGroupByPwdAndLoc(Integer password, double[] location) {
        Point point = new Point(location[0],location[1]);
        //搜索目标位置10m内是否有群存在
        UserGroup userGroup = template.findOne(Query.query(Criteria.where("password").is(password).and("location").near(point).maxDistance(0.01/111)),
                UserGroup.class,"group");
        return userGroup;
    }
    @Override
    public UserGroup insertGroup(UserGroup group) {
        template.insert(group,"group");
        return group;
    }
    @Override
    public UserGroup updateGroupName(UserGroup group) {
        Update update = new Update();
        update.set("groupName", group.getGroupName());
        update.set("annoucement", group.getAnnoucement());
        template.updateFirst(Query.query(Criteria.where("groupId").is(group.getGroupId())), update, "group");
        return group;
    }
    @Override
    public UserGroup updateGroupAnnouncement(UserGroup group) {
        Update update = new Update();
        update.set("annoucement", group.getAnnoucement());
        template.updateFirst(Query.query(Criteria.where("groupId").is(group.getGroupId())), update, "group");
        return group;
    }
    @Override
    public UserGroup selectUserGroup() {
        return null;
    }
}
