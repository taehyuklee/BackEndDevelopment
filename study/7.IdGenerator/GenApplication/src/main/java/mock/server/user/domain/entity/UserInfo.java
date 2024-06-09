package mock.server.user.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import mock.server.user.id_generator.SnowFlakeIdGenerator;

@Getter
@Setter
@NoArgsConstructor //이거 있어야 구현이 가능하다.
@Slf4j
public class UserInfo {

    SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator();

    private Long id;

    private String name;

    private String pwd;

    public UserInfo(String name, String pwd) throws Exception {
        this.id = idGenerator.generateNewId(1L, 2L);
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString(){
        return "name: " + this.name + " pwd: " + this.pwd;
    }



}
