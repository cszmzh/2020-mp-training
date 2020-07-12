<template>
  <div class="detailContainer">
    <img class="detail_img" :src="detailObj.detail_img" alt="">
    <div class="avatar_date">
      <img :src="detailObj.avatar" alt="">
      <span>{{detailObj.author}}</span>
      <span>发布于</span>
      <span>{{detailObj.date}}</span>
    </div>
    <p class="company">{{detailObj.title}}</p>
    <div class="conllection_share_container">
      <div class="collection_share">
        <img @tap="handleCollection"
          :src="isCollected?'/static/images/icon/collection.png':'/static/images/icon/collection-anti.png'" alt="">
        <img @tap="handleShare" src="/static//images/icon/share-anti.png" alt="">
      </div>
      <div class="line"></div>
    </div>
    <Button>转发此文章</Button>
    <p class="content">{{detailObj.detail_content}}</p>
  </div>
</template>

<script>
  //发送请求
  const TEST_URL = "http://47.95.32.217:8080/monitorApplication/maintenance/getAll?pageNum=0&pageSize=19";


  import { mapState } from 'vuex'
  export default {
    // onLoad(options){
    //   console.log(options)
    // }
    data() {
      return {
        detailObj: {},
        isCollected: false //标识文章是否收藏
      }
    },
    beforeMount() {

      //axios
      //fly
      this.$fly.get(TEST_URL)
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });

      //取代onload中options
      this.index = this.$mp.query.index;

      //预处理工作：本地是否收藏缓存
      let oldStorage = wx.getStorageSync('isCollected');
      if (!oldStorage) {
        //为空
        wx.setStorage({
          key: 'isCollected',
          data: {}
        })
      } else {
        //用户缓存过
        this.isCollected = (oldStorage[this.index] ? true : false);
      }
    },
    mounted() {
      //更新state数据
      this.detailObj = this.listTmp[this.index];
    },
    computed: {
      ...mapState(['listTmp'])
    },
    methods: {
      handleCollection() {
        //修改状态
        this.isCollected = !this.isCollected;
        let title = this.isCollected ? '收藏成功' : '取消收藏';
        wx.showToast({
          title: title,
          icon: 'success',
          image: '',
          duration: 1000,
          mask: false,
          success: (result) => {

          },
          fail: () => { },
          complete: () => { }
        });

        //读取之前本地缓存的状态查看是否收藏

        let oldStorage = wx.getStorageSync('isCollected');

        oldStorage[this.index] = this.isCollected;

        //将本次设置的结果缓存到本地

        wx.setStorage({
          key: 'isCollected',
          data: oldStorage
        });

      },
      handleShare() {
        wx.showActionSheet({
          itemList: ['朋友圈', '分享到QQ', '分享到微信']
        })
      }
    }
  }
</script>
<style>
  .detailContainer {
    display: flex;
    flex-direction: column;
  }

  .detail_img {
    width: 100%;
    height: 460rpx;
  }

  .avatar_date {
    padding: 10rpx;
  }

  .avatar_date img {
    width: 64rpx;
    height: 64rpx;
    vertical-align: middle;
  }

  .avatar_date span {
    font-weight: 28rpx;
    margin-left: 10rpx;
  }

  .company {
    font-size: 32rpx;
    font-weight: bold;
    padding: 10rpx;
  }

  .collection_share_container {
    position: relative;
  }

  .collection_share {
    float: right;
    margin-left: 30rpx;
  }

  .collection_share img {
    width: 90rpx;
    height: 90rpx;
    margin-right: 20rpx;
  }

  .line {
    position: absolute;
    top: 45rpx;
    left: 5%;
    width: 90%;
    height: 1rpx;
    background: #f0f;
    z-index: -1;
  }

  .content {
    font-size: 32rpx;
    text-indent: 32rpx;
    letter-spacing: 3rpx;
    line-height: 50rpx;
  }
</style>