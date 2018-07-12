package com.digikent.web.rest.dto;

/**
 * Created by Kadir on 21.05.2018.
 * React eğitimindeki ödev için hazırlanmıştır.
 * Gerçek mobil projede kullanılmaması gerekmektedir.
 */
public class MahalleDTO {

    private Long belediyeId;
    private Long userId;

    public Long getBelediyeId() {
        return belediyeId;
    }

    public void setBelediyeId(Long belediyeId) {
        this.belediyeId = belediyeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
