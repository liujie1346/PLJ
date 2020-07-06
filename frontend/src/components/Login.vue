<template>
  <div>
    <Form ref="form" label="登录页面">
      <FormItem prop="user">
        <Input v-model="username" placeholder="用户名" style="width: 300px"/>
      </FormItem>
      <FormItem prop="user">
        <Input v-model="password" placeholder="密码" style="width: 300px"/>
      </FormItem>
      <FormItem prop="user">
        <Input v-model="captchaImg" placeholder="验证码" style="width: 200px"/>
        <img :src="captchaImg" @click="img" alt="加载验证码失败"/>
      </FormItem>
      <FormItem prop="user">
        <Button type="primary" @click="login">提交</Button>
      </FormItem>
    </Form>
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Login',
    data() {
      return {
        username: "admin",
        password: "123456",
        captchaImg: "",
      }
    },
    created() {
      this.img();
    },
    methods: {
      login() {
        axios
          .post("http://localhost:8083/login?username=" + this.username + "&password=" + this.password)
          .then(response => {
            console.log(response);
            if (response.status == '200') {
              if(response.data.status != '200'){
                this.$Notice.info({
                  title: '消息',
                  desc: response.data.msg
                });
              }else {
                //存储accessToken
                localStorage.setItem("accessToken",response.data.accessToken)
                this.$router.push({path: '/main'})
              }
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      },
      img() {
        axios({
          url: 'http://localhost:8083/captcha/init',
          method: 'get',
        }).then(response => {
          if (response.status == '200') {
            this.captchaImg = "http://localhost:8083/captcha/draw/" + response.data.captchaId;
          }
        });

      }
    }
  }
</script>
<style scoped>

</style>
