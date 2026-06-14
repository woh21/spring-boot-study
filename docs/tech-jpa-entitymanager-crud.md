# Spring Boot + JPA EntityManager로 CRUD 기능 단계별 구현하기

## 1. 기술자료 목표

이 기술자료는 Spring Boot 프로젝트에서 JPA `EntityManager`를 사용하여 기본적인 CRUD 기능을 구현하는 방법을 단계별로 설명한다.

이 자료를 따라 하면 다음 기능을 직접 구현하고 실행해볼 수 있다.

* MySQL 데이터베이스 생성
* Spring Boot JPA 프로젝트 생성
* `Student` Entity 작성
* DAO 인터페이스와 구현체 작성
* `EntityManager`를 사용한 저장, 조회, 수정, 삭제
* JPQL을 사용한 전체 조회와 조건 조회
* MySQL Workbench로 실행 결과 확인

이 문서는 완성된 코드만 보여주는 설명서가 아니라, 각 기능을 하나씩 추가하면서 따라 구현하는 튜토리얼 형태로 작성되었다.

---

## 2. 예제 소스코드

본 기술자료에서 사용하는 예제 프로젝트는 GitHub에 등록되어 있다.

* GitHub Repository: https://github.com/woh21/spring-boot-study
* 예제 프로젝트 경로: `tech-jpa-entitymanager-crud`
* 예제 코드 링크: https://github.com/woh21/spring-boot-study/tree/main/tech-jpa-entitymanager-crud

---

## 3. 사용 기술

| 기술              | 사용 목적             |
| --------------- | ----------------- |
| Java            | 백엔드 코드 작성         |
| Spring Boot     | 애플리케이션 실행 및 자동 설정 |
| Spring Data JPA | JPA 기반 DB 연동      |
| Hibernate       | JPA 구현체           |
| MySQL           | 데이터 저장            |
| MySQL Workbench | DB 생성 및 결과 확인     |
| Maven           | 의존성 관리            |
| IntelliJ IDEA   | 개발 환경             |

---

## 4. 완성 후 전체 구조

최종적으로 만들 프로젝트 구조는 다음과 같다.

```text
tech-jpa-entitymanager-crud/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── woh/
│       │           └── tech_jpa_entitymanager_crud/
│       │               ├── TechJpaEntitymanagerCrudApplication.java
│       │               ├── dao/
│       │               │   ├── StudentDAO.java
│       │               │   └── StudentDAOImpl.java
│       │               └── entity/
│       │                   └── Student.java
│       └── resources/
│           └── application.properties
```

각 파일의 역할은 다음과 같다.

| 파일                                         | 역할                              |
| ------------------------------------------ | ------------------------------- |
| `TechJpaEntitymanagerCrudApplication.java` | 애플리케이션 실행 및 CRUD 테스트            |
| `Student.java`                             | DB 테이블과 매핑되는 JPA Entity         |
| `StudentDAO.java`                          | CRUD 기능을 정의하는 DAO 인터페이스         |
| `StudentDAOImpl.java`                      | `EntityManager`를 사용해 CRUD 기능 구현 |
| `application.properties`                   | DB 연결 정보 및 JPA 설정               |

---

## 5. 전체 동작 구조

이 예제의 전체 흐름은 다음과 같다.

```text
CommandLineRunner
        ↓
StudentDAO
        ↓
StudentDAOImpl
        ↓
EntityManager
        ↓
MySQL Database
```

애플리케이션은 직접 DB에 접근하지 않는다.

대신 `StudentDAO`를 호출하고, `StudentDAOImpl`이 `EntityManager`를 사용해서 실제 DB 작업을 수행한다.

---

## 6. 1단계: MySQL 데이터베이스 생성

먼저 MySQL Workbench에서 기술자료 예제용 데이터베이스를 생성한다.

root 계정으로 MySQL Workbench에 접속한 뒤, 새 SQL 탭을 열고 아래 SQL을 실행한다.

```sql
CREATE USER IF NOT EXISTS 'springstudent'@'localhost' IDENTIFIED BY 'springstudent';

CREATE DATABASE IF NOT EXISTS tech_jpa_crud;

GRANT ALL PRIVILEGES ON tech_jpa_crud.* TO 'springstudent'@'localhost';

FLUSH PRIVILEGES;
```

