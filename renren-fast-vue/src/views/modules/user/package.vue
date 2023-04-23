<template>
  <div class="mod-config">
    <el-form :inline="true" :model="searchForm" @keyup.enter.native="getList()">
      <el-form-item>
        <el-input v-model="searchForm.key" placeholder="请输入关键字" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column prop="name" header-align="center" align="center" label="套餐名称">
      </el-table-column>
      <el-table-column prop="type" header-align="center" align="center" label="套餐类别">
      </el-table-column>
      <el-table-column prop="price" header-align="center" align="center" label="价格">
      </el-table-column>
      <el-table-column prop="tags" header-align="center" align="center" label="套餐标签">
      </el-table-column>
      <el-table-column prop="notion" header-align="center" align="center" label="注意事项">
      </el-table-column>

      <el-table-column fixed="right" header-align="center" align="center" width="200" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addProject(scope.row)">查看包含项目</el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.packageId)">预约</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.packageId)">取消预约</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getList"></add-or-update>

    <!--新增关联-->
    <el-dialog title="套餐包含项目" :visible.sync="packageProjectRelationDialogVisible" width="40%">
      <!-- 关联项目列表 -->
      <el-table :data="projectRelationTableData" style="width: 100%">
        <el-table-column prop="packageName" label="套餐名"></el-table-column>
        <el-table-column prop="projectName" label="项目名"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="packageProjectRelationDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="packageProjectRelationDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import AddOrUpdate from './package-add-or-update.vue'
import CategoryCascader from '../common/category-cascader.vue'

export default {
  data() {
    return {
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      // 搜索表单
      searchForm: {
        key: ''
      },
      // 套餐列表
      dataList: [],
      // 套餐列表 loading
      dataListLoading: false,
      // 套餐列表已选择
      packageListSelections: [],
      // 新增或更新弹窗
      addOrUpdateVisible: false,

      // 新增关联弹窗loading
      packageProjectRelationDialogVisible: false,
      // 新增项目pop
      projectAddSelectVisible: false,
      // 已关联项目列表
      projectRelationTableData: [],
      // 套餐 id
      packageId: 0,
      packageName: '',
      categorys: [],
      catelogPath: [],

      // 新增项目项目列表
      projectList: [],
      // 新增项目项目已选择
      projectChecked: undefined
    }
  },
  components: {
    AddOrUpdate,
    CategoryCascader
  },
  activated() {
    this.getList()
  },
  methods: {
    // 获取数据列表
    getList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/package/listTree'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.searchForm.key
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },

    // 角色状态修改
    handleStatusChange(row) {
      this.$http({
        url: this.$http.adornUrl('/product/package/updateIsValid'),
        method: 'get',
        params: this.$http.adornParams({
          'id': row.packageId,
          'isValid': row.isValid
        })
      }).then(({data}) => {
        this.getList()
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getList()
    },
    // 新增关联项目多选
    selectionChangeHandle(val) {
      this.packageListSelections = val
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle(id) {
      var ids = id ? [id] : this.packageListSelections.map(item => {
        return item.packageId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/package/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    // 添加项目
    addProject(row) {
      this.packageProjectRelationDialogVisible = true
      this.packageId = row.packageId
      this.packageName = row.name
      this.getRelationList()
      this.getProjectList()
    },
    // 获取关联项目列表
    getRelationList() {
      this.$http({
        url: this.$http.adornUrl('/product/packageprojectrelation/catelog/list'),
        method: 'get',
        params: this.$http.adornParams({
          'packageId': this.packageId
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.projectRelationTableData = data.data
        } else {
          this.projectRelationTableData = []
        }
      })
    },
    // 获取可新增项目列表
    getProjectList() {
      this.projectChecked = undefined
      this.$http({
        url: this.$http.adornUrl('/product/project/all/list'),
        method: 'get',
        params: this.$http.adornParams({
          'packageId': this.packageId
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.projectList = data.data
        } else {
          this.projectList = []
        }
      })
    },
    // 新增关联  确定按钮
    addProjectSelect() {
      if (this.projectChecked === undefined) {
        return
      }
      this.popCatelogSelectVisible = false
      this.$http({
        url: this.$http.adornUrl('/product/packageprojectrelation/save'),
        method: 'post',
        data: this.$http.adornData({
          packageId: this.packageId,
          packageName: this.packageName,
          projectId: this.projectChecked.projectId,
          projectName: this.projectChecked.name
        }, false)
      }).then(({data}) => {
        this.getRelationList()
      })
    },
    // 移除关联
    deleteCateRelationHandle(id, brandId) {
      this.$http({
        url: this.$http.adornUrl('/product/packageprojectrelation/delete'),
        method: 'post',
        data: this.$http.adornData([id], false)
      }).then(({data}) => {
        this.getRelationList()
      })
    }
  }
}
</script>
