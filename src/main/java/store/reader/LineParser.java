package store.reader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.constants.MdFileRegex;
import store.exception.BusinessException;
import store.exception.ErrorMessage;

public class LineParser {

    protected final Matcher matcher;

    protected LineParser(String regex, String line) {
        this.matcher = Pattern.compile("^" + regex + "$").matcher(line);
    }

    public static ProductParser withProductRegex(String line) {
        return ProductParser.read(line);
    }

    public static PromotionParser withPromotionRegex(String line) {
        return PromotionParser.read(line);
    }

    protected static String buildRegex(MdFileRegex... regexes) {
        return Stream.of(regexes)
                .map(MdFileRegex::toString)
                .collect(Collectors.joining(","));
    }

    protected void validate() {
        if (!matcher.matches()) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
    }
}
