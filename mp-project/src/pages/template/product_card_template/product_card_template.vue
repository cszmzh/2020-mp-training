<template>
    <div @tap="toDetail" class="cardItem">
        <view class="cu-card case" :class="isCard?'no-card':''">
            <view class="cu-item shadow" style="margin:15rpx">
                <view class="image" style="height: 350rpx;">
                    <div class="productImage"
                        style="height: 100%; width: 100%; background-position: center center;background-size: cover;"
                        :style="{backgroundImage:'url(' + item.prodImg1 + ')'}"></div>
                    <view class="cu-tag bg-blue">推荐</view>
                    <view class="cu-bar bg-shadeBottom">
                        <text class="text-cut">{{item.nowPrice}}元</text>
                        <text class="text-cut"
                            style="text-decoration: line-through rgba(255,255,255,0.6);font-size: 22rpx;">{{item.prePrice}}元</text>
                    </view>
                </view>
                <view class="cu-list menu-avatar">
                    <view class="cu-item" style="height: 100rpx;">
                        <view class="cu-avatar round lg"
                            :style="['background-image:url('+ item.subAvatar +');width: 48rpx; height: 48rpx;']">
                        </view>
                        <view class="content flex-sub" style="width:210rpx;height:85%;left:100rpx">
                            <view class="text-grey"
                                style="height:60%;width:100%;color:black; font-size: 21rpx;line-height: 25rpx;display: block;overflow: hidden;">
                                {{item.prodName}}</view>
                            <view class="text-gray text-sm flex justify-between" style="height: 35%;">
                                <view class="text-gray text-sm" style="right: 0;position: absolute ;font-size: 20rpx;">
                                    <text class="cuIcon-appreciatefill margin-lr-xs">&nbsp;{{item.prodLike}}</text>
                                </view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
        </view>
    </div>
</template>

<script>
    export default {
        props: [
            'item', 'index'
        ],
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
            }
        }
    }

</script>
<style>
    /*商品卡片*/
    .card {
        width: 100%;
        height: auto;
        display: flex;
        justify-content: center;
        flex-direction: row;
        flex-wrap: wrap;
    }

    .cardItem {
        height: auto;
        width: 48%;
        display: block;
    }
</style>