<style>
  .ivu-layout-sider-children{
    height: 90.5vh;
  }
</style>
<template>
  <div class="layout">
    <Header>
      <Menu mode="horizontal" theme="dark" active-name="1">
        <div class="layout-logo">
          <img width="100" height="30" src="https://imgm.gmw.cn/attachement/jpg/site215/20200706/7388386464722435949.jpg"/>
        </div>
        <div class="layout-nav">
          <MenuItem name="1">
          </MenuItem>
          <MenuItem name="2">
            <Icon @click="fullScreenClick" v-if="isFullScreen" type="ios-contract" size="20" color="white"/>
            <Icon @click="fullScreenClick" v-if="!isFullScreen" type="ios-expand" size="20" color="white"/>
          </MenuItem>
          <MenuItem name="3">
            <Avatar src="https://imgm.gmw.cn/attachement/jpg/site215/20200706/7388386464722435949.jpg"/>
          </MenuItem>
          <MenuItem name="4">
            <Button type="default" ghost @click="logout">登出</button>
          </MenuItem>
        </div>
      </Menu>
    </Header>
    <Layout>
      <Sider ref="sider" hide-trigger collapsible :collapsed-width="78" v-model="isCollapsed">
        <Menu theme="dark" @on-select="onSelect" width="auto" :class="menuitemClasses" active-name="1" :open-names="['1']">
          <Submenu name="1">
            <template slot="title">
              <Icon type="ios-paper"/>
              <span>系统管理</span>
            </template>
            <MenuItem name="User">用户管理</MenuItem>
            <MenuItem name="Role">角色管理</MenuItem>
            <MenuItem name="Permission">菜单管理</MenuItem>
          </Submenu>
        </Menu>
      </Sider>
      <Layout>
        <Header class="layout-header-bar">
          <Breadcrumb>
            <BreadcrumbItem>
              <Icon @click.native="collapsedSider" :class="rotateIcon" type="md-menu" size="24"></Icon>
            </BreadcrumbItem>
            <BreadcrumbItem>Home</BreadcrumbItem>
            <BreadcrumbItem>Components</BreadcrumbItem>
            <BreadcrumbItem>Layout</BreadcrumbItem>
          </Breadcrumb>
        </Header>
        <Content :style="{margin: '20px',padding: '20px', background: '#fff', minHeight: '260px'}">
          <V-User v-if="name=='User'"></V-User>
          <V-Role v-if="name=='Role'"></V-Role>
          <V-Permission v-if="name=='Permission'"></V-Permission>
        </Content>
        <Footer class="layout-footer-center">2011-2016 &copy; TalkingData</Footer>
      </Layout>
    </Layout>
  </div>
</template>
<script>
  import User from './User'
  import Role from './Role'
  import Permission from './Permission'
  import axios from 'axios'
  import screenfull from 'screenfull'  //引入screenfull
  export default {
    name: 'Main',
    data() {
      return {
        name: "User",
        isFullScreen: false, //是否全屏
        isCollapsed: false,
      }
    },
    components: {"V-User": User, "V-Role": Role, "V-Permission": Permission},
    computed: {
      rotateIcon() {
        return [
          'menu-icon',
          this.isCollapsed ? 'rotate-icon' : ''
        ];
      },
      menuitemClasses() {
        return [
          'menu-item',
          this.isCollapsed ? 'collapsed-menu' : ''
        ]
      }
    },
    methods: {
      collapsedSider() {
        this.$refs.sider.toggleCollapse();
      },
      onSelect(name) {
        console.log(name);
        this.name = name;
      },
      //全屏
      fullScreenClick() {
        if (!screenfull.enabled) {
          this.$Notice.info({
            title: '消息',
            desc: '你的浏览器不支持全屏',
          })
          return false
        }
        screenfull.toggle();
        this.isFullScreen = !this.isFullScreen;
      },
      logout() {
        request.get("logout", {}).then(res => {
          if (res.status == '200') {
            this.$Notice.info({
              title: '消息',
              desc: res.data.msg
            });
            this.$router.push({path: '/'})
          }
        })
      },
    }
  }
</script>
<style scoped>
  .layout-logo {
    width: 100px;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    float: left;
    position: relative;
    top: 15px;
    left: -25px;
  }

  .layout {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
  }

  .layout-header-bar {
    background: #fff;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
  }

  .layout-nav {
    margin-left: 80%;
    width: 420px;
    margin-right: 20px;
  }

  .layout-logo-left {
    width: 90%;
    height: 30px;
    background: #5b6270;
    border-radius: 3px;
    margin: 15px auto;
  }

  .menu-icon {
    transition: all .3s;
  }

  .rotate-icon {
    transform: rotate(-90deg);
  }

  .menu-item span {
    display: inline-block;
    overflow: hidden;
    width: 69px;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: bottom;
    transition: width .2s ease .2s;
  }

  .menu-item i {
    transform: translateX(0px);
    transition: font-size .2s ease, transform .2s ease;
    vertical-align: middle;
    font-size: 16px;
  }

  .collapsed-menu span {
    width: 0px;
    transition: width .2s ease;
  }

  .collapsed-menu i {
    transform: translateX(5px);
    transition: font-size .2s ease .2s, transform .2s ease .2s;
    vertical-align: middle;
    font-size: 22px;
  }
</style>
