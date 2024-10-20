package com.hhplus.commerce._3weeks.domain.user;

import com.hhplus.commerce._3weeks.api.dto.request.OrderProductsRequest;
import com.hhplus.commerce._3weeks.api.dto.request.OrderRequest;
import com.hhplus.commerce._3weeks.domain.product.Product;
import com.hhplus.commerce._3weeks.infra.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity mockUser;

    @BeforeEach
    void setUp() {
        mockUser = UserEntity.builder()
                .id(1L)
                .name("조광호")
                .point(10000L)
                .build();
    }

    @Test
    void 유저_정보_조회() {
        when(userRepository.getUserInfo(1L)).thenReturn(mockUser);

        UserEntity userInfo = userService.getUserInfo(mockUser.getId());

        assertEquals(1L, userInfo.getId());
        assertEquals("조광호", userInfo.getName());
        assertEquals(10000L, userInfo.getPoint());
    }

    @Test
    void 주문시_유저포인트_소모() {
        Product testProduct1 = new Product(1L, "스크류바", 2000, 30);
        Product testProduct2 = new Product(2L, "우유", 4000, 20);

        List<OrderProductsRequest> orderProducts = List.of(
                new OrderProductsRequest(testProduct1.getId(), 2),
                new OrderProductsRequest(testProduct2.getId(), 1)
        );

        OrderRequest orderRequest = new OrderRequest(mockUser.getId(), orderProducts, 8000L);

        userService.payment(mockUser, orderRequest);

        assertEquals(2000L, mockUser.getPoint());
    }
}