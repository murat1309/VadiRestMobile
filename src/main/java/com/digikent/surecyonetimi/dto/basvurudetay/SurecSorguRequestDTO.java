package com.digikent.surecyonetimi.dto.basvurudetay;

/**
 * Created by Medet on 12/28/2017.
 */
public class SurecSorguRequestDTO {

    private String sorguNo;
    private String tabName;

    public SurecSorguRequestDTO() {
    }

    public String getSorguNo() {
        return sorguNo;
    }

    public void setSorguNo(String sorguNo) {
        this.sorguNo = sorguNo;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
