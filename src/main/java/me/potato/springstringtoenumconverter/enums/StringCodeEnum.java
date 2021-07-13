package me.potato.springstringtoenumconverter.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.potato.springstringtoenumconverter.kornbutter.EnumCode;

/**
 * @author dongju.paek
 */
@Getter
@RequiredArgsConstructor
public enum StringCodeEnum implements EnumCode<StringCodeEnum> {
	TEST01("1"),
	TEST02("2");

	private final  String                      code;
	private static ConcurrentHashMap<String, StringCodeEnum> codeMap = new ConcurrentHashMap<>();

	static {
		Arrays.stream(values()).forEach(value -> codeMap.put(value.getCode(), value));
	}

	@Override
	public Optional<StringCodeEnum> search(String code) {
		return Optional.of(codeMap.get(code));
	}
}

