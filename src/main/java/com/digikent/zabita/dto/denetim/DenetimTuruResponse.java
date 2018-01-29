package com.digikent.zabita.dto.denetim;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 26.01.2018.
 */
public class DenetimTuruResponse implements Serializable {

    private List<DenetimTuruDTO> denetimTuruDTOList;







    public List<DenetimTuruDTO> getDenetimTuruDTOList() {
        return denetimTuruDTOList;
    }

    public void setDenetimTuruDTOList(List<DenetimTuruDTO> denetimTuruDTOList) {
        this.denetimTuruDTOList = denetimTuruDTOList;
    }
}