위 SQL은 다음 작업을 수행한다.

| SQL                    | 역할                         |
| ---------------------- | -------------------------- |
| `CREATE USER`          | `springstudent` 사용자 생성     |
| `CREATE DATABASE`      | `tech_jpa_crud` 데이터베이스 생성  |
| `GRANT ALL PRIVILEGES` | `springstudent`에게 DB 권한 부여 |
| `FLUSH PRIVILEGES`     | 권한 적용                      |

이후 MySQL Workbench에서 `springstudent` 계정으로 접속했을 때 `tech_jpa_crud` 스키마가 보이면 성공이다.

---

## 7. 2단계: Spring Boot 프로젝트 생성

Spring Initializr에서 새 프로젝트를 생성한다.

설정값은 다음과 같다.

| 항목           | 설정                                    |
| ------------ | ------------------------------------- |
| Project      | Maven                                 |
| Language     | Java                                  |
| Spring Boot  | 최신 정식 버전                              |
| Group        | `com.woh`                             |
| Artifact     | `tech-jpa-entitymanager-crud`         |
| Package name | `com.woh.tech_jpa_entitymanager_crud` |
| Packaging    | Jar                                   |
| Java         | 21                                    |
| Dependencies | Spring Data JPA, MySQL Driver         |

생성한 프로젝트 폴더는 GitHub 저장소 안에 다음 이름으로 배치한다.

```text
tech-jpa-entitymanager-crud
```

---

## 8. 3단계: application.properties 설정

`src/main/resources/application.properties` 파일에 MySQL 연결 정보를 작성한다.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tech_jpa_crud
spring.datasource.username=springstudent
spring.datasource.password=springstudent

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.main.banner-mode=off
logging.level.root=warn
```

각 설정의 의미는 다음과 같다.

| 설정                                     | 설명                       |
| -------------------------------------- | ------------------------ |
| `spring.datasource.url`                | 연결할 MySQL DB 주소          |
| `spring.datasource.username`           | DB 접속 사용자 이름             |
| `spring.datasource.password`           | DB 접속 비밀번호               |
| `spring.jpa.hibernate.ddl-auto=update` | Entity 기준으로 테이블 자동 생성/수정 |
| `spring.jpa.show-sql=true`             | Hibernate가 실행하는 SQL 출력   |
| `spring.main.banner-mode=off`          | Spring Boot 배너 숨김        |
| `logging.level.root=warn`              | 로그를 warning 이상으로 줄임      |

`spring.jpa.hibernate.ddl-auto=update` 설정 때문에, `Student` Entity를 작성한 뒤 애플리케이션을 실행하면 Hibernate가 자동으로 `student` 테이블을 생성한다.

---

## 9. 4단계: Student Entity 작성

먼저 `entity` 패키지를 만든다.

```text
src/main/java/com/woh/tech_jpa_entitymanager_crud/entity
```

그 안에 `Student.java` 파일을 생성한다.

```java
package com.woh.tech_jpa_entitymanager_crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public Student() {
    }

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
```

### 주요 어노테이션 설명

| 어노테이션                                                 | 설명                    |
| ----------------------------------------------------- | --------------------- |
| `@Entity`                                             | JPA Entity 클래스로 등록    |
| `@Table(name = "student")`                            | `student` 테이블과 매핑     |
| `@Id`                                                 | Primary Key 지정        |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | DB의 Auto Increment 사용 |
| `@Column(name = "...")`                               | Java 필드와 DB 컬럼 매핑     |

Java 필드명은 `firstName`, `lastName`처럼 CamelCase를 사용한다.

반면 DB 컬럼명은 `first_name`, `last_name`처럼 언더스코어를 사용한다.

따라서 `@Column`으로 Java 필드와 DB 컬럼을 명확하게 연결한다.

---

## 10. 5단계: StudentDAO 인터페이스 작성

다음으로 `dao` 패키지를 만든다.

```text
src/main/java/com/woh/tech_jpa_entitymanager_crud/dao
```

그 안에 `StudentDAO.java` 파일을 생성한다.

```java
package com.woh.tech_jpa_entitymanager_crud.dao;

import com.woh.tech_jpa_entitymanager_crud.entity.Student;

import java.util.List;

public interface StudentDAO {

    void save(Student student);

