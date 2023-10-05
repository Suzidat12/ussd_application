package com.zik.ussd_application.utils;

import java.util.Date;

public class DataUtil {
    // Calculate the difference in seconds between two dates
    public static int diffTime(Date expiration) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        long timeDifferenceMillis = expirationTimeMillis - currentTimeMillis;

        // Convert milliseconds to seconds (1 second = 1000 milliseconds)
        int timeDifferenceSeconds = (int) (timeDifferenceMillis / 1000);
        return timeDifferenceSeconds;
    }
}
