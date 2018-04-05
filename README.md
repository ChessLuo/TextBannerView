# Android文字轮播控件
[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)


现在的绝大数APP特别是类似淘宝京东等这些大型APP都有文字轮播界面，实现循环轮播多个广告词等功能；这种空间俗称“跑马灯”，而TextBannerView已经实现了可垂直跑、可水平跑的跑马灯了。


## 效果图
![](./someImg/textbanner.gif)

[Download Apk](https://github.com/zsml2016/TextBannerView/releases/download/1.0.1/demo-1.0.1.apk)


## <a name="1"></a>Attributes属性（TextBannerView布局文件中调用）
|Attributes|forma|describe
|---|---|---|
|setInterval| integer |文字切换时间间隔,默认3000
|setAnimDuration| integer |动画持续时间，默认1500
|setTextSize| dimension |设置文字尺寸
|setTextColor| color |设置文字颜色,默认黑色
|setSingleLine| boolean|是否为显示单行
|setGravity| |文字显示位置,默认左边居中；可设置left、center、right
|setDirection| |文字轮播方向，默认水平从右到左轮播：right_to_left；还可以设置left_to_right（从左到右轮播）、bottom_to_top（从底部到顶部轮播）、top_to_bottom（从顶部到底部轮播）


## <a name="2"></a>方法
|方法名|描述|版本限制
|---|---|---|
|setDatas(List<String> datas)| 设置数据集合，类型：List<String>|无
|startViewAnimator()| 设置开始文字切换（默认自动）|无
|stopViewAnimator()| 设置暂停文字切换|无
|setItemOnClickListener(listener))| 设置点击监听事件回调 |无


## 使用步骤

#### Step 1.依赖TextBannerView
Gradle 
```groovy
dependencies{
    compile 'com.superluo:textbannerview:1.0.1'  //最新版本
}
```
或者引用本地lib
```groovy
compile project(':textbannerlibrary')
```

Maven
```xml
<dependency>
  <groupId>com.superluo</groupId>
  <artifactId>textbannerview</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

#### Step 2.在布局文件中添加TextBannerView，可以设置自定义属性

```xml
<com.superluo.textbannerlibrary.TextBannerView
        android:id="@+id/tv_banner"
        android:layout_width="match_parent"
        android:layout_height="38dp"
        android:background="#cc8ac6"
        app:setGravity="right"
        app:setTextColor="#fff"/>
```
* <a href="#1">点击可参考更多自定义属性</a>



#### Step 3.在Activity或者Fragment中配置TextBannerView 


```java
//初始化TextBannerView
TextBannerView tvBanner = (TextBannerView) findViewById(R.id.tv_banner);

//设置数据
List<String> list = new ArrayList<>();

list.add("学好Java、Android、C#、C、ios、html+css+js");
list.add("走遍天下都不怕！！！！！");
list.add("不是我吹，就怕你做不到，哈哈");
list.add("superluo");
list.add("你是最棒的，奔跑吧孩子！");

//调用setDatas(List<String>)方法后,TextBannerView自动开始轮播
//注意：此方法目前只接受List<String>类型
tvBanner.setDatas(mList);

//设置TextBannerView点击监听事件，返回点击的data数据, 和position位置
tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Log.i("点击了：",String.valueOf(position)+">>"+data);
            }
        });


```
* <a href="#2">点击可参考更多使用方法</a>


## 更多开源库请关注：[GitHub](https://github.com/zsml2016)









