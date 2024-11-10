package store.reader.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import store.constants.ParseModelRegex;
import store.exception.BusinessException;
import store.exception.ErrorMessage;

public abstract class LineParser<Parsable> {

    protected final Matcher matcher;

    protected LineParser(String line) {
        this.matcher = Pattern.compile("^" + getRegex() + "$").matcher(line);
        validate();
    }

    protected String buildRegex(String delimiters, ParseModelRegex... regexes) {
        return Stream.of(regexes)
                .map(ParseModelRegex::toString)
                .collect(Collectors.joining(delimiters));
    }

    protected void validate() {
        if (!matcher.matches()) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
    }

    protected abstract String getRegex();

    public abstract Parsable parse();
}
