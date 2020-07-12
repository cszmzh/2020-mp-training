<template>
    <div>
        <form>
            <view class="cu-form-group margin-top">
                <view class="title">商品名</view>
                <input v-model="productName" placeholder="请填写商品关键名称哦~" :value="goods.prodName"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">价格</view>
                <input v-model="nowPrice" placeholder="请输入商品价格哦，例如9.99" :value="goods.nowPrice"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">原价</view>
                <input v-model="prePrice" placeholder="可以输入商品原价格~" :value="goods.prePrice"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">手机号</view>
                <input v-model="phoneNumber" placeholder="方便买家联系哦" :value="goods.subContact"></input>
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
                <view class="title">商品分类</view>
                <picker mode="multiSelector" @change="MultiChange" @columnchange="MultiColumnChange" :value="multiIndex"
                    :range="multiArray">
                    <view class="picker">
                        {{multiArray[0][multiIndex[0]]}}，{{multiArray[1][multiIndex[1]]}}
                    </view>
                </picker>
            </view>
            <!-- !!!!! placeholder 在ios表现有偏移 建议使用 第一种样式 -->
            <view class="cu-form-group margin-top">
                <textarea v-model="productDescription" maxlength="-1" :disabled="modalName!=null"
                    @input="textareaAInput" placeholder="输入商品描述" :value="goods.prodDescription"></textarea>
            </view>
        </form>

        <view class="padding flex flex-direction" @tap="updateProduct">
            <button class="cu-btn bg-blue margin-tb-sm lg">点击更新</button>
        </view>

    </div>
</template>
<script>

    export default {
        data() {
            return {
                goods: {},
                StatusBar: this.globalData.StatusBar,
                CustomBar: this.globalData.CustomBar,
                index: null,
                picker: ['喵喵喵', '汪汪汪', '哼唧哼唧'],
                multiArray: [
                    ['学习', '数码', '生活', '运动', '二手书', '其他'],
                    ['个人笔记', '复习资料', '学习用品', '考研专区']
                ],
                multiIndex: [0, 0],
                time: '12:01',
                date: '2018-12-25',
                region: ['天津市', '天津市', '西青区'],
                imgList: [],
                submitImgList: [],
                modalName: null,
                textareaAValue: '',
                textareaBValue: '',
                //商品信息
                productName: '',
                nowPrice: '',
                prePrice: '',
                phoneNumber: '',
                categoryName: '',
                categoryMininame: '',
                productDescription: ''
            }
        },
        beforeMount() {
            this.getProductInfo();
        },
        //compunted打错了
        computed: {
            //映射状态到组件,名字和state.js中一致

        },
        methods: {
            PickerChange(e) {
                this.index = e.mp.detail.value
            },
            MultiChange(e) {
                this.multiIndex = e.mp.detail.value
            },
            MultiColumnChange(e) {
                this.multiIndex[e.mp.detail.column] = e.mp.detail.value;
                var that = this;
                switch (that.multiIndex[0]) {
                    case 0:
                        that.multiArray[1] = ['个人笔记', '复习资料', '学习用品', '考研专区'];
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                    case 1:
                        that.multiArray[1] = ['手机', '电脑', '配件', '其他'];
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                    case 2:
                        that.multiArray[1] = ['盆栽', '衣物', '其他'];
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                    case 3:
                        that.multiArray[1] = ['鞋子', '装备']
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                    case 4:
                        that.multiArray[1] = ['教材', '周刊杂志', '读物']
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                    case 5:
                        that.multiArray[1] = ['其他']
                        this.multiIndex = [this.multiIndex[0], this.multiIndex[1]]
                        break;
                }

            },
            RegionChange: function (e) {
                this.region = e.mp.detail.value
            },
            textareaAInput(e) {
                this.textareaAValue = e.mp.detail.value
            },
            textareaBInput(e) {
                this.textareaBValue = e.mp.detail.value
            },
            getProductInfo() {
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
                        })
                            .then(function (response) {
                                this.goods = response.data.data
                                
                                //设置原商品属性
                                this.productName= this.goods.prodName,
                                this.nowPrice= this.goods.nowPrice,
                                this.prePrice= this.goods.prePrice,
                                this.phoneNumber= this.goods.subContact,
                                this.productDescription= this.goods.prodDescription

                            }.bind(this))
                            .catch(function (error) {
                                console.log(error);
                            });
                    }
                });

            },
            updateProduct() {

                //设置类目名称
                this.categoryName = this.multiArray[0][this.multiIndex[0]];
                this.categoryMininame = this.multiArray[1][this.multiIndex[1]];

                //上传商品信息
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
                        this.$fly.post(this.globalData.backgroundURL + 'products/update', {
                            prodId: this.$mp.query.prodId,
                            prodName: this.productName,
                            nowPrice: this.nowPrice,
                            prePrice: this.prePrice,
                            categoryName: this.categoryName,
                            categoryMininame: this.categoryMininame,
                            prodDescription: this.productDescription,
                            subContact: this.phoneNumber
                        })
                            .then(function (response) {
                                wx.hideLoading();
                                wx.showToast({
                                    title: '更新成功',
                                    icon: 'success',
                                    image: '',
                                    duration: 3000,
                                    mask: true,
                                    success: (result) => {
                                        //延迟5秒跳转
                                        var that = this;
                                        setTimeout(function () {
                                            wx.switchTab({
                                                url: '/pages/shop_index/main',
                                                success: (result) => {
                                                    that.productName = '',
                                                        that.nowPrice = '',
                                                        that.prePrice = '',
                                                        that.phoneNumber = '',
                                                        that.productDescription = '',
                                                        that.submitImgList = [],
                                                        that.imgList = []
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