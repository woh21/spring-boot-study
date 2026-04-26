# 학습 문서

## Section 1: Spring Boot 시작하기

### 1-1. Spring Boot 개요
- Spring Boot는 Spring Framework를 기반으로 자동 설정(Auto Configuration)과
  내장 서버(Embedded Tomcat)를 제공하여 설정을 최소화해주는 프레임워크
- 기존 Spring은 XML 설정이 복잡했는데, Spring Boot는 이를 자동으로 처리

### 1-2. 프로젝트 생성 (Spring Initializr)
- https://start.spring.io 에서 프로젝트 생성
- 설정값:
    - Project: Maven
    - Language: Java
    - Spring Boot: 4.x
    - Dependencies: Spring Web
- 생성된 프로젝트를 IntelliJ에서 Import

### 1-3. REST Controller 생성 - Hello World

**핵심 개념:**
- `@SpringBootApplication`: 앱의 시작점. 자동 설정 + 컴포넌트 스캔 수행
- `@RestController`: 이 클래스가 REST API 요청을 처리한다는 것을 Spring에 알림
- `@GetMapping("/")`: HTTP GET 요청을 해당 메서드에 매핑

**코드:**
```java
package com.luv2code.springboot.demo.mycoolapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {

    // "/" 엔드포인트로 GET 요청이 오면 "Hello World!" 반환
    @GetMapping("/")
    public String sayHello() {
        return "Hello World!";
    }
}
```

**실행 결과:**

![Hello World 실행 결과](screenshots/hello-world.png)
![콘솔 로그](screenshots/hello-world-console.png)

**배운 점:**
- Spring Boot 앱을 실행하면 내장 Tomcat이 8080 포트에서 자동으로 시작됨
- REST Controller를 만들면 별도 설정 없이 바로 HTTP 요청 처리 가능
- 콘솔 로그에서 Tomcat 시작, 포트 번호, 초기화 시간 등을 확인할 수 있음