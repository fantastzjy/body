<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="检查值" prop="value">
        <el-input v-model="dataForm.value" placeholder="检查值"></el-input>
      </el-form-item>
      <el-form-item label="报告" prop="report">
        <el-input type="textarea" autosize v-model="dataForm.report" placeholder="报告"></el-input>
      </el-form-item>
      <el-form-item label="检查人" prop="checkBy">
        <el-input v-model="dataForm.checkBy" placeholder="检查人"></el-input>
      </el-form-item>
      <el-form-item label="是否异常" prop="isException">
        <el-switch v-model="dataForm.isException" active-color="#13ce66" inactive-color="#ff4949" :active-value=1
                   :inactive-value=0></el-switch>
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
      visible: false,
      dataForm: {
        id: 0,
        projectId: '',
        value: '',
        report: '',
        checkBy: '',
        checkTime: '',
        isException: '',
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
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/product/checkproject/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.projectId = data.mCheckProject.projectId
              this.dataForm.value = data.mCheckProject.value
              this.dataForm.report = data.mCheckProject.report
              this.dataForm.checkBy = data.mCheckProject.checkBy
              this.dataForm.checkTime = data.mCheckProject.checkTime
              this.dataForm.isException = data.mCheckProject.isException
              this.dataForm.isValid = data.mCheckProject.isValid
              this.dataForm.isDel = data.mCheckProject.isDel
              this.dataForm.createBy = data.mCheckProject.createBy
              this.dataForm.createTime = data.mCheckProject.createTime
              this.dataForm.updateBy = data.mCheckProject.updateBy
              this.dataForm.updateTime = data.mCheckProject.updateTime
            }
          })
        }
      })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/checkproject/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'projectId': this.dataForm.projectId,
              'value': this.dataForm.value,
              'report': this.dataForm.report,
              'checkBy': this.dataForm.checkBy,
              'checkTime': this.dataForm.checkTime,
              'isException': this.dataForm.isException,
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
