package com.digikent.issurecleri;

/**
 * Created by Kadir on 19.06.2018.
 */
public class IzinSurecTestDTO {
    private Long saat;
    private Long gun;

    public IzinSurecTestDTO(Long gun,Long saat) {
        this.saat = saat;
        this.gun = gun;
    }

    public Long getSaat() {
        return saat;
    }

    public void setSaat(Long saat) {
        this.saat = saat;
    }

    public Long getGun() {
        return gun;
    }

    public void setGun(Long gun) {
        this.gun = gun;
    }
}