    Student findById(Integer id);

    List<Student> findAll();

    List<Student> findByLastName(String lastName);

    void update(Student student);

    void delete(Integer id);

    int deleteAll();
}
```

이 인터페이스는 `Student` 데이터에 대해 제공할 CRUD 기능 목록을 정의한다.

| 메서드              | 기능        |
| ---------------- | --------- |
| `save`           | 학생 저장     |
| `findById`       | id로 학생 조회 |
| `findAll`        | 전체 학생 조회  |
| `findByLastName` | 성으로 학생 조회 |
| `update`         | 학생 정보 수정  |
| `delete`         | id로 학생 삭제 |
| `deleteAll`      | 전체 학생 삭제  |

---

## 11. 6단계: StudentDAOImpl 구현

`dao` 패키지 안에 `StudentDAOImpl.java` 파일을 생성한다.

```java
package com.woh.tech_jpa_entitymanager_crud.dao;

import com.woh.tech_jpa_entitymanager_crud.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    private final EntityManager entityManager;

    public StudentDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findById(Integer id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> query =
                entityManager.createQuery("FROM Student", Student.class);

        return query.getResultList();
    }

    @Override
    public List<Student> findByLastName(String lastName) {
        TypedQuery<Student> query =
                entityManager.createQuery(
                        "FROM Student WHERE lastName = :theData", Student.class);

        query.setParameter("theData", lastName);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Student student = entityManager.find(Student.class, id);

        if (student != null) {
            entityManager.remove(student);
        }
    }

    @Override
    @Transactional
    public int deleteAll() {
        return entityManager.createQuery("DELETE FROM Student").executeUpdate();
    }
}
```

### 핵심 요소 설명

| 요소               | 설명                             |
| ---------------- | ------------------------------ |
| `@Repository`    | DAO 구현체를 Spring Bean으로 등록      |
| `EntityManager`  | JPA의 핵심 DB 작업 객체               |
| 생성자 주입           | Spring이 `EntityManager`를 자동 주입 |
| `@Transactional` | DB 변경 작업을 트랜잭션 안에서 실행          |

`save`, `update`, `delete`, `deleteAll`은 DB 데이터를 변경하므로 `@Transactional`이 필요하다.

`findById`, `findAll`, `findByLastName`은 조회 작업이므로 여기서는 `@Transactional`을 붙이지 않았다.

---

## 12. 7단계: CommandLineRunner 기본 구조 작성

`TechJpaEntitymanagerCrudApplication.java`에서 `CommandLineRunner`를 사용한다.

이 코드는 Spring Bean들이 모두 로드된 뒤 실행된다.

```java
@Bean
public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
    return runner -> {
        createMultipleStudents(studentDAO);
        // queryForStudents(studentDAO);
        // queryForStudentsByLastName(studentDAO);
        // updateStudent(studentDAO);
        // deleteStudent(studentDAO);
        // deleteAllStudents(studentDAO);
    };
}
```

처음에는 `createMultipleStudents(studentDAO);`만 실행하고, 나머지는 주석 처리한다.

기능을 하나씩 확인하면서 다음 단계로 넘어가는 방식이 가장 안전하다.

---

## 13. 8단계: Create 기능 구현

`Student` 객체를 생성하고 DB에 저장하는 기능을 만든다.

```java
private void createMultipleStudents(StudentDAO studentDAO) {
    System.out.println("Creating students...");

    Student student1 = new Student("John", "Doe", "john@test.com");
    Student student2 = new Student("Mary", "Public", "mary@test.com");
    Student student3 = new Student("Daffy", "Duck", "daffy@test.com");

    System.out.println("Saving students...");

    studentDAO.save(student1);
    studentDAO.save(student2);
    studentDAO.save(student3);

    System.out.println("Saved students.");
}
```

### 실행 방법

`CommandLineRunner`에서 아래 코드만 주석 해제한다.

```java
createMultipleStudents(studentDAO);
```

### 예상 결과

콘솔에 insert SQL이 출력되고, MySQL Workbench에서 `student` 테이블을 조회하면 학생 데이터가 추가된다.

```sql
SELECT * FROM tech_jpa_crud.student;
```

---

## 14. 9단계: Read - 전체 조회 구현

전체 학생을 조회하는 기능을 만든다.

```java
private void queryForStudents(StudentDAO studentDAO) {
    System.out.println("\nFinding all students...");

    List<Student> students = studentDAO.findAll();

    for (Student student : students) {
        System.out.println(student);
    }
}
```

### 실행 방법

`CommandLineRunner`에서 다음 코드를 주석 해제한다.

```java
createMultipleStudents(studentDAO);
queryForStudents(studentDAO);
```

### 예상 결과

저장된 모든 학생이 콘솔에 출력된다.

---

## 15. 10단계: Read - lastName 조건 조회 구현

특정 성을 가진 학생을 조회하는 기능을 만든다.

```java
private void queryForStudentsByLastName(StudentDAO studentDAO) {
    System.out.println("\nFinding students by last name: Doe");

    List<Student> students = studentDAO.findByLastName("Doe");

    for (Student student : students) {
        System.out.println(student);
    }
}
```

DAO 구현체에서는 JPQL named parameter를 사용한다.

```java
TypedQuery<Student> query =
        entityManager.createQuery(
                "FROM Student WHERE lastName = :theData", Student.class);

query.setParameter("theData", lastName);
```

`:theData`는 나중에 값을 넣는 자리이다.

`setParameter()`를 사용해 실제 값을 넣는다.

### 실행 방법

```java
createMultipleStudents(studentDAO);
queryForStudents(studentDAO);
queryForStudentsByLastName(studentDAO);
```

### 예상 결과

last name이 `Doe`인 학생만 출력된다.

---

## 16. 11단계: Update 기능 구현

학생 정보를 수정하는 기능을 만든다.

```java
private void updateStudent(StudentDAO studentDAO) {
    System.out.println("\nUpdating student...");

    Student student = studentDAO.findByLastName("Doe").get(0);

    student.setFirstName("Scooby");

    studentDAO.update(student);

    System.out.println("Updated student: " + student);
}
```

DAO 구현체에서는 `entityManager.merge()`를 사용한다.

```java
@Override
@Transactional
public void update(Student student) {
    entityManager.merge(student);
}
```

### 실행 방법

```java
createMultipleStudents(studentDAO);
queryForStudents(studentDAO);
queryForStudentsByLastName(studentDAO);
updateStudent(studentDAO);
```

### 예상 결과

`Doe` 성을 가진 학생의 first name이 `Scooby`로 변경된다.

MySQL Workbench에서 `student` 테이블을 조회하면 수정 결과를 확인할 수 있다.

---

## 17. 12단계: Delete - 특정 학생 삭제

특정 학생 한 명을 삭제하는 기능을 만든다.

```java
private void deleteStudent(StudentDAO studentDAO) {
    System.out.println("\nDeleting one student...");

    Student student = studentDAO.findByLastName("Duck").get(0);

    studentDAO.delete(student.getId());

    System.out.println("Deleted student id: " + student.getId());
}
```

DAO 구현체에서는 `entityManager.remove()`를 사용한다.

```java
@Override
@Transactional
public void delete(Integer id) {
    Student student = entityManager.find(Student.class, id);

    if (student != null) {
        entityManager.remove(student);
    }
}
```

### 실행 방법

```java
createMultipleStudents(studentDAO);
queryForStudents(studentDAO);
queryForStudentsByLastName(studentDAO);
updateStudent(studentDAO);
deleteStudent(studentDAO);
```

### 예상 결과

last name이 `Duck`인 학생이 삭제된다.

---

## 18. 13단계: Delete - 전체 학생 삭제

모든 학생을 삭제하는 기능을 만든다.

```java
private void deleteAllStudents(StudentDAO studentDAO) {
    System.out.println("\nDeleting all students...");

    int deletedCount = studentDAO.deleteAll();

    System.out.println("Deleted row count: " + deletedCount);
}
```

DAO 구현체에서는 JPQL delete 쿼리를 사용한다.

```java
@Override
@Transactional
public int deleteAll() {
    return entityManager.createQuery("DELETE FROM Student").executeUpdate();
}
```

`executeUpdate()`는 update뿐만 아니라 delete처럼 DB를 변경하는 쿼리에도 사용된다.

반환값은 삭제된 row 수이다.

### 실행 방법

```java
createMultipleStudents(studentDAO);
queryForStudents(studentDAO);
queryForStudentsByLastName(studentDAO);
updateStudent(studentDAO);
deleteStudent(studentDAO);
deleteAllStudents(studentDAO);
```

### 예상 결과

모든 학생 데이터가 삭제된다.

주의할 점은 `deleteAllStudents()`를 실행하면 DB가 비어버린다는 것이다.

시연 영상에서 데이터가 남아 있는 화면을 보여주고 싶다면 마지막 단계는 주석 처리하는 것이 좋다.

---

## 19. 실행 결과 예시

아래 콘솔 출력에서는 저장, 전체 조회, 조건 조회, 수정, 단일 삭제, 전체 삭제가 순서대로 실행된 것을 확인할 수 있다.

```text
Creating students...
Saving students...
Saved students.

