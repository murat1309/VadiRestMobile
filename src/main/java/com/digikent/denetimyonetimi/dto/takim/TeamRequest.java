package com.digikent.denetimyonetimi.dto.takim;

import java.io.Serializable;

/**
 * Created by Kadir on 22.02.2018.
 */
public class TeamRequest implements Serializable {

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
