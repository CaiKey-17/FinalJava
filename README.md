# ✨Website bất động sản✨
> Tác giả: Nguyễn Cao Kỳ - 52200056
> Tác giả: Nguyễn Minh Luân - 52200077
> Tác giả: Huỳnh Tấn Nhã - 52200026


# Các Bước Chạy Ứng Dụng Trên Máy Tính Cá Nhân

### Yêu cầu
- Java JDK: Phiên bản 17 trở lên.
- Maven: Để xây dựng và quản lý dependencies.
- MySQL: Để lưu trữ dữ liệu.
- Postman hoặc CURL: Để kiểm tra API.
### Cài đặt
Clone repository:
```sh
git clone https://github.com/CaiKey-17/FinalJava.git
```
Cấu hình cơ sở dữ liệu:
- Tạo cơ sở dữ liệu trong MySQL:
```sh
CREATE DATABASE cuoiky
CREATE DATABASE depositdb
```
- (batdongsan)Cập nhật thông tin cấu hình trong file application.yaml trong /src/main/resources/templates (nếu không có, hãy tạo file):
```sh
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/cuoiky
    username: ...
    password: ...

  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB

  mail:
    host: smtp.gmail.com
    port: 587
    username: ...
    password: ...
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

```

- (demo)Cập nhật thông tin cấu hình trong file application.yaml trong /src/main/resources/templates (nếu không có, hãy tạo file):
```sh
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/depositdb
    username: ...
    password: ...

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  servlet:
    multipart:
      enabled: true
      max-file-size: 10MB
      max-request-size: 10MB

server:
  port: 8081

```
- Build và chạy ứng dụng:
```sh
mvn clean install
mvn spring-boot:run
```
- Thông tin để test VN-Pay:
```sh
Ngân hàng:	NCB
Số thẻ:	9704198526191432198
Tên chủ thẻ:	NGUYEN VAN A
Ngày phát hành:	07/15
Mật khẩu OTP:	123456
```
