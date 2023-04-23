<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="套餐id" prop="packageId">
        <el-input v-model="dataForm.packageId" placeholder="套餐id"></el-input>
      </el-form-item>
      <el-form-item label="项目id" prop="projectId">
        <el-input v-model="dataForm.projectId" placeholder="项目id"></el-input>
      </el-form-item>
      <el-form-item label="项目顺序" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="项目顺序"></el-input>
      </el-form-item>
      <el-form-item label="是否有效：0 无，1 有" prop="isValid">
        <el-input v-model="dataForm.isValid" placeholder="是否有效：0 无，1 有"></el-input>
      </el-form-item>
      <el-form-item label="是否删除：0 否，1 是" prop="isDel">
        <el-input v-model="dataForm.isDel" placeholder="是否删除：0 否，1 是"></el-input>
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input v-model="dataForm.createBy" placeholder="创建人"></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input v-model="dataForm.updateBy" placeholder="更新人"></el-input>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
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
        packageId: '',
        projectId: '',
        sort: '',
        isValid: '',
        isDel: '',
        createBy: '',
        createTime: '',
        updateBy: '',
        updateTime: ''
      },
      dataRule: {
        packageId: [
          { required: true, message: '套餐id不能为空', trigger: 'blur' }
        ],
        projectId: [
          { required: true, message: '项目id不能为空', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '项目顺序不能为空', trigger: 'blur' }
        ],
        isValid: [
          { required: true, message: '是否有效：0 无，1 有不能为空', trigger: 'blur' }
        ],
        isDel: [
          { required: true, message: '是否删除：0 否，1 是不能为空', trigger: 'blur' }
        ],
        createBy: [
          { required: true, message: '创建人不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ],
        updateBy: [
          { required: true, message: '更新人不能为空', trigger: 'blur' }
        ],
        updateTime: [
          { required: true, message: '更新时间不能为空', trigger: 'blur' }
        ]
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
            url: this.$http.adornUrl(`/product/cpackageprojectrelation/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.packageId = data.cPackageProjectRelation.packageId
              this.dataForm.projectId = data.cPackageProjectRelation.projectId
              this.dataForm.sort = data.cPackageProjectRelation.sort
              this.dataForm.isValid = data.cPackageProjectRelation.isValid
              this.dataForm.isDel = data.cPackageProjectRelation.isDel
              this.dataForm.createBy = data.cPackageProjectRelation.createBy
              this.dataForm.createTime = data.cPackageProjectRelation.createTime
              this.dataForm.updateBy = data.cPackageProjectRelation.updateBy
              this.dataForm.updateTime = data.cPackageProjectRelation.updateTime
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
            url: this.$http.adornUrl(`/product/cpackageprojectrelation/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'packageId': this.dataForm.packageId,
              'projectId': this.dataForm.projectId,
              'sort': this.dataForm.sort,
              'isValid': this.dataForm.isValid,
              'isDel': this.dataForm.isDel,
              'createBy': this.dataForm.createBy,
              'createTime': this.dataForm.createTime,
              'updateBy': this.dataForm.updateBy,
              'updateTime': this.dataForm.updateTime
            })
          }).then(({ data }) => {
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
