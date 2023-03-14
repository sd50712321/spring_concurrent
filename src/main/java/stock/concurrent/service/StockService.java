package stock.concurrent.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import stock.concurrent.domain.Stock;
import stock.concurrent.repository.StockRepository;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // 각 커넥션 따로 얻어서 써야함(named)
    // @Transactional
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
