package com.example.kakaomaptest.service;

import java.util.List;
import java.util.Optional;

import com.example.kakaomaptest.Charge;
import com.example.kakaomaptest.data.repository.ChargeRepository;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {
    private final ChargeRepository chargeRepository;
    public ChargeService(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    public List<Charge> getAllCharges() {
        return chargeRepository.findAll();
    }

    public Charge getChargeById(String id) {
        Optional<Charge> charge = chargeRepository.findById(id);
        return charge.get();
    }

    public void saveCharge(Charge charge) {
        chargeRepository.save(charge);
    }

    public void updateCharge(Charge charge) {
        chargeRepository.save(charge);
    }

    public void deleteCharge(String id) {
        chargeRepository.deleteById(id);
    }

}