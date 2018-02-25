package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.User;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-01-29 20:50
 **/
public interface UserDao {
    /**
     * 添加用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAllUser();

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByName(String username);

    /**
     * 通过email查询用户
     * @param email
     * @return
     */
    User findUserByEmail(String email);

    /**
     * 通过用户名和密码查询用户
     * @param email
     * @param password
     * @return
     */
    User findUserByEmailAndPassword(String email, String password);

    /**
     * 更新用户信息
     * @param query
     * @param update
     * @return
     */
    WriteResult updateUser(Query query, Update update);
}
