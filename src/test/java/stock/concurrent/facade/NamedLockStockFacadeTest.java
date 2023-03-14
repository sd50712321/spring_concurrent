package stock.concurrent.facade;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stock.concurrent.domain.Stock;
import stock.concurrent.repository.StockRepository;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NamedLockStockFacadeTest {

    @Autowired
    private NamedLockStockFacade stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void 동시에100개요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        //다른 스레드에서 작업 완료까지 대기해주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0 ; i < threadCount ; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }

            });
        }
        latch.await();
        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertEquals(0L, stock.getQuantity());
    }
}