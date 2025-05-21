### 해당 bidirection에서는 다대일 Mapping을 기준으로 하고 있다.
- N이 주인인 경우를 기준으로 반대로 1:N관계를 (1)을 주인으로 하게 된다면, 이와는 다르게 된다.

``` java
@OneToMany
@JoinColumn(name = "TEAM_ID")
private List<Member> members = new ArrayList<>();

@ManyToOne(mappedBy = "members")
private Member member;
```

```mappedBy``` 가 있는게 주인이 아니다. 위의 One-To-Many는 지양해야 하며 일반적으로 N:1 (ManyToOne - 주인이 N)을 사용할 수 있게 설계해야한다.

* OneToMany의 경우 Table이 복잡해지면 추적이 안된다. One을 Update하면 Many가 다 업데이트 되니까.