package stock.concurrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.concurrent.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
