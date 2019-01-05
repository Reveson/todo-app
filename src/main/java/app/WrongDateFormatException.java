package app;

import org.aeonbits.owner.ConfigFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class WrongDateFormatException extends Exception {
    private DateFormatException exceptionType;

    public WrongDateFormatException(DateFormatException exceptionType) {
        super(exceptionType.getDescription());
        this.exceptionType = exceptionType;
    }

    public DateFormatException getType() {
        return exceptionType;
    }

    public enum DateFormatException {
        WRONG_TIME(),
        WRONG_DATE(),
        DATE_BEFORE_NOW(),
        WRONG_FORMAT();

        private final String description;
        private static final String prefix = "dateFormatException.";
        private AppConfig config = ConfigFactory.create(AppConfig.class);
        private ResourceBundle text = ResourceBundle
                .getBundle("lang", new Locale(config.language(), config.country()));

        DateFormatException() {
            description = text.getString(prefix+this.name());
        }

        public String getDescription() {
            return description;
        }


    }
}
