package com.example.offerservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferJpaRepository extends JpaRepository<OfferEntity, Long> {
    List<OfferEntity> findByActiveTrue();
    List<OfferEntity> findByCategoryIgnoreCaseAndActiveTrue(String category);
}
