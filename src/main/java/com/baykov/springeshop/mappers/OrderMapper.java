package com.baykov.springeshop.mappers;

import com.baykov.springeshop.dtos.OrderDto;
import com.baykov.springeshop.dtos.OrderPositionDto;
import com.baykov.springeshop.models.Order;
import com.baykov.springeshop.models.OrderPosition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderPosition toOrderPosition(OrderPositionDto orderPositionDto);

    OrderPositionDto fromOrderPosition(OrderPosition orderPosition);

    Order toOrder(OrderDto orderDto);

    OrderDto fromOrder(Order order);

    Set<Order> toOrders(Set<OrderDto> orderDtos);

    Set<OrderDto> fromOrders(Set<Order> orders);
}