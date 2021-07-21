# Spring Controller에서 Enum을 Parameter 로 사용하는 방법
- Restful API를 개발하다 보면 외부를 통해 입력되는 파라미터의 Validation을 통해 BadRequest를 반환해야하는 경우가 있다.
- Enum을 통해 값을 한정하고 Validation 하는 방법을 설명한다.


## 설정 

- Converter 등록
```java
@Configuration
public class EnumConverterConfiguration extends WebMvcConfigurationSupport {
	@Override
	protected void addFormatters(FormatterRegistry registry) {
		registry.addConverterFactory(new StringToEnumConverter());
	}
}
```

- Converter 
```java
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
```

## Enum Class
- EnumCode를 상속받고 search를 구현해야 함

```java
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
```


## Controller 생성
- Param에 Enum을 사용한다.

```java
@RestController
public class TestController {

	@GetMapping("/string")
	public StringCodeEnum getString(@RequestParam StringCodeEnum code) {
		return code;
	}

	@GetMapping("/integer")
	public IntegerCodeEnum getInteger(@RequestParam IntegerCodeEnum code) {
		return code;
	}
}
```

