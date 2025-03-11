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

}