import { format, utcToZonedTime } from "date-fns-tz";

export const datePattern = "YYYY-MM-DD HH:mm";
const localDatePattern = "EEEE yyyy-MM-dd HH:mm";

/**
 * Format given UTC date to client's date zone.
 * @param utcDate The given UTC date.
 * @returns The formatted date to client's zone.
 */
export const toLocalDate = (utcDate: string) => {
  const localTimeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
  const zonedDate = utcToZonedTime(new Date(utcDate), localTimeZone);

  return format(zonedDate, localDatePattern, { timeZone: localTimeZone });
};
