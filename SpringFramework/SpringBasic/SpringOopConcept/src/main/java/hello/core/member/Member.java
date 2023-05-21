package hello.core.member;

//회원 Entity
public class Member {

    private Long id; //Wrapper Class - Long, Primitive datatype long
    //null이 들어갈수도 있으므로 int가 아니라 Integer사용할수 있다 객체형으로.
    private String name;
    private Grade grade;

    public Member(Long id, String memberA, Grade vip) {
        this.id = id;
        this.name = memberA;
        this.grade = vip;
    }

    //생성해서 Getter/Setter - > Command A or Ctrl A 전체 선택됨.
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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
