# VulnerableApp

## 介绍

Android应用层漏洞靶场，总结和复现一下自己学习过的漏洞。

比起其他的漏洞靶场项目，添加了复现的APP及代码。

### 项目结构

该项目为AndroidStudio项目，可通过AndroidStudio直接导入。

项目中含有`:vulnerableapp`和`:attackapp`两个app模块，分别对应含漏洞的App和攻击的app。

具体部分还是读源码叭，每个漏洞的代码还算蛮独立的。

## 漏洞列表

-   Manifest安全
    1.   Manifest中定义组件未实现漏洞
    2.   应用数据备份配置不当漏洞
    3.   应用调试模式配置不当漏洞
-   通用组件安全
    1.   App通用型拒绝服务漏洞
-   Provider组件安全
    1.   ContentProviderURI授权不当漏洞
    2.   ContentProvider文件目录遍历漏洞
-   WebView组件安全
    1.   应用克隆漏洞(webview部分)
    2.   污染cookie任意执行漏洞
    3.   js2native漏洞
-   网络通信安全
    1.   HTTPS关闭主机名验证漏洞
    2.   自定义HostnameVerifier未验证主机名漏洞
    3.   SSL通信信任任意服务端证书漏洞



## 此外

新建了一个公众号，后续可能会更新一些本项目的漏洞应用文章🤪~

![微信公众号](https://fastly.jsdelivr.net/gh/Forgo7ten/VulnerableApp@main/assets/link.png)

### 已有文章

[#Android漏洞](https://mp.weixin.qq.com/mp/appmsgalbum?__biz=MzkyMzQ4MzAyOQ==&action=getalbum&album_id=2896565996352667649#wechat_redirect)

-   [揭秘APP通用型拒绝服务漏洞：原理 本质 与防护](https://mp.weixin.qq.com/s/wOs55II0fmEkUl8Sbq0cMg)
