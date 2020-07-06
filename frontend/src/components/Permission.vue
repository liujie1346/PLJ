<template>
  <div>
    <!--使用树形数据时，必须指定 row-key，比如 id。-->
    <Table row-key="id" :columns="columns" :data="tableData" width="800"></Table>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Permission',
    data() {
      return {
        columns: [
          {title: 'index', key: 'index', type: 'index'},//在 column 开启属性 tree，则该列会显示展开/收起图标。
          {title: 'id', key: 'id', tree: true},
          {title: 'resource_name', key: 'resource_name'},
          {title: 'resource_method', key: 'resource_method'},
          {title: 'resource_url', key: 'resource_url'},
          {title: 'parent_id', key: 'parent_id'},
          {title: 'sort', key: 'sort'},
          {title: 'description', key: 'description'},
        ],
        tableData: [],
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      init() {
        //存储accessToken
        let accessToken = localStorage.getItem("accessToken")

        console.log("accessToken=" + accessToken);

        let _this = this
        axios.get('http://localhost:8083/permission/find', {
          // params: {
          //   id: 123,
          // },
          //其他相关配置
          headers: {'accessToken': accessToken}//设置header信息
        }).then(function (res) {
          console.log("==========res.data====", res.data);
          _this.tableData = res.data;
        }).catch(function (err) {
          console.log(err);
        });
      }
    }
  }
</script>

<style scoped>

</style>
