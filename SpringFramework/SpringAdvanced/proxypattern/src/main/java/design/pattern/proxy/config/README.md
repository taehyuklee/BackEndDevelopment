## Config에 대한 설명
- 프록시 패턴의 특성상 app부분의 service, controller, impl등은 건드리지 않고 부가기능을 입히기때문에, config부분만 바뀌게 된다 <br>

1. v1_proxy: 순수 proxy를 적용했을때 - 자바만으로 프록시패턴, 또는 데코레이션패턴을 구현했을때의 케이스 <br>

2. v2_dynamicproxy: jdk또는 cglib를 통해서 (Java reflection기반) 프록시를 만들때의 케이스 <br>

3. v3_proxyfactory: 스프링에서 제공해주는 추상화된 개념의 proxyfactory를 이용해서 어떻게 구현하는지에 대한 케이스 <br>
    * 여전히 수동으로 프록시 객체 빈을 등록해야한다는 한계점이 있고 이것을 아래 빈 후처리기를 통해 컴포넌트 스캔에 의해 타겟이 등록될때 가로채서 프록시로 대신 저장하게 될 것<br><br>

4. v4_postprocessor: 빈후처리기를 통해서 프록시를 자동으로 등록하게 하기 위한 내용. <br>

5. v5_autoproxy: 스프링에서 pointcut만으로 프록시를 생성해주는 기능이 있다. 이에 대한 개념과 사용방법에 대해서 설명한다. - 어드바이저의 pointcut으로 자동 프록시 생성기 <br>
    * 결국 스프링도 위의 과정을 거쳐서 v5만으로 어드바이저안의 pointcut으로 자동으로 proxy객체를 만들수 있었다