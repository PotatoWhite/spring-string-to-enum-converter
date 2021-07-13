/*
 * WebConfig.java 2021. 07. 12
 *
 * Copyright 2021 Naver Cloud Corp. All rights Reserved.
 * Naver Business Platform PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package me.potato.springstringtoenumconverter.kornbutter;

import java.util.Arrays;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import lombok.SneakyThrows;

/**
 * @author dongju.paek
 */
public class StringToEnumConverter implements ConverterFactory<String, Enum<? extends EnumCode>> {

	@Override
	public <T extends Enum<? extends EnumCode>> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToEnumsConverter<>(targetType);
	}

	private static final class StringToEnumsConverter<T extends Enum<? extends EnumCode>> implements Converter<String, T> {

		private final Class<T> enumType;
		private final boolean  constantEnum;

		public StringToEnumsConverter(Class<T> enumType) {
			this.enumType     = enumType;
			this.constantEnum = Arrays.stream(enumType.getInterfaces()).anyMatch(i -> i == EnumCode.class);
		}

		@SneakyThrows
		@Override
		public T convert(String source) {
			if (source.isEmpty()) {
				return null;
			}

			T[] constants = enumType.getEnumConstants();
			for (T c : constants) {
				if (constantEnum) {
					return (T)((EnumCode)c).search(source).orElseThrow(() -> new IllegalArgumentException());
				} else {
					if (c.name().equals(source.trim())) {
						return c;
					}
				}
			}
			return null;
		}
	}
}