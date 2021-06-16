# 项目介绍
* 基于MVVM模式集成谷歌官方推荐的JetPack组件库LiveData+ViewModel+DataBinding，以ARouter为组件路由实现，采用组件化开发，通过配置可实现模块单独运行
* 网络请求采用Retrofit+OkHttp+kotlin Coroutines+moshi，Glide加载图片，数据库采用Room，使用Paging3实现列表分页加载，使用X5Webview加载网页
* 项目Api采用[玩安卓](https://www.wanandroid.com/blog/show/2)开放api实现

# 主要开源框架
* 图片加载[bumptech/glide](https://github.com/bumptech/glide)
* 路由框架[alibaba/ARouter](https://github.com/alibaba/ARouter)
* JSON解析[squareup/moshi](https://github.com/square/moshi)
* 网络请求[squareup/retrofit](https://github.com/square/retrofit)
* 消息总线[JeremyLiao/LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)

## 组件配置
gradle.properties 中 isModule 进行配置

## 其它
玩安卓[flutter版本](https://github.com/Tommys-code/WanAndroidFlutter)






