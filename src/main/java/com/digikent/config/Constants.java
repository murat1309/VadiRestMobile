package com.digikent.config;

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


    private Constants() {
    }
}
