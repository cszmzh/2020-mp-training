<template>
    <div>
        <form>
            <view class="cu-form-group margin-top">
                <view class="title">商品名</view>
                <input v-model="productName" placeholder="请填写商品关键名称哦~"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">价格</view>
                <input v-model="nowPrice" placeholder="请输入商品价格哦，例如9.99"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">原价</view>
                <input v-model="prePrice" placeholder="可以输入商品原价格~"></input>
            </view>
            <view class="cu-form-group">
                <view class="title">手机号</view>
                <input v-model="phoneNumber" placeholder="方便买家联系哦"></input>
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
            <view class="cu-bar bg-white margin-top">
                <view class="action">
                    图片上传(至少一张)
                </view>
                <view class="action">
                    {{imgList.length}}/4
                </view>
            </view>
            <view class="cu-form-group">
                <view class="grid col-4 grid-square flex-sub">
                    <view class="bg-img" v-for="(item,index) in imgList" :key="index" @tap="ViewImage"
                        :data-url="imgList[index]">
                        <image :src='imgList[index]' mode='aspectFill'></image>
                        <view class="cu-tag bg-red" @tap="DelImg" :data-index="index">
                            <text class="cuIcon-close"></text>
                        </view>
                    </view>
                    <view class="solids" @tap="ChooseImage" v-if="imgList.length<4">
                        <text class="cuIcon-cameraadd"></text>
                    </view>
                </view>
            </view>
            <!-- !!!!! placeholder 在ios表现有偏移 建议使用 第一种样式 -->
            <view class="cu-form-group margin-top">
                <textarea v-model="productDescription" maxlength="500" :disabled="modalName!=null"
                    @input="textareaAInput" placeholder="输入商品描述(必填)"></textarea>
            </view>
        </form>

        <view class="padding flex flex-direction" @tap="handleSubmit">
            <button class="cu-btn bg-blue margin-tb-sm lg">点击发布</button>
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
                productDescription: '',
                resultCode: -1
            }
        },
        beforeMount() {
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
            TimeChange(e) {
                this.time = e.mp.detail.value
            },
            DateChange(e) {
                this.date = e.mp.detail.value
            },
            RegionChange: function (e) {
                this.region = e.mp.detail.value
            },
            ChooseImage() {
                wx.chooseImage({
                    count: 4, //默认9
                    sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album'], //从相册选择
                    success: (res) => {
                        if (this.imgList.length != 0) {
                            this.imgList = this.imgList.concat(res.tempFilePaths)
                        } else {
                            this.imgList = res.tempFilePaths
                        }
                    }
                });
            },
            ViewImage(e) {
                wx.previewImage({
                    urls: this.imgList,
                    current: e.currentTarget.dataset.url
                });
            },
            DelImg(e) {
                wx.showModal({
                    title: '删除图片',
                    content: '确定要删除这张图片吗？',
                    cancelText: '取消',
                    confirmText: '确定',
                    success: res => {
                        if (res.confirm) {
                            this.imgList.splice(e.currentTarget.dataset.index, 1);
                            this.imgList = this.imgList
                        }
                    }
                })
            },
            textareaAInput(e) {
                this.textareaAValue = e.mp.detail.value
            },
            textareaBInput(e) {
                this.textareaBValue = e.mp.detail.value
            },
            handleSubmit() {
                this.submitPicture();
            },
            submitPicture() {

                wx.showLoading({
                    title: '商品发布中',
                    mask: true
                });

                //检验参数
                var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
                var priceReg = /^[1-9]\d*(\.\d{1,2})?$|^0+(\.\d{1,2})?$/;
                if (this.prodName == '' || !priceReg.test(this.nowPrice) || !priceReg.test(this.nowPrice) ||
                    !phoneReg.test(this.phoneNumber) || this.detailAddress == '' || this.productDescription == '' || this.imgList.length == 0) {
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

                var tempFilePaths = this.imgList;
                var submitImgList = this.submitImgList;

                var promise = Promise.all(tempFilePaths.map((tempFilePath, index) => {
                    var that = this;
                    return new Promise(function (resolve, reject) {
                        wx.uploadFile({
                            url: that.globalData.backgroundURL + 'upload/productImage',
                            filePath: tempFilePath,
                            name: 'productImage',
                            formData: {
                            },
                            success: function (res) {
                                resolve(res.data);
                                var data = JSON.parse(res.data);
                                //设置图片链接
                                that.submitImgList.push(that.globalData.backgroundURL + data.data);
                                that.resultCode = 200.
                            },
                            fail: function (err) {
                                reject(new Error('failed to upload file'));
                            }
                        });
                    });
                }));

                var that = this;
                promise.then(function (results) {
                    console.log(results);
                    that.submitProduct();
                }).catch(function (err) {
                    console.log(err);
                });

            },
            submitProduct() {

                //图片上传失败检测
                if (this.resultCode != 200) {
                    wx.hideLoading();
                    wx.showToast({
                        title: '图片上传失败',
                        icon: 'none',
                        image: '',
                        duration: 1500,
                        mask: false,
                    });

                    return;
                }

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
                        this.$fly.post(this.globalData.backgroundURL + 'products/create', {
                            code: this.code,
                            prodName: this.productName,
                            nowPrice: this.nowPrice,
                            prePrice: this.prePrice,
                            categoryName: this.categoryName,
                            categoryMininame: this.categoryMininame,
                            prodDescription: this.productDescription,
                            subContact: this.phoneNumber,
                            prodImg1: this.submitImgList[0],
                            prodImg2: this.submitImgList[1] == undefined ? 'null' : this.submitImgList[1],
                            prodImg3: this.submitImgList[2] == undefined ? 'null' : this.submitImgList[2],
                            prodImg4: this.submitImgList[3] == undefined ? 'null' : this.submitImgList[3]
                        })
                            .then(function (response) {
                                wx.hideLoading();
                                wx.showToast({
                                    title: '发布成功',
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