package store.reader;

import java.util.List;

public interface FileLineReader<ConvenienceEntity> {

    List<ConvenienceEntity> readAll();

    ConvenienceEntity read(String line);
}
