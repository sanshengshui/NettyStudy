##**Much effort,much prosperity**
```
在传统的Java应用中，通常使用以下4种方式进行跨节点通信。
（1）通过RMI进行远程服务调用
（2）通过Java的Socket+Java序列化的方式进行跨节点调用;
（3）利用一些开源的RPC框架进行远程服务调用，例如Facebook的Thrift,Apache的Avro等；
（4）利用标准的共有协议进行跨节点服务调用，例如HTTP+XML.RESTful+JSON或者WebService.
```
###Netty协议栈功能设计
#####协议栈功能描述
Netty协议栈承载了业务内部各模块之间的消息交互和服务调用，它的主要功能如下。
```
*基于Netty的NIO通信框架，提供高性能的异步通信能力;
*提供消息的编解码框架，可以实现POJO的序列化和反序列化;
*提供基于IP地址的白名单接入认证机制;
*链路的有效性校验机制;
*链路的断连重连机制。
```

