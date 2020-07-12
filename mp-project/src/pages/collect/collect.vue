<template>
  <div>
    <CollectItem v-for="(item,index) in collectList" :key="index" :item="item" :index="index"></CollectItem>
    <view v-if="collectList.length==0" class="padding-xs flex align-center">
      <view class="flex-sub text-center">
        <view class="solid-bottom text-xs padding">
          <text class="text-gray">收藏空空如也，快去逛逛或下拉刷新试试吧~</text>
        </view>
      </view>
    </view>
  </div>
</template>

<script>
  import CollectItem from '../template/collect_card_template/collect_card_template.vue'
  export default {
    components: { CollectItem },
    data() {
      return {
        collectList: [],
        currentPage: 0, //当前页数
        totalPage: 0, //总页数
        pageSize: 4 //一页显示四条
      }
    },
    //生命周期
    onLoad() {
      this.currentPage=0;
      this.getCollects(0)
    },
    beforeMount() {

    },
    onPullDownRefresh() {
      wx.showNavigationBarLoading();
      this.getCollects(0);
      wx.hideNavigationBarLoading();
      wx.stopPullDownRefresh();

    },
    //上拉加载更多，设置底线
    onReachBottom: function () {
      if (this.currentPage < this.totalPage) {
        this.currentPage++;
        this.getCollects(this.currentPage)
      } else {
        wx.showToast({
          title: "没有更多收藏啦~",
          icon: "none",
          diration: 1500
        })
      }
    },
    methods: {
      getCollects(page) {
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
            this.$fly.post(this.globalData.backgroundURL + 'click/getCollects', {
              code: this.code,
              pageNum: page,
              pageSize: this.pageSize
            })
              .then(function (response) {
                if(page==0){
                  this.collectList=response.data.data.collectVOList;
                }else{
                  this.collectList = this.collectList.concat(response.data.data.collectVOList);
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