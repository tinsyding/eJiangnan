# 后端项目

后端项目使用Spring Boot框架，使用MyBatis作为持久层框架，使用MySQL作为数据库。

## 项目地址

[GITHUB地址](https://github.com/tinsyding/eJiangnan/tree/2a3fd7f80170d78df0c988e91973945368b3fc8e/springboot)

## 项目结构

    │  pom.xml
    │
    └─src
        └─main
            ├─java
            │  └─com
            │      └─example
            │          │  SpringbootApplication.java
            │          │
            │          ├─common
            │          │      CorsConfig.java
            │          │      Result.java
            │          │      RoleEnum.java
            │          │
            │          ├─controller
            │          │      CourseController.java
            │          │      DocController.java
            │          │      FileController.java
            │          │      StudentController.java
            │          │      StudentCourseController.java
            │          │      WebController.java
            │          │
            │          ├─entity
            │          │      Account.java
            │          │      Admin.java
            │          │      Course.java
            │          │      Doc.java
            │          │      Student.java
            │          │      StudentCourse.java
            │          │
            │          ├─exception
            │          │      CustomException.java
            │          │      GlobalExceptionHandler.java
            │          │
            │          ├─mapper
            │          │      AdminMapper.java
            │          │      CourseMapper.java
            │          │      DocMapper.java
            │          │      StudentCourseMapper.java
            │          │      StudentMapper.java
            │          │
            │          └─service
            │                  AdminService.java
            │                  CourseService.java
            │                  DocService.java
            │                  StudentCourseService.java
            │                  StudentService.java
            │
            └─resources
                │  application.yml
                │
                └─mapper
                        StudentCourse.xml

## 项目依赖

- JDK
- Spring Boot
- MyBatis
- MySQL
- Maven

## 项目启动

1. **数据库**

    1. 创建数据库

        ```sql
        CREATE DATABASE ejiangnan;
        ```

    2. 创建一个mysql账户，确保该账户有对ejiangnan数据库的所有权限

        ```sql
        CREATE USER '你的数据库用户名'@'localhost' IDENTIFIED BY '你的数据库密码';
        GRANT ALL PRIVILEGES ON ejiangnan.* TO '你的数据库用户名'@'localhost';
        FLUSH PRIVILEGES;
        ```

    3. 导入数据库

        将`SQL\MySQL\eJiangnan.sql`导入ejiangnan数据库

    4. 在admin表中插入一条初始管理员账户

        **请注意：admin表中，role的值应为`ADMIN`**
        **请注意：student表中，role的值应为`STUDENT`**
    
        ```sql
        -- 创建一个学号/工号为admin、密码为admin、姓名为admin_name的管理员用户
        INSERT INTO `admin` VALUES (1, 'admin', 'admin', 'admin_name', 'ADMIN');
        ```
    
    5. 修改数据库配置

        打开`springboot\src\main\resources\application.yml`，修改数据库配置

        ```yaml
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql://localhost:3306/ejiangnan?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
            username: 你的数据库用户名
            password: 你的数据库密码
        ```

2. **后端**

   - 进入项目根目录，打开命令行工具，输入以下命令：

        ```bash
        cd springboot
        mvn spring-boot:run
        ```
     
