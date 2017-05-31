##**Much effort,much prosperity**
```
在传统的Java应用中，通常使用以下4种方式进行跨节点通信。
（1）通过RMI进行远程服务调用
（2）通过Java的Socket+Java序列化的方式进行跨节点调用;
（3）利用一些开源的RPC框架进行远程服务调用，例如Facebook的Thrift,Apache的Avro等；
（4）利用标准的共有协议进行跨节点服务调用，例如HTTP+XML.RESTful+JSON或者WebService.
```
###Netty协议栈功能设计<br/>
#####协议栈功能描述<br/>
Netty协议栈承载了业务内部各模块之间的消息交互和服务调用，它的主要功能如下。
```
*基于Netty的NIO通信框架，提供高性能的异步通信能力;
*提供消息的编解码框架，可以实现POJO的序列化和反序列化;
*提供基于IP地址的白名单接入认证机制;
*链路的有效性校验机制;
*链路的断连重连机制。
```
Netty的数据处理API通过倆个组件暴露- -abstract class ByteBuf和interface ByteBufHolder.<br/>
下面是一些ByteBuf API的优点:
```
(1)它可以被用户自定义的缓冲区类型扩展;
(2)通过内置的复合缓冲区类型实现了透明的零拷贝;
(3)容量可以按需增长（类似于JDK的StringBuilder);
(4)在读和写这倆种模式之间切换不需要调用ByteBuffer的flip()方法;
(5)读和写使用了不同的索引;
(6)支持方法的链式调用;
(7)支持引用计数;
(8)支持池化。
```
第6章 ChannelHandler和ChannelPipeline
<br/>
6.1 ChannelHandler家族
<br/>
6.1.1 Channel的生命周期
<br/>
Channel的四个状态:
```
状态                        描述
(1)ChannelUnregistered      Channel已经被创建,但还未注册到EventLoop
(2)ChannelRegistered        Channel已经被注册到了EventLoop
(3)ChannelActive            Channel处于活动状态（已经连接到它的远程节点)。它现在可以接受和发送数据了
(4)ChannelInactive          Channel没有连接到远程节点
Channel的生命周期是:
ChannelRegistered- - ->ChannelActive- ->ChannelInactive- ->ChannelUnregistered
```
6.1.2ChannelHandler的生命周期
<br/>
在ChannelHandler被添加到ChannelPipeline中或者被从ChannelPipeline中移除时会调用这些操作。这些方法中的每一个都接受一个
<br/>
ChannelHandlerContext参数。
<br/>
ChannelHandler的生命周期方法
```
      类型                            描述
(1)handlerAdded                    当把ChannelHandler添加到ChannelPipeline中时被调用
(2)handlerRemoved                  当从ChannelPipeLine中移除ChannelHandler时被调用
(3)exceptionCaught                 当处理过程中在ChannelPipeLine中有错误产生时被调用
Netty定义了下面2个重要的ChannelHandler子接口:
>+< ChannelInboundHandler - - -处理入站数据以及各种状态变化；
>+< ChannelOutboundHandler - - -处理出站数据并且允许拦截所有的操作。
```
6.1.3 ChannelInboundHandler接口
<br/>
6.1.4 ChannelOutboundHandler接口
<br/>
6.1.5 ChannelHandler适配器
<br/>
6.1.6 资源管理
```
  每当通过调用ChannelInboundHandler.channelRead()或者ChannelOutboundHandler.write()方法来处理数据时，你都需要确保没有任何的资源泄露
。你可能还记得在前面的章节中所提到的，Netty使用引用计数来处理池化的ByteBuf。所以在完全使用完某个ByteBuf后,调整其引用计数是很重要的。
Netty提供了class ResourceLeakDetector,它将对你应用程序的缓冲区分配做大约1%的采样来检测内存泄漏。相关的开销是非常小的。
  如果检测到了内存泄漏，将会产生类似于下面的日志信息:
详情请看p75  Netty in Action
泄露检测级别 
       级别                  描述
(1)  DISABLED           禁用泄露检测。只有在详尽的测试之后才应设置为这个值
(2)  SIMPLE             使用1%的默认采样率检测并报告任何发现的泄露。这是默认级别，适合绝大部分的情况
(3)  ADVANCED           使用默认的采样率，报告所发现的任何的泄露以及相应的消息被访问的位置
(4)  PARANOID           类似于ADVANCED,但是其将会对每次(对消息的)访问都进行采样。这对性能将会有很大的影响，应该只在调试阶段使用

泄露检测级别可以通过将下面的Java系统属性设置为表中的一个值来定义:
java -Dio.netty.leakDetectionLevel=ADVANCED
```
6.2 ChannelPipeLine接口
```
  每一个新创建的Channel都将会被分配一个新的ChannelPipeline。这项关联是永久性的;Channel既不能
附加另外一个ChannelPipeline,也不能分离其当前的。在Netty组件的生命周期中，这是一项固定的操作，不需要
开发人员的任何干预。
```
6.3 ChannelHandlerContext接口
```
  ChannelHandlerContext代表了ChannelHandler和ChannelPipeline之间的关联，每当有ChannelHandler
添加到ChannelPipeline中时，都会创建ChannelHandlerContext。ChannelHandlerContext的主要功能是管理
它所关联的ChannelHandler和在同一个ChannelPipeline中的其他ChannelHandler之间的交互。
  ChannelHandlerContext有很多的方法，其中一些方法也存在于Channel和ChannelPipeline本身上，但是有一点重要的不同。
如果调用Channel或者ChannelPipeline上的这些方法，它们将沿着整个ChannelPipeline进行传播。而调用位于ChannelHandler
Context上的相同方法，则将从当前所关联的ChannelHandler开始，并且只会传播给位于该ChannelPipeline中的下一个能够处理
该事件的ChannelHandler。
```
解码器
- 将字节解码为消息---ByteToMessageDecoder和ReplayingDecoder;
- 将一种消息类型解码为另一种---MessageToMessageDecoder。
<br />
将字节编码为消息(或者另一个字节序列)是一项如此常见的任务。

```

   方法                                          描述
decode(ChannelHandlerContext ctx,            这是你必须实现的唯一抽象方法。decode()方法被调用时将会传入一个包含了传入数据的ByteBUf,
ByteBuf in,List<Object> out)                 以及一个用来添加解码信息的List。对这个方法的调用将会重复进行，直到确定没有新的元素被添加到该
                                             List,或者该ByteBuf中没有更多可读取的字节时为止。然后，如果该List不为空，那么它的内容将会被
                                             传递给ChannelPipeline中的下一个ChannelInboundHandler


decodeLast(                                  Netty提供的这个默认实现只是简单地调用了decode()方法。当Channel的状态变为非活动时，这个
ChannelHandlerContext ctx,                   方法将会被调用一次。可以重写该方法以提供特殊的处理
ByteBuf in,
List<Object> out)    
                                         
```
