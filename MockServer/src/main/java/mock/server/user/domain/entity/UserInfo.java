package mock.server.user.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserInfo {

    private Long id;
    private String name;
    private String pwd;

    public UserInfo(String name, String pwd){
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString(){
        return "name: " + this.name + " pwd: " + this.pwd;
    }

}
