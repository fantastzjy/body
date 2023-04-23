<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible"
             @closed="dialogClose" append-to-body>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="110px">
      <el-form-item label="项目类别" prop="type">
        <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
      </el-form-item>
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="项目名称"></el-input>
      </el-form-item>
      <el-form-item label="体检科室" prop="room">
        <el-input v-model="dataForm.room" placeholder="体检科室"></el-input>
      </el-form-item>

      <el-form-item label="度量单位" prop="unit">
        <el-input v-model="dataForm.unit" placeholder="度量单位"></el-input>
      </el-form-item>
      <el-form-item label="正常范围最小值" prop="scopeMin">
        <el-input-number v-model="dataForm.scopeMin" placeholder="正常范围最小值"/>
      </el-form-item>
      <el-form-item label="正常范围最大值" prop="scopeMax">
        <el-input-number v-model="dataForm.scopeMax" placeholder="正常范围最大值"/>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number v-model="dataForm.price" :precision="2" :step="0.1" :min="0" placeholder="价格"/>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker type="datetime" placeholder="项目开始时间" v-model="dataForm.startTime"
                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker type="datetime" placeholder="项目结束时间" v-model="dataForm.endTime"
                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>

      <el-form-item label="是否重点" prop="isNotion">
        <el-switch v-model="dataForm.isNotion" active-color="#13ce66" inactive-color="#ff4949" :active-value=1
                   :inactive-value=0></el-switch>
      </el-form-item>
      <el-form-item label="是否有效" prop="isValid">
        <el-switch v-model="dataForm.isValid" active-color="#13ce66" inactive-color="#ff4949" :active-value=1
                   :inactive-value=0></el-switch>
      </el-form-item>
      <el-form-item label="注意事项" prop="notion">
        <el-input type="textarea" v-model="dataForm.notion" autosize placeholder="注意事项"></el-input>
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
import CategoryCascader from "../common/category-cascader.vue";

export default {
  components: {
    CategoryCascader
  },

  data() {
    return {
      visible: false,
      categorys: [],
      catelogPath: [],
      dataForm: {
        projectId: 0,
        type: 0,//校验
        name: '',
        room: '',
        price: '',
        notion: '',
        isNotion: 0,
        unit: '',
        scopeMin: '',
        scopeMax: '',
        startTime: '',
        endTime: '',
        extra: '',
        isValid: 0,
        isDel: 0,
        createBy: '',
        createTime: '',
        updateBy: '',
        updateTime: ''
      },
      dataRule: {
        // type: [
        //   { required: true, message: '项目类别不能为空', trigger: 'blur' }
        // ],
        // name: [
        //   { required: true, message: '项目名称不能为空', trigger: 'blur' }
        // ],
        // room: [
        //   { required: true, message: '体检科室不能为空', trigger: 'blur' }
        // ],
        // price: [
        //   { required: true, message: '价格不能为空', trigger: 'blur' }
        // ],
        // notion: [
        //   { required: true, message: '注意事项不能为空', trigger: 'blur' }
        // ],
        // isNotion: [
        //   { required: true, message: '是否重点提醒不能为空', trigger: 'blur' }
        // ],
        // unit: [
        //   { required: true, message: '度量单位不能为空', trigger: 'blur' }
        // ],
        // scopeMin: [
        //   { required: true, message: '正常范围最小值不能为空', trigger: 'blur' }
        // ],
        // scopeMax: [
        //   { required: true, message: '正常范围最大值不能为空', trigger: 'blur' }
        // ],
        // startTime: [
        //   { required: true, message: '项目开始时间不能为空', trigger: 'blur' }
        // ],
        // endTime: [
        //   { required: true, message: '项目结束时间不能为空', trigger: 'blur' }
        // ]
      }
    }
  },

  methods: {
    dialogClose() {
      this.catelogPath = [];
    },
    getCategorys() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({data}) => {
        this.categorys = data.data;
      });
    },
    init(id) {
      this.dataForm.projectId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.projectId) {
          this.$http({
            url: this.$http.adornUrl(`/product/project/info/${this.dataForm.projectId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.type = data.cProject.type
              this.dataForm.name = data.cProject.name
              this.dataForm.room = data.cProject.room
              this.dataForm.price = data.cProject.price
              this.dataForm.notion = data.cProject.notion
              this.dataForm.isNotion = data.cProject.isNotion
              this.dataForm.unit = data.cProject.unit
              this.dataForm.scopeMin = data.cProject.scopeMin
              this.dataForm.scopeMax = data.cProject.scopeMax
              this.dataForm.startTime = data.cProject.startTime
              this.dataForm.endTime = data.cProject.endTime
              this.dataForm.extra = data.cProject.extra
              this.dataForm.isValid = data.cProject.isValid
              this.dataForm.isDel = data.cProject.isDel
              this.dataForm.createBy = data.cProject.createBy
              this.dataForm.createTime = data.cProject.createTime
              this.dataForm.updateBy = data.cProject.updateBy
              this.dataForm.updateTime = data.cProject.updateTime
              //查出catelogId的完整路径
              this.catelogPath = data.cProject.catelogPath
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
            url: this.$http.adornUrl(`/product/project/${!this.dataForm.projectId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'projectId': this.dataForm.projectId || undefined,
              'type': this.catelogPath[this.catelogPath.length - 1],
              'name': this.dataForm.name,
              'room': this.dataForm.room,
              'price': this.dataForm.price,
              'notion': this.dataForm.notion,
              'isNotion': this.dataForm.isNotion,
              'unit': this.dataForm.unit,
              'scopeMin': this.dataForm.scopeMin,
              'scopeMax': this.dataForm.scopeMax,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime,
              'extra': this.dataForm.extra,
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
  },
  created() {
    this.getCategorys();
  }
}
</script>
