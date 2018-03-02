package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.utils.ErrorCodeEnum;
import cn.pjmike.xuebei.utils.ResponseResult;
import cn.pjmike.xuebei.web.chat.Model.GroupRelation;
import cn.pjmike.xuebei.web.dao.GroupRelationDao;
import cn.pjmike.xuebei.web.service.GroupRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pjmike
 * @create 2018-02-28 16:09
 */
@Service
public class GroupRelationServiceImpl implements GroupRelationService{
    @Autowired
    private GroupRelationDao groupRelationDao;

    @Override
    public GroupRelation changeGroupAlias(GroupRelation groupRelation) {
        groupRelationDao.changeGroupAlias(groupRelation);
        return groupRelation;
    }
    @Override
    public ResponseResult<Object> leaveGroup(GroupRelation groupRelation) {
        GroupRelation relation = groupRelationDao.selectByUuidAndGroupId(groupRelation.getUuid(), groupRelation.getGroupId());
        long count = 0;
        //判断退出群的该用户是否为群主
        if (relation.getIsOwner() == 0) {
            //判断该群的人数，如果人数大于1，需先移交群主,再退出
            count = groupRelationDao.selectGroupRelationCount(groupRelation.getGroupId());
            if (count > 0) {
                //返回错误提示，需先移交群主
                return new ResponseResult<Object>(1, ErrorCodeEnum.LENDER_NOT_CHANGE.getMsg());
            }
        }
        groupRelationDao.deleteGroupRelationByuidAndGid(groupRelation.getGroupId(), groupRelation.getUuid());
        return new ResponseResult<Object>(0, "退群成功");
    }
    @Override
    public void setManager(GroupRelation groupRelation) {
        //设置为管理员，状态为0
        groupRelation.setIsManager((byte)0);
        groupRelationDao.updateGroupRelationWithIsManager(groupRelation);
    }
    @Override
    public void delManager(GroupRelation groupRelation) {
        //设置为管理员，状态为1
        groupRelation.setIsManager((byte)1);
        groupRelationDao.updateGroupRelationWithIsManager(groupRelation);
    }
}
