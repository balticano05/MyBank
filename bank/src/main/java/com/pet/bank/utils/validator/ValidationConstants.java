package com.pet.bank.utils.validator;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationConstants {

    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;

    public static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";

    public static final int MAX_ADDRESS_LENGTH = 255;

    public static final String NAME_LENGTH_MESSAGE =
            "Length must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters";

    public static final String PHONE_MESSAGE =
            "Phone number must be in the format +XXXXXXXXXXX";

    public static final String ADDRESS_LENGTH_MESSAGE =
            "Address must not exceed " + MAX_ADDRESS_LENGTH + " characters";

    public static final String BANK_ACCOUNT_STATUS_REGEX = "^(ACTIVE|INACTIVE|BLOCKED)$";
    public static final String BANK_ACCOUNT_STATUS_MESSAGE = "Invalid account status. Allowed values: ACTIVE, INACTIVE, BLOCKED";

    public static final int CURRENCY_CODE_LENGTH = 3;
    public static final String CURRENCY_CODE_MESSAGE = "Currency code must be 3 characters";

    public static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    public static final String CARD_NUMBER_MESSAGE = "Card number must be 16 digits";

}