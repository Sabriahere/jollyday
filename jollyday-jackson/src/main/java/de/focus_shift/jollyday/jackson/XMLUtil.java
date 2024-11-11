package de.focus_shift.jollyday.jackson;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.focus_shift.jollyday.jackson.mapping.Configuration;
import de.focus_shift.jollyday.jackson.mapping.Month;
import de.focus_shift.jollyday.jackson.mapping.Weekday;

import java.io.InputStream;
import java.time.DayOfWeek;

public class XMLUtil {

  private static final XmlMapper mapper = new JacksonMapperCreator().create();

  /**
   * Unmarshalls the configuration from the stream. Uses <code>jackson</code> for
   * this.
   *
   * @param stream a {@link InputStream} object.
   * @return The unmarshalled configuration.
   */
  public Configuration unmarshallConfiguration(InputStream stream) {
    try {
      return mapper.readValue(stream, Configuration.class);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot parse holidays XML file.", e);
    }
  }

  /**
   * Returns the {@link DayOfWeek} equivalent for the given weekday.
   *
   * @param weekday a {@link Weekday} object.
   * @return a DayOfWeek instance.
   */
  public final DayOfWeek getWeekday(Weekday weekday) {
    return DayOfWeek.valueOf(weekday.value());
  }

  /**
   * Returns the value for the given month.
   *
   * @param month a {@link Month} object.
   * @return a 1-12 value.
   */
  public int getMonth(Month month) {
    return month.ordinal() + 1;
  }

  public static class JacksonMapperCreator {
    public XmlMapper create() {
      final XmlMapper mapper = new XmlMapper();
      mapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
      return mapper;
    }
  }
}
