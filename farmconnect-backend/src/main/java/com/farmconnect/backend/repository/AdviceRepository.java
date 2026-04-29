package com.farmconnect.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmconnect.backend.entity.Advice;

public interface AdviceRepository extends JpaRepository<Advice, Long> {

    List<Advice> findByFarmerId(Long farmerId);

    List<Advice> findByExpertId(Long expertId);

    List<Advice> findByStatus(String status);
}