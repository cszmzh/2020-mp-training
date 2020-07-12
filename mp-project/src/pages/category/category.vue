<template>
  <div>
    <view class='cu-custom'>
      <view class="cu-bar fixed bg-shadeTop"
        :style="['height:'+this.CustomBar+'px','padding-top:'+this.StatusBar+'px']">

      </view>
    </view>
    <swiper class="screen-swiper round-dot" indicator-dots="true" circular="true" autoplay="true" interval="5000"
      duration="500">
      <swiper-item v-for="(item,index) in imgUrls" :key="index">
        <image :src="item" mode='aspectFill'></image>
      </swiper-item>
    </swiper>

    <!--左侧-->
    <view class="VerticalBox">
      <scroll-view class="VerticalNav nav" scroll-y scroll-with-animation :scroll-top="VerticalNavTop"
        style="height:calc(100vh - 375rpx)">
        <view :class="['cu-item', index==TabCur?'text-blue cur':'']" v-for="(item,index) in list" :key="index"
          @tap='tabSelect' :data-id="index">
          {{item.name}}
        </view>
      </scroll-view>

      <!--右侧-->
      <scroll-view class="VerticalMain" scroll-y scroll-with-animation style="height:calc(100vh - 375rpx)"
        :scroll-into-view="'main-'+MainCur" @scroll="VerticalMain">
        <view class="padding-top padding-lr" v-for="(item,index) in list" :key="index" :id="'main-'+index">

          <view class='cu-bar solid-bottom bg-white'>
            <view class='action'>
              <text class='cuIcon-title text-blue'></text>{{item.name}}</view>
          </view>

          <view class="cu-list menu-avatar">
            <view class="cu-item" @tap="toDetail(detailItem.id,detailItem.mininame,detailItem.description)"
              v-for="(detailItem,detailIndex) in categoryList " v-if="detailItem.name==item.name" :key="detailIndex"
              :id="detailItem.id">
              <view class="cu-avatar round lg" :style="['background-image:url(' + detailItem.image  + ');']"></view>
              <view class="content" style="width: calc(100% - 60rpx - 120rpx - 20rpx);">
                <view class="text-black">{{detailItem.mininame}}</view>
                <view class="text-gray text-sm flex">
                  <text class="text-cut">
                    <text class="cuIcon-infofill text-blue  margin-right-xs"></text>{{detailItem.description}}
                  </text>
                </view>
              </view>
              <view class="action">
                <!-- <view class="text-grey text-xs">{{item.updateTime}}</view>
                      <view class="cu-tag round bg-grey sm">5</view> -->
              </view>
            </view>
          </view>
        </view>
        <view style="height:150rpx;"></view>
      </scroll-view>

    </view>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        imgUrls: [
          'https://yun.515code.com/static/miniprogram/6.jpg',
          'https://yun.515code.com/static/miniprogram/7.jpg',
          'https://yun.515code.com/blog/source/photos/%E6%B7%B1%E5%9C%B3%E5%8D%97%E5%B1%B1%E5%8C%BA-%E7%A7%91%E5%85%B4%E7%A7%91%E5%AD%A6%E5%9B%AD%E4%B8%80%E8%A7%92.jpg',
          'https://www.515code.com/about/about.jpeg',
          'https://yun.515code.com/static/miniprogram/4.jpg'
        ],
        StatusBar: this.globalData.StatusBar,
        CustomBar: this.globalData.CustomBar,
        Custom: this.globalData.Custom,
        TabCur: 0,
        MainCur: 0,
        VerticalNavTop: 0,
        list: [{ id: 0, name: "学习" }, { id: 1, name: "生活" }, { id: 2, name: "数码" }, { id: 3, name: "二手书" }, { id: 4, name: "运动" }, { id: 5, name: "其他" }],
        load: true,
        categoryList: {}
      }
    },
    //生命周期
    onLoad() {
    },
    beforeMount() {
      this.getProductList();
      // let list = [{}];
      // for (let i = 0; i < 26; i++) {
      //     list[i] = {};
      //     list[i].name = String.fromCharCode(65 + i);
      //     list[i].id = i;
      // }
      // this.list = list,
      // this.listCur = list[0]
    }, methods: {
      getProductList() {
        this.$fly.interceptors.request.use((config, promise) => {
          config.headers["Content-Type"] = "application/x-www-form-urlencoded";
          return config;
        })
        this.$fly.get(this.globalData.backgroundURL + 'category/getAll', {

        })
          .then(function (response) {
            this.categoryList = response.data.data;
          }.bind(this))
          .catch(function (error) {
            console.log(error);
          });
      },
      toDetail(categoryId, name, description) {
        //跳转到详情页+传递参数index
        wx.navigateTo({
          url: '/pages/category_product/main?categoryId=' + categoryId + '&categoryName=' + name + '&categoryDescription=' + description,
          success: (result) => {
          },
          fail: () => { },
          complete: () => { }
        });
      }
      ,
      tabSelect(e) {
        this.TabCur = e.currentTarget.dataset.id,
          this.MainCur = e.currentTarget.dataset.id,
          this.VerticalNavTop = (e.currentTarget.dataset.id - 1) * 50
      },
      //右侧滑动触发事件
      VerticalMain(e) {
        let that = this;
        let list = this.list;
        let tabHeight = 0;
        if (this.load) {
          for (let i = 0; i < list.length; i++) {
            let view = wx.createSelectorQuery().select("#main-" + list[i].id);
            view.fields({
              size: true
            }, data => {
              list[i].top = tabHeight;
              tabHeight = tabHeight + data.height;
              list[i].bottom = tabHeight;
            }).exec();
          }
          this.load = false,
            this.list = list
        }
        let scrollTop = e.mp.detail.scrollTop + 20;
        for (let i = 0; i < list.length; i++) {
          if (scrollTop > list[i].top && scrollTop < list[i].bottom) {
            this.VerticalNavTop = (this.list[i].id - 1) * 50,
              this.TabCur = this.list[i].id
            return false
          }
        }
      }
    }

  }
</script>
<style>
  .VerticalNav.nav {
    width: 200rpx;
    white-space: initial;
  }

  .VerticalNav.nav .cu-item {
    width: 100%;
    text-align: center;
    background-color: #fff;
    margin: 0;
    border: none;
    height: 50px;
    position: relative;
  }

  .VerticalNav.nav .cu-item.cur {
    background-color: #f1f1f1;
  }

  .VerticalNav.nav .cu-item.cur::after {
    content: "";
    width: 8rpx;
    height: 30rpx;
    border-radius: 10rpx 0 0 10rpx;
    position: absolute;
    background-color: currentColor;
    top: 0;
    right: 0rpx;
    bottom: 0;
    margin: auto;
  }

  .VerticalBox {
    display: flex;
  }

  .VerticalMain {
    background-color: #f1f1f1;
  }
</style>