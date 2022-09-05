package com.baykov.springeshop.daos;

import com.baykov.springeshop.models.Order;
import com.baykov.springeshop.models.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDao {
    private final EntityManager entityManager;

    public Set<Order> getOrders() {
        Session session = entityManager.unwrap(Session.class);
        String query = "select o from Order o left join fetch o.orderPositions";
        return new HashSet<>(session.createQuery(query, Order.class).getResultList());
    }

    public Set<Order> getOrdersByStatus(OrderStatus status) {
        Session session = entityManager.unwrap(Session.class);
        String query = "select o from Order o left join fetch o.orderPositions where o.orderStatus = :status";
        return new HashSet<>(session.createQuery(query, Order.class).setParameter("status", status).getResultList());
    }

    public Set<Order> getOrdersByUserId(Long id) {
        Session session = entityManager.unwrap(Session.class);
        String query = "select o from Order o left join fetch o.orderPositions where o.user.id = :id";
        return new HashSet<>(session.createQuery(query, Order.class).setParameter("id", id).getResultList());
    }
}
