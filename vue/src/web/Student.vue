<template>
  <div>
    <div class="card">
      <div class="search-inputs">
        <el-input v-model="data.username" placeholder="请输入学号/工号查询"></el-input>
        <el-input v-model="data.name" placeholder="请输入学生姓名查询"></el-input>
<!--        <el-input v-model="data.phone" placeholder="请输入学生手机号查询"></el-input>-->
<!--        <el-input v-model="data.email" placeholder="请输入学生邮箱查询"></el-input>-->
<!--        <el-input v-model="data.sex" placeholder="请输入学生性别查询"></el-input>-->
      </div>
      <div class="search-buttons">
        <el-button type="primary" @click="load">查询</el-button>
        <el-button type="info" @click="reset">重置</el-button>
      </div>
    </div>

    <div class="card">
      <div class="add-button">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="学生ID" prop="id"></el-table-column>
        <el-table-column label="学生学号/工号" prop="username"></el-table-column>
        <el-table-column label="学生姓名" prop="name"></el-table-column>
        <el-table-column label="学生手机号" prop="phone"></el-table-column>
        <el-table-column label="学生邮箱" prop="email"></el-table-column>
        <el-table-column label="学生性别" prop="sex"></el-table-column>
        <el-table-column label="学生头像" prop="avatar">
          <template v-slot="scope">
            <el-image v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]" style="width: 40px; height: 40px; border-radius: 5px"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="编辑/删除" width="180">
          <template v-slot="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
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

    <el-dialog width="40%" v-model="data.formVisible" title="学生信息" destroy-on-close>
      <el-form :model="data.form" :rules="rules" ref="formRef" label-width="80px" label-position="right">
        <el-form-item label="学号/工号">
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input show-password v-model="data.form.password" autocomplete="off" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="data.form.phone" autocomplete="off" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="data.form.email" autocomplete="off" />
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="data.form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker style="width: 100%" format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="data.form.birth"></el-date-picker>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload action="http://localhost:8888/files/upload" list-type="picture" :on-success="handleImgUploadSuccess">
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

const rules = reactive({
  username: [
    { required: true, message: '请输入学号/工号', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ],
});

const formRef = ref();

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  username: '',
  name: '',
});

const load = () => {
  request.get('/student/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.username,
      name: data.name,
    },
  }).then(res => {
    data.tableData = res.data?.list || [];
    data.total = res.data?.total || 0;
  });
};

load(); // 调用方法获取后台数据

const handelCurrentChange = () => {
  // 当翻页的时候重新加载数据即可
  load();
};

const reset = () => {
  data.username = '';
  data.name = '';
  load();
};

const handleAdd = () => {
  data.form = {};
  data.formVisible = true;
};

// 保存数据到后台
const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.request({
        url: data.form.id ? '/student/update' : '/student/add',
        method: data.form.id ? 'PUT' : 'POST',
        data: data.form,
      }).then(res => {
        if (res.code === '200') {
          load(); // 重新获取数据
          data.formVisible = false; // 关闭弹窗
          ElMessage.success("操作成功");
        } else {
          ElMessage.error(res.msg);
        }
      });
    }
  });
};

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)); // 深拷贝
  data.formVisible = true; // 显示弹窗
};

const del = (id) => {
  ElMessageBox.confirm('确认删除？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/student/delete/' + id).then(res => {
      if (res.code === '200') {
        load(); // 重新获取数据
        ElMessage.success("操作成功");
      } else {
        ElMessage.error(res.msg);
      }
    });
  }).catch(res => {});
};

const handleImgUploadSuccess = (res) => {
  data.form.avatar = res.data;
};

</script>

<style scoped>
.card {
  margin-bottom: 10px;
}

.search-inputs {
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
