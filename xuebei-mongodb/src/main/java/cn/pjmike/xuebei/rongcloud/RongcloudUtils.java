package cn.pjmike.xuebei.rongcloud;

import cn.pjmike.xuebei.web.chat.Model.UserTemp;
import io.rong.models.group.GroupMember;
import io.rong.models.group.GroupModel;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-03-19 17:27
 */
public class RongcloudUtils {
    /**
     * 创建融云群组
     *
     * @param groupId
     * @param groupName
     * @param userTemps
     * @return
     */
    public static GroupModel aboutGroupCreate(String groupId, String groupName, List<UserTemp> userTemps) {
        GroupMember[] members = new GroupMember[userTemps.size()];
        for (int i = 0; i < userTemps.size(); i++) {
            members[i] = new GroupMember().setId(userTemps.get(i).getId());
        }
        GroupModel group = new GroupModel()
                .setId(groupId)
                .setMembers(members)
                .setName(groupName);
        return group;
    }
}
