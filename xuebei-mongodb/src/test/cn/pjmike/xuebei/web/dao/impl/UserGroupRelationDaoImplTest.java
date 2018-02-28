package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserGroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.UserGroupRelationDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class UserGroupRelationDaoImplTest {
    @Autowired
    private UserGroupRelationDao groupRelationDao;
    @Test
    public void insertBatch() throws Exception {
        UserGroupRelation groupRelation1 = new UserGroupRelation();
        groupRelation1.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation1.setUser(new UserTemp("5a6f30d8cbfd00a87a00d545"));
        UserGroupRelation groupRelation2 = new UserGroupRelation();
        groupRelation2.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation2.setUser(new UserTemp("5a72b4a451603d3910041f17"));
        UserGroupRelation groupRelation3 = new UserGroupRelation();
        groupRelation3.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation3.setUser(new UserTemp("5a918a2676f4b9720ab39f7f"));
        UserGroupRelation groupRelation4 = new UserGroupRelation();
        groupRelation4.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation4.setUser(new UserTemp("5a94bf2076f402090989454a"));

        List<UserGroupRelation> groupRelations = new ArrayList<UserGroupRelation>();
        groupRelations.add(groupRelation1);
        groupRelations.add(groupRelation2);
        groupRelations.add(groupRelation3);
        groupRelations.add(groupRelation4);
        groupRelationDao.insertBatch(groupRelations);
    }

    @Test
    public void selectGroupUser() throws Exception {
        UserGroup group = new UserGroup();
        group.setGroupId("5a94fa7776f4d1513e367853");
        List<UserTemp> users = groupRelationDao.selectGroupUser(group);
        System.out.println(users);
    }

}