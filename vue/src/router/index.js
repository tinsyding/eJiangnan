import {createRouter, createWebHistory} from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'Manager', component: () => import('@/web/Manager.vue'), redirect: '/login', children: [
        { path: 'home', name: 'Home', component: () => import('@/web/Home.vue')}, // 懒加载
        { path: 'course', name: 'Course', component: () => import('@/web/Course.vue')},
        { path: 'student', name: 'Student', component: () => import('@/web/Student.vue')},
        { path: 'person', name: 'Person', component: () => import('@/web/Person.vue')},
        { path: 'file', name: 'File', component: () => import('@/web/Doc.vue')},
        { path: 'doc', name: 'Doc', component: () => import('@/web/Doc.vue')},
        { path: 'docStudent', name: 'DocStudent', component: () => import('@/web/DocStudent.vue')},
        { path: 'studentCourse', name: 'StudentCourse', component: () => import('@/web/StudentCourse.vue')},
        { path: 'courseList', name: 'CourseList', component: () => import('@/web/CourseList.vue')},
      ]
    },
    { path: '/login', name: 'Login', component: () => import('@/web/Login.vue')},
    { path: '/register', name: 'Register', component: () => import('@/web/Register.vue')},
    { path: '/reset', name: 'Reset', component: () => import('@/web/Reset.vue')},
  ]
})

export default router
