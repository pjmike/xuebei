package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.UserDto;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-01 16:29
 **/
public interface CreateGroupDao {
    /**
     * 保存包含位置信息的用户
     * @param userDto
     */
    void saveUserAndVenue(UserDto userDto);

    /**
     * 返回输入相同密码且在一定区域内的用户信息
     * @param point 以参考点为准，查询一定范围的用户
     * @return
     */
    List<UserDto> findUserDTOByPwd(Point point,Integer number);

    /**
     * 找出第一个插入集合的用户
     * @return
     */
    UserDto findFirstUserDto();

    /**
     * 返回10条插入信息，不足10条，有多少返回多少
     * @return
     */
    List<UserDto> findUserDtoLimit(Point point,Integer number);

    /**
     * 返回所有信息
     * @return
     */
    List<UserDto> findAllUserDto();
}
