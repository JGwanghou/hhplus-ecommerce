package com.hhplus.commerce._3weeks.domain.product;

public interface ProductStockRepository {
    int findProductStockByProductId(Long id);
}
