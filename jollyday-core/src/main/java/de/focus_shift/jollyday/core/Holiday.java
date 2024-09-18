package de.focus_shift.jollyday.core;

import de.focus_shift.jollyday.core.util.ResourceUtil;

import java.time.LocalDate;
import java.util.Locale;

/**
 * Represents the holiday and contains the actual date and an localized
 * description.
 */
public final class Holiday implements Comparable<Holiday> {
  /**
   * The calculated hashcode cached for performance.
   */
  private int hashCode = 0;
  /**
   * The date the holiday occurs.
   */
  private final LocalDate date;
  /**
   * The observed date of the holiday.
   */
  private final LocalDate observedDate;
  /**
   * The properties key to retrieve the description with.
   */
  private final String propertiesKey;

  /**
   * The type of holiday. e.g. official holiday or not.
   */
  private final HolidayType type;

  /**
   * Constructs a holiday for a date using the provided properties key to
   * retrieve the description with.
   *
   * @param date          a {@link LocalDate} object.
   * @param propertiesKey a {@link java.lang.String} object.
   * @param type          a {@link HolidayType} object.
   */
  public Holiday(final LocalDate date, final String propertiesKey, final HolidayType type) {
    this(date, date, propertiesKey, type);
  }

  /**
   * Constructs a holiday for a date using the provided properties key to
   * retrieve the description with.
   *
   * @param date          a {@link LocalDate} object.
   * @param observedDate  a {@link LocalDate} object.
   * @param propertiesKey a {@link java.lang.String} object.
   * @param type          a {@link HolidayType} object.
   */
  public Holiday(final LocalDate date, final LocalDate observedDate, final String propertiesKey, final HolidayType type) {
    super();
    this.type = type;
    this.date = date;
    this.observedDate = observedDate;
    this.propertiesKey = propertiesKey == null ? "" : propertiesKey;
  }

  /**
   * <p>
   * Getter for the field <code>date</code>.
   * </p>
   *
   * @return the holiday date
   */
  public LocalDate getDate() {
    return date;
  }

  /**
   * <p>
   * Getter for the field <code>observedDate</code>.
   * </p>
   *
   * @return the observed holiday date
   */
  public LocalDate getObservedDate() {
    return observedDate;
  }

  /**
   * <p>
   * Getter for the field <code>propertiesKey</code>.
   * </p>
   *
   * @return the holidays properties key
   */
  public String getPropertiesKey() {
    return propertiesKey;
  }

  /**
   * The description read with the default locale.
   *
   * @return Description for this holiday
   */
  public String getDescription() {
    return ResourceUtil.getHolidayDescription(Locale.getDefault(), getPropertiesKey());
  }

  /**
   * The description read with the provided locale.
   *
   * @param locale a {@link java.util.Locale} object.
   * @return Description for this holiday
   */
  public String getDescription(Locale locale) {
    return ResourceUtil.getHolidayDescription(locale, getPropertiesKey());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Holiday) {
      final Holiday other = (Holiday) obj;
      return other.date.equals(this.date) && other.observedDate.equals(this.observedDate) && other.propertiesKey.equals(this.propertiesKey) && type.equals(other.type);
    }
    return false;
  }

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      int hash = 1;
      hash = hash * 31 + date.hashCode();
      hash = hash * 31 + observedDate.hashCode();
      hash = hash * 31 + propertiesKey.hashCode();
      hash = hash * 31 + type.hashCode();
      hashCode = hash;
    }
    return hashCode;
  }

  @Override
  public String toString() {
    return date.toString() + " (" + getDescription() + ")";
  }

  /**
   * Gets the type holiday.
   *
   * @return the type holiday
   */
  public HolidayType getType() {
    return type;
  }

  /**
   * Compares this holiday to another holiday.
   * <p>
   * The comparison is primarily based on the date, from earliest to latest by using the LocalDate comparator.
   *
   * @param other the other holiday to compare to, not null
   * @return the comparator value, negative if less, positive if greater
   */
  @Override
  public int compareTo(final Holiday other) {
    return this.getDate().compareTo(other.getDate());
  }
}
