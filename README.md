# MyRPC
项目的最新文件放在matser分支中<br>
自己写的一个简单的RPC项目，功能已基本实现。项目主要是基于netty进行实现的。<br>
此RPC框架分别实现了基于http和tcp的消息传输，
http的实现是基于embed-tomcat实现的，用来处理http消息
tcp则在自定义传输协议的基础上实现了服务注册发现、负载均衡、心跳检测等功能<br>
使用时与dubbo相近，测试案例在Test包下均已给出，此项目适合刚学完netty的同学练手<br>

