package cn.pjmike.xuebei.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

/**
 * 地点坐标
 *
 * @author pjmike
 * @create 2018-01-29 16:35
 **/
@Document(collection = "location")
public class Venue {
    @Id
    private String id;
    private String name;
    private double[] location;
    @PersistenceConstructor
    public Venue(String name, double[] location) {
        this.name = name;
        this.location = location;
    }
    public Venue(String name, double x, double y) {
        this.name = name;
        this.location = new double[]{x,y};
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location=" + Arrays.toString(location) +
                '}';
    }
}
