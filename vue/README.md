# 前端项目

前端项目基于 Vue.js 框架，使用 Vite 构建工具进行构建。

## 项目地址

[GITHUB地址](https://github.com/tinsyding/eJiangnan/tree/2a3fd7f80170d78df0c988e91973945368b3fc8e/vue)

## 项目结构

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


## 项目依赖

- Node.js (npm)
- Vue.js (vue3)
- Vite

## 项目启动

- **替换镜像源（可选）**

    ```bash
    npm config set registry https://registry.npm.taobao.org
    ```

- **安装依赖（初次运行时需要）**

    ```bash
    npm install
    ```

- **启动项目**

    ```bash
    npm run dev
    ```