<template>
    <div class="Container">

        <!--顶部搜索框-->
        <div class="topBar">
            <div class="cu-bar search bg-white">
                <div class="search-form round">
                    <text class="cuIcon-search"></text>
                    <input v-model="inputValue" @focus="InputFocus" @blur="InputBlur" :adjust-position="false"
                        type="text" placeholder="输入商品，或者下拉页面试试~" confirm-type="search"></input>
                </div>
                <div class="action">
                    <input type="button" @tap="searchProducts" class="cu-btn shadow-blur round" style="background-color:#5B8FF9;color:white;" value="搜索">
                </div>
            </div>
        </div>

        <!--轮播图-->
        <swiper indicator-dots indicator-color="white" indicator-active-color="#5B8FF9" autoplay=true interval=5000>
            <block v-for="(item,index) in imgUrls" :key="index" :item="item" :index="index">
                <swiper-item class="" item-id="">
                    <img :src="item" alt="">
                </swiper-item>
            </block>
        </swiper>

        <!--卡片组件-->
        <div class="card">
            <CardItem v-for="(item,index) in productsList" :key="index" :item="item" :index="index"></CardItem>
            <div id="tip" style="width: 100%;" v-if="productsList.length==0">
                <view class="padding-lr bg-white margin-top">
                    <view class="solid-bottom padding">
                        <text class="text-Abc">Powered By 515code</text>
                    </view>
                    <view class="padding">暂时没有搜索到商品哦，建议换个关键字试试~</view>
                </view>
            </div>
        </div>

        <!-- <view style="height:150rpx;"></view> -->


    </div>
    </div>
    </div>
</template>

<script>
    import CardItem from '../template/product_card_template/product_card_template.vue'
    export default {
        components: { CardItem },
        data() {
            return {
                inputValue: '',
                imgUrls: [
                    'https://cdn.jsdelivr.net/gh/BA-NANA/ba-nana.github.io@1.0/assets/img/tjut.jpg',
                    'https://yun.515code.com/static/miniprogram/%E7%90%86%E5%B7%A5%E5%A4%A7%E5%AD%A6.jpg',
                    'https://yun.515code.com/static/miniprogram/1.jpg',
                    'https://www.515code.com/viedo/index/banner.jpg',
                    'https://yun.515code.com/static/miniprogram/4.jpg'
                ],
                productsList: {}
            }
        },
        onPullDownRefresh() {
            wx.showNavigationBarLoading();
            this.getProdcuts();
            wx.hideNavigationBarLoading();
            wx.stopPullDownRefresh();
        },
        beforeMount() {

        },
        onLoad() {
            this.getProdcuts();
        },
        //compunted打错了
        computed: {
            //映射状态到组件,名字和state.js中一致

        },
        methods: {
            //随机获取商品（推荐）
            getProdcuts() {

                this.$fly.interceptors.request.use((config, promise) => {
                    config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                    return config;
                })
                this.$fly.get(this.globalData.backgroundURL + 'products/recommend', {
                    num: 12  //随机获取12条商品
                })
                    .then(function (response) {
                        this.productsList = response.data.data;

                    }.bind(this))
                    .catch(function (error) {
                        wx.showToast({
                            title: '加载失败，请检查网络',
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
            searchProducts() {
                this.$fly.interceptors.request.use((config, promise) => {
                    config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                    return config;
                })
                this.$fly.get(this.globalData.backgroundURL + 'products/search', {
                    prodName: this.inputValue
                })
                    .then(function (response) {
                        this.productsList = response.data.data;
                    }.bind(this))
                    .catch(function (error) {
                        console.log(error);
                    });
            }//结束
        }
    }
</script>
<style>
    page {
        width: 100%;
        height: auto;
    }

    .Container {
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    /*顶部搜索框*/
    .topBar {
        width: 100%;
        top: 0;
    }

    /*轮播图*/
    .Container swiper {
        width: 100%;
        height: 400rpx;

    }

    .Container swiper img {
        width: 100%;
        height: 100%;
    }

    /*底部导航栏*/
    .bottomBar {
        bottom: 0;
        width: 100%;
        margin: 0 auto;
        position: fixed;
    }

    /*底部导航栏字体*/
    .bottomItem {
        font-size: 20rpx;
    }
</style>