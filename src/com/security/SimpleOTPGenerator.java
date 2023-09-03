package com.security;

import java.util.Random;

public class SimpleOTPGenerator implements OTPGenerator{
	
    public String generateOTP() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        return String.valueOf(otp);
        
    }

}
