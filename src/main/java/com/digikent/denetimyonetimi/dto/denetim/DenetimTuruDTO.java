package com.digikent.denetimyonetimi.dto.denetim;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 26.01.2018.
 */
public class DenetimTuruDTO implements Serializable {

    private Long id;
    private String tanim;
    private List<TespitGrubuDTO> tespitGrubuDTOList;

}