Finding all students...
Student{id=1, firstName='John', lastName='Doe', email='john@test.com'}
Student{id=2, firstName='Mary', lastName='Public', email='mary@test.com'}
Student{id=3, firstName='Daffy', lastName='Duck', email='daffy@test.com'}

Finding students by last name: Doe
Student{id=1, firstName='John', lastName='Doe', email='john@test.com'}

Updating student...
Updated student: Student{id=1, firstName='Scooby', lastName='Doe', email='john@test.com'}

Deleting one student...
Deleted student id: 3

Deleting all students...
Deleted row count: 2
```

```

---

## 20. 자주 발생하는 오류와 해결

### 1. 패키지 이름 불일치

오류 예시:

```text
cannot find symbol class Student
package ... entity does not exist
```

원인:

파일의 `package` 선언과 `import` 경로가 서로 다르면 발생한다.

예를 들어 실제 패키지는 다음과 같은데,

```java
package com.woh.tech_jpa_entitymanager_crud.entity;
```

다른 파일에서 아래처럼 잘못 import하면 오류가 발생한다.

```java
import com.woh.techjpacrud.entity.Student;
```

해결:

모든 파일의 package와 import를 다음 기준으로 통일한다.

```java
com.woh.tech_jpa_entitymanager_crud
```

---

### 2. MySQL 접속 오류

오류 예시:

```text
Access denied for user 'springstudent'@'localhost'
```

원인:

DB 사용자 이름, 비밀번호, 권한 설정이 잘못되었을 수 있다.

확인할 파일:

```text
src/main/resources/application.properties
```

설정 확인:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tech_jpa_crud
spring.datasource.username=springstudent
spring.datasource.password=springstudent
```

필요하면 root 계정에서 권한을 다시 부여한다.

```sql
GRANT ALL PRIVILEGES ON tech_jpa_crud.* TO 'springstudent'@'localhost';
FLUSH PRIVILEGES;
```

---

### 3. JPQL에서 DB 컬럼명을 사용한 경우

잘못된 예:

```java
"FROM Student WHERE last_name = :theData"
```

올바른 예:

```java
"FROM Student WHERE lastName = :theData"
```

JPQL에서는 DB 컬럼명 `last_name`이 아니라 Java Entity 필드명 `lastName`을 사용해야 한다.

---

### 4. deleteAll 실행 후 DB가 비어 있는 경우

`deleteAllStudents()`를 실행하면 모든 데이터가 삭제된다.

시연이나 테스트에서 데이터가 필요하면 다시 `createMultipleStudents()`를 실행하거나, `deleteAllStudents()`를 주석 처리한다.

---

## 21. 마무리

이 기술자료에서는 Spring Boot와 JPA `EntityManager`를 사용하여 기본 CRUD 기능을 단계별로 구현했다.

최종적으로 구현한 기능은 다음과 같다.

| 기능           | 구현 방식                            |
| ------------ | -------------------------------- |
| Create       | `entityManager.persist()`        |
| Read 단일 조회   | `entityManager.find()`           |
| Read 전체 조회   | JPQL `FROM Student`              |
| Read 조건 조회   | JPQL `WHERE lastName = :theData` |
| Update       | `entityManager.merge()`          |
| Delete 단일 삭제 | `entityManager.remove()`         |
| Delete 전체 삭제 | JPQL `DELETE FROM Student`       |

이 구조는 이후 REST API, 회원 관리, 게시판, 상품 관리 같은 백엔드 프로젝트에서 데이터베이스 CRUD 기능을 구현할 때 기본 구조로 활용할 수 있다.
