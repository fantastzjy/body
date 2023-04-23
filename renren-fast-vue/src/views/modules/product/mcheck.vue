<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('product:mcheck:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('product:mcheck:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
      </el-form-item>
    </el-form>
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <el-table-column prop="memberName" header-align="center" align="center" label="用户昵称">
      </el-table-column>
      <el-table-column prop="date" header-align="center" align="center" label="预约日期">
      </el-table-column>
      <el-table-column prop="packageName" header-align="center" align="center" label="体检套餐">
      </el-table-column>
      <el-table-column prop="projectIds" header-align="center" align="center" label="加项项目列表">
      </el-table-column>
      <el-table-column prop="checkedIds" header-align="center" align="center" label="已体检项目">
      </el-table-column>
      <el-table-column prop="status" header-align="center" align="center" label="体检状态">
      </el-table-column>
      <el-table-column prop="exceptionIds" header-align="center" align="center" label="异常项目">
      </el-table-column>
      <el-table-column prop="report" header-align="center" align="center" label="体检报告">
      </el-table-column>
      <el-table-column prop="reportBy" header-align="center" align="center" label="出具体检报告医生">
      </el-table-column>
      <el-table-column prop="createBy" header-align="center" align="center" label="创建人">
      </el-table-column>
      <el-table-column prop="createTime" header-align="center" align="center" label="创建时间">
      </el-table-column>
      <el-table-column prop="updateBy" header-align="center" align="center" label="更新人">
      </el-table-column>
      <el-table-column prop="updateTime" header-align="center" align="center" label="更新时间">
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="120" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="getProjectListHandle(scope.row.checkId)">项目列表</el-button>
          <el-button type="text" size="small" @click="saveCheckResult(scope.row.checkId)">录入体检报告</el-button>
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.checkId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.checkId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <save-check-report v-if="saveCheckReportVisible" ref="saveCheckReport"
                       @refreshDataList="getDataList"></save-check-report>


    <el-dialog title="体检项目列表" :visible.sync="packageProjectRelationDialogVisible" width="60%">

      <!-- 新增关联 -->
      <el-popover placement="right-end" v-model="projectAddSelectVisible">
        <div>
          <el-select v-model="projectChecked" filterable placeholder="请选择">
            <el-option
              v-for="item in projectList"
              :key="item.projectId"
              :label="item.name"
              :value="item">
            </el-option>
          </el-select>
        </div>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="projectAddSelectVisible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="addProjectSelect">确定</el-button>
        </div>
        <el-button slot="reference">新增项目</el-button>
      </el-popover>

      <!-- 关联项目列表 -->
      <el-table :data="projectRelationTableData" style="width: 100%">
        <el-table-column prop="projectName" header-align="center" align="center" label="项目">
        </el-table-column>
        <el-table-column prop="value" header-align="center" align="center" label="检查值">
        </el-table-column>
        <el-table-column prop="report" header-align="center" align="center" label="报告">
        </el-table-column>
        <el-table-column prop="checkBy" header-align="center" align="center" label="检查人">
        </el-table-column>
        <el-table-column prop="checkTime" header-align="center" align="center" label="检查时间">
        </el-table-column>
        <el-table-column prop="isException" header-align="center" align="center" label="是否异常">
          <template slot-scope="scope">
            <el-tag>
              {{ scope.row.isException == 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isValid" header-align="center" align="center" label="是否有效">
          <template slot-scope="scope">
            <el-tag>
              {{ scope.row.isValid == 1 ? '是' : '否' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="updateBy" header-align="center" align="center" label="更新者">
        </el-table-column>
        <el-table-column prop="updateTime" header-align="center" align="center" label="更新时间">
        </el-table-column>
        <el-table-column fixed="right" header-align="center" align="center" label="操作">
          <template slot-scope="scope">
            <!--            <el-button type="text" size="small" @click="saveProjectCheckResult(scope.row.id, scope.row.projectId)">-->
            <!--              录入检查结果-->
            <!--            </el-button>-->
            <!--            <el-button type="text" size="small" @click="updateCheckProject(scope.row.id, scope.row.projectId)">-->
            <!--              修改-->
            <!--            </el-button>-->
            <el-button type="text" size="small" @click="deleteCateRelationHandle(scope.row.id, scope.row.projectId)">
              移除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="packageProjectRelationDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="packageProjectRelationDialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import AddOrUpdate from './mcheck-add-or-update'
import SaveCheckReport from './mcheck-save-check-report'

export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,

      // 录入检查结果
      saveCheckReportVisible: false,

      // 新增关联弹窗loading
      packageProjectRelationDialogVisible: false,
      // 新增项目pop
      projectAddSelectVisible: false,
      // 已关联项目列表
      projectRelationTableData: [],
      // 套餐 id
      checkId: 0,
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
    SaveCheckReport
  },
  activated() {
    this.getDataList()
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/check/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key
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
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
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
      var ids = id ? [id] : this.dataListSelections.map(item => {
        return item.checkId
      })
      this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/check/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },

    //体检关联项目列表
    getProjectListHandle(id) {
      this.packageProjectRelationDialogVisible = true;
      this.getRelationList(id);
      this.getProjectList();
    },
    // 录入体检综合报告
    saveCheckResult(id) {
      this.saveCheckReportVisible = true
      this.$nextTick(() => {
        this.$refs.saveCheckReport.init(id)
      })
    },

    /*项目列表弹窗*/

    // 获取关联项目列表
    getRelationList(id) {
      this.$http({
        url: this.$http.adornUrl('/product/checkproject/project/list'),
        method: 'get',
        params: this.$http.adornParams({
          'checkId': id
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
        url: this.$http.adornUrl('/product/checkproject/project/list'),
        method: 'get',
        params: this.$http.adornParams({
          'packageId': this.checkId
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.projectList = data.data
        } else {
          this.projectList = []
        }
      })
    },
  }
}
</script>
