package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.User;
import cn.pjmike.xuebei.web.chat.Model.UserGroup;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-03-19 21:27
 */
public interface SelectDao {
    /**
     * 从mongodb中模糊搜索用户
     *
     * @param name
     * @return
     */
    List<User> selectUserByLike(String name);

    /**
     * 从mongodb中模糊搜索群组
     *
     * @param name
     * @return
     */
    List<UserGroup> selectGroupByLike(String name);
}
