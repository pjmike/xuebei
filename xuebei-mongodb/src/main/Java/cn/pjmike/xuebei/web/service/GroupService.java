package cn.pjmike.xuebei.web.service;

import cn.pjmike.xuebei.domain.UserDto;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-01 17:12
 **/
public interface GroupService {
    /**
     * 保存有位置信息的用户
     * @param userDto
     */
    List<UserDto> saveAndReturnUserDto(UserDto userDto);

    /**
     * 返回输入相同密码且在一定区域内的用户信息
     *以参考点为准，查询一定范围的用户
     * @return
     */
    List<UserDto> findUserDTOByPwd();

    /**
     * 限制返回条数
     * @return
     */
    List<UserDto> findUserDTOByPwdLimit();
}
