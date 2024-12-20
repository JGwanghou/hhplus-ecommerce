package com.hhplus.commerce._3weeks.domain.product;

import com.hhplus.commerce._3weeks.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductReader {
    private final ProductRepository productRepository;

    public List<Product> readProductAll() {
        return productRepository.findProductAll();
    }

    public List<Product> readProductByIds(List<Long> productIds) {
        return productRepository.findByIdsWithStock(productIds);
    }

    public List<Product> readProductPopulars() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        Pageable page = PageRequest.of(0, 5);

        return productRepository.findProductPopulars(startDate, endDate, page);
    }

    public Product readProductDetail(Long id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        return product;
    }
}
