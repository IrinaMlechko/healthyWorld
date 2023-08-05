package com.example.pharmacy.mapper;

public class DBFields {
    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_DATE_OF_BIRTH = "date_of_birth";

    public static final String CREDENTIALS_ID = "id";
    public static final String CREDENTIALS_LOGIN = "login";
    public static final String CREDENTIALS_PASSWORD = "password";
    public static final String CREDENTIALS_ROLE= "role";
    public static final String CREDENTIALS_USER_ID = "user_id";

    public static final String MEDICINE_ID = "id";
    public static final String MEDICINE_NAME = "medicine_name";
    public static final String MEDICINE_MANUFACTURER = "manufacturer";
    public static final String MEDICINE_PRICE = "price";
    public static final String MEDICINE_DESCRIPTION = "description";
    public static final String MEDICINE_CREATED_AT = "created_at";
    public static final String MEDICINE_PRESCRIPTION_REQUIRED = "prescription_required";

    private DBFields(){}
}
