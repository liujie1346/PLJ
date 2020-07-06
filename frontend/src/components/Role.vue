<template>
  <div>
    <Table :columns="columns" :data="tableData" width="800"></Table>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Role',
    data() {
      return {
        columns: [
          {title: 'id', key: 'id'},
          {title: 'role_name', key: 'role_name'},
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
        axios.get('http://localhost:8083/role/find', {
          // params: {
          //   id: 123,
          // },
          //其他相关配置
          headers: {'accessToken': accessToken}//设置header信息
        }).then(function (res) {
          console.log(res.data);
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
