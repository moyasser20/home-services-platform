package com.example.bookingservice.repository;

import com.example.bookingservice.entity.BookingEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Stateless
public class BookingRepository {

    @PersistenceContext(unitName = "bookingPU")
    private EntityManager entityManager;

    public BookingEntity save(BookingEntity bookingEntity) {
        if (bookingEntity.getId() == null) {
            entityManager.persist(bookingEntity);
            return bookingEntity;
        }
        return entityManager.merge(bookingEntity);
    }

    public Optional<BookingEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(BookingEntity.class, id));
    }

    public List<BookingEntity> findAll() {
        return entityManager.createQuery("SELECT b FROM BookingEntity b ORDER BY b.createdAt DESC", BookingEntity.class)
                .getResultList();
    }

    public List<BookingEntity> findByCustomerId(Long customerId) {
        return entityManager.createQuery(
                        "SELECT b FROM BookingEntity b WHERE b.customerId = :customerId ORDER BY b.createdAt DESC",
                        BookingEntity.class
                )
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
