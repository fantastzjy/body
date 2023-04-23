<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-form-item label="预约日期" prop="date">
        <el-date-picker type="datetime" placeholder="预约日期" v-model="dataForm.date"
                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="体检套餐" prop="packageId">
        <template>
          <el-select v-model="dataForm.packageId" filterable placeholder="请选择">
            <el-option
              v-for="item in packageList"
              :key="item.packageId"
              :label="item.name"
              :value="item.packageId">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="加项体检项目" prop="projectIds">
        <el-input v-model="dataForm.projectIds" placeholder="加项项目列表"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 套餐列表
      packageList: [],
      visible: false,
      dataForm: {
        checkId: 0,
        memberId: '',
        memberName: '',
        date: '',
        packageId: '',
        projectIds: '',
        checkedIds: '',
        status: '',
        exceptionIds: '',
        report: '',
        reportBy: '',
        isValid: '',
        isDel: '',
        createBy: '',
        createTime: '',
        updateBy: '',
        updateTime: ''
      },
      // dataRule: {
      //   memberId: [
      //     {required: true, message: '用户id不能为空', trigger: 'blur'}
      //   ],
      //   memberName: [
      //     {required: true, message: '用户昵称不能为空', trigger: 'blur'}
      //   ],
      //   date: [
      //     {required: true, message: '预约日期不能为空', trigger: 'blur'}
      //   ],
      //   packageId: [
      //     {required: true, message: '体检套餐不能为空', trigger: 'blur'}
      //   ],
      //   projectIds: [
      //     {required: true, message: '加项项目列表不能为空', trigger: 'blur'}
      //   ],
      //   checkedIds: [
      //     {required: true, message: '已体检项目 id 列表不能为空', trigger: 'blur'}
      //   ],
      //   status: [
      //     {required: true, message: '体检状态不能为空', trigger: 'blur'}
      //   ],
      //   exceptionIds: [
      //     {required: true, message: '异常项目 id列表不能为空', trigger: 'blur'}
      //   ],
      //   report: [
      //     {required: true, message: '体检报告不能为空', trigger: 'blur'}
      //   ],
      //   reportBy: [
      //     {required: true, message: '体检报告出具医生不能为空', trigger: 'blur'}
      //   ],
      //   isValid: [
      //     {required: true, message: '是否有效不能为空', trigger: 'blur'}
      //   ],
      //   isDel: [
      //     {required: true, message: '是否删除不能为空', trigger: 'blur'}
      //   ],
      //   createBy: [
      //     {required: true, message: '创建人不能为空', trigger: 'blur'}
      //   ],
      //   createTime: [
      //     {required: true, message: '创建时间不能为空', trigger: 'blur'}
      //   ],
      //   updateBy: [
      //     {required: true, message: '更新人不能为空', trigger: 'blur'}
      //   ],
      //   updateTime: [
      //     {required: true, message: '更新时间不能为空', trigger: 'blur'}
      //   ]
      // }
    }
  },
  methods: {
    init(id) {
      this.dataForm.checkId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.checkId) {
          this.getFormData();
        }
        this.getPackageList();
      })
    },
    // 获取表单数据
    getFormData() {
      this.$http({
        url: this.$http.adornUrl(`/product/check/info/${this.dataForm.checkId}`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataForm.memberId = data.mCheck.memberId
          this.dataForm.memberName = data.mCheck.memberName
          this.dataForm.date = data.mCheck.date
          this.dataForm.packageId = data.mCheck.packageId
          this.dataForm.projectIds = data.mCheck.projectIds
          this.dataForm.checkedIds = data.mCheck.checkedIds
          this.dataForm.status = data.mCheck.status
          this.dataForm.exceptionIds = data.mCheck.exceptionIds
          this.dataForm.report = data.mCheck.report
          this.dataForm.reportBy = data.mCheck.reportBy
          this.dataForm.isValid = data.mCheck.isValid
          this.dataForm.isDel = data.mCheck.isDel
          this.dataForm.createBy = data.mCheck.createBy
          this.dataForm.createTime = data.mCheck.createTime
          this.dataForm.updateBy = data.mCheck.updateBy
          this.dataForm.updateTime = data.mCheck.updateTime
        }
      })
    },
    // 获取套餐列表
    getPackageList() {
      this.$http({
        url: this.$http.adornUrl('/product/package/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': 1,
          'limit': 200,
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.packageList = data.page.list
        } else {
          this.packageList = []
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/check/${!this.dataForm.checkId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'checkId': this.dataForm.checkId || undefined,
              'memberId': this.dataForm.memberId,
              'memberName': this.dataForm.memberName,
              'date': this.dataForm.date,
              'packageId': this.dataForm.packageId,
              'projectIds': this.dataForm.projectIds,
              'checkedIds': this.dataForm.checkedIds,
              'status': this.dataForm.status,
              'exceptionIds': this.dataForm.exceptionIds,
              'report': this.dataForm.report,
              'reportBy': this.dataForm.reportBy,
              'isValid': this.dataForm.isValid,
              'isDel': this.dataForm.isDel,
              'createBy': this.dataForm.createBy,
              'createTime': this.dataForm.createTime,
              'updateBy': this.dataForm.updateBy,
              'updateTime': this.dataForm.updateTime
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>
