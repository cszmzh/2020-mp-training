<template>
    <div class="Container">

        <!--轮播图-->
        <swiper indicator-dots indicator-color="pink" indicator-active-color="green" autoplay=true interval=5000>
            <block v-for="(item,index) in imgUrls" :key="index" :item="item" :index="index">
                <swiper-item class="" item-id="">
                    <img :src="item" alt="">
                </swiper-item>
            </block>
        </swiper>

        <!--分类信息-->
        <div class="catInfo">
            <view class="padding-xs flex align-center">
                <view class="flex-sub text-center">
                    <view class="solid-bottom text-xs padding">
                        <text class="text-gray"
                            style="font-size: 23rpx;">欢迎来到{{categoryName}}专区，{{categoryDescription}}</text>
                    </view>
                </view>
            </view>
        </div>

        <!--卡片组件-->
        <div class="card">
            <CardItem v-for="(item,index) in productsList" :key="index" :item="item" :index="index"></CardItem>
            <div id="tip" style="width: 100%;" v-if="productsList.length==0">
                <view class="padding-lr bg-white margin-top">
                    <view class="solid-bottom padding">
                        <text class="text-Abc">Powered By 515code</text>
                    </view>
                    <view class="padding">该分区暂时没有商品哦，快来发布一个试试~</view>
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
                    'https://yun.515code.com/blog/background-image/Abstract%204.jpg',
                    'https://yun.515code.com/blog/background-image/Bahamas%20Aerial.jpg',
                    'https://yun.515code.com/blog/background-image/Balance.jpg',
                    'https://yun.515code.com/blog/background-image/A%20Tree%20And%20Stars.jpg'
                ],
                productsList: {},
                categoryName: '',
                categoryDescription: ''
            }
        },
        beforeMount() {

            //设置页面标题
            wx.setNavigationBarTitle({
                title: this.$mp.query.categoryName,
                success: (result) => {

                },
                fail: () => { },
                complete: () => { }
            });

            //接收跳转参数
            this.categoryName = this.$mp.query.categoryName;
            this.categoryDescription = this.$mp.query.categoryDescription;

            wx.login({
                success: function (res) {
                    console.log(res)
                }
            })

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
                this.$fly.get(this.globalData.backgroundURL + 'products/getAllByCatId', {
                    catId: this.$mp.query.categoryId
                })
                    .then(function (response) {
                        this.productsList = response.data.data;
                    }.bind(this))
                    .catch(function (error) {
                        console.log(error);
                    });
            }
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

    /*轮播图*/
    .Container swiper {
        width: 100%;
        height: 400rpx;

    }

    .Container swiper img {
        width: 100%;
        height: 100%;
    }
</style>