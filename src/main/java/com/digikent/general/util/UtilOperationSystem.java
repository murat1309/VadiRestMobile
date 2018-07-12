package com.digikent.general.util;

import sun.awt.OSInfo;

/**
 * Created by Kadir on 14.03.2018.
 */
public class UtilOperationSystem {

    public static Boolean isWindows() {
        return (OSInfo.getOSType() == OSInfo.OSType.WINDOWS);
    }

}
