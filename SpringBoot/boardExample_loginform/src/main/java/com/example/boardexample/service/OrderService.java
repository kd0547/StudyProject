package com.example.boardexample.service;

import com.example.boardexample.dto.ProductDto;
import com.example.boardexample.entity.Orders;
import com.example.boardexample.entity.OrdersDetails;
import com.example.boardexample.entity.Product;
import com.example.boardexample.entity.User;
import com.example.boardexample.repository.OrderRepository;
import com.example.boardexample.repository.ProductRepository;
import com.example.boardexample.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    public Long OrderSave(Long userId, List<ProductDto> productDtos) {
        User findUser = userRepository.selectUserProduct(userId);


        List<Long> productIds = productDtos.stream()
                .map(ProductDto::getId)
                .toList();

        List<Product> findProducts = productRepository.findProducts(productIds);

        Orders order = new Orders();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(findUser);

        for (ProductDto productDto : productDtos) {
            Product product = findProducts.stream()
                    .filter(p -> Objects.equals(p.getId(), productDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + productDto.getId()));

            product.reduceStock(productDto.getCount());

            OrdersDetails ordersDetails = new OrdersDetails();
            ordersDetails.addProduct(product,productDto.getCount());
            order.addOrderDetails(ordersDetails);
        }

        orderRepository.save(order);

        return order.getId();
    }

}
