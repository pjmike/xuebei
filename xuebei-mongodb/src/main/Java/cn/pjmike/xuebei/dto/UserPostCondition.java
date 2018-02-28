package cn.pjmike.xuebei.dto;

import cn.pjmike.xuebei.domain.User;

import javax.validation.constraints.NotBlank;

/**
 * user的dto类，用于登录传递参数
 *
 * @author pjmike
 * @create 2018-02-24 20:57
 */
public class UserPostCondition {

    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "密码不能为空")
    private String password;

    private int state;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
