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
      <el-table stripe :data="data.tableData">
        <el-table-column label="文件标题" prop="title"></el-table-column>
        <el-table-column label="发文时间" prop="time"></el-table-column>
        <el-table-column label="发文机关" prop="authority"></el-table-column>
        <el-table-column label="发文字号" prop="number"></el-table-column>
        <el-table-column label="点击下载文件正文" prop="pdf">
          <template v-slot="scope">
            <el-link :href="scope.row.pdf" target="_blank">点击下载</el-link>
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

load()   // 调用方法获取后台数据

const handelCurrentChange = () => {
  load()  // 当翻页的时候重新加载数据即可
}

const reset = () => {
  data.number = ''
  data.title = ''
  data.time = ''
  data.authority = ''
  load()
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