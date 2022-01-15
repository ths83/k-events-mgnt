package com.ths83.keventsapi.fixtures;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ZonedDateTimeFixtures {

	public static final String EUROPE_TIME = "2018-10-25T23:12:31.123+02:00[Europe/Paris]";

	public static ZonedDateTime getZonedDateTime() {
		return ZonedDateTime.parse(EUROPE_TIME);
	}

}
