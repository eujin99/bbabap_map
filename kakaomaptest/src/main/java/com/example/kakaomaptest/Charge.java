package com.example.kakaomaptest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="charge")
public class Charge {

    @Id
    @Column(name="charge_id")
    private String chargeId;

    @Column(nullable = false)
    private String chargeName;

    @Column(nullable=false)
    private String chargeAddr;

    private String chargeType;

    @Column(nullable=false)
    private String busiName;

    public Charge() {}

    public Charge(String chargeId, String chargeName, String chargeAddr, String chargeType, String busiName) {
        this.setChargeId(chargeId);
        this.setChargeName(chargeName);
        this.setChargeAddr(chargeAddr);
        this.setChargeType(chargeType);
        this.setBusiName(busiName);
    }

    public String getChargeId() {
        return chargeId;
    }
    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public String getChargeName() {
        return chargeName;
    }
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getChargeAddr() {
        return chargeAddr;
    }
    public void setChargeAddr(String chargeAddr) {
        this.chargeAddr = chargeAddr;
    }

    public String getChargeType() {
        return chargeType;
    }
    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getBusiName() {
        return busiName;
    }
    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    @Override
    public String toString() {
        return "chargeId:" + chargeId + ",chargeName:" + chargeName + ",chargeAddr:" + chargeAddr
                + ",chargeType:" + chargeType + ",busiName:" + busiName;
    }


}