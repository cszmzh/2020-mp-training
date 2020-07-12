<template>
    <block>
        <view class="w-sold ">
            <!-- 买家信息 -->
            <view class="padding w-sold-userinfo " style="width:100%">
                <text v-if="title=='我的发布'" class="text-center"
                    style="font-size: 25rpx;margin-left: 20rpx;">超赞:{{item.totalLike}}&nbsp;收藏:{{item.totalCollect}}</text>
                <text v-if="title=='我的卖出'" class="text-center"
                    style="font-size: 25rpx;margin-left: 20rpx;">状态：{{statusListForSale[item.status]}}&nbsp;</text>
                <text v-if="title=='我的买到'" class="text-center"
                    style="font-size: 25rpx;margin-left: 20rpx;">状态：{{statusListForBuy[item.status]}}&nbsp;</text>
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
                        <text v-if="title=='我的发布'" class="text-gray" style="margin-right:20rpx;font-size:90%">
                            价格:
                        </text>
                        <text v-if="title=='我的卖出'" class="text-gray" style="margin-right:20rpx;font-size:90%">
                            成交价:
                        </text>
                        <text v-if="title=='我的买到'" class="text-gray" style="margin-right:20rpx;font-size:90%">
                            实付金额:
                        </text>
                        <text class="text-price text-red">{{item.nowPrice}}</text>
                    </view>
                </view>
            </view>
            <!-- 取消收藏 -->
            <view class="w-appendix " style="width:100%;display: flex;justify-content:flex-end">
                <button v-if="item.status==1" @tap="deleteSubmit(item.prodId)" type="text"
                    style="font-size: 24rpx;background-color: white;">删除发布</button>
                <button v-if="title=='我的发布'" @tap="updateProduct(item.prodId)" type="text"
                    style="font-size: 24rpx;background-color: white;">更新商品</button>
                <button v-if="title=='我的卖出'" @tap="toRecord" type="text"
                    style="font-size: 24rpx;background-color: white;">查看订单</button>
                <button v-if="title=='我的卖出'&&item.status==2" @tap="sendGoods" type="text"
                    style="font-size: 24rpx;background-color: white;">发货</button>
                <button v-if="title=='我的买到'" @tap="toRecord" type="text"
                    style="font-size: 24rpx;background-color: white;">查看详情</button>
                <button v-if="title=='我的买到'&&item.status==3" @tap="takeGoods" type="text"
                    style="font-size: 24rpx;background-color: white;">收货</button>
            </view>
        </view>
    </block>
</template>

<script>
    export default {
        props: [
            'item', 'index', 'title'
        ],
        data() {
            return {
                isCollect: true,
                statusListForSale: ['', '商品发布中', '有买家下单', '您已发货，等待买家收货', '买家取消下单', '您已取消交易', '交易成功', '取消发布'],
                statusListForBuy: ['', '', '您已下单', '卖家已发货', '您已取消下单', '卖家取消交易', '交易成功', '']
            }
        },
        //生命周期
        onLoad() {

        },
        beforeMount() {

        },
        methods: {
            toDetail() {
                //跳转到详情页+传递参数index
                wx.navigateTo({
                    url: '/pages/show_product/main?prodId=' + this.item.prodId,
                    success: (result) => {

                    },
                    fail: () => { },
                    complete: () => { }
                }).bind(this);
            },
            updateProduct(prodId){
                wx.navigateTo({
                    url: '/pages/update_page/main?prodId='+prodId,
                    success: (result) => {
                        
                    },
                    fail: () => {},
                    complete: () => {}
                });
                  
            },
            deleteSubmit(prodId) {
                //提示用户是否真的要删除
                wx.showModal({
                    title: '提示',
                    content: '确定要删除发布该商品吗？',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                            this.$fly.interceptors.request.use((config, promise) => {
                                config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                                return config;
                            })
                            this.$fly.post(this.globalData.backgroundURL + 'products/delete', {
                                prodId: prodId
                            })
                                .then(function (response) {
                                    wx.showToast({
                                        title: '删除成功',
                                        icon: 'success',
                                        image: '',
                                        duration: 1500,
                                        mask: true,
                                        success: (result) => {
                                            //延迟5秒跳转
                                            var that = this;
                                            setTimeout(function () {
                                                wx.switchTab({
                                                    url: '/pages/home/main',
                                                    success: (result) => {
                                                    },
                                                    fail: () => { },
                                                    complete: () => {
                                                    }
                                                })
                                            }, 1500);
                                        },
                                        fail: () => { },
                                        complete: () => { }
                                    });
                                }.bind(this))
                                .catch(function (error) {
                                    wx.showToast({
                                        title: '删除失败，请检查网络',
                                        icon: 'none',
                                        image: '',
                                        duration: 3000,
                                        mask: false,
                                        success: (result) => {

                                        },
                                        fail: () => { },
                                        complete: () => { }
                                    });

                                    console.log(error);
                                });
                        }
                    },
                    fail: () => { },
                    complete: () => { }
                });

            },
            toRecord() {
                wx.navigateTo({
                    url: '/pages/product_record/main?prodId=' + this.item.prodId,
                    success: (result) => {

                    },
                    fail: () => { },
                    complete: () => { }
                });

            },
            sendGoods() {
                //提示用户是否真的要删除
                wx.showModal({
                    title: '提示',
                    content: '确定发货吗？建议发货后用短信通知买家哦~',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                            wx.login({
                                timeout: 10000,
                                success: (result) => {
                                    this.$fly.interceptors.request.use((config, promise) => {
                                        config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                                        return config;
                                    })
                                    this.$fly.post(this.globalData.backgroundURL + 'buy/sendProduct', {
                                        code: result.code,
                                        prodId: this.item.prodId
                                    })
                                        .then(function (response) {
                                            wx.showToast({
                                                title: '发货成功！',
                                                icon: 'success',
                                                image: '',
                                                duration: 1500,
                                                mask: true,
                                                success: (result) => {
                                                    //延迟5秒跳转
                                                    var that = this;
                                                    setTimeout(function () {
                                                        wx.switchTab({
                                                            url: '/pages/home/main',
                                                            success: (result) => {
                                                            },
                                                            fail: () => { },
                                                            complete: () => {
                                                            }
                                                        })
                                                    }, 1500);
                                                },
                                                fail: () => { },
                                                complete: () => { }
                                            });
                                        }.bind(this))
                                        .catch(function (error) {
                                            wx.showToast({
                                                title: '操作失败，请检查网络',
                                                icon: 'none',
                                                image: '',
                                                duration: 3000,
                                                mask: false,
                                                success: (result) => {

                                                },
                                                fail: () => { },
                                                complete: () => { }
                                            });

                                            console.log(error);
                                        });
                                },
                                fail: () => { },
                                complete: () => { }
                            });


                        }
                    },
                    fail: () => { },
                    complete: () => { }
                });
            },
            takeGoods() {
                //提示用户是否真的要删除
                wx.showModal({
                    title: '提示',
                    content: '确定收货吗？请仔细检查商品，谨防诈骗~',
                    showCancel: true,
                    cancelText: '取消',
                    cancelColor: '#000000',
                    confirmText: '确定',
                    confirmColor: '#3CC51F',
                    success: (result) => {
                        if (result.confirm) {
                            wx.login({
                                timeout: 10000,
                                success: (result) => {
                                    this.$fly.interceptors.request.use((config, promise) => {
                                        config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                                        return config;
                                    })
                                    this.$fly.post(this.globalData.backgroundURL + 'buy/finish', {
                                        code: result.code,
                                        prodId: this.item.prodId
                                    })
                                        .then(function (response) {
                                            wx.showToast({
                                                title: '收货成功！',
                                                icon: 'success',
                                                image: '',
                                                duration: 1500,
                                                mask: true,
                                                success: (result) => {
                                                    //延迟5秒跳转
                                                    var that = this;
                                                    setTimeout(function () {
                                                        wx.switchTab({
                                                            url: '/pages/home/main',
                                                            success: (result) => {
                                                            },
                                                            fail: () => { },
                                                            complete: () => {
                                                            }
                                                        })
                                                    }, 1500);
                                                },
                                                fail: () => { },
                                                complete: () => { }
                                            });
                                        }.bind(this))
                                        .catch(function (error) {
                                            wx.showToast({
                                                title: '操作失败，请检查网络',
                                                icon: 'none',
                                                image: '',
                                                duration: 3000,
                                                mask: false,
                                                success: (result) => {

                                                },
                                                fail: () => { },
                                                complete: () => { }
                                            });

                                            console.log(error);
                                        });
                                },
                                fail: () => { },
                                complete: () => { }
                            });


                        }
                    },
                    fail: () => { },
                    complete: () => { }
                });
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

    button {
        margin: 10rpx;
    }
</style>