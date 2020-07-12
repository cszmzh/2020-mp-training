<template>
    <div class="container">
        <!--步骤条-->
        <view class="bg-white padding" style="width: 100%;">
            <view class="cu-steps">
                <view :class="['cu-item', index>num?'':'text-blue']" v-for="(item,index) in numList" :key="index">
                    <text :class="['num', isTrue?'':'err']" :data-index="index + 1"></text> {{item.name}}
                </view>
            </view>
        </view>

        <!--订单信息-->
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">{{goods.prodName}}</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">订单编号</text>
            </view>
            <view class="action">
                <text class="text-grey text-sm">NO.{{goods.prodId}}</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">价格</text>
            </view>
            <view class="action">
                <text class="text-grey text-sm">{{goods.nowPrice}}元</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">买家下单时间</text>
            </view>
            <view class="action">
                <text class="text-grey text-sm">{{goods.buyTime}}</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">收货电话</text>
            </view>
            <view class="action">
                <text class="text-grey text-sm">{{goods.buyContact}}</text>
            </view>
        </view>
        <view class="cu-bar bg-white solid-bottom" style="width: 100%;">
            <view class="action">
                <text class="text-grey text-sm">收货地址</text>
            </view>
            <view class="action">
                <text class="text-grey text-sm">{{goods.buyAddress}}</text>
            </view>
        </view>

        <!--时间轴-->

        <view class="cu-timeline" v-for="(item,indx) in recordList" :key="index">
            <view class="cu-time" style="width: 300rpx;">{{item.createTime}}</view>
            <view class="cu-item text-blue">
                <view class="bg-blue content">
                    {{item.description}}
                </view>
            </view>
        </view>

    </div>
</template>

<script>
    export default {
        data() {
            return {
                numList: [{
                    name: '新下单'
                }, {
                    name: '卖家发货'
                }, {
                    name: '买家收货'
                }, {
                    name: '评价'
                },],
                num: 0,
                scroll: 0,
                isTrue: true,
                goods: {},
                recordList: []
            }
        },
        //生命周期
        onLoad() {
        },
        beforeMount() {
            this.num=-1;
            this.getProductDetail();
            this.getRecordList();
        },
        methods: {
            getProductDetail() {
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
                        this.$fly.get(this.globalData.backgroundURL + 'products/getDetailById', {
                            code: this.code,
                            id: this.$mp.query.prodId
                        }).then(function (response) {
                            this.goods = response.data.data
                            //修改页面订单状态
                            if(response.data.data.prodStatus==2){
                                this.num=0;
                            }else if(response.data.data.prodStatus==3){
                                this.num=1;
                            }else if(response.data.data.prodStatus==6){
                                this.num=2;
                            }
                        }.bind(this)).catch(function (error) {
                            console.log(error);
                        });
                    }
                });
            },
            getRecordList() {
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
                        this.$fly.post(this.globalData.backgroundURL + 'record/getAll', {
                            code: this.code,
                            prodId: this.$mp.query.prodId
                        })
                            .then(function (response) {
                                this.recordList = response.data.data;
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
    page {
        background-color: white;
    }

    .container {
        padding: 0;
        width: 100%;
    }

    .steps-bottom.cu-steps .cu-item .num::before {
        content: "\e668";
        font-family: 'iconfont';
    }

    .cu-timeline {
        width: 100%
    }
</style>