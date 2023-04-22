# VulnerableApp

## 介绍

Android应用层漏洞靶场，总结和复现一下自己学习过的漏洞。

同时为了锻炼Android开发，采用了kotlin语言，编写了简单而丑陋且无用的界面😭。

### 项目结构

该项目为AndroidStudio项目，可通过AndroidStudio直接导入。

项目中含有`:vulnerableapp`和`:attackapp`两个app模块，分别对应含漏洞的App和攻击的app。

具体部分还是读源码叭，每个漏洞的代码还算蛮独立的。


## 漏洞列表

> 目前还在持续更新中，StarStar~

[>漏洞文档汇总<](./assets/VulnerabilityDetails/)

-   Manifest安全
    1.   [Manifest中定义组件未实现漏洞](./assets/VulnerabilityDetails/defining_components_not_implemented.md)
    2.   [应用数据备份配置不当漏洞](./assets/VulnerabilityDetails/allow_backup_config.md)
    3.   [应用调试模式配置不当漏洞](./assets/VulnerabilityDetails/allow_debug_config.md)
-   通用组件安全
    1.   [App通用型拒绝服务漏洞](./assets/VulnerabilityDetails/components_denial_service.md)
-   Provider组件安全
    1.   [ContentProviderURI授权不当漏洞](./assets/VulnerabilityDetails/provider_improper_uri_authorization.md)
    2.   [ContentProvider文件目录遍历漏洞](./assets/VulnerabilityDetails/provider_directory_traversal.md)
-   WebView组件安全
    1.   [应用克隆漏洞(webview部分)](./assets/VulnerabilityDetails/webview_bypass_origin_policy.md)
    2.   [污染cookie任意执行漏洞](./assets/VulnerabilityDetails/webview_bypass_origin_policy.md)
    3.   [WebView File域同源策略绕过漏洞（低版本有效，未能复现）](./assets/VulnerabilityDetails/webview_bypass_origin_policy.md)
    4.   [js2native漏洞](./assets/VulnerabilityDetails/webview_js2native.md)



## 此外

新建了一个公众号，点个关注啦拜托🤪~

![微信公众号](https://fastly.jsdelivr.net/gh/Forgo7ten/VulnerableApp@main/assets/link.png)
