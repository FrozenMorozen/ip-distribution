# ip-distribution
Выделение адреса очередному устройству, имея на вход ip предыдущего.

### Запуск приложения
**`./gradlew clean build && java -jar build/libs/ip-distribution-0.0.1-SNAPSHOT.jar`**

### Тестирование
**`curl http://localhost:8080/getnextip/172.28.0.1`**
`curl http://localhost:8080/getnextip/172.28.0.1`