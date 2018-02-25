package cn.pjmike.xuebei.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 用户类
 *
 * @author pjmike
 * @create 2018-01-29 23:04
 **/
@Document
public class User {
    @Id
    private String id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "用户性别")
    private String gender;
    @ApiModelProperty(value = "用户邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不对")
    private String email;
    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "用户激活状态")
    private Integer state;
    @ApiModelProperty(value = "学校")
    private String school;
    @ApiModelProperty(value = "班级")
    private String grade;

    public User() {
    }
    @PersistenceConstructor
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", icon='" + icon + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", school='" + school + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
