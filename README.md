# WebMvcConfigurer sample configuration

- AuthenticationInterceptor: 인증
- LoggingHandlerInterceptor: request logging
- CompletedInterceptor: 처리 완료 후 request 확인 
- RequestBodyCustomValueHandlerMethodArgumentResolver: `@RequestBody` 데이터 변경 
- ResponseBodyCustomValueHandlerMethodReturnValueHandler: `@ResponseBody` 데이터 변경 
- HeaderKeyHandlerMethodArgumentResolver: controller method argument 생성
