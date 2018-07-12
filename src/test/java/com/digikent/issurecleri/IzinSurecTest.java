package com.digikent.issurecleri;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadir on 19.06.2018.
 */
public class IzinSurecTest {

    @Test
    public void calculate_izinSuresi() {
        String izinSuresiValue = "";
        Long eklenecekGun = 0l;
        Long toplamSaat = 0l;
        Long toplamGun = 0l;

        List<IzinSurecTestDTO> izinSurecTestDTOList = new ArrayList<>();
        izinSurecTestDTOList.add(new IzinSurecTestDTO(2l,9l));
        izinSurecTestDTOList.add(new IzinSurecTestDTO(0l,0l));

        for (IzinSurecTestDTO item: izinSurecTestDTOList) {
            if(item.getSaat() != null && item.getSaat() != null){
                toplamGun = toplamGun + item.getGun().longValue();
                toplamSaat = toplamSaat + item.getSaat().longValue();
            }
        }

        if (toplamSaat != 0) {
            eklenecekGun = toplamSaat.longValue()/8;
            toplamGun = toplamGun + eklenecekGun;
            if (toplamSaat % 8 == 0) {
                izinSuresiValue = toplamGun + " Gün";
            } else if (toplamSaat % 8 != 0) {
                if (toplamGun == 0) {
                    izinSuresiValue = toplamSaat + " Saat";
                } else {
                    izinSuresiValue = toplamGun + " Gün " + (toplamSaat % 8) + " Saat";
                }
            }
        } else if (toplamSaat == 0) {
            izinSuresiValue = toplamGun + " Gün";
        }

        assertEquals(izinSuresiValue,"3 Gün 1 Saat");

    }

}
