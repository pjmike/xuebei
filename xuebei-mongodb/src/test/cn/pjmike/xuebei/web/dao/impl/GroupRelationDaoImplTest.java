package cn.pjmike.xuebei.web.dao.impl;

import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import cn.pjmike.xuebei.web.dao.GroupRelationDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class GroupRelationDaoImplTest {
    @Autowired
    private GroupRelationDao groupRelationDao;
    @Test
    public void insertBatch() throws Exception {
        GroupRelation groupRelation1 = new GroupRelation();
        groupRelation1.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation1.setUser(new UserTemp("5a6f30d8cbfd00a87a00d545"));
        GroupRelation groupRelation2 = new GroupRelation();
        groupRelation2.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation2.setUser(new UserTemp("5a72b4a451603d3910041f17"));
        GroupRelation groupRelation3 = new GroupRelation();
        groupRelation3.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation3.setUser(new UserTemp("5a918a2676f4b9720ab39f7f"));
        GroupRelation groupRelation4 = new GroupRelation();
        groupRelation4.setGroupId("5a94fa7776f4d1513e367853");
        groupRelation4.setUser(new UserTemp("5a94bf2076f402090989454a"));

        List<GroupRelation> groupRelations = new ArrayList<GroupRelation>();
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