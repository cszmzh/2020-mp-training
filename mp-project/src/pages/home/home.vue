<template>
    <div class="container">
        <view class="cu-avatar xl round margin-left" :style="['background-image:url('+ userInfo.avatarUrl +' );']"
            id="head-img"></view>
        <view>
            <text class="name">{{userInfo.nickname}}</text>
        </view>
        </view>

        <!--宫格列表-->
        <view class="cu-bar bg-white solid-bottom margin-top" style="width: 95%;">
            <view class="action">
                <text class="cuIcon-title text-black "></text> 我的功能
            </view>
        </view>
        <view :class="['cu-list', 'grid' ,'col-'+ gridCol + gridBorder?'':'no-border']" style="width: 95%;">
            <view class="cu-item" @tap="toDetail(item.name)" v-for="(item,index) in iconList" :key="index"
                v-if="index<gridCol*2" style="width: 25%;">
                <view :class="['cuIcon-' + item.icon, 'text-' + item.color]">
                    <view class="cu-tag badge" v-if="item.badge!=0">
                        <block v-if="item.badge!=1"></block>
                    </view>
                </view>
                <text>{{item.name}}</text>
            </view>
        </view>


        <view class="cu-bar bg-white solid-bottom margin-top" style="width: 95%;" @tap="showModal">
            <view class="action">
                关于我们
            </view>
            <view class="action">
                <text class="text-grey text-sm">点击我看看~</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 95%;">
            <view class="action">
                技术交流与BUG反馈
            </view>
            <view class="action">
                <text class="text-grey text-sm">515code.com</text>
            </view>
        </view>

        <!--模态提示框-->
          <view :class="['cu-modal', modalName=='Modal'?'show':'']">
            <view class="cu-dialog">
              <view class="cu-bar bg-white justify-end">
                <view class="content">About us</view>
                <view class="action" @tap="hideModal">
                  <text class="cuIcon-close text-red"></text>
                </view>
              </view>
              <view class="padding-xl">
                开发者团队：<br>张钊铭&nbsp;吴梦歌&nbsp;潘韵竹&nbsp;吴尚&nbsp;张若萱<br>
                目前版本：V1.0.1内测
              </view>
            </view>
          </view>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                modalName: 'hide',
                userInfo: {},
                StatusBar: this.globalData.StatusBar,
                CustomBar: this.globalData.CustomBar,
                iconList: [
                    {
                        icon: 'picfill',
                        color: 'yellow',
                        badge: 0,
                        name: '我的发布'
                    }, {
                        icon: 'cardboardfill',
                        color: 'red',
                        badge: 120,
                        name: '我的卖出'
                    }, {
                        icon: 'recordfill',
                        color: 'orange',
                        badge: 1,
                        name: '我的买到'
                    }],
                gridCol: 3,
                skin: false
            }
        },
        //生命周期
        onLoad() {
            this.handleGetUserInfo()
        },
        beforeMount() {
        },
        methods: {
            toDetail(title) {
                wx.navigateTo({
                    url: '/pages/home_function/main?title=' + title,
                    success: (result) => {

                    },
                    fail: () => { },
                    complete: () => { }
                });
            },
            handleGetUserInfo() {
                //登录
                wx.login({
                    timeout: 10000,
                    success: (result) => {
                        this.code = result.code;
                    },
                    fail: () => { },
                    complete: () => { //完成code获取后再更新数据
                        this.$fly.interceptors.request.use((config, promise) => {
                            config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                            return config;
                        })
                        this.$fly.post(this.globalData.backgroundURL + 'user/getUser', {
                            code: this.code
                        }).then(function (response) {
                            this.userInfo = response.data.data
                        }.bind(this)).catch(function (error) {
                            console.log(error);
                        });
                    }
                });
            },
            showModal() {
                this.modalName = 'Modal'
            },
            hideModal() {
                this.modalName = 'hide'
            },
        }
    }
</script>
<style>
    .page {
        height: 100Vh;
        width: 100vw;
    }

    .page.show {
        overflow: hidden;
    }

    .switch-sex::after {
        content: "\e716";
    }

    .switch-sex::before {
        content: "\e7a9";
    }

    .switch-music::after {
        content: "\e66a";
    }

    .switch-music::before {
        content: "\e6db";
    }


    .container {
        padding-top: 100rpx;
        padding-bottom: 80rpx;
        display: flex;
        flex-direction: column;
        height: 10%;
        align-items: center;
    }

    #head-img {
        height: 175rpx;
        width: 175rpx;
    }

    .somewords {
        margin-top: 10px;
        text-align: center;
        justify-content: center;
        display: inline-block;
        height: 20px;
        font-size: 10px;
    }

    .name {
        margin-top: 10px;
        text-align: center;
        justify-content: center;
        display: inline-block;
        height: 20px;
        font-size: 15px;
    }

    .round {
        border-radius: 5000rpx;
    }

    .margin-left {
        margin-left: 30rpx;
    }

    .cu-avatar {
        font-variant: small-caps;
        margin: 0;
        padding: 0;
        display: inline-flex;
        text-align: center;
        justify-content: center;
        align-items: center;
        background-color: #ccc;
        color: var(--white);
        white-space: nowrap;
        position: relative;
        width: 64rpx;
        height: 64rpx;
        background-size: cover;
        background-position: center;
        vertical-align: middle;
        font-size: 1.5em;
    }

    .cu-avatar.xl {
        width: 128rpx;
        height: 128rpx;
        font-size: 2.5em;
    }
</style>