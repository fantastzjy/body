<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

      <el-form-item label="套餐名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="套餐名称"></el-input>
      </el-form-item>
      <el-form-item label="套餐类别" prop="type">
        <el-input v-model="dataForm.type" placeholder="套餐类别"></el-input>
      </el-form-item>
      <el-form-item label="套餐标签" prop="tags">
        <el-input v-model="dataForm.tags" placeholder="套餐标签"></el-input>
      </el-form-item>
      <el-form-item label="注意事项" prop="notion">
        <el-input type="textarea" v-model="dataForm.notion" autosize placeholder="注意事项"></el-input>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number v-model="dataForm.price" :precision="2" :step="0.1" :min="0" placeholder="价格" />
      </el-form-item>
      <el-form-item label="是否有效" prop="isValid">
        <el-switch v-model="dataForm.isValid" active-color="#13ce66" inactive-color="#ff4949" :active-value=1
          :inactive-value=0></el-switch>
      </el-form-item>

      <el-form-item label="创建人" prop="createBy">
        <el-input v-model="dataForm.createBy" placeholder="创建人"></el-input>
      </el-form-item>
      <el-form-item label="更新人" prop="updateBy">
        <el-input v-model="dataForm.updateBy" placeholder="更新人"></el-input>
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
        packageId: 0,
        type: '',
        name: '',
        price: '',
        tags: '',
        notion: '',
        extra: '',
        isValid: '',
        isDel: '',
        createBy: '',
        createTime: '',
        updateBy: '',
        updateTime: ''
      },
      dataRule: {
        type: [
          { required: true, message: '套餐类别不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '套餐名称不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '价格不能为空', trigger: 'blur' }
        ],
        tags: [
          { required: true, message: '套餐标签不能为空', trigger: 'blur' }
        ],
        notion: [
          { required: true, message: '注意事项不能为空', trigger: 'blur' }
        ],
        extra: [
          { required: true, message: '拓展参数不能为空', trigger: 'blur' }
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
      this.dataForm.packageId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.packageId) {
          this.$http({
            url: this.$http.adornUrl(`/product/cpackage/info/${this.dataForm.packageId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.dataForm.type = data.cPackage.type
              this.dataForm.name = data.cPackage.name
              this.dataForm.price = data.cPackage.price
              this.dataForm.tags = data.cPackage.tags
              this.dataForm.notion = data.cPackage.notion
              this.dataForm.extra = data.cPackage.extra
              this.dataForm.isValid = data.cPackage.isValid
              this.dataForm.isDel = data.cPackage.isDel
              this.dataForm.createBy = data.cPackage.createBy
              this.dataForm.createTime = data.cPackage.createTime
              this.dataForm.updateBy = data.cPackage.updateBy
              this.dataForm.updateTime = data.cPackage.updateTime
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
            url: this.$http.adornUrl(`/product/package/${!this.dataForm.packageId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'packageId': this.dataForm.packageId || undefined,
              'type': this.dataForm.type,
              'name': this.dataForm.name,
              'price': this.dataForm.price,
              'tags': this.dataForm.tags,
              'notion': this.dataForm.notion,
              'extra': this.dataForm.extra,
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
    },

    dialogClose() {
      this.catelogPath = [];
    },
    getCategorys() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        this.categorys = data.data;
      });
    },
  }
  ,
  created() {
    this.getCategorys();
  }
}
</script>
