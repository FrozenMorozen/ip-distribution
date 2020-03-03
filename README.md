# ip-distribution
Выделение адреса очередному устройству, имея на вход ip предыдущего.

### Запуск приложения
 Docker контейнер:\
 **`./gradlew clean build && docker build -t ip-distribution . && docker run -p 8080:8080 ip-distribution`** 
 
 или Jar файл:\
 **`./gradlew clean build && java -jar build/libs/ip-distribution-0.0.1-SNAPSHOT.jar`**

### Тестирование
**`curl http://localhost:8080/getnextip/172.28.0.1`**\
Экшн лог контроллера в файле: _/logs/controller.log_