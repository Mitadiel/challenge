package com.reba.api.validation;

public class PeopleValidation {
    private static final String DESCRIPTION = "The value must be between ";

	public static final int NAME_MAX_LENGTH = 20;
	public static final int LAST_NAME_MAX_LENGTH = 20;
	public static final int DOCUMENT_MAX_LENGTH = 9;
	public static final int COUNTRY_MAX_LENGTH = 30;

	public static final String BIRTH_DATE_MSG = "The date of birth must be before the current date.";

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String EMAIL_MSG= "Email must be valid";

	public static final String NULL_MSG = "Field is required.";
	public static final String EMPTY_MSG = "Field can not be empty.";

	public static final String NAME_VALUE = DESCRIPTION + NAME_MAX_LENGTH;
	public static final String LAST_NAME_VALUE = DESCRIPTION + LAST_NAME_MAX_LENGTH;
	public static final String DOCUMENT_VALUE = DESCRIPTION + DOCUMENT_MAX_LENGTH;
	public static final String COUNTRY_VALUE = DESCRIPTION + COUNTRY_MAX_LENGTH;

    private PeopleValidation() {
		super();
	}
}
