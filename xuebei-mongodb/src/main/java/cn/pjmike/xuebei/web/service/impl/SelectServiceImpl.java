package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;
import cn.pjmike.xuebei.web.dao.SelectDao;
import cn.pjmike.xuebei.web.service.SelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-03-19 21:45
 */
@Service
public class SelectServiceImpl implements SelectService{
    @Autowired
    private SelectDao selectDao;
    @Override
    public List<User> selectUserByLike(String name) {
        return selectDao.selectUserByLike(name);
    }

    @Override
    public List<UserGroup> selectGroupByLike(String name) {
        return selectDao.selectGroupByLike(name);
    }
}
