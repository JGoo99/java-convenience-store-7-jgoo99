package store.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import store.exception.BusinessException;
import store.exception.ErrorMessage;

public abstract class MdFileLineReader<ConvenienceEntity> implements FileLineReader<ConvenienceEntity> {

    protected abstract String getFilePath();

    @Override
    public List<ConvenienceEntity> readAll() {
        List<ConvenienceEntity> entities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getFilePath()))) {
            br.lines()
                    .skip(1)
                    .forEach(line -> entities.add(read(line)));
        } catch (IOException | IllegalArgumentException | DateTimeParseException e) {
            throw new BusinessException(ErrorMessage.INVALID_FILE_VALUE);
        }
        return entities;
    }
}
