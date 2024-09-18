package de.focus_shift.jollyday.core.parser.functions;

import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.spi.Described;

import java.time.LocalDate;
import java.util.function.Function;

public class CreateHoliday implements Function<Described, Holiday> {

  private final LocalDate localDate;
  private final LocalDate observedLocalDate;

  public CreateHoliday(LocalDate localDate) {
    this(localDate, localDate);
  }

  public CreateHoliday(LocalDate localDate, LocalDate observedLocalDate) {
    this.localDate = localDate;
    this.observedLocalDate = observedLocalDate;
  }


  @Override
  public Holiday apply(final Described described) {
    return new Holiday(localDate, observedLocalDate, described.descriptionPropertiesKey(), described.officiality());
  }
}
