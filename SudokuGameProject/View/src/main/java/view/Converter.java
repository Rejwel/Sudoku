package view;

import java.util.regex.Pattern;
import javafx.util.StringConverter;


public class Converter extends StringConverter<Integer> {

    @Override
    public String toString(Integer integer) {
        if (integer == 0) {
            return "";
        }
        return String.valueOf(integer);
    }

    @Override
    public Integer fromString(String s) {
        Pattern pattern = Pattern.compile("[0-9]");
        if (pattern.matcher(s).matches()) {
            return Integer.valueOf(s);
        }
        return 0;
    }

}
