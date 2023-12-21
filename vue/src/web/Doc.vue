<template>
  <div>

    <div class="card search-card">
      <div class="search-inputs">
        <el-input class="search-input" v-model="data.title" placeholder="请输入文件标题查询" />
        <el-input class="search-input" v-model="data.time" placeholder="请输入发文时间查询" />
        <el-input class="search-input" v-model="data.authority" placeholder="请输入发文机关查询" />
        <el-input class="search-input" v-model="data.number" placeholder="请输入发文字号查询" />
      </div>
      <div class="search-buttons">
        <el-button type="primary" @click="load">查 询</el-button>
        <el-button type="info" @click="reset">重 置</el-button>
      </div>
    </div>

    <div class="card table-card">
      <div class="add-button">
        <el-button type="primary" @click="handleAdd">新增文件</el-button>
      </div>
      <el-table stripe :data="data.tableData">
        <el-table-column label="文件ID" prop="id"></el-table-column>
        <el-table-column label="文件标题" prop="title"></el-table-column>
        <el-table-column label="发文时间" prop="time"></el-table-column>
        <el-table-column label="发文机关" prop="authority"></el-table-column>
        <el-table-column label="发文字号" prop="number"></el-table-column>
        <el-table-column label="点击下载文件正文" prop="pdf">
          <template v-slot="scope">
            <el-link :href="scope.row.pdf" target="_blank">点击下载</el-link>
          </template>
        </el-table-column>
        <el-table-column label="编辑/删除">
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

    <el-dialog width="40%" v-model="data.formVisible" title="文件" destroy-on-close > <!-- destroy-on-close 关闭弹窗时销毁 -->
      <el-form :model="data.form" :rules="rules" ref="formRef" label-width="80px" label-position="right" style="padding-right: 40px">
        <el-form-item label="文件标题">
          <el-input v-model="data.form.title" autocomplete="off" />
        </el-form-item>
        <el-form-item label="发文机关">
          <el-input v-model="data.form.authority" autocomplete="off" />
        </el-form-item>
        <el-form-item label="发文字号">
          <el-input v-model="data.form.number" autocomplete="off" />
        </el-form-item>
        <el-form-item label="发文时间">
          <el-date-picker format="YYYY-MM-DD" value-format="YYYY-MM-DD" v-model="data.form.time"></el-date-picker>
        </el-form-item>
        <el-form-item label="文件内容">
          <el-upload action="http://localhost:8888/files/upload" :on-success="handleImgUploadSuccess"> <!-- list-type="picture"-->
            <el-button type="primary">上传文件</el-button>
          </el-upload>
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
import { ref, reactive } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

const rules = reactive({
  number: [
    { required: true, message: '请输入发文字号', trigger: 'blur' }
  ],
})

const formRef = ref()

const data = reactive({
  pageNum: 1,
  pageSize: 10,
  total: 0,
  tableData: [],
  formVisible: false,
  form: {},
  title: '',
  time: '',
  authority: '',
  number: '',
})

const load = () => {
  request.get('/doc/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      number: data.number,
      title: data.title,
      time: data.time,
      authority: data.authority,
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

load()// 调用方法获取后台数据

const handelCurrentChange = () => {
  // 当翻页的时候重新加载数据即可
  load()
}

const reset = () => {
  data.number = ''
  data.title = ''
  data.time = ''
  data.authority = ''
  load()
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

// 保存数据到后台
const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.request({
        url: data.form.id ? '/doc/update' : '/doc/add',
        method: data.form.id ? 'PUT' : 'POST',
        data: data.form
      }).then(res => {
        if (res.code === '200') {
          load()    // 重新获取数据
          data.formVisible = false // 关闭弹窗
          ElMessage.success("操作成功")
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) // 深拷贝
  data.formVisible = true // 显示弹窗
}

const del = (id) => {
  ElMessageBox.confirm('确认删除？', '删除确认', { type: 'warning' }).then(res => {
    request.delete('/doc/delete/' + id).then(res => {
      if (res.code === '200') {
        load()    // 重新获取数据
        ElMessage.success("操作成功")
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(res => {})
}

const handleImgUploadSuccess = (res) => {
  data.form.pdf = res.data
}

</script>

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

.add-button {
  margin-bottom: 10px;
}

</style>