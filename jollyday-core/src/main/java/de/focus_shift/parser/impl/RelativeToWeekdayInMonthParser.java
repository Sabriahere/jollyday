package de.focus_shift.parser.impl;

import de.focus_shift.Holiday;
import de.focus_shift.parser.HolidayParser;
import de.focus_shift.parser.functions.CreateHoliday;
import de.focus_shift.parser.functions.FindWeekDayInMonth;
import de.focus_shift.parser.predicates.ValidLimitation;
import de.focus_shift.spi.Holidays;
import de.focus_shift.spi.Relation;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * RelativeToWeekdayInMonthParser class.
 * </p>
 *
 * @author Sven
 * @version $Id: $
 */
public class RelativeToWeekdayInMonthParser implements HolidayParser {

  @Override
  public List<Holiday> parse(Integer year, Holidays holidays) {
    return holidays.relativeToWeekdayInMonth().stream()
      .filter(new ValidLimitation(year))
      .map(rwm -> {
        LocalDate date = new FindWeekDayInMonth(year).apply(rwm.weekdayInMonth()).plusDays(1);
        int direction = (rwm.when() == Relation.BEFORE ? -1 : 1);
        while (date.getDayOfWeek() != rwm.weekday()) {
          date = date.plusDays(direction);
        }
        return new CreateHoliday(date).apply(rwm);
      })
      .collect(toList());
  }
}
