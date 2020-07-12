<template>
  <div class="indexContainer">
    <img v-if="isShow" class="index_img" :src="userInfo.avatarUrl">
    <Button class="btn" v-else open-type=getUserInfo @getuserinfo="getUserInfo" style="border-radius: 60%;">点击进入</Button>
    <p class="userName">Hola！欢迎使用天理跳蚤市场 {{userInfo.nickName}}</p>
    <div @tap="toDetail" class="goStudy" v-if="userInfo.nickName!=undefined">
      <p>点击进入</p>
    </div>
  </div>
</template>

<script>
  export default{
    data(){
      return{
        userInfo:{},
        isShow: false, //用户未授权
        code: ''
      }
    },
    //生命周期
    onLoad(){
      //console.log('--onload--');
    },
    beforeMount(){
      //console.log('--onload--');
    },
    // mounted(){
    //   console.log('--onload--');
    //   
    // },
    methods:{
      handleGetUserInfo(){
        //登录
        wx.login({
              timeout:10000,
              success: (result) => {
                this.code=result.code;
              },
              fail: () => {},
              complete: () => { //完成code获取后再更新数据
                wx.getUserInfo({
                  success: (result) => {
                  //更新data中的数据
                  this.userInfo=result.userInfo;
                  //新增用户
                  this.createUser();
                  },
                  fail: () => {console.log("获取失败")},
                  complete: () => {}
                });
              }
        });
          
      },
      getUserInfo(data){
        //判断用户是否授权
        if(data.mp.detail.rawData){
          //用户授权
          this.handleGetUserInfo();
          this.isShow=true;
          this.toDetail();
        }
      },
      createUser(){
        //存储用户信息到数据库中
        this.$fly.interceptors.request.use((config,promise)=>{
          config.headers["Content-Type"]="application/x-www-form-urlencoded";
          return config;
        })

        this.$fly.post(this.globalData.backgroundURL+'user/create',{
              nickName: this.userInfo.nickName,
              avatarUrl: this.userInfo.avatarUrl,
              gender: this.userInfo.gender,
              country: this.userInfo.country,
              province: this.userInfo.province,
              city: this.userInfo.city,
              code: this.code

            })
            .then(function (response) {
            })
            .catch(function (error) {
              console.log(error);
            });

      },
      toDetail(){
        wx.switchTab({
          url: '/pages/shop_index/main',  //注意路径
          success: (result) => {
          },
          fail: () => {},
          complete: () => {}
        });
          
      }
    }
  }
</script>
<style>
  page{
    background:white;
  }
  .indexContainer{
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .index_img{
    width:200rpx;
    height:200rpx;
    border-radius:100rpx;
    margin: 100rpx;
  }
  .userName{
    font-size: 40rpx;
    font-weight: bold;
    margin:100rpx 0;
  }
  .goStudy{
    width:300rpx;
    height: 80rpx;
    border:1rpx solid #eee;
    font-size: 24rpx;
    line-height: 80rpx;
    text-align: center;
    border-radius: 10rpx;
  }
  .btn{
    width:300rpx;
    height:300rpx;
    border:150rpx;
    margin: 100rpx 0;
    line-height: 300rpx;
    text-align: center;
  }
</style>