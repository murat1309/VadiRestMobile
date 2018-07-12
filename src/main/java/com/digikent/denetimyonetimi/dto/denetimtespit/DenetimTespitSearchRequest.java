package com.digikent.denetimyonetimi.dto.denetimtespit;

import java.util.Date;

/**
 * Created by Kadir on 19.03.2018.
 */
public class DenetimTespitSearchRequest {

    /**
     * criteria değeri vergi no, paydaş no, tc no olabilir.
     */
    private Long criteria;
    private String paydasName;
    private String startDate;
    private String endDate;

    public Long getCriteria() {
        return criteria;
    }

    public void setCriteria(Long criteria) {
        this.criteria = criteria;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaydasName() {
        return paydasName;
    }

    public void setPaydasName(String paydasName) {
        this.paydasName = paydasName;
    }
}
