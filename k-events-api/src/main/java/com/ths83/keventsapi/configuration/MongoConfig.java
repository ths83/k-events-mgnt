package com.ths83.keventsapi.configuration;

import com.ths83.keventsapi.configuration.converter.StringToZonedDateTimeConverter;
import com.ths83.keventsapi.configuration.converter.ZonedDateTimeToStringConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String databaseName;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public MongoCustomConversions customConversions() {
		final var converters =
				List.of(new ZonedDateTimeToStringConverter(),
						new StringToZonedDateTimeConverter());
		return new MongoCustomConversions(converters);
	}

}