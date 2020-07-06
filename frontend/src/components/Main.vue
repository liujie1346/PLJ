<style>
  /*所有标签的margin，padding清零*/
  * {
    padding: 0px;
    margin: 0px;
  }

  /*导航栏*/
  .nav {
    width: 100%;
    height: 60px;
    list-style-type: none; /*去掉无序列表前面的圆圈*/
    background-color: #515a1e;
    margin: -60px auto;
  }
</style>
<template>
  <div>
    <Row>
      <div class="nav">
        <span style="color: white;font-size: 24px;margin: 5px;float: left">庞留杰个人站</span>
        <span @click="logout" style="float: right">
          <Button>登出</button>
        </span>
        <span style="color: white;font-size: 28px;float: right">
          <img style="width: 50px;height: 50px;border-radius:25px" src="https://imgm.gmw.cn/attachement/jpg/site215/20200706/7388386464722435949.jpg">
        </span>
      </div>
    </Row>
    <Row>
      <Col span="6">
        <Menu theme="dark" @on-select="onSelect">
          <Submenu name="1">
            <template slot="title">
              <Icon type="ios-paper"/>
              系统管理
            </template>
            <MenuItem name="User">用户管理</MenuItem>
            <MenuItem name="Role">角色管理</MenuItem>
            <MenuItem name="Permission">菜单管理</MenuItem>
          </Submenu>
        </Menu>
      </Col>
      <Col span="18">
        <V-User v-if="name=='User'"></V-User>
        <V-Role v-if="name=='Role'"></V-Role>
        <V-Permission v-if="name=='Permission'"></V-Permission>
      </Col>
    </Row>
  </div>
</template>

<script>
  import User from './User'
  import Role from './Role'
  import Permission from './Permission'
  import axios from 'axios'

  export default {
    name: 'Main',
    data() {
      return {
        name: "User"
      }
    },
    components: {"V-User": User, "V-Role": Role, "V-Permission": Permission},
    methods: {
      onSelect(name) {
        console.log(name);
        this.name = name;
      },
      logout(){
        //存储accessToken
        let accessToken = localStorage.getItem("accessToken")
        axios({
          url: 'http://localhost:8083/logout',
          method: 'get',
          headers: {'accessToken': accessToken}//设置header信息
        }).then(response => {
          if (response.status == '200') {
            this.$Notice.info({
              title: '消息',
              desc: response.data.msg
            });
            this.$router.push({path: '/'})
          }
        });
      },
    }
  }
</script>
<style scoped>

</style>
