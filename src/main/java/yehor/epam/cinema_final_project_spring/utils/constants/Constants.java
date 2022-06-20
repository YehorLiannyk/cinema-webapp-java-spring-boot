package yehor.epam.cinema_final_project_spring.utils.constants;

import java.time.Duration;

public final class Constants {
    public static final int DEFAULT_PAGING_SIZE = 2;
    public static final String DEF_PAGING_SIZE_STR = "2";

    public static final String PAGE_NO_PARAM = "page";
    public static final String PAGE_SIZE_PARAM = "size";
    public static final String PAGE_AMOUNT_PARAM = "pageAmount";

    public static final int PASSWORD_ENCODE_STRENGTH = 10;

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";

    public static final int MAX_FIRST_NAME_LENGTH = 45;
    public static final int MIN_FIRST_NAME_LENGTH = 1;
    public static final int MAX_LAST_NAME_LENGTH = 45;
    public static final int MIN_LAST_NAME_LENGTH = 1;
    public static final int MAX_PASS_LENGTH = 120;
    public static final int MIN_PASS_LENGTH = 3;
    public static final String PHONE_NUMBER_PATTERN = "^(380[0-9]{9})|(^$)|(^\\s*$)$";

    public static final int MIN_TICKET_COST = 1;
    public static final int MAX_TICKET_COST = 5000;

    public static final int MAX_FILM_NAME_LENGTH = 100;
    public static final int MAX_FILM_DESC_LENGTH = 1000;
    public static final int MIN_FILM_DURATION_IN_MINUTE = 10;
    public static final int MAX_FILM_DURATION_IN_MINUTE = 300;

    public static final String ONLY_LETTERS_PATTERN = "^[a-zA-Z]+$";
    public static final String ONLY_DIGITS_PATTERN = "^[0-9]+$";

    /**
     * Lifetime of Cookie login in seconds, equal to 90 days
     */
    public static final int COOKIE_LOGIN_LIFETIME = (int) Duration.ofDays(90).toSeconds();
    /**
     * Lifetime of Cookie Local in seconds, equal to 360 days
     */
    public static final int COOKIE_LANG_LIFETIME = (int) Duration.ofDays(360).toSeconds();

    private Constants() {
    }
}
