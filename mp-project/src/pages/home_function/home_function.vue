<template>
    <div>
        <MyFunctionCard v-for="(item,index) in productList" :key="index" :item="item" :index="index" :title="title">
        </MyFunctionCard>
        <view v-if="productList.length==0" class="padding-xs flex align-center">
            <view class="flex-sub text-center">
                <view class="solid-bottom text-xs padding">
                    <text class="text-gray">{{title}}空空如也~</text>
                </view>
            </view>
        </view>
    </div>
</template>

<script>
    import MyFunctionCard from '../template/my_function_card_template/my_function_card_template.vue'
    export default {
        components: { MyFunctionCard },
        data() {
            return {
                title: '',
                status: 0,
                productList: [],
                currentPage: 0, //当前页数
                totalPage: 0, //总页数
                pageSize: 4 //一页显示四条
            }
        },
        //生命周期
        onLoad() {
            this.currentPage=0
        },
        beforeMount() {
            //设置页面标题(我的买到 我的卖出 我的发布)
            wx.setNavigationBarTitle({
                title: this.$mp.query.title,
                success: (result) => {

                },
                fail: () => { },
                complete: () => {

                    this.getProducts(this.currentPage);
                }
            });
            this.title = this.$mp.query.title;

        },
        onPullDownRefresh(){
            wx.showNavigationBarLoading();
            this.getProducts(this.currentPage);
            wx.hideNavigationBarLoading();
            wx.stopPullDownRefresh();
        },
        //上拉加载更多，设置底线
        onReachBottom: function () {
            if (this.currentPage < this.totalPage) {
                this.currentPage++;
                this.getProducts(this.currentPage)
            } else {
                wx.showToast({
                    title: "没有更多啦~",
                    icon: "none",
                    diration: 1500
                })
            }
        },
        methods: {
            getProducts(page) {
                var requestURL = this.globalData.backgroundURL;
                if (this.title == "我的发布") {
                    requestURL = requestURL + 'products/getSaleList';
                    this.status = 1;
                } else if (this.title == "我的卖出") {
                    requestURL = requestURL + 'products/getSaleList';
                    this.status = 2;
                } else if (this.title == "我的买到") {
                    requestURL = requestURL + 'products/getBuyList';
                }
                wx.login({
                    timeout: 10000,
                    success: (result) => {
                        this.code = result.code;
                    },
                    fail: () => { },
                    complete: () => {
                        this.$fly.interceptors.request.use((config, promise) => {
                            config.headers["Content-Type"] = "application/x-www-form-urlencoded";
                            return config;
                        })
                        this.$fly.post(requestURL, {
                            code: this.code,
                            status: this.status,
                            pageNum: page,
                            pageSize: this.pageSize
                        })
                            .then(function (response) {
                                if (this.currentPage == 0) {
                                    this.productList = response.data.data.productList;
                                } else {
                                    this.productList = this.productList.concat(response.data.data.productList);
                                }
                                this.totalPage = Math.ceil(response.data.data.total / this.pageSize);
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
                    }
                });

            }
        }

    }
</script>
<style>

</style>