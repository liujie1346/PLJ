<template>
  <div>
    <Button type="primary" v-has="'6'">测试1</Button>
    <Button type="primary" v-has="['1','21']">测试2</Button>

    <br/>

    <Button type="primary" @click="add">添加</Button>
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
          {
            title: '操作', key: 'action', render: (h, params) => {
              return h('div', [
                h('Button', {
                  props: {
                    type: 'primary',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.edit();
                    },
                  },
                }, '编辑'),
                h('Button', {
                  props: {
                    type: 'error',
                    size: 'small'
                  },
                  on: {
                    click: () => {
                      this.del();
                    },
                  },
                }, '删除'),
              ]);
            }
          },
        ],
        tableData: [],
      }
    },
    mounted() {
      this.init();
    },
    methods: {
      init() {
        request.get("permission/find", {}).then(res => {
          if (res.data.code == '200') {
            this.tableData = res.data.data;
          }
        })
      },
      add(){
        alert('add');
      },
      edit(){
        alert('edit');
      },
      del(){
        alert('del');
      },
    }
  }
</script>

<style scoped>

</style>
