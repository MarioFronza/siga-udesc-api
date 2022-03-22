# siga-udesc-api

Unofficial API for SIGA - Sistemas de Gestão Educacional

## Requirements

- [JDK 11](https://sdkman.io/)
- [Kotlin 1.6.10](https://sdkman.io/)

## Technologies/Frameworks

- [Gradle](https://gradle.org/)
- [Ktor](https://ktor.io/)
- [Ktor Client](https://ktor.io/docs/client.html)
- [JUnit4](https://junit.org/junit4/)

## Start

To start the application, execute a Gradle Wrapper *run* task.

```bash
./gradlew run
```

Or using `docker-compose`.

```bash
docker-compose up
```


## Heroku

Test requesting your semester results using heroku.

```
curl --request GET \
--url 'https://siga-udesc-api.herokuapp.com/semester-results?cpf=11111111111&password=pass&year=2022&term=1'
```

## Endpoints

### REST

- ***GET*** /semester-results

  <details>
    <summary><b>Request Query Params</b></summary>
    <p>

    ```
    cpf: 11111111111
    password: siga-password
    year: 2021
    term: 2
    ```
    </p>

  </details>

  <details>
    <summary><b>Response</b></summary>
    <p>

  ```json
  {
    "studentName": "Student Name",
    "course": "Engenharia de Software",
    "semesterResults": {
      "period": "2021/2",
      "semesterResults": [
        {
          "subjectName": "Tests",
          "groupName": "ESO06 2021/2",
          "finalGrade": 10.0,
          "courseLoad": 72,
          "absencesCount": 0,
          "attendancePercentage": 100.0,
          "result": "Aprovado"
        }
      ]
    }
  }

  ```

    </p>

  </details>

## Tests

- TODO
