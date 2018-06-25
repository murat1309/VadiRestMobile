package com.digikent.issurecleri;

import static org.junit.Assert.assertEquals;

public class CalculateTheKullanilacakIzinSuresiTest {

    public static void main(String args[]) {

        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(null), "");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(0L), "");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(1L), "1 Dakika ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(60L), "1 Saat ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(61L), "1 Saat 1 Dakika ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(480L), "1 Gun ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(540L), "1 Gun 1 Saat ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(510L), "1 Gun 30 Dakika ");
        assertEquals(calculateTheKullanilacakIzinSuresiByDakika(541L), "1 Gun 1 Saat 1 Dakika ");
    }

    private static String calculateTheKullanilacakIzinSuresiByDakika(Long toplamIzinSuresiDakika) {


        String izinSuresiText = "";
        if(toplamIzinSuresiDakika != null) {

            Long toplamGun = toplamIzinSuresiDakika / (8 * 60);
            izinSuresiText = toplamGun.equals(0L) ? "" : toplamGun + " Gun ";

            Long toplamSaat = ( toplamIzinSuresiDakika % (8 * 60) ) / 60;
            izinSuresiText += toplamSaat.equals(0L) ? "" : toplamSaat + " Saat ";

            Long toplamDakika = (toplamIzinSuresiDakika % (8 * 60) ) % 60;
            izinSuresiText += toplamDakika.equals(0L) ? "" : toplamDakika + " Dakika ";
        }

        return izinSuresiText;
    }
}
