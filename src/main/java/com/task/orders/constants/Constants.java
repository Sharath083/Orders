package com.task.orders.constants;

public class Constants {

    //redis key
    public static final String REDIS_KEY="onb:";
    public static final String OTP_REDIS_KEY = "otp:";

    public static final String CHARACTERS = "0123456789";
    public static final int OTP_LENGTH = 6;

    //mail
    public static final String ORDER_DETAILS = "Order Details";
    public static final String OTP_SUBJECT = "Your one-time password.";

    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String REMOVE = "remove";
    public static final String CODE = "+91";

    public static final String ORDERS_PATHNAME = "D:\\Orders.pdf";
    public static final String ORDERS_PDF = "Orders.pdf";
    public static final String ORDER_ID_COLUMN = "Order Id";
    public static final String PRODUCTS_COLUMN = "Products";
    public static final String TOTAL_PRICE_COLUMN = "Total price";
    public static final String PATH = "D:\\";
    public static final String TOKEN = "token";
    public static final String API_URL = "${api.url}";
    public static final String API_BASE_URL = "${api.baseUrl}";
    public static final String API_KEY = "${api.key}";
    public static final String API_REQUEST_CODE = "${api.requestCode}";
    public static final String API_USER_ID = "${api.UserId}";
    public static final String API_PASSWORD = "${api.password}";
    public static final String TWILIO_ACCOUNT_SID = "${twilio.account.sid}";
    public static final String TWILIO_AUTH_TOKEN = "${twilio.auth.token}";
    public static final String TWILIO_PHONE_NUMBER = "${twilio.phone.number}";
    public static final String TWILIO_MESSAGE = "${twilio.message}";
    public static final String MAIL_HOST = "${mail.host}";
    public static final String MAIL_PORT = "${mail.port}";
    public static final String MAIL_USERNAME = "${mail.username}";
    public static final String MAIL_PASSWORD = "${mail.password}";
    public static final String FRONTEND_ENDPOINT = "${frontend.endpoint}";



    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";
    public static final String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    public static final String MAIL_SMTP_WRITETIMEOUT = "mail.smtp.writetimeout";
    public static final String MAIL_DEBUG = "mail.debug";
    public static final String SMTP = "smtp";
    public static final String TRUE = "true";
    public static final String TIMEOUT = "5000";
    public static final String SESSION_ID = "session-id";

    public static final String MAIL_Message = "Hi User,\n" +
            "Please find your order details below. ";

    public static final String NAME_SHOULD_NOT_BE_NULL = "Name Should not be null";
    public static final String PASSWORD_SIZE = "Password should be between  5 t0 8 characters";
    public static final String AGE_LIMIT = "Age must be between 18 t0 60";
    public static final String GENDER_MESSAGE = "Gender field should not be null";
    public static final String MOBILE_REG = "^\\d{10}$";
    public static final String INVALID_MOBILE_NUMBER = "Invalid Mobile Number";

    public static final int FIVE = 5;
    public static final int SIXTEEN = 16;
    public static final int EIGHTEEN = 18;
    public static final int SIXTY = 60;
    public static final int TEN = 10;
    public static final String MASK = "XXXXXX";

    public static final String REDIS_HOST = "${redis.host}";
    public static final String REDIS_PORT = "${redis.port}";
    public static final String REDIS_UNAME = "${redis.user.name}";
    public static final String REDIS_PASSWORD = "${redis.password}";
}
