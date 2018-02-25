package cn.pjmike.xuebei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Date;


/**
 * 用户及其位置和密码
 *
 * @author pjmike
 * @create 2018-02-01 16:24
 **/
@Document(collection = "userdto")
public class UserDto extends User{
    /**
     *用户的位置
     *地理索引创建注解
     */
    @GeoSpatialIndexed
    private double[] location;
    /**
     * 用户输入的数字
     */
    private Integer number;
    /**
     * 生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date create_time;

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "location=" + Arrays.toString(location) +
                ", number=" + number +
                ", create_time=" + create_time +
                '}';
    }
}
