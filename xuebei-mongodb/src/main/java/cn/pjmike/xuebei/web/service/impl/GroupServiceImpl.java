package cn.pjmike.xuebei.web.service.impl;

import cn.pjmike.xuebei.web.dao.CreateGroupDao;
import cn.pjmike.xuebei.domain.UserDto;
import cn.pjmike.xuebei.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-01 17:14
 **/
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private CreateGroupDao createGroupDao;

    @Override
    public List<UserDto> saveAndReturnUserDto(UserDto userDto) {
        createGroupDao.saveUserAndVenue(userDto);
        //找出10条数据，不足10条，有多少返回多少
        List<UserDto> userDtos = findUserDTOByPwdLimit();
        return userDtos;
    }

    @Override
    public List<UserDto> findUserDTOByPwd() {
        //找出第一个插入的用户
        UserDto userDto = findUserDto();
        if (userDto == null) {
            return null;
        }
        //数字
        Integer number = findNumber();
        //以第一用户插入的位置信息为基准点
        double[] location = userDto.getLocation();
        Point point = new Point(location[0], location[1]);
        //找出符合条件的用户
        List<UserDto> userDtos = createGroupDao.findUserDTOByPwd(point,number);
        return userDtos;
    }

    @Override
    public List<UserDto> findUserDTOByPwdLimit() {
        //找出第一个插入的用户
        UserDto userDto = findUserDto();
        if (userDto == null) {
            return null;
        }
        //数字
        Integer number = findNumber();
        if (number == null) {
            return null;
        }
        //以第一用户插入的位置信息为基准点
        double[] location = userDto.getLocation();
        Point point = new Point(location[0], location[1]);
        //找出符合条件的用户
        List<UserDto> userDtos = createGroupDao.findUserDtoLimit(point,number);
        //找出数据库中输入密码相同且在10范围内的用户信息，则调用FindUserDto方法
        return userDtos;
    }

    /**
     * 找出数据库中输入密码相同且在10范围内的用户信息
     *
     * @return
     */
    public Integer findNumber() {
        //找出所有插入信息
        List<UserDto> userDtos = createGroupDao.findAllUserDto();
        //判断是否为空
        if (userDtos == null || userDtos.size() == 0) {
            return null;
        }
        //若插入信息只有一条则直接返回其数字
        if (userDtos.size() == 1) {
            return userDtos.get(0).getNumber();
        }
        //辅助对象
        List<UserDto> resultUser = new ArrayList<UserDto>();
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        //利用for循环，用hashmap将具有相同数字的userDto找出来。
        for (UserDto user : userDtos) {
            if (hashMap.get(user.getNumber()) != null) {
                Integer value = hashMap.get(user.getNumber());
                hashMap.put(user.getNumber(), value + 1);
                //将相同数字的用户信息添加到一个新的集合里
                resultUser.add(user);
            } else {
                hashMap.put(user.getNumber(), 1);
            }
        }
        //判断是否为空
        if (resultUser == null || resultUser.size() == 0) {
            return null;
        }
        //找出共同的密码
        Integer num = hashMap.get(resultUser.get(0).getNumber());
        return num;
    }
    public UserDto findUserDto() {
        //找出所有插入信息
        List<UserDto> userDtos = createGroupDao.findAllUserDto();
        //判断是否为空
        if (userDtos == null || userDtos.size() == 0) {
            return null;
        }
        //若插入信息只有一条则直接返回其数字
        if (userDtos.size() == 1) {
            return userDtos.get(0);
        }
        //辅助对象
        List<UserDto> result = new ArrayList<UserDto>();
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        //利用for循环，用hashmap将具有相同数字的userDto找出来。
        for (UserDto user : userDtos) {

            if (hashMap.get(user.getNumber()) != null) {
                Integer value = hashMap.get(user.getNumber());
                hashMap.put(user.getNumber(), value + 1);
                //将相同数字的用户信息添加到一个新的集合里
                result.add(user);
            } else {
                hashMap.put(user.getNumber(), 1);
            }
        }
        //判断是否为空
        if (result == null || result.size() == 0) {
            return null;
        }
        //找出一个参考点
        return result.get(0);
    }

}
