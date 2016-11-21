package com.cavalier.model;

import java.io.Serializable;
import java.util.Date;

public class CoursFilter implements Serializable {

    private Long moniteurId;

    private Long cavalierId;

    private Long montureId;

    private Date startDate;

    private Date endDate;


    public Long getMoniteurId() {
        return moniteurId;
    }

    public void setMoniteurId(Long moniteurId) {
        this.moniteurId = moniteurId;
    }

    public Long getCavalierId() {
        return cavalierId;
    }

    public void setCavalierId(Long cavalierId) {
        this.cavalierId = cavalierId;
    }

    public Long getMontureId() {
        return montureId;
    }

    public void setMontureId(Long montureId) {
        this.montureId = montureId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
