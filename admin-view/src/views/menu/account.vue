<template>
  <div v-loading="fullloading">
    <div style="margin-top: 10px;margin-bottom: 10px;display: flex;justify-content: center;align-items: center">
      <el-input
        v-model="keywords"
        placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"/>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
      <el-button size="small" class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加用户</el-button>
    </div>
    <div style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
      <el-card
        v-loading="cardLoading[index]"
        v-for="(item,index) in hrs"
        :key="item.userId"
        style="width: 350px;margin-bottom: 20px">
        <div slot="header" class="clearfix">
          <span>{{ item.name }}</span>
          <el-button
            type="text"
            style="color: #f6061b;margin: 0px;float: right; padding: 3px 0;width: 15px;height:15px"
            icon="el-icon-delete"
            @click="deleteUser(item.userId)"/>
        </div>
        <div>
          <div style="width: 100%;display: flex;justify-content: center">
            <img src="http://bpic.588ku.com/element_pic/01/40/00/64573ce2edc0728.jpg" alt="item.username" style="width: 70px;height: 70px;border-radius: 70px">
          </div>
          <div style="margin-top: 20px">
            <div>
              <span class="user-info">用户名:{{ item.username }}</span>
            </div>
            <div>
              <span class="user-info">手机号码:{{ item.phone }}</span>
            </div>
            <div>
              <span class="user-info">电话号码:{{ item.telephone }}</span>
            </div>
            <div>
              <span class="user-info">地址:{{ item.address }}</span>
            </div>
            <div class="user-info" style="display: flex;align-items: center;margin-bottom: 3px">
              用户状态:
              <el-switch
                v-model="item.enabled"
                :key="item.userId"
                style="display: inline;margin-left: 5px"
                active-color="#13ce66"
                inactive-color="#aaaaaa"
                active-text="启用"
                inactive-text="禁用"
                @change="switchChange(item.enabled,item.userId,index)"/>
            </div>
            <div class="user-info">
              用户角色:
              <el-tag
                v-for="role in item.roles"
                :key="role.userId"
                :disable-transitions="false"
                type="success"
                size="mini"
                style="margin-right: 5px">{{ role.nameZh }}
              </el-tag>
              <el-popover
                v-loading="eploading[index]"
                :key="item.userId"
                placement="right"
                title="角色列表"
                width="200"
                trigger="click"
                @hide="updateHrRoles(item.userId,index)">
                <el-select v-model="selRoles" multiple placeholder="请选择角色">
                  <el-option
                    v-for="ar in allRoles"
                    :key="ar.id"
                    :label="ar.nameZh"
                    :value="ar.id"/>
                </el-select>
                <el-button
                  slot="reference"
                  :disabled="moreBtnState"
                  type="text"
                  icon="el-icon-more"
                  style="color: #09c0f6;padding-top: 0px"
                  @click="loadSelRoles(item.roles,index)"/>
                  <!--                <i class="el-icon-more" style="color: #09c0f6;cursor: pointer" slot="reference"
                                 @click="loadSelRoles(item.roles,index)" disabled="true"></i>-->
              </el-popover>
            </div>
            <div>
              <span class="user-info">备注:{{ item.remark }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" title="添加用户">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
        <el-form-item label="USERID" prop="userId">
          <el-input v-model="temp.userId"/>
        </el-form-item>
        <el-form-item label="USERNAME" prop="username">
          <el-input v-model="temp.username"/>
        </el-form-item>
        <el-form-item label="ENABLED" prop="enabled">
          <el-input v-model="temp.enabled"/>
        </el-form-item>
        <el-form-item label="PASSWORD" prop="password">
          <el-input v-model="temp.password"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import { postRequest } from '@/api/api'

export default {
  data() {
    return {
      keywords: '',
      fullloading: false,
      hrs: [],
      cardLoading: [],
      eploading: [],
      allRoles: [],
      moreBtnState: false,
      selRoles: [],
      selRolesBak: [],
      temp: {
        userId: '',
        username: '',
        enabled: '',
        password: ''
      },
      rules: {
        userId: [{ required: true, message: 'required', trigger: 'blur' }],
        username: [{ required: true, message: 'required', trigger: 'blur' }],
        enabled: [{ required: true, message: 'required', trigger: 'blur' }],
        password: [{ required: true, message: 'required', trigger: 'blur' }]
      },
      dialogFormVisible: false
    }
  },
  mounted: function() {
    this.initCards()
    this.loadAllRoles()
  },
  methods: {
    searchClick() {
      this.initCards()
      this.loadAllRoles()
    },
    updateHrRoles(userId, index) {
      this.moreBtnState = false
      var _this = this
      if (this.selRolesBak.length === this.selRoles.length) {
        for (var i = 0; i < this.selRoles.length; i++) {
          for (var j = 0; j < this.selRolesBak.length; j++) {
            if (this.selRoles[i] === this.selRolesBak[j]) {
              this.selRolesBak.splice(j, 1)
              break
            }
          }
        }
        if (this.selRolesBak.length === 0) {
          return
        }
      }
      this.eploading.splice(index, 1, true)
      postRequest('/menu/updateRoles', {
        userId: userId,
        roleIds: JSON.stringify(this.selRoles)
      }).then(resp => {
        _this.eploading.splice(index, 1, false)
        if (resp && resp.status === 200) {
          const data = resp.data
          if (data.status === 'success') {
            _this.refreshHr(userId, index)
          }
        }
      })
    },
    refreshHr(hrId, index) {
      var _this = this
      _this.cardLoading.splice(index, 1, true)
      request({
        url: '/menu/id/' + hrId,
        method: 'get'
      }).then(resp => {
        _this.cardLoading.splice(index, 1, false)
        _this.hrs.splice(index, 1, resp.data)
      })
    },
    loadSelRoles(hrRoles, index) {
      this.moreBtnState = true
      this.selRoles = []
      this.selRolesBak = []
      hrRoles.forEach(role => {
        this.selRoles.push(role.id)
        this.selRolesBak.push(role.id)
      })
    },
    loadAllRoles() {
      var _this = this
      request({
        url: '/menu/roles',
        method: 'get'
      }).then(resp => {
        _this.fullloading = false
        if (resp && resp.status === 200) {
          _this.allRoles = resp.data
        }
      })
    },
    switchChange(newValue, hrId, index) {
      var _this = this
      _this.cardLoading.splice(index, 1, true)
      request({
        url: '/system/hr/',
        method: 'post',
        data: {
          enabled: newValue,
          id: hrId
        }
      }).then(resp => {
        _this.cardLoading.splice(index, 1, false)
        if (resp && resp.status === 200) {
          var data = resp.data
          if (data.status === 'error') {
            _this.refreshHr(hrId, index)
          }
        } else {
          _this.refreshHr(hrId, index)
        }
      })
    },
    initCards() {
      this.fullloading = true
      var _this = this
      var searchWords
      if (this.keywords === '') {
        searchWords = 'all'
      } else {
        searchWords = this.keywords
      }
      request({
        url: '/menu/user/' + searchWords,
        method: 'get'
      }).then(resp => {
        if (resp && resp.status === 200) {
          _this.hrs = resp.data
          var length = resp.data.length
          _this.cardLoading = Array.apply(null, Array(length)).map(function(item, i) {
            return false
          })
          _this.eploading = Array.apply(null, Array(length)).map(function(item, i) {
            return false
          })
        }
      })
    },
    deleteUser(userId) {
      var _this = this
      this.fullloading = true
      request({
        url: '/menu/deleteUser/' + userId,
        method: 'get'
      }).then(resp => {
        _this.fullloading = false
        if (resp && resp.status === 200) {
          var data = resp.data
          if (data.status === 'success') {
            _this.initCards()
            _this.loadAllRoles()
          }
        }
      })
    },
    resetTemp() {
      this.temp = {
        userId: '',
        username: '',
        enabled: '',
        password: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          postRequest('/menu/createUser', this.temp).then((res) => {
            if (res.data && res.data.status === 200) {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify({
                title: '失败',
                message: '创建失败',
                type: 'fail',
                duration: 2000
              })
            }
          })
        }
      })
    }
  }
}
</script>
<style>
  .user-info {
    font-size: 12px;
    color: #09c0f6;
  }
</style>
