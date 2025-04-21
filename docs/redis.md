## Redis

### 1. Lettuce
- setnx(set if not exist)로 분산락 구현
  - key, value를 set 할 때 기존 값이 없을 때만 set
- spin lock 방식
  - retry 로직을 개발자가 작성
  - lock을 획득하려는 스레드가 락을 사용할 수 있는지 반복적으로 확인하며 락 획득 시도
- 장점
  - 구현이 간단함
- 단점
  - spin lock 방식이므로 redis에 부하를 줄 수 있음
  - 따라서 Thread.sleep을 통해 락 획득 재시도 간에 텀을 두어야 함

### 2. Redisson
- pub-sub 기반으로 Lock 구현 제공
- 채널을 하나 만들고 lock 점유 중인 스레드가 lock 획득을 대기 중인 스레드에 해제를 알리면, 안내 받은 스레드가 lock 획득 시도
- 별도의 retry 로직 작성할 필요 없음

