package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;

import javax.xml.ws.Response;

/**
 * @author pjmike
 * @create 2018-02-28 16:09
 */
public interface GroupRelationService {
    /**
     * 修改群昵称
     */
    GroupRelation changeGroupAlias(GroupRelation groupRelation);

    /**
     * 主动退群
     * @param groupRelation
     * @return
     */
    ResponseResult<Object> leaveGroup(GroupRelation groupRelation);

    /**
     * 设置管理员
     */
    void setManager(GroupRelation groupRelation);

    /**
     * 移除管理员
     */
    void delManager(GroupRelation groupRelation);
}
