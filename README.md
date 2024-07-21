# eJiangnan(parody)

基于springboot和vue3的前后端分离项目


## 项目地址

[GITHUB地址](https://github.com/tinsyding/ejiangnan)


## 功能


1. **管理员**

    - [x] 登录
    - [x] 个人资料管理
    - [x] 文件管理
    - [x] 课程管理
    - [x] 学生管理
    - [x] 学生选课管理

2. **用户**

    - [x] 登录
    - [x] 注册
    - [x] 找回密码
    - [x] 个人资料管理
    - [x] 查看文件
    - [x] 选课 


## 技术栈

- SpringBoot2
- Mybatis
- MySQL
- vue3
- Element-ui


## 项目依赖

- Node.js (npm)
- Apache Maven

## 项目结构

    └─eJiangnan
        │  README.md
        │
        ├─springboot
        │  │  pom.xml
        │  │
        │  └─src
        │      └─main
        │          ├─java
        │          │  └─com
        │          │      └─example
        │          │          │  SpringbootApplication.java
        │          │          │
        │          │          ├─common
        │          │          │      CorsConfig.java
        │          │          │      Result.java
        │          │          │      RoleEnum.java
        │          │          │
        │          │          ├─controller
        │          │          │      CourseController.java
        │          │          │      DocController.java
        │          │          │      FileController.java
        │          │          │      StudentController.java
        │          │          │      StudentCourseController.java
        │          │          │      WebController.java
        │          │          │
        │          │          ├─entity
        │          │          │      Account.java
        │          │          │      Admin.java
        │          │          │      Course.java
        │          │          │      Doc.java
        │          │          │      Student.java
        │          │          │      StudentCourse.java
        │          │          │
        │          │          ├─exception
        │          │          │      CustomException.java
        │          │          │      GlobalExceptionHandler.java
        │          │          │
        │          │          ├─mapper
        │          │          │      AdminMapper.java
        │          │          │      CourseMapper.java
        │          │          │      DocMapper.java
        │          │          │      StudentCourseMapper.java
        │          │          │      StudentMapper.java
        │          │          │
        │          │          └─service
        │          │                  AdminService.java
        │          │                  CourseService.java
        │          │                  DocService.java
        │          │                  StudentCourseService.java
        │          │                  StudentService.java
        │          │
        │          └─resources
        │              │  application.yml
        │              │
        │              └─mapper
        │                      StudentCourse.xml
        │
        └─vue
            │  .env.development
            │  .env.production
            │  index.html
            │  package.json
            │  vite.config.js
            │
            ├─public
            │      JiangnanUniversity.ico
            │
            └─src
                │  App.vue
                │  main.js
                │
                ├─css
                │      global.css
                │      index.scss
                │
                ├─images
                │      admin.ico
                │      app.png
                │      bg0.jpg
                │      bg1.jpg
                │      bg2.jpg
                │      bg3.jpg
                │      ejiangnan.png
                │      ewmbtn.png
                │      logo.ico
                │      logo.png
                │      QRcode.jpg
                │      XiaoYuan.png
                │
                ├─router
                │      index.js
                │
                ├─utils
                │      request.js
                │
                └─web
                        Course.vue
                        CourseList.vue
                        Doc.vue
                        DocStudent.vue
                        Home.vue
                        Login.vue
                        Manager.vue
                        Person.vue
                        Register.vue
                        Reset.vue
                        Student.vue
                        StudentCourse.vue


##  项目运行

- **数据库 & 后端**

    详见[springboot/README.md](springboot/README.md)

- **前端**
    
    详见[vue/README.md](vue/README.md)


## 项目截图

- **登录页面**

  ![登录页面](TEMP/images/1.png)

- **注册页面**

  ![](TEMP/images/2.png)

- **找回密码页面**

  ![](TEMP/images/3.png)

- **管理员对文件信息进行增删改查**

  ![](TEMP/images/4.png)

  ![](TEMP/images/5.png)

- **管理员对课程信息进行增删改查**

  ![](TEMP/images/6.png)

- **管理员对学生信息进行增删改查**

  ![](TEMP/images/7.png)

  ![](TEMP/images/8.png)

- **管理员对学生选课情况进行查看、删除**

  ![](TEMP/images/14.png)

- **用户个人资料页**

  ![](TEMP/images/9.png)

- **用户查看、下载文件**
  ![](TEMP/images/10.png)

- **用户选课**

  ![](TEMP/images/11.png)

- **用户查看个人选课情况**

  ![](TEMP/images/12.png)
