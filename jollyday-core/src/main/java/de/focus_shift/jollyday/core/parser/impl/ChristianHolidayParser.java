package de.focus_shift.jollyday.core.parser.impl;

import de.focus_shift.jollyday.core.Holiday;
import de.focus_shift.jollyday.core.parser.HolidayParser;
import de.focus_shift.jollyday.core.parser.functions.CalculateEasterSunday;
import de.focus_shift.jollyday.core.parser.functions.CreateHoliday;
import de.focus_shift.jollyday.core.parser.functions.MoveDateRelative;
import de.focus_shift.jollyday.core.parser.predicates.ValidLimitation;
import de.focus_shift.jollyday.core.spi.Holidays;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * This parser creates christian holidays for the given year relative to easter
 * sunday.
 */
public class ChristianHolidayParser implements HolidayParser {

  @Override
  public List<Holiday> parse(final Year year, final Holidays holidays) {
    return holidays.christianHolidays().stream()
      .filter(new ValidLimitation(year))
      .map(christianHoliday -> {

        LocalDate easterSunday = new CalculateEasterSunday(year).apply(christianHoliday.chronology());

        switch (christianHoliday.type()) {
          case EASTER:
            break;
          case CLEAN_MONDAY:
          case SHROVE_MONDAY:
            easterSunday = easterSunday.minusDays(48);
            break;
          case MARDI_GRAS:
          case CARNIVAL:
            easterSunday = easterSunday.minusDays(47);
            break;
          case ASH_WEDNESDAY:
            easterSunday = easterSunday.minusDays(46);
            break;
          case MAUNDY_THURSDAY:
            easterSunday = easterSunday.minusDays(3);
            break;
          case GOOD_FRIDAY:
            easterSunday = easterSunday.minusDays(2);
            break;
          case EASTER_SATURDAY:
            easterSunday = easterSunday.minusDays(1);
            break;
          case EASTER_MONDAY:
            easterSunday = easterSunday.plusDays(1);
            break;
          case EASTER_TUESDAY:
            easterSunday = easterSunday.plusDays(2);
            break;
          case GENERAL_PRAYER_DAY:
            easterSunday = easterSunday.plusDays(26);
            break;
          case ASCENSION_DAY:
            easterSunday = easterSunday.plusDays(39);
            break;
          case PENTECOST:
          case WHIT_SUNDAY:
            easterSunday = easterSunday.plusDays(49);
            break;
          case WHIT_MONDAY:
          case PENTECOST_MONDAY:
            easterSunday = easterSunday.plusDays(50);
            break;
          case CORPUS_CHRISTI:
            easterSunday = easterSunday.plusDays(60);
            break;
          case SACRED_HEART:
            easterSunday = easterSunday.plusDays(68);
            break;
          default:
            throw new IllegalArgumentException("Unknown christian holiday type " + christianHoliday.type());
        }
        final LocalDate movedChristianHoliday = new MoveDateRelative(easterSunday).apply(christianHoliday);
        return new CreateHoliday(easterSunday, movedChristianHoliday).apply(christianHoliday);
      })
      .collect(toList());
  }
}
