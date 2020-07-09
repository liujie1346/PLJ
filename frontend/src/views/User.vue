<template>
  <div>
    <Table :columns="columns" :data="tableData" width="800"></Table>
  </div>
</template>
<script>
  import axios from 'axios'
  export default {
    name: 'User',
    data() {
      return {
        columns: [
          {title: 'id',key: 'id'},
          {title: 'user_name',key: 'user_name'},
          {title: 'password',key: 'password'},
          {title: 'description',key: 'description'},
          {title: 'state',key: 'state'},
        ],
        tableData: [],
      }
    },
    mounted(){
      this.init();
    },
    methods: {
      init() {
        //存储accessToken
        request.get("user/find", {}).then(res => {
          if (res.data.code == '200') {
             this.tableData = res.data.data;
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
