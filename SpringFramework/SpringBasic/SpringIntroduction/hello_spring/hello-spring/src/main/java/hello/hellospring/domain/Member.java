package hello.hellospring.domain;

public class Member {

    private Long id; //오 Long을 쓰는구나? 8byte
    private String name;

    //Getter Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
