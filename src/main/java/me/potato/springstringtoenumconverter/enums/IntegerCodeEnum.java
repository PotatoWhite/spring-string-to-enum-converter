package me.potato.springstringtoenumconverter.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.potato.springstringtoenumconverter.kornbutter.EnumCode;

/**
 * @author dongju.paek
 */
@Getter
@RequiredArgsConstructor
public enum IntegerCodeEnum implements EnumCode {
	TESTINTEGER01(1),
	TESTINTEGER02(2);

	private final  Integer                       code;
	private static Map<Integer, IntegerCodeEnum> codeMap = new HashMap<>();

	static {
		Arrays.stream(values()).forEach(value -> codeMap.put(value.getCode(), value));
	}

	@Override
	public Optional<IntegerCodeEnum> search(String code) {
		return Optional.of(codeMap.get(Integer.parseInt(code)));
	}
}
