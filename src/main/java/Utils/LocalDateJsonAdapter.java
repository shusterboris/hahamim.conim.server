package Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Необходим для корректной конвертации локальной даты в json и назад
 */
public final class LocalDateJsonAdapter extends TypeAdapter<LocalDate> {
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDate value) throws IOException {
        jsonWriter.value(dtf.format(value));
    }

    @Override
    public LocalDate read( final JsonReader jsonReader ) throws IOException {
    	String dateStr = jsonReader.nextString();
        return LocalDate.parse(dateStr, dtf);
    }
}
