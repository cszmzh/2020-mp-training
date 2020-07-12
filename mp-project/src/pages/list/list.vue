<template>
  <div class="listContainer">

    <div class>
      <div class="cu-bar search bg-white">
				<div class="search-form round">
					<text class="cuIcon-search"></text>
					<input @focus="InputFocus" @blur="InputBlur" :adjust-position="false" type="text" placeholder="搜索图片、文章、视频"
					 confirm-type="search"></input>
				</div>
				<div class="action">
					<input type ="button" class="cu-btn bg-green shadow-blur round" value="搜索">
				</div>
			</div>
    </div>
    
    <swiper indicator-dots indicator-color="pink" indicator-active-color="green">
      <swiper-item class="" item-id="">
        <img src="/static/tabs/home-active.png" alt="">
      </swiper-item>
  
      <swiper-item class="" item-id="">
        <img src="/static/tabs/home-active.png" alt="">
      </swiper-item>
  
      <swiper-item class="" item-id="">
        <img src="/static/tabs/home-active.png" alt="">
      </swiper-item>
  
      <swiper-item class="" item-id="">
        <img src="/static/tabs/home-active.png" alt="">
      </swiper-item>
    </swiper>
    
    <div>
      <ListTemp v-for="(item,index) in listTmp" :key="index" :item="item" :index="index"></ListTemp>
    </div>
  </div>
  </template>
  
  <script>
    import {mapState} from 'vuex'
    import ListTemp from'../list_template/list_template.vue'


    const APPID="wx3f352602226a5db2"   //开发者appid
    const SECRET="82d1109c84567a7f670e8f88d2377697"  //开发者AppSecret(小程序密钥)	,登录微信小程序平台后-》开发-》开发设置-》开发者ID（AppSecret(小程序密钥)	）生成


    export default{
      components: {ListTemp},
      beforeMount(){
        //分发action修改状态，填入action名字
        this.$store.dispatch('getList')
        wx.login({
    success: function (res) {
        console.log(res)
        //  if (res.code) {
        //     console.log('通过login接口的code换取openid');
        //      wx.request({
        //        url: 'https://api.weixin.qq.com/sns/jscode2session',
        //        data: {
        //           //填上自己的小程序唯一标识
        //          appid: APPID,
        //           //填上自己的小程序的 app secret
        //          secret: SECRET,
        //          grant_type: 'authorization_code',
        //          js_code: res.code
        //        },
        //        method: 'GET',
        //        header: { 'content-type': 'application/json'},
        //        success: function(openIdRes){
        //             console.info("登录成功返回的openId：" + openIdRes.data.openid);
        //        },
        //        fail: function(error) {
        //            console.info("获取用户openId失败");
        //            console.info(error);
        //        }
        //     })
        //   }
         }
    })
          
        
      },
      //compunted打错了
      computed:{
        //映射状态到组件,名字和state.js中一致
        ...mapState(['listTmp'])
      }
    }
  </script>
  <style>
    .listContainer swiper{
      width: 100%;
      height: 400rpx;

    }
    .listContainer swiper img{
      width: 100%;
      height: 100%;
    }
  </style>