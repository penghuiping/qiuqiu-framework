<template>
  <el-row align="middle" class="loginFormWrapper" justify="center" type="flex">
    <el-form ref="form" :rules="rules" :model="loginForm" class="loginForm" label-width="80px" >
      <el-form-item>
        <label class="loginFormTitle">登入后台管理系统</label>
      </el-form-item>
      <el-form-item label="用户名:"  prop="username">
        <el-input v-model=loginForm.username></el-input>
      </el-form-item>
      <el-form-item label="密码:"  prop="password">
        <el-input v-model=loginForm.password show-password></el-input>
      </el-form-item>
      <el-row justify="space-between" type="flex">
        <el-form-item>
          <el-checkbox v-model="loginForm.checked" id="rememberPwd">记住密码</el-checkbox>
        </el-form-item>
        <el-form-item>
          <a @click="forgetPwd()" id="forgetPwdBtn">忘记密码?</a>
        </el-form-item>
      </el-row>
      <el-form-item>
        <el-button id="loginBtn" type="primary" @click="login()">登入</el-button>
      </el-form-item>
      <el-form-item>
        <label class="account">管理员账号:admin 密码:123456</label>
        <label class="account">普通账号:jack 密码:123456</label>
      </el-form-item>
    </el-form>
  </el-row>
</template>

<script lang="ts">
import { Component } from 'vue-property-decorator'
import { BaseVue } from '@/BaseVue'
import { UserApi } from '@/api/user'
import { ElForm } from 'element-ui/types/form'

@Component
export default class Login extends BaseVue {
  private loginForm = {
    username: '',
    password: '',
    checked: false
  }

  private rules = {
    username: [
      { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'change' },
      { min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'change' }
    ]
  }

  login () {
    (this.$refs.form as ElForm).validate(async valid => {
      if (valid) {
        const loading = this.showLoading()
        const res = await UserApi.login(this.loginForm.username, this.loginForm.password)
        this.closeLoading(loading)
        const jsonResponse = res.data
        if (jsonResponse.code === '0') {
          const token = res.data.data.token
          const expireTime = res.data.data.expireTime
          console.log(expireTime)
          this.$store.commit('login', {
            token: token,
            expireTime: expireTime
          })
          this.routePage('/home')
        } else {
          this.$message.error(jsonResponse.message)
        }
      }
    })
  }

  forgetPwd () {
    console.log('forgetPwd')
  }
}
</script>

<style lang="scss" scoped>
//PC 端
@media (min-width: 992px) {
  .loginFormWrapper {
    width: 100vw;
    height: 100vh;
    background-color: black;
    text-align: center;
  }

  a,label{
    color: white;
  }

  .loginForm {
    width: 500px;
    height: 500px;
  }

  .loginFormTitle {
    font-size: 30px;
    color: white;
  }

  #loginBtn {
    width: 100%;
  }

  .account {
    color: yellow !important;
    display: block;
  }
}

//移动端
@media (max-width: 991px) {
  .loginFormWrapper {
    width: 100vw;
    height: 100vh;
    background-color: black;
    text-align: center;
  }

  a,label{
    color: white;
  }

  .loginForm {
    width: 90%;
    height: 500px;
  }

  .loginFormTitle {
    font-size: 20px;
    color: white;
  }

  #loginBtn {
    width: 100%;
  }

  .account {
    color: yellow !important;
    display: block;
  }

  #forgetPwdBtn,#rememberPwd{
    font-size: 5px !important;
  }
}

</style>

<style scoped>
.el-form-item >>> label {
  color: white;
}
</style>
