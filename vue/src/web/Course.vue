<template>
  <div>
    <div class="card">
      <div class="search-bar">
        <el-input v-model="data.name" placeholder="请输入课程名称查询" :prefix-icon="Search"></el-input>
        <el-input v-model="data.no" placeholder="请输入课程编号查询" :prefix-icon="Search"></el-input>
        <el-input v-model="data.description" placeholder="请输入课程描述查询" :prefix-icon="Search"></el-input>
        <el-input v-model="data.times" placeholder="请输入课程课时查询" :prefix-icon="Search"></el-input>
        <el-input v-model="data.teacher" placeholder="请输入任课教师查询" :prefix-icon="Search"></el-input>
      </div>
      <div class="search-buttons">
        <el-button type="primary" @click="load">查 询</el-button>
        <el-button type="info" @click="reset">重 置</el-button>
      </div>
    </div>

    <div class="card">
      <div class="add-button">
        <el-button type="primary" @click="handleAdd">新 增</el-button>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="课程ID" prop="id"></el-table-column>
        <el-table-column label="课程名称" prop="name"></el-table-column>
        <el-table-column label="课程编号" prop="no"></el-table-column>
        <el-table-column label="课程描述" prop="description"></el-table-column>
        <el-table-column label="课程课时" prop="times"></el-table-column>
        <el-table-column label="任课教师" prop="teacher"></el-table-column>
        <el-table-column label="编辑/删除" width="180">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编 辑</el-button>
            <el-button type="danger" @click="del(scope.row.id)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination v-model:current-page="data.pageNum" v-model:page-size="data.pageSize"
                     @current-change="handelCurrentChange"
                     background layout="prev, pager, next" :total="data.total" />
    </div>

    <el-dialog width="35%" v-model="data.formVisible" title="课程信息">
      <el-form :model="data.form" label-width="100px" label-position="right" style="padding-right: 40px">
        <el-form-item label="课程名称">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程编号">
          <el-input v-model="data.form.no" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input v-model="data.form.description" autocomplete="off" />
        </el-form-item>
        <el-form-item label="课程课时">
          <el-input v-model="data.form.times" autocomplete="off" />
        </el-form-item>
        <el-form-item label="任课教师">
          <el-input v-model="data.form.teacher" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </span>
      </template>
    </el-dialog>
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
  description: '',
  times: '',
  teacher: '',
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  formVisible: false,
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
  data.description = '';
  data.times = '';
  data.teacher = '';
  load();
};

const handleAdd = () => {
  data.form = {};
  data.formVisible = true;
};

const save = () => {
  request.request({
    url: data.form.id ? '/course/update' : '/course/add',
    method: data.form.id ? 'PUT' : 'POST',
    data: data.form
  }).then(res => {
    if (res.code === '200') {
      load();
      data.formVisible = false;
      ElMessage.success("操作成功");
    } else {
      ElMessage.error(res.msg);
    }
  });
};

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row));
  data.formVisible = true;
};

const del = (id) => {
  ElMessageBox.confirm('确认删除？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/course/delete/' + id).then(res => {
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

.search-bar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.search-buttons {
  display: flex;
  justify-content: center;
}

.add-button {
  margin-bottom: 10px;
}

</style>