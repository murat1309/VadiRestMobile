package com.digikent.sosyalyardim.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kadir on 29.05.2018.
 */
public class VSY1TespitDTO implements Serializable {

    private String tespitTarihi;
    private String tespitYapan;
    private String tespitBilgisi;
    private List<VSY1TespitLineDTO> tespitLineDTOList;

    public String getTespitTarihi() {
        return tespitTarihi;
    }

    public void setTespitTarihi(String tespitTarihi) {
        this.tespitTarihi = tespitTarihi;
    }

    public String getTespitYapan() {
        return tespitYapan;
    }

    public void setTespitYapan(String tespitYapan) {
        this.tespitYapan = tespitYapan;
    }

    public String getTespitBilgisi() {
        return tespitBilgisi;
    }

    public void setTespitBilgisi(String tespitBilgisi) {
        this.tespitBilgisi = tespitBilgisi;
    }

    public List<VSY1TespitLineDTO> getTespitLineDTOList() {
        return tespitLineDTOList;
    }

    public void setTespitLineDTOList(List<VSY1TespitLineDTO> tespitLineDTOList) {
        this.tespitLineDTOList = tespitLineDTOList;
    }
}
