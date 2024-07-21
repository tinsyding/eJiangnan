<template>
  <div>
    <div class="card">
      <div class="search-container">
        <el-input v-model="data.name" placeholder="请输入课程名称查询" :prefix-icon="Search"></el-input>
        <el-input v-model="data.no" placeholder="请输入课程编号查询" :prefix-icon="Search"></el-input>
      </div>
      <div class="button-container">
        <el-button type="primary" @click="load">查询</el-button>
        <el-button type="info" @click="reset">重置</el-button>
      </div>
    </div>

    <div class="card">
      <el-table stripe :data="data.tableData">
        <el-table-column label="课程名称" prop="name"></el-table-column>
        <el-table-column label="课程编号" prop="no"></el-table-column>
        <el-table-column label="选课学生姓名" prop="studentName" v-if="data.user.role === 'ADMIN'"></el-table-column>
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handelCurrentChange"
                     background layout="prev, pager, next" :total="data.total" />
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import { reactive } from "vue";
import { Search } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

const data = reactive({
  name: '',
  no: '',
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  user: JSON.parse(localStorage.getItem('student-user') || '{}')
});

const load = () => {
  let params = {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    name: data.name,
    no: data.no,
  };
  if (data.user.role === 'STUDENT') {
    params.studentId = data.user.id;
  }
  request.get('/studentCourse/selectPage', {
    params: params,
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
  });
};

load();

const handelCurrentChange = () => {
  load();
};

const reset = () => {
  data.name = '';
  data.no = '';
  load();
};

const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/studentCourse/delete/' + id).then(res => {
      if (res.code === '200') {
        load();
        ElMessage.success("操作成功");
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(res => {});
};
</script>

<style scoped>
.card {
  margin-bottom: 10px;
}

.search-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}
</style>
