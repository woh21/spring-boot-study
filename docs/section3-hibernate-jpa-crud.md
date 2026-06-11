# Section 3: Hibernate/JPA CRUD

## 3-1. Hibernate / JPA 개요

이번 Section에서는 Spring Boot 애플리케이션에서 데이터베이스를 다루기 위한 Hibernate와 JPA를 학습한다.

Hibernate는 Java 객체를 데이터베이스에 저장하거나, 데이터베이스에서 다시 조회할 수 있도록 도와주는 ORM 프레임워크이다.

ORM은 Object-to-Relational Mapping의 약자로, Java 객체와 관계형 데이터베이스 테이블을 서로 연결해주는 기술이다.

예를 들어 Java 코드에서는 다음과 같은 `Student` 클래스가 있을 수 있다.

```java
public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
```

반면 데이터베이스에는 다음과 같은 `student` 테이블이 존재할 수 있다.

```text
student
- id
- first_name
- last_name
- email
```

Java에서는 `firstName`, `lastName`처럼 CamelCase를 사용하지만, 데이터베이스에서는 `first_name`, `last_name`처럼 언더스코어 형식을 사용하는 경우가 많다. Hibernate/JPA는 이런 Java 필드와 DB 컬럼 사이의 매핑을 처리해준다.

---

## 3-2. Hibernate를 사용하는 이유

Hibernate를 사용하면 개발자가 직접 작성해야 하는 JDBC 코드와 SQL 코드의 양을 줄일 수 있다.

기존 JDBC 방식에서는 개발자가 직접 SQL을 작성하고, `Connection`, `PreparedStatement`, `ResultSet` 등을 다루어야 한다.

하지만 Hibernate/JPA를 사용하면 Java 객체를 중심으로 코드를 작성할 수 있다.

예를 들어 객체를 저장할 때는 다음과 같은 방식으로 처리할 수 있다.

```java
Student student = new Student("Paul", "Doe", "paul@luv2code.com");

entityManager.persist(student);
```

이 코드에서 `entityManager.persist(student)`를 호출하면, JPA가 매핑 정보를 바탕으로 `Student` 객체를 적절한 테이블과 컬럼에 저장한다.

즉, 개발자는 “객체를 저장한다”는 관점에서 코드를 작성하고, 실제 SQL 실행은 Hibernate/JPA가 내부적으로 처리한다.

---

## 3-3. JPA란?

JPA는 Jakarta Persistence API의 약자이다.

이전에는 Java Persistence API라고 불렸다.

JPA는 ORM을 위한 표준 API이다.
다만 JPA 자체는 실제 동작하는 라이브러리가 아니라, 인터페이스와 규칙을 정의한 명세이다.

즉, JPA를 실제로 사용하려면 구현체가 필요하다.

대표적인 JPA 구현체는 다음과 같다.

* Hibernate
* EclipseLink

이 중 Hibernate는 가장 널리 사용되는 JPA 구현체이며, Spring Boot에서 기본적으로 사용되는 JPA 구현체이기도 하다.

JPA를 사용하면 특정 구현체에 완전히 종속되지 않고, 표준 API를 기준으로 코드를 작성할 수 있다. 이론적으로는 EclipseLink를 사용하다가 Hibernate로 바꾸는 것도 가능하다.

---

## 3-4. Hibernate/JPA와 JDBC의 관계

Hibernate/JPA를 사용한다고 해서 JDBC가 사라지는 것은 아니다.

Hibernate/JPA는 내부적으로 JDBC를 사용해서 데이터베이스와 통신한다.

구조를 단순하게 표현하면 다음과 같다.

```text
Spring Boot 애플리케이션
        ↓
JPA API
        ↓
Hibernate
        ↓
JDBC API
        ↓
JDBC Driver
        ↓
MySQL Database
```

개발자는 JPA API를 사용해서 객체를 저장하고 조회하지만, 내부적으로 실제 데이터베이스 통신은 JDBC를 통해 이루어진다.

따라서 Hibernate/JPA는 JDBC 위에 올라가 있는 추상화 계층이라고 볼 수 있다.

이후 Spring Boot 프로젝트에서 MySQL에 연결할 때도 JDBC Driver를 설정하게 된다.

