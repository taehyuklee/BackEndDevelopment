
TraceID와 FieldLogTrace를 singleton으로 관리함으로써, 동기화를 할때, parameter를 넘겨주지 않아도 되게 만들었습니다.
하지만, singleton이기에, 여러 스레드가 접근할때 raceCondtion에 의해 동시성문제가 발생할수 있습니다.

(스핀락, 세마포 알고리즘과 같은 것으로 해결할수도 있겠지만, ThreadLocal이란걸 (각 스레드마다의 스택영역 (고유영역))을 이용해서 해결해보고자 합니다)