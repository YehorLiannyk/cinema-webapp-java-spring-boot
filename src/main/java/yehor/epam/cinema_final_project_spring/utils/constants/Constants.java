package yehor.epam.cinema_final_project_spring.utils.constants;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Class holding all constants
 */
public final class Constants {
    public static final int DEFAULT_PAGING_SIZE = 6;
    public static final String DEF_PAGING_SIZE_STR = "6";

    public static final String PAGE_NO_PARAM = "page";
    public static final String PAGE_SIZE_PARAM = "size";
    public static final String PAGE_AMOUNT_PARAM = "pageAmount";

    public static final int PASSWORD_ENCODE_STRENGTH = 10;

    /**
     * Name of USER role
     */
    public static final String USER_ROLE = "USER";
    /**
     * Name of ADMIN role
     */
    public static final String ADMIN_ROLE = "ADMIN";

    /*  VALIDATION  */
    // General
    public static final String ONLY_LETTERS_PATTERN = "^[a-zA-Z]+$";
    public static final String ONLY_DIGITS_PATTERN = "^[0-9]+$";
    // User
    public static final int MAX_FIRST_NAME_LENGTH = 45;
    public static final int MIN_FIRST_NAME_LENGTH = 1;
    public static final int MAX_LAST_NAME_LENGTH = 45;
    public static final int MIN_LAST_NAME_LENGTH = 1;
    public static final int MAX_PASS_LENGTH = 120;
    public static final int MIN_PASS_LENGTH = 3;
    public static final String PHONE_NUMBER_PATTERN = "^(380[0-9]{9})|(^$)|(^\\s*$)$"; // empty/has a gap/is a number
    // Session
    public static final int MIN_TICKET_COST = 1;
    public static final int MAX_TICKET_COST = 5000;
    // Film
    public static final int MAX_FILM_NAME_LENGTH = 100;
    public static final int MAX_FILM_DESC_LENGTH = 1000;
    public static final int MIN_FILM_DURATION_IN_MINUTE = 10;
    public static final int MAX_FILM_DURATION_IN_MINUTE = 300;

    /**
     * Lifetime of Cookie login in seconds, equal to 90 days
     */
    public static final int COOKIE_LOGIN_LIFETIME = (int) Duration.ofDays(90).toSeconds();
    /**
     * Lifetime of Cookie Local in seconds, equal to 360 days
     */
    public static final int COOKIE_LANG_LIFETIME = (int) Duration.ofDays(360).toSeconds();

    /**
     * Minimum set time for Session
     */
    public static final LocalTime MIN_SESSION_TIME = LocalTime.parse("09:00");
    /**
     * Maximum set time for Session
     */
    public static final LocalTime MAX_SESSION_TIME = LocalTime.parse("22:00");

    // Constants for Filter and Sorter on Schedule page
    /**
     * Schedule page filter param name
     */
    public static final String FILTER_SHOW_PARAM = "show";
    /**
     * Schedule page filter "show only available" param value
     */
    public static final String FILTER_SHOW_ONLY_AVAILABLE = "available";
    /**
     * Schedule page filter "show all" param value
     */
    public static final String FILTER_SHOW_ALL = "all";
    /**
     * Schedule page sorter param name
     */
    public static final String SORT_BY_PARAM = "sortBy";
    /**
     * Schedule page sorter by datetime param value
     */
    public static final String SORT_BY_DATETIME = "dateTime";
    /**
     * Schedule page sorter by film name param value
     */
    public static final String SORT_BY_FILM_NAME = "filmName";
    /**
     * Schedule page sorter by remaining seats param value
     */
    public static final String SORT_BY_SEATS_REMAIN = "seatsRemain";

    /**
     * Schedule page sort method param name
     */
    public static final String SORT_METHOD_PARAM = "sortMethod";
    /**
     * Schedule page descending sort method
     */
    public static final String SORT_METHOD_DESC = "desc";
    /**
     * Schedule page ascending sort method
     */
    public static final String SORT_METHOD_ASC = "asc";

    /**
     * Path to font for PDF former
     */
    public static final String FONTS_BAHNSCHRIFT_TTF_PATH = "static/fonts/bahnschrift.ttf";

    /**
     * Email for No Reply
     */
    public static final String NO_REPLY_EMAIL = "noreply@cinema-epam-localhost.com";

    /**
     * Default name for ticket file name
     */
    public static final String DEF_TICKET_FILENAME = "ticket";

    private Constants() {
    }
}
