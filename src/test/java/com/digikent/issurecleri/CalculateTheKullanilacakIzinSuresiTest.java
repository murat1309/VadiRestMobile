package com.digikent.issurecleri;

import com.digikent.surecyonetimi.izinonay.dao.IzinSurecRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateTheKullanilacakIzinSuresiTest {

    @Test
    public void calculate() {

        IzinSurecRepository izinSurecRepository = new IzinSurecRepository();

        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(null), "");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(0L), "");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(1L), "1 Dakika ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(60L), "1 Saat ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(61L), "1 Saat 1 Dakika ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(480L), "1 Gun ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(540L), "1 Gun 1 Saat ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(510L), "1 Gun 30 Dakika ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(541L), "1 Gun 1 Saat 1 Dakika ");
        assertEquals(izinSurecRepository.calculateTheKullanilacakIzinSuresiByDakika(542l),"1 Gun 1 Saat 2 Dakika ");

    }
}
