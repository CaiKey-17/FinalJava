# ✨Website bất động sản✨
> Tác giả: Nguyễn Cao Kỳ - 52200056

> Tác giả: Nguyễn Minh Luân - 52200077

> Tác giả: Huỳnh Tấn Nhã - 52200026


# Các Bước Chạy Ứng Dụng Trên Máy Tính Cá Nhân

### Video demo
https://drive.google.com/file/d/1XTDOhY65xi23ocVSmUqd5zbuMFaOQhbN/view

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


# Kiểm thử API với Postman

1. Đăng nhập http://localhost:8080/login

![image](./images/login.jpg)

2. Đăng ký http://localhost:8080/register

![image](./images/dangky.jpg)

3. Đăng xuất http://localhost:8080/logout

![image](./images/logout.jpg)

4. Trang chủ http://localhost:8080/

![image](./images/home.jpg)

5. Nhà đất bán http://localhost:8080/nha-dat-ban

![image](./images/nhadatban.jpg)

6. Chi tiết nhà đất bán http://localhost:8080/chi-tiet-nha-dat-ban/{id_nha_dat_ban}

![image](./images/ctnhadatban.jpg)

7. Nhà đất cho thuê http://localhost:8080/nha-dat-cho-thue

![image](./images/chothue.jpg)

8. Chi tiết nhà đất cho thuê http://localhost:8080/chi-tiet-nha-dat-cho-thue/{id_nha_dat_cho_thue}

![image](./images/ctchothue.jpg)

9. Nhà môi giới http://localhost:8080/nha-moi-gioi

![image](./images/moigioi.jpg)

10. Tin tức http://localhost:8080/tin-tuc

![image](./images/tintuc.jpg)

11. Chi tiết tin tức http://localhost:8080/chitiet-tin-tuc/{id_tin_tuc}

![image](./images/cttintuc.jpg)

12. Đăng tin http://localhost:8080/dang-tin

![image](./images/dangtin.jpg)

13. Quản trị viên http://localhost:8080/admin

![image](./images/admin.jpg)

14. Quản lý người đăng tin http://localhost:8080/manager-customer

![image](./images/qlkhachhang.jpg)

15. Quản lý gói tin http://localhost:8080/manager-package

![image](./images/goitin.jpg)

16. Quản lý bài đăng http://localhost:8080/manager-list

![image](./images/qlbaidang.jpg)

17. Lịch sử giao dịch http://localhost:8080/manager-history

![image](./images/lichsugiaodich.jpg)

18. Đổi mật khẩu(GET) http://localhost:8080/admin-change

![image](./images/doimatkhau.jpg)

19. Đổi mật khẩu(POST) http://localhost:8080/admin-change-password

![image](./images/doimatkhau1.jpg)

20. Thống kê(GET) http://localhost:8080/package-statistics

![image](./images/thongke.jpg)

21. Thống kê gói tin http://localhost:8080/package-statistics-data

![image](./images/thongke1.jpg)

22. Xóa bài đăng http://localhost:8080/admin/delete/{id_bai_dang}

![image](./images/xoabaidang.jpg)

23. Xóa tin tức http://localhost:8080/admin/delete/news/{id_tin_tuc}

![image](./images/xoatintuc.jpg)

24. Người đăng tin http://localhost:8080/customer

![image](./images/khach.jpg)

25. Quản lý gói tin (Người đăng tin) http://localhost:8080/customer-package

![image](./images/khachgoitin.jpg)

26. Lịch sử giao dịch (Người đăng tin) http://localhost:8080/customer-history

![image](./images/khachlichsu.jpg)

27. Đăng bài (Người đăng tin) http://localhost:8080/post

![image](./images/dangbai.jpg)

28. Đăng tin tức (Người đăng tin) http://localhost:8080/post-news

![image](./images/dangtintuc.jpg)

29. Sửa bài đăng (Người đăng tin) http://localhost:8080/edit/{id_bai_dang}

![image](./images/suabaidang.jpg)

30. Xóa bài đăng (Người đăng tin) http://localhost:8080/delete/{id_bai_dang}

![image](./images/xoabaidang1.jpg)

30. Xóa tin tức (Người đăng tin) http://localhost:8080/delete/news/{id_tin_tuc}

![image](./images/xoatintuc1.jpg)

31. Mua gói tin (Người đăng tin) http://localhost:8080/customer-package/purchase

![image](./images/muagoitin1.jpg)

32. Đăng tin (Người đăng tin) http://localhost:8080/customer-dangtin

![image](./images/dangtin2.jpg)

33. Danh sách tin (Người đăng tin) http://localhost:8080/customer-history-list 

![image](./images/danhsachtin.jpg)

34. Quản lý tài khoản (Người đăng tin) http://localhost:8080/customer-account

![image](./images/quanlytk.jpg)

35. Cập nhât thông tin tài khoản (Người đăng tin) http://localhost:8080/customer-account/update

![image](./images/capnhattk.jpg)

36. Đổi mật khẩu (Người đăng tin) http://localhost:8080/customer-change-password

![image](./images/doimk1.jpg)

37. Đổi mật khẩu (Người đăng tin) http://localhost:8080/customer-change

![image](./images/changepass.jpg)

38. Thanh toán (Người đăng tin) http://localhost:8080/checkout

![image](./images/thanhtoan.jpg)

39. Thanh toán VNPay (Người đăng tin) http://localhost:8080/vnpay-payment

![image](./images/vnpayjpg.jpg)

40. Nạp tiền (Người đăng tin) http://localhost:8080/customer/deposits/user/{id_nguoi_dung}

![image](./images/naptien.jpg)
