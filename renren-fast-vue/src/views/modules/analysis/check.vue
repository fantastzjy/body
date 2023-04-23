<template>
  <div>
    <el-row>
      <el-form :model="queryParams" ref="queryForm" :rules="rules" label-width="100px">
        <el-col :span="24">
          <el-form-item label="时间范围" prop="dateRange">
            <el-date-picker v-model="queryParams.dateRange" @change="handleQuery" size="small" style="width: 350px"
                            value-format="yyyy-MM-dd HH:mm:ss" type="datetimerange" :picker-options="pickerOptions"
                            range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                            :default-time="['00:00:00', '23:59:59']"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <!-- 折线图 -->
    <el-row>
      <el-card shadow="hover">
        <div ref="pageShow" style="width:100%;height:350px;" v-loading="load"></div>
      </el-card>
    </el-row>
  </div>
</template>

<script>

import echarts from "echarts";

export default {

  data() {
    return {
      load: true,
      loading: true,

      // 耗时列表数据
      pageList: [],

      // 详情
      tableData: [],

      // 时间选择器
      pickerOptions: {
        shortcuts: [
          {
            text: '昨天',
            onClick(picker) {
              const end = new Date(new Date().setHours(23, 59, 59));
              const start = new Date(new Date().setHours(0, 0, 0));
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 1);
              end.setTime(end.getTime() - 3600 * 1000 * 24 * 1);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '前天',
            onClick(picker) {
              const end = new Date(new Date().setHours(23, 59, 59));
              const start = new Date(new Date().setHours(0, 0, 0));
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 2);
              end.setTime(end.getTime() - 3600 * 1000 * 24 * 2);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近七天',
            onClick(picker) {
              const end = new Date(new Date().setHours(23, 59, 59));
              const start = new Date(new Date().setHours(0, 0, 0));
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }]
      },

      // 查询参数
      queryParams: {
        dateRange: [],
      },

      // 表单校验
      rules: {
        dateRange: [
          {required: true, message: "时间范围不能为空", trigger: "blur"},
        ],
      }
    };
  },

  created() {
    this.getList();
  },

  activated() {
    if (this.pageShow) {
      this.pageShow.resize()
    }
  },

  methods: {
    // 获取页面页表
    getList() {
      debugger
      const end = new Date(new Date().setHours(23, 59, 59));
      const start = new Date(new Date().setHours(0, 0, 0));
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.queryParams.dateRange = [this.parseTime(start), this.parseTime(end)];

      this.$http({
        url: this.$http.adornUrl('/product/check/analysis'),
        method: 'get',
        params: this.addDateRange(this.queryParams.dateRange)
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.load = false;
          this.pageShowData = data.content;
          this.TimeChart();
        }
      })
    },
    /** 分布图 */
    TimeChart() {
      var keyList = this.pageShowData.keyList;
      var androidList = this.pageShowData.androidList;

      this.pageShow = echarts.init(this.$refs.pageShow, "macarons");
      this.pageShow.setOption({
        title: {
          text: '预约趋势'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['android']
        },
        toolbox: {
          show: true,
          feature: {
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            restore: {show: true},
            saveAsImage: {show: true}
          }
        },
        calculable: true,
        xAxis: [
          {
            type: 'category',
            data: keyList
          }
        ],
        yAxis: [
          {
            name: '次数',
            type: 'value'
          }
        ],
        series: [
          {
            name: 'android',
            type: 'line',
            data: androidList,
            barWidth: '20%',
            color: '#5470c6'
          }
        ]
      });

      window.addEventListener('resize', this.pageShow.resize);
    },
    // 添加日期范围
    addDateRange(dateRange) {
      debugger
      var search = {
        beginTime: "",
        endTime: ""
      };

      if (null != dateRange && '' != dateRange) {
        search.beginTime = dateRange[0];
        search.endTime = dateRange[1];
      }
      return search;
    },

    /**分布图 搜索按钮操作 */
    handleQuery() {
      this.getTimeout();
    }
  }
};
</script>

<style>
.custom-card {
  width: 90%;
  margin: 10px auto;
}

.size-float {
  float: right
}

.custom-warning {
  background-color: #fdf6ec;
  border-color: #faecd8;
  color: #e6a23c;
}
</style>