---

## 3-5. MySQL 개발 환경

이번 강의에서는 CRUD 실습을 위해 MySQL 데이터베이스를 사용한다.

MySQL 환경은 크게 두 가지 구성 요소로 나눌 수 있다.

### MySQL Database Server

MySQL Database Server는 실제 데이터를 저장하고 관리하는 데이터베이스 엔진이다.

데이터 생성, 조회, 수정, 삭제 같은 CRUD 작업은 이 서버를 대상으로 이루어진다.

### MySQL Workbench

MySQL Workbench는 MySQL Database Server에 접속하기 위한 GUI 도구이다.

Workbench를 사용하면 다음 작업을 할 수 있다.

* 데이터베이스 스키마 생성
* 테이블 생성
* SQL 쿼리 실행
* 데이터 조회
* 데이터 삽입, 수정, 삭제
* 사용자 계정 생성 및 권한 관리

이번 실습에서는 MySQL Workbench를 사용해서 사용자 계정과 `student` 테이블을 생성했다.

---

## 3-6. 데이터베이스 테이블 설정 개요

JPA 실습을 진행하기 위해 강의에서 제공하는 SQL 스크립트를 사용했다.

사용한 폴더와 파일은 다음과 같다.

```text
00-starter-sql-scripts/
├── 01-create-user.sql
└── 02-student-tracker.sql
```

### 01-create-user.sql

`01-create-user.sql` 파일은 Spring Boot 애플리케이션에서 사용할 MySQL 사용자를 생성한다.

생성한 사용자 정보는 다음과 같다.

```text
User ID: springstudent
Password: springstudent
```

root 계정으로 MySQL Workbench에 접속한 뒤, 해당 SQL 파일을 실행하여 `springstudent` 사용자를 생성했다.

이후 MySQL Workbench에서 `springstudent`라는 새 Connection을 만들고, Test Connection을 통해 정상 접속되는 것을 확인했다.

---

### 02-student-tracker.sql

`02-student-tracker.sql` 파일은 JPA 실습에서 사용할 데이터베이스 스키마와 테이블을 생성한다.

생성된 스키마 이름은 다음과 같다.

```text
student_tracker
```

생성된 테이블 이름은 다음과 같다.

```text
student
```

`student` 테이블에는 다음 네 개의 컬럼이 생성된다.

| 컬럼명          | 설명     |
| ------------ | ------ |
| `id`         | 기본 키   |
| `first_name` | 학생 이름  |
| `last_name`  | 학생 성   |
| `email`      | 이메일 주소 |

현재는 테이블 구조만 생성한 상태이므로 아직 데이터는 들어있지 않다.

이후 강의에서 Java 코드와 JPA `EntityManager`를 사용하여 `Student` 객체를 생성하고, 이 객체를 `student` 테이블에 저장하는 실습을 진행할 예정이다.

---

## 3-7. 실행 결과

MySQL Workbench에서 `student_tracker` 스키마와 `student` 테이블을 생성한 뒤, `student` 테이블을 조회했다.

아직 데이터를 삽입하지 않았기 때문에 조회 결과는 비어 있다.

![student 테이블 조회 결과](screenshots/section3-empty-student-table-query.png)

---

## 3-8. 이번 범위에서 배운 점

이번 범위에서는 Hibernate, JPA, JDBC, MySQL의 관계를 학습했다.

정리하면 다음과 같다.

* Hibernate는 Java 객체를 데이터베이스에 저장하고 조회할 수 있게 해주는 ORM 프레임워크이다.
* JPA는 ORM을 위한 표준 API이고, Hibernate는 JPA의 대표적인 구현체이다.
* Hibernate/JPA는 JDBC를 대체하는 것이 아니라, 내부적으로 JDBC를 사용한다.
* 개발자는 JPA API를 통해 객체 중심으로 코드를 작성하고, 실제 SQL 처리와 DB 통신은 Hibernate/JPA가 도와준다.
* MySQL 실습을 위해 `springstudent` 사용자, `student_tracker` 스키마, `student` 테이블을 생성했다.
* 현재 `student` 테이블은 비어 있으며, 이후 Java 코드로 데이터를 삽입할 예정이다.

