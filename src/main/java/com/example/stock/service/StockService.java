package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * @Transactional을 사용하면 StockService를 wrapping 한 클래스를 새로 만들어서 사용
     * Tx 시작 -> 로직 실행 -> Tx 종료(DB 업데이트)
     * ----------------------------------------------------------------------
     * Tx의 종료 시점에 DB에 업데이트 하는데
     * stockService.decrease가 완료되고 실제 DB에 업데이트 되기 전에
     * 다른 스레드가 stockService.decrease 호출 가능
     * -> 다른 스레드는 갱신 전 값을 가져가기 때문에 동시성 이슈 발생
     */
//    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        // Stock 조회
        Stock stock = stockRepository.findById(id)
                .orElseThrow();

        // 재고 감소
        stock.decrease(quantity);

        // 갱신된 값 저장
        stockRepository.saveAndFlush(stock);
    }
}
