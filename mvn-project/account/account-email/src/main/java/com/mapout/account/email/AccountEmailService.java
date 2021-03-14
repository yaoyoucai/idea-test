package com.mapout.account.email;

public interface AccountEmailService {
    void sendEmail(String to, String subject, String htmlText);

}
