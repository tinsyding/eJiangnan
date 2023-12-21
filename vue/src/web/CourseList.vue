<template>
  <div>
    <div class="card search-card">
      <div class="search-inputs">
        <el-input class="search-input" v-model="data.name" placeholder="请输入课程名称查询" :prefix-icon="Search"></el-input>
        <el-input class="search-input" v-model="data.no" placeholder="请输入课程编号查询" :prefix-icon="Search"></el-input>
        <el-input class="search-input" v-model="data.description" placeholder="请输入课程描述查询" :prefix-icon="Search"></el-input>
        <el-input class="search-input" v-model="data.times" placeholder="请输入课程课时查询" :prefix-icon="Search"></el-input>
        <el-input class="search-input" v-model="data.teacher" placeholder="请输入任课教师查询" :prefix-icon="Search"></el-input>
      </div>
      <div class="search-buttons">
        <el-button type="primary" @click="load">查 询</el-button>
        <el-button type="info" @click="reset">重 置</el-button>
      </div>
    </div>

    <div class="card table-card">
      <el-table stripe :data="data.tableData">
        <el-table-column label="课程名称" prop="name"></el-table-column>
        <el-table-column label="课程编号" prop="no"></el-table-column>
        <el-table-column label="课程描述" prop="description"></el-table-column>
        <el-table-column label="课程课时" prop="times"></el-table-column>
        <el-table-column label="任课教师" prop="teacher"></el-table-column>
        <el-table-column label="操作" width="180">
          <template v-slot="scope">
            <el-button type="primary" @click="selectCourse(scope.row)">选课</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card pagination-card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handelCurrentChange"
                     background layout="prev, pager, next" :total="data.total" />
    </div>
  </div>
</template>

<style scoped>
.search-card {
  display: flex;
  flex-direction: column;
  margin-bottom: 10px;
}

.search-inputs {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-input {
  flex-grow: 1;
  margin-right: 10px;
}

.search-buttons {
  display: flex;
  justify-content: center;
}

.table-card {
  margin-bottom: 10px;
}

</style>

<script setup>
import request from "@/utils/request";
import {reactive} from "vue";
import {Search} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  name: '',
  no: '',
  description: '',
  times: '',
  teacher: '',
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  student: JSON.parse(localStorage.getItem('student-user') || '{}')
})

const load = () => {
  request.get('/course/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      no: data.no,
      description: data.description,
      times: data.times,
      teacher: data.teacher
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

load()

const handelCurrentChange = () => {
  load()
}

const reset = () => {
  data.name = ''
  data.no = ''
  data.description = ''
  data.times = ''
  data.teacher = ''
  load()
}

const selectCourse = (row) => {
  ElMessageBox.confirm('确定选课吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    request.post('/studentCourse/add', {
      studentId: data.student.id, name: row.name, no: row.no, courseId: row.id }).then(res => {
      if (res.code === '200') {
        ElMessage.success('选课成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(() => {
    ElMessage.info('已取消选课')
  })
}
</script>
