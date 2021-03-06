package com.digikent.config;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application constants.
 */
public final class Constants {

    // Spring profile for development, production and "fast", see http://jhipster.github.io/profiles.html
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_FAST = "fast";
    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    public static final String SYSTEM_ACCOUNT = "system";
    public static final int MESSAGE_OLD_VERSION = 9;
    public static final int MESSAGE_CURRENT_VERSION = 1;
    public static final String MESSAGE_TYPE_REAL_MESSAGE = "real";
    public static final String MESSAGE_TYPE_LINE_MESSAGE = "line";
    public static final long RUHSAT_GENEL_SONUC = 1;
    public static final long RUHSAT_BASVURU_DURUM = 2;
    public static final long MESSAGE_SYSTEM_USER_ID = -1;
    public static final String ERROR_MESSAGE_PAYDAS_MIN_CHARACHTER_SIZE = "Filtreleme yetersiz";
    public static final String DENETIM_AKSIYON_TYPE_CEZA = "CEZA";
    public static final String DENETIM_AKSIYON_TYPE_TUTANAK = "TUTANAK";
    public static final String DENETIM_AKSIYON_TYPE_EKSURE = "EKSURE";
    public static final String DENETIM_AKSIYON_TYPE_KAPAMA = "KAPAMA";

    public static final String TESPIT_AKSIYON_TYPE_CEZA = "CEZA";
    public static final String TESPIT_AKSIYON_TYPE_TUTANAK = "TUTANAK";

    public static final String TESPIT_TUR_TESPIT = "TESPIT";
    public static final String TESPIT_TUR_EKBILGI = "EKBILGI";

    public static final String TESPIT_SECENEK_TURU_CHECHBOX = "CHECKBOX";
    public static final String TESPIT_SECENEK_TURU_TEXT = "TEXT";
    public static final String TESPIT_SECENEK_TURU_DATE = "DATE";
    public static final String TESPIT_SECENEK_TURU_NUMBER = "NUMBER";

    public static final String DENETIM_TARAF_TURU_BELEDIYE = "BELEDIYE";
    public static final String DENETIM_TARAF_TURU_PAYDAS = "PAYDAS";
    public static final String DENETIM_TARAF_TURU_DIGER = "DIGER";

    public static final String DENETIM_TARAF_PAYDAS_GOREV = "PAYDASGOREV";
    public static final String DENETIM_TARAF_MEMUR_GOREV = "MEMURGOREV";

    public static final String PAYDAS_TURU_SAHIS = "S";//şahıs
    public static final String PAYDAS_TURU_KURUM = "K";//Kurum

    public static final String DENETIM_TARAFTIPI_SAHIS = "P";//şahıs paydaşı
    public static final String DENETIM_TARAFTIPI_KURUM = "I";//Kurum-işletme

    public static final String SOSYAL_YARDIM_AKTIVITE_TANIM_TAMAMLANDI = "TAMAMLANDI";

    public static final String[] BPM_IZIN_SUREC = new String[]{"BPM_SERVER_PORT", "BPM_SERVER_HOST_NAME", "BPM_SERVER_USER_NAME", "BPM_SERVER_PASSWORD"};

    public static final String MOBIL = "MOBIL";
    public static final String[] MOBILE_AUTH_FIELD = new String[]{"MOBILE_AUTH_USERNAME", "MOBILE_AUTH_PASSWORD"};

    public static final String FIREBASE_CLOUD_MESSAGING_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String FIREBASE_CLOUD_MESSAGING_API_KEY = "AAAA6uAS0Cs:APA91bFA0P8DW9woh_L-wJdP9yLwqZRgf-THnYQ0vLPO7B_nDoq7zClXFc1x8tyP1TzAtbLjdkAHprZQ5WoabfjMFo73kApQ5qT1M4uSsRJdxCZzNzDG-mWs4rnnaNBOJflhwSV4oJPYd7azazTL_vLUZ9ySVuUHDQ";

    public static final HashMap<String, String> NOTIFICATION_TYPE = new HashMap<String, String>() {{
        put("EBYS_ONAY_BEKLEYEN", "Onay Bekleyen evrağınız bulunmaktadır. \uD83D\uDCDD");
        put("EBYS_GELEN_HAVALE", "Havale edilmiş evrağınız bulunmaktadır.");
        put("SUREC_IZIN_ONAY", "Onay Bekleyen izin süreciniz bulunmaktadır.");
    }};

    public static final HashMap<String, String> IHR1PERSONEL_STATU = new HashMap<String, String>() {{
        put("S", "Sözlesmeli");
        put("M", "Memur");
        put("I", "Isci");
        put("G", "Geçici");
        put("F", "Firma");
        put("L", "Meclis");
        put("O", "Stajyer");
        put("C", "Geçici Memur");
        put("D", "Diğer");
    }};

    private Constants() {
    }
}
