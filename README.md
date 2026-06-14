# Spring Boot 학습 프로젝트

## 개요

* **과목**: 소프트웨어캡스톤디자인
* **학번/이름**: 201911018 신현우
* **진행 방식**: Spring Boot 인강을 따라 실습하고, 학습 내용과 구현 과정을 GitHub 문서로 정리
* **인강**: [Spring Boot 4, Spring 7 & Hibernate for Beginners (Chad Darby)](https://www.udemy.com/course/spring-hibernate-tutorial/)

---

## 기술자료


* **기술자료 주제**: Spring Boot + JPA EntityManager로 CRUD 기능 단계별 구현하기
* **기술자료 문서**: [Spring Boot + JPA EntityManager CRUD 튜토리얼](docs/tech-jpa-entitymanager-crud.md)
* **기술자료 예제 프로젝트**: [`tech-jpa-entitymanager-crud/`](tech-jpa-entitymanager-crud)

Spring Boot 프로젝트에서 JPA `EntityManager`를 사용하여 CRUD 기능을 직접 따라 구현할 수 있도록 작성한 튜토리얼 문서이다.

---

## 강의 학습 

| 구분    | 인강 범위                            | 문서                                             |
| ----- | -------------------------------- | ---------------------------------------------- |
| 학습 문서 | Section 1: Spring Boot 시작하기      | [학습 문서](docs/section1-spring-boot-overview.md) |
| 학습 문서 | Section 2: Spring Core (DI, IoC) | [학습 문서](docs/section2-spring-core.md)          |
| 학습 문서 | Section 3: Hibernate/JPA CRUD    | [학습 문서](docs/section3-hibernate-jpa-crud.md)   |

---

## 프로젝트 구조

* `mycoolapp/` — Section 1 실습 프로젝트
* `springcoredemo/` — Section 2 Spring Core 실습 프로젝트
* `03-spring-boot-hibernate-jpa-crud/` — Section 3 Hibernate/JPA CRUD 강의 실습 프로젝트
* `tech-jpa-entitymanager-crud/` — 기술자료용 JPA EntityManager CRUD 예제 프로젝트
* `docs/` — 강의 학습 문서, 기술자료 문서, 스크린샷

---

## 문서 구분

이 저장소의 문서는 크게 두 종류로 나뉜다.

### 1. 강의 학습 문서

인강을 들으며 따라 구현한 내용, 개념 정리, 실행 결과, 트러블슈팅을 정리한 문서이다.

* Section 1: Spring Boot 기본 구조, Maven, application.properties
* Section 2: Spring Core, IoC/DI, Component Scan, Qualifier, Bean Scope
* Section 3: Hibernate/JPA, Entity, DAO, EntityManager, CRUD

### 2. 기술자료 문서

수업 최종 제출용 기술자료이다.

기술자료는 프로젝트 개요 설명이 아니라, 다른 학생들이 직접 따라 구현할 수 있는 튜토리얼 형태로 작성하였다.

* 주제: Spring Boot + JPA EntityManager CRUD 구현
* 예제 코드: `tech-jpa-entitymanager-crud/`
* 문서: `docs/tech-jpa-entitymanager-crud.md`

---

## 실행 환경

* Java 21
* Spring Boot 4
* Maven
* MySQL
* MySQL Workbench
* IntelliJ IDEA

---


