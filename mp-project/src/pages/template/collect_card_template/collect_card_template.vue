<template>
    <block>
        <view class="w-sold ">
            <!-- 买家信息 -->
            <view class="padding w-sold-userinfo " style="width:100%">
                <view class="cu-avatar round" :style="['background-image:url('+ item.subAvatar +');']"></view>
                <text class="text-center" style="font-size: 25rpx;margin-left: 20rpx;">{{item.subUser}}</text>
            </view>
            <!-- 商品信息 -->
            <view class="w-content flex" @tap="toDetail">
                <view class="flex-sub">
                    <image :src="item.prodImage" mode="widthFix" />
                </view>
                <view class="flex-sub flex w-information">
                    <view class=" w-infor1">
                        <text class="" style="font-size: 25rpx;">{{item.prodName}}</text>
                    </view>
                    <view class="text-df text-right w-infor2">
                        <text class="text-gray" style="margin-right:20rpx;font-size:90%">
                            实付金额:
                        </text>
                        <text class="text-price text-red">{{item.nowPrice}}</text>
                    </view>
                </view>
            </view>
            <!-- 取消收藏 -->
            <view class="w-appendix " style="width:100%">
                <button @tap="cancelCollect" class="unlike" type="text"
                    style="font-size: 24rpx;background-color: white;">{{isCollect==true?'取消收藏':'收藏'}}</button>
            </view>
        </view>
    </block>
</template>

<script>
    export default {
        props: [
            'item', 'index'
        ],
        data() {
            return {
                isCollect:true
            }
        },
        //生命周期
        onLoad() {
        },
        beforeMount() {

            this.isCollect=true
        },
        methods: {
            cancelCollect() {
                wx.login({
                    timeout: 10000,
                    success: (result) => {
                        this.code = result.code;
                    },
                    fail: () => { },
                    complete: () => {
                        //完成code获取后再更新数据
                        this.$fly.interceptors.request.use((config, promise) => {
                            config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                            return config;
                        })
                        this.$fly.post(this.globalData.backgroundURL + 'click/collect', {
                            code: this.code,
                            productId: this.item.prodId
                        })
                            .then(function (response) {
                                wx.showToast({
                                    title: this.isCollect ? '取消收藏' : "收藏成功",
                                    icon: 'success',
                                    image: '',
                                    duration: 1500,
                                    mask: false,
                                    success: (result) => {
                                        this.isCollect = !this.isCollect;
                                    },
                                    fail: () => { },
                                    complete: () => {
                                        const pages = getCurrentPages();
                                        const perpage = pages[pages.length - 1];
                                        perpage.onLoad();
                                        this.isCollect=true;
                                    }
                                });
                            }.bind(this))
                            .catch(function (error) {
                                console.log(error);
                            });
                    }
                });
            },
            toDetail() {
                //跳转到详情页+传递参数index
                wx.navigateTo({
                    url: '/pages/show_product/main?prodId=' + this.item.prodId,
                    success: (result) => {

                    },
                    fail: () => { },
                    complete: () => { }
                }).bind(this);
            }
        }

    }
</script>
<style>
    page {
        background-color: #eeeeef;
    }

    .w-sold {
        background-color: white;
        width: 90%;
        margin: 20rpx auto;
        border-radius: 30rpx;
    }

    /* 头部 */
    .w-sold-userinfo {
        width: 100%;
        height: 90rpx;
    }

    .w-payover {
        position: absolute;
        right: 20rpx;
    }

    /* 中间 */
    .w-content {
        width: 100%;
        padding: 15rpx;
    }

    .w-content>view {
        width: 100%;
        padding: 15rpx 15rpx;
    }

    .w-content image {
        width: 100%;
        border-radius: 20rpx;
    }

    .w-content>view>text {

        overflow: hidden;
        text-overflow: ellipsis;
    }

    .w-information {
        width: 100%;
        flex-direction: column;
        flex-wrap: wrap;
    }

    .w-infor1 {
        width: 100%;
        flex: 3;
    }

    .w-infor2 {
        width: 100%;
        flex: 1 over
    }

    /* 底部 */
    .w-appendix {
        position: relative;
        width: 100%;
        height: 70rpx;
        display: flex;
        padding: 10rpx 0 30rpx;
        align-items: center;
    }

    .w-appendix>image {
        margin: 10rpx 20rpx 10rpx 40rpx;
        width: 60rpx;
    }

    .w-appendix>text {
        line-height: 50rpx;
    }

    .w-else {
        position: absolute;
        right: 20rpx;
        bottom: 19rpx;
        border: 2rpx solid #8a8a8a;
        border-radius: 30rpx;
    }

    .unlike {
        position: relative;
        margin-right: 30rpx;
    }
</style>