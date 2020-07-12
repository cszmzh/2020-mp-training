<template>
    <div>
        <view class="cu-card article no-card">
            <view class="cu-item shadow">
                <view class="title">
                    <view class="text-cut">{{prodName}}</view>
                </view>
                <view class="content">
                    <image :src="prodImage" mode="aspectFill"></image>
                    <view class="desc">
                        <view class="text-content" style="color: red;">现价：{{nowPrice}}元<p
                                style="font-size: 24rpx;text-decoration: line-through;color:gray;">{{prePrice}}元</p>
                        </view>

                        <view>
                            <view class="cu-tag bg-red light sm round">卖家：{{subUser}}</view>
                            <view class="cu-tag bg-green light sm round">金牌卖家</view>
                        </view>
                    </view>
                </view>
            </view>
        </view>
        <form>
            <view class="cu-form-group margin-top">
                <view class="title">收货人</view>
                <input v-model="buyerName" placeholder="请输入你的尊姓大名~"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">手机号</view>
                <input v-model="phoneNumber" placeholder="方便卖家联系哦"></input>
                <view class="cu-capsule radius">
                    <view class="cu-tag bg-blue">
                        +86
                    </view>
                    <view class="cu-tag line-blue">
                        中国大陆
                    </view>
                </view>
            </view>
            <view class="cu-form-group">
                <view class="title">收货地区</view>
                <picker mode="region" @change="RegionChange" :value="region" :custom-item="customItem">
                    <view class="picker">
                        {{region[0]}}，{{region[1]}}，{{region[2]}}
                    </view>
                </picker>
            </view>
            <view class="cu-form-group margin-top">
                <view class="title">详细地址</view>
                <input v-model="detailAddress" placeholder="具体街道，小区，门牌号~"></input>
            </view>
        </form>

        <view class="padding flex flex-direction" @tap="submitProduct">
            <button @tap="placeOrder" class="cu-btn bg-blue margin-tb-sm lg">确认下单</button>
        </view>

    </div>
</template>
<script>

    export default {
        data() {
            return {
                StatusBar: this.globalData.StatusBar,
                CustomBar: this.globalData.CustomBar,
                index: null,
                time: '12:01',
                date: '2018-12-25',
                region: ['天津市', '天津市', '西青区'],
                modalName: null,
                textareaAValue: '',
                textareaBValue: '',
                //商品信息
                prodName: '',
                prePrice: '',
                nowPrice: '',
                subUser: '',
                prodId: 0,
                prodImage: '',

                //购买信息
                buyerName: '',
                phoneNumber: '',
                detailAddress: ''

            }
        },
        beforeMount() {

            //接收参数
            this.prodImage = decodeURIComponent(this.$mp.query.prodImage)
            this.prodName = this.$mp.query.prodName,
                this.prePrice = this.$mp.query.prePrice,
                this.nowPrice = this.$mp.query.nowPrice,
                this.subUser = this.$mp.query.subUser,
                this.prodId = this.$mp.query.prodId

        },
        //compunted打错了
        computed: {
            //映射状态到组件,名字和state.js中一致

        },
        methods: {
            RegionChange: function (e) {
                console.log(e)
                this.region = e.mp.detail.value
            },
            textareaAInput(e) {
                this.textareaAValue = e.mp.detail.value
            },
            textareaBInput(e) {
                this.textareaBValue = e.mp.detail.value
            },
            placeOrder() {

                wx.showLoading({
                    title: '正在提交订单',
                    mask: true
                });

                //检验参数
                var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
                if (this.buyerName == '' || !myreg.test(this.phoneNumber) || this.detailAddress == '') {
                    wx.hideLoading();
                    wx.showToast({
                        title: '请正确填写所有内容',
                        icon: 'none',
                        image: '',
                        duration: 1500,
                        mask: false,
                        success: (result) => {

                        },
                        fail: () => { },
                        complete: () => { }
                    });
                    return;
                }

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
                        this.$fly.post(this.globalData.backgroundURL + 'buy/newOrder', {
                            code: this.code,
                            productId: this.prodId,
                            contact: this.phoneNumber,
                            address: this.region[0] + this.region[1] + this.region[2] + this.detailAddress,
                            buyerName: this.buyerName
                        })
                            .then(function (response) {
                                
                                if (response.data.code == 2) {
                                    wx.hideLoading();
                                    wx.showToast({
                                        title: '商品已被别人拍下',
                                        icon: 'none',
                                        image: '',
                                        duration: 3000,
                                        mask: true,
                                        success: (result) => {
                                            //延迟3秒跳转
                                            setTimeout(function () {
                                                wx.switchTab({
                                                    url: '/pages/shop_index/main',
                                                    success: (result) => {
                                                        let page = getCurrentPages().pop();
                                                        if (page == undefined || page == null) return;
                                                        page.onLoad();
                                                    },
                                                    fail: () => { },
                                                    complete: () => {
                                                    }
                                                })
                                            }, 3000);
                                        },
                                        fail: () => { },
                                        complete: () => { }
                                    });
                                    return;
                                }

                                wx.hideLoading();
                                wx.showToast({
                                    title: '下单成功！',
                                    icon: 'success',
                                    image: '',
                                    duration: 3000,
                                    mask: true,
                                    success: (result) => {
                                        //延迟3秒跳转
                                        setTimeout(function () {
                                            wx.switchTab({
                                                url: '/pages/shop_index/main',
                                                success: (result) => {
                                                    let page = getCurrentPages().pop();
                                                    if (page == undefined || page == null) return;
                                                    page.onLoad();
                                                },
                                                fail: () => { },
                                                complete: () => {
                                                }
                                            })
                                        }, 3000);
                                    },
                                    fail: () => { },
                                    complete: () => {
                                    }
                                });

                            }.bind(this))
                            .catch(function (error) {
                                console.log(error);
                            });
                    }
                });
            }
        }
    }
</script>
<style>
    .cu-form-group .title {
        min-width: calc(4em + 30rpx);
    }
</style>