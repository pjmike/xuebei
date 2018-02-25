package cn.pjmike.xuebei.web.dao;

import cn.pjmike.xuebei.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pjmike
 * @create 2018-02-01 16:57
 **/
@Repository
public class CreateGroupDaoImpl implements CreateGroupDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void saveUserAndVenue(UserDto userDto) {
      /*  mongoTemplate.indexOps(UserDto.class).ensureIndex(new GeospatialIndex("location"));*/
        mongoTemplate.insert(userDto,"userdto");
    }
    @Override
    public List<UserDto> findUserDTOByPwd(Point point,Integer number) {
        //查询距离目标点10米范围内的用户
     /*   NearQuery nearQuery = NearQuery.near(point).maxDistance(new Distance(6, Metrics.MILES));*/
        List<UserDto> userDtos = mongoTemplate.find(Query.query(Criteria.where("number").is(number).and("location").near(point).maxDistance(0.01/111)), UserDto.class,"userdto");
//        List<UserDao> userDtos = mongoTemplate.
       /* GeoResults<UserDto> userDtos = mongoTemplate.geoNear(nearQuery, UserDto.class, "userdto");*/
        return userDtos;
    }

    @Override
    public UserDto findFirstUserDto() {
        UserDto userDto = mongoTemplate.findAll(UserDto.class,"userdto").get(0);
        return userDto;
    }
    @Override
    public List<UserDto> findUserDtoLimit(Point point,Integer number) {
        List<UserDto> userDtos = mongoTemplate.find(Query.query(Criteria.where("number").is(1234).and("location").near(point).maxDistance(0.01/111)).limit(10), UserDto.class,"userdto");
            return userDtos;
    }
    @Override
    public List<UserDto> findAllUserDto() {
        List<UserDto> userDtos = mongoTemplate.findAll(UserDto.class, "userdto");
        return userDtos;
    }
}
