package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.UserGroupInfo;
import cn.pjmike.xuebei.web.service.UserGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class UserGroupServiceImplTest {
    @Autowired
    private UserGroupService groupService;
    @Test
    public void joinAroundGroup() throws Exception {
        UserGroupInfo groupInfo = new UserGroupInfo();
        groupInfo.setUuid("5a94cade76f417d5e15d8f8c");
        groupInfo.setNickname("sddddd");
        groupInfo.setAvatar("sfzdvszdsdfsfasfs");
        groupInfo.setPassword(1226);
        groupInfo.setLocation(new double[]{25.502412906242,113.93832783229});
        ResponseResult<Object> result = groupService.joinAroundGroup(groupInfo);
        System.out.println(result);
    }
}