<template>
  <el-dialog title="录入体检报告" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="体检报告" prop="report">
        <el-input type="textarea" v-model="dataForm.report" placeholder="体检报告" autosize></el-input>
      </el-form-item>
      <el-form-item label="医生" prop="reportBy">
        <el-input v-model="dataForm.reportBy" placeholder="体检报告出具医生"></el-input>
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
      }

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
          this.getPackageList();
        }
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
