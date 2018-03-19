package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;

import java.util.List;

/**
 * 搜索用户
 *
 * @author pjmike
 * @create 2018-03-19 21:19
 */
public interface SelectService {
    /**
     * 模糊搜索用户
     *
     * @param name
     * @return
     */
    List<User> selectUserByLike(String name);

    /**
     * 模糊搜索群组
     *
     * @param name
     * @return
     */
    List<UserGroup> selectGroupByLike(String name);
}
