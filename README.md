# spring_concurrent
스프링 동시성 관련 테스트

Lettuce vs redis
### Lettuce
  - 구현이 간단
  - Spring data redis를 이용하면 lettuce가 기본이기 때문에 별도의 라이브러리를 사용하지 않아도 됨
  - Spin lock 방식이기 때문에 동시에 많은 스레드가 lock 획득
  - 대기 상태라면 redis에 부하가 갈 수 있다

### Reddison
  - 락 획득 재시도를 기본으로 제공
  - Pub-sub 방식으로 구현이 되어 있기 때문에 lettuce와 비교했을 때 redis에 부하가 덜 간다
  - 별도의 라이브러리를 사용해야 한다
  - lock을 라이브러리 차원에서 제공해주기 때문에 사용법을 공부해야 한다

### 실무에서는
  - 재시도가 필요하지 않은 lock은 lettuce
  - 재시도가 필요한 경우 redisson
