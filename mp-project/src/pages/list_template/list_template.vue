<template>
    <div @tap="toDetail">
        <view class="cu-card case" :class="isCard?'no-card':''">
			<view class="cu-item shadow">
				<view class="image">
					<image :src="item.detail_img" mode="widthFix"></image>
					<view class="cu-tag bg-blue">{{item.title}}</view>
					<span>{{item.date}}</span>
					<view class="cu-bar bg-shadeBottom"> <text class="text-cut">{{item.detail_content}}</text></view>
				</view>
				<view class="cu-list menu-avatar">
					<view class="cu-item">
						<view class="cu-avatar round lg" style="background-image:url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg);"></view>
						<view class="content flex-sub">
							<view class="text-grey">{{item.author}}</view>
							<view class="text-gray text-sm flex justify-between">
								十天前
								<view class="text-gray text-sm">
									<text class="cuIcon-attentionfill margin-lr-xs"></text> {{item.love_count}}
									<text class="cuIcon-appreciatefill margin-lr-xs"></text> {{item.attention_count}}
									<text class="cuIcon-messagefill margin-lr-xs"></text> 31
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
    export default{
      props:[
		  'item','index'
	  ],
	  methods:{
		  toDetail(){
			  //跳转到详情页+传递参数index
			  wx.navigateTo({
				  url: '/pages/detail/main?index='+this.index,
				  success: (result) => {
					  
				  },
				  fail: () => {},
				  complete: () => {}
			  });
		  }
	  }
    }
  </script>
  <style>
    
  </style>