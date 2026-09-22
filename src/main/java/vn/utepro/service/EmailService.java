package vn.utepro.service;

public interface EmailService {
    void sendOtp(String email, String otp, String subject);
}