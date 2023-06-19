# WebMvcConfigurer sample configuration

- AuthenticationInterceptor: 인증
- LoggingHandlerInterceptor: request logging
- ContentCachingInterceptor: request payload logging 
- RequestBodyCustomValueHandlerMethodArgumentResolver: `@RequestBody` 데이터 변경 
- ResponseBodyCustomValueHandlerMethodReturnValueHandler: `@ResponseBody` 데이터 변경 
- HeaderKeyHandlerMethodArgumentResolver: controller method argument 생성
