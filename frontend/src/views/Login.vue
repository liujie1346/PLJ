<template>
  <div class="login_page">
    <div class="login_box">
      <Menu mode="horizontal" @on-select="onSelect">
        <MenuItem name="login_card">
          <Icon type="ios-paper"/>
          登录
        </MenuItem>
        <MenuItem name="register_card">
          <Icon type="ios-people"/>
          注册
        </MenuItem>
      </Menu>
      <Card v-show="name == 'login_card'">
        <Form class="login_form" ref="form" label="登录页面">
          <FormItem prop="user">
            <Input v-model="username" placeholder="用户名" style="width: 300px">
              <Icon type="ios-contact-outline" slot="prefix"/>
            </Input>
          </FormItem>
          <FormItem prop="user">
            <Input v-model="password" placeholder="密码" style="width: 300px">
              <Icon type="ios-unlock-outline" slot="prefix"/>
            </Input>
          </FormItem>
          <FormItem prop="user">
            <Input v-model="captcha" placeholder="验证码" style="width: 200px"/>
            <img :src="captchaImg" @click="initCaptcha" alt="加载验证码失败"/>
          </FormItem>
          <FormItem prop="user">
            <Button type="primary" @click="login" long>提交</Button>
          </FormItem>
        </Form>
        <span style="float: right;">
          <a class="">密码找回>></a>
        </span>

      </Card>
      <Card v-show="name == 'register_card'">
        <div style="height: 500px">
          1111111
        </div>
      </Card>
    </div>
  </div>
</template>

<script>
  import Cookies from 'js-cookie'
  export default {
    name: 'Login',
    data() {
      return {
        username: "admin",
        password: "123456",
        captcha: "",
        captchaImg: "",
        name: "login_card",
      }
    },
    created() {
      this.initCaptcha(); //初始化验证码
    },
    methods: {
      //登录注册box切换
      onSelect(name) {
        this.name = name;
      },
      //登录
      login() {
        request.post("login", {
          username: this.username,
          password: this.password,
          captcha: this.captcha
        }).then(res => {
          if (res.data.code != '200') {
            this.$Notice.info({
              title: '消息',
              desc: res.data.msg
            });
          } else {
            //存储accessToken
            localStorage.setItem("accessToken", res.data.data.accessToken)
            this.$router.push({path: '/main'})
          }
        })
      },
      //初始化验证码
      initCaptcha() {
        request.get("captcha/init", {}).then(res => {
          if (res.data.code == '200') {
            this.captchaImg = "http://localhost:8083/captcha/draw/" + res.data.data.captchaId;
          }
        })
      }
    }
  }
</script>
<style scoped>
  .login_page {
    background-image: url("../assets/img/bg.jpg");
    background-repeat: no-repeat;
    background-size: cover;
    height: 100%;
    background-color: red;
    width: 100%;
    /*
    *  height:100vh == height:100%;
    * 但是当元素没有内容时候，设置height:100%，该元素不会被撑开，此时高度为0，
    * 但是设置height:100vh，该元素会被撑开屏幕高度一致。
    */
    height: 100vh;
  }

  .login_box {
    padding: 5px;
    width: 400px;
    height: auto;
    background-color: #2e527bb3;
    border-radius: 5px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .login_form {
    text-align: center;
  }
</style>
