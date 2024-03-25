package com.example.kakaomaptest.data.repository;

import com.example.kakaomaptest.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChargeRepository extends JpaRepository<Charge, String> {
    List<Charge> findByBusiNameContaining(String busiName);
    List<Charge> findByChargeTypeContaining(String busiName);
    List<Charge> findByChargeAddrContaining(String busiName);
    List<Charge> findByChargeNameContaining(String busiName);
}