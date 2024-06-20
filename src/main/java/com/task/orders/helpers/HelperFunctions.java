package com.task.orders.helpers;

import java.security.SecureRandom;

import static com.task.orders.constants.Constants.CHARACTERS;
import static com.task.orders.constants.Constants.OTP_LENGTH;

public class HelperFunctions {
    public static String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return otp.toString();
    }
}
