# Netty-Learning
<p align="center"><img src ="picture/logo.png" alt="Netty-Learning logo" /></p>

## Much effort,much prosperity

进度如下<br>
- [x] 【Netty in Action1】 异步和事件驱动
- [x] 【Netty in Action2】 你的第一款Netty应用程序
- [x] 【Netty in Action3】 Netty的组件和设计
- [x] 【Netty in Action4】 传输
- [x] 【Netty in Action5】 ByteBuf
- [x] 【Netty in Action6】 ChannelHandler和ChannelPipeline
- [x] 【Netty in Action7】 EventLoop和线程
- [x] 【Netty in Action8】 引导
- [x] 【Netty in Action9】 单元测试
- [x] 【Netty in Action10】 编解码框架
- [x] 【Netty in Action11】 预置的ChannelHandler和编解码器
- [ ] 【Netty in Action12】 WebSocket
- [ ] 【Netty in Action13】 使用UDP广播事件
- [ ] 【Netty in Action14】 案例研究，第一部分
- [ ] 【Netty in Action15】 案例研究，第二部分

```
在传统的Java应用中，通常使用以下4种方式进行跨节点通信。
（1）通过RMI进行远程服务调用
（2）通过Java的Socket+Java序列化的方式进行跨节点调用;
（3）利用一些开源的RPC框架进行远程服务调用，例如Facebook的Thrift,Apache的Avro等；
（4）利用标准的共有协议进行跨节点服务调用，例如HTTP+XML.RESTful+JSON或者WebService.
```

## Netty协议栈功能设计<br/>
## 协议栈功能描述<br/>
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
## ChannelHandler和ChannelPipeline
<br/>

### ChannelHandler家族

<br/>

### Channel的生命周期

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

### ChannelHandler的生命周期

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

### ChannelInboundHandler接口
 
<br/>

### ChannelOutboundHandler接口

<br/>

###6.1.5 ChannelHandler适配器

<br/>

### 6.1.6 资源管理

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
 ChannelPipeLine接口
```
  每一个新创建的Channel都将会被分配一个新的ChannelPipeline。这项关联是永久性的;Channel既不能
附加另外一个ChannelPipeline,也不能分离其当前的。在Netty组件的生命周期中，这是一项固定的操作，不需要
开发人员的任何干预。
```
 ChannelHandlerContext接口
```
  ChannelHandlerContext代表了ChannelHandler和ChannelPipeline之间的关联，每当有ChannelHandler
添加到ChannelPipeline中时，都会创建ChannelHandlerContext。ChannelHandlerContext的主要功能是管理
它所关联的ChannelHandler和在同一个ChannelPipeline中的其他ChannelHandler之间的交互。
  ChannelHandlerContext有很多的方法，其中一些方法也存在于Channel和ChannelPipeline本身上，但是有一点重要的不同。
如果调用Channel或者ChannelPipeline上的这些方法，它们将沿着整个ChannelPipeline进行传播。而调用位于ChannelHandler
Context上的相同方法，则将从当前所关联的ChannelHandler开始，并且只会传播给位于该ChannelPipeline中的下一个能够处理
该事件的ChannelHandler。
  对ChannelHandlerContext　API进行了总结
     方法名称                           描述
alloc                             返回和这个实例相关联的Channel所配置的ByteBufAllocator
bind                              绑定到给定的SocketAddress,并返回ChannelFuture
channel                           返回绑定到这个实例的Channel
close                             关闭Channel,并返回ChannelFuture
connect                           连接给定的SocketAddress,并返回ChannelFuture
deregister                        从之前分配的EventExecutor注销，并返回ChannelFuture
disconnect                        从远程节点断开，并返回ChannelFuture
executor                          返回调度事件的EventExecutor
fireChannelActive                 触发对下一个ChannelInboundHandler上的channelActive()(已连接)的调用
fireChannelInactive               触发对下一个ChannelInboundHandler上的channelInactive()(已关闭)的调用
fireChannelRead                   触发对下一个ChannelInboundHandler上的channelRead()(已接受的消息)的调用
fireChannelReadComplete           触发对下一个ChannelInboundHandler上的channelReadComplete()方法的调用
fireChannelRegistered             触发对下一个ChannelInboundHandler上的fireChannelRegistered()方法的调用
fireChannelUnregistered           触发对下一个ChannelInboundHandler上的fireChannelUnregistered()方法的调用
fireChannelWritabilityChanged     触发对下一个ChannelInboundHandler上的fireChannelWritabilityChanged()方法的调用
fireExceptionCaught               触发对下一个ChannelInboundHandler上的fireExceptionCaught(Throwable)方法的调用
fireUserEventTriggered            触发对下一个ChannelInboundHandler上的fireUserEventTriggered(Object evt)方法的调用
handler                           返回绑定到这个实例的ChannelHandler
isRemoved                         如果所关联的ChannelHandler已经被从ChannelPipeline中移除则返回true
name                              返回这个实例的唯一名称
pipeline                          返回这个实例所关联的ChannelPipeline
read                              将数据从Channel读取到第一个入站缓冲区;如果读取成功则触发一个channelRead事件，并(在最后一个消息被读取完成后)
                                  通知ChannelInboundHandler的channelReadComplete(channelReadComplete)(ChannelHandlerContext)方法
write                             通过这个实例写入消息并经过ChannelPipeline
writeAndFlush                     通过这个实例写入并冲刷并经过ChannelPipeline

当使用ChannelHandlerContext的API的时候，请牢记以下2点：
  ChannelHandlerContext 和 ChannelHandler之间的关联(绑定)是永远不会改变的，所以缓存对它的应用是安全的;
  如同我们在本节开头所解释的一样，相对于其他类的同名方法，ChannelHandlerContext的开头将产生更短的事件流，
应该尽可能地利用这个特性来获得最大的性能。
```
ChannelHandler和ChannelHandlerContext的高级用法
<br />
描述：

```
  为什么会想要从ChannelPipeline中的某个特定点开始传播事件呢?
为了减少将事件传经对它不感兴趣的ChannelHandler所带来的开销。
为了避免将事件传经那些可能会对它感兴趣的ChannelHandler。
  要想调用从某个特定的ChannelHandler开始的处理过程，必须获取到在(ChannelPipeline)该ChannelHandler之前的ChannelHandler所关联的
ChannelHandelerContext。这个ChannelHandlerContext将调用和它所关联的ChannelHandler之后的ChannelHandler

你可以调用ChannelHandlerContext上的pipeline()方法来获得被封闭的ChannelPipeline的引用。这使得运行时得以操作ChannelPipeline的
ChannelHandler，我们可以利用这一点来实现一些复杂的的设计。例如，你可以通过将ChannelHandler添加到ChannelPipeline中来实现动态的
协议转换。
另一种高级的用法是缓存到ChannelHandlerContext的引用以供稍后使用，这可能会发生在任何的ChannelHandler方法之外，甚至来自于
不同的
因为一个ChannelHandler可以从属于多个ChannelPipeline,所以它也可以绑定到多个ChannelHandlerContext实例。对于这种用法指在多个
ChannelPipeline中共享同一个ChannelHandler,对应的ChannelHandler必须要使用@Sharable注解标注;否则，试图将它添加到多个
ChannelPipeline时将会触发异常。显而易见，为了安全地被用于多个并发的Channel(即连接),这样的ChannelHandler必须是线程安全的。
```

异常处理
- 处理入站异常
- 处理出站异常

```
  你应该如何响应异常，可能很大程度上取决于你的应用程序。你可能想要关闭Channel(和连接)，也可能会尝试进行恢复。如果你不实现
任何处理入站异常的逻辑(或者没有消费改异常)，那么Netty将会记录改异常没有被记录的事实。
总结一下:
   ChannelHandler.execptionCaught()的默认实现是简单地将当前异常转发给ChannelPipeline中的下一个ChannelHandler;
   如果异常到达了ChannelPipeline的尾端，它将会被记录为未被处理；
   要想定义自定义的处理逻辑，你需要重写exceptionCaught()方法。然后你需要决定是否需要将该异常传播出去
   
   
  用于处理出站操作中的正常完成以及异常的选项，都基于以下的通知机制。
每个出站操作都将返回一个ChannelFuture。注册到ChannelFuture的ChannelFutureListener将在操作完成时被通知该操作是成功了还是出错了。
几乎所有的ChannelOutboundHandler上的方法都会传入一个ChannelPromise的实例。作为ChannelFuture的子类,ChannelPromise也可以被分配用于
异步通知的监听器。但是,ChannelPromise还具有提供立即通知的可写方法:
  ChannelPromise setSuccess();
  ChannelPromise setFailure(Throwable cause);

添加ChannelFutureListener只需要调用ChannelFuture实例上的addListener(ChannelFutureListener)方法，并且有2种不同的方式可以做到
这一点。其中最常用的方式是，调用出站操作(如write()方法)所返回的ChannelFuture上的addListener()方法。
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
编码器
- 抽象类MessageToByteEncoder
- 抽象类MessageToMessageEncoder
<hr/>

抽象的编解码器类
<br/>
抽象类ByteToMessageCodec                     

```
  让我们来研究这样的一个场景：我们需要将字节解码为某种形式的消息，可能是POJO，随后再次对它进行编码。
ByteToMessageCodec将为我们处理好这一切，因为它结合了ByteToMessageDecoder以及它的逆向--
MessageToByteEncoder。
   任何的请求/响应协议都可以作为使用ByteToMessageCodec的理想选择。例如，在某个SMTP的实现中，
编解码器将读取传入字节，并将它们解码为一个自定义的消息类型，如smtpRequest.在接受端，当一个响应被创建
时，将会产生一个smtpResponse,其将被编码回字节以便进行传输。
```
抽象类MessageToMessageCodec

```
  你看到了一个扩展了MessageToMessageEncoder以将一种消息格式转换为另外一种消息格式的例子。通过
使用MessageToMessageCodec，我们可以在一个单一的类中实现该转换的往返过程。MessageToMessageCodec
是一个参数化的类，定义如下:
public abstract class MessageToMessageCodec<INBOUND_IN,OUTBOUND_IN>
```

CombinedChannelDuplexHandler类

```
  结合一个解码器和编码器可能会对可重用性造成影响。但是，有一种方法既能够避免这种惩罚，又不会牺牲将一个解码器和一个编码器作为一个
单元的单元部署所带来的便利性。CombinedChannelDuplexHandler提供了这个解决方案,其声明为:
 public class CombinedChannelDuplexHandler
 <I extends ChannelInboundHandler,O extends ChannelOutboundHandler>
```
 EventLoop和线程模型
- 线程模型概述
- 事件循环的概念和实现
- 任务调度
- 实现细节


子类的声明如下:

```
public class Bootstrap
   extends AbstractBootstrap<Bootstrap,Channel>
```
和
```
public class ServerBootstrap
   extends AbstractBootstrap<ServerBootstrap,ServerChannel>
```

引导客户端和无连接协议
Bootstrap类被用于客户端或者使用了无连接协议的应用程序中。

```
   名称                                       描述
Bootstrap group(EventLoopGroup)        设置用于处理Channel所有事件的EventLoopGroup
                                       
Bootstrap channel(                     channel()方法指定了Channel的实现类。如果该实现
    class<?,extends c>)                类没提供默认的构造函数，可以通过调用channelFactory()
Bootstrap channelFactory(              方法来指定一个工厂类,它将会被bind()方法调用
    ChannelFactory<? extends C>)
    
Bootstrap localAddress(                指定Channel应该绑定到的本地地址。如果没有指定，则将由
    SocketAddress)                     操作系统创建一个随机的地址。或者，也可以通过bind()或者
                                       connect()方法指定localAddress
    
<T> Bootstrap option(                  设置ChannelOption,其将应用到每个新创建的Channel的
    ChannelOption<T> option,           ChannelConfig。这些选项将会通过bind()或者connect()
    T value)                           设置到Channel,不管哪个先被调用。这个方法在Channel已经
                                       被创建后再调用将不会有任何的效果。支持的ChannelOption取决于
                                       使用的Channel类型。
                                       
<T> Bootstrap attr(                    指定新创建的Channel的属性值。这些属性值是通过bind()或者
    Attribute<T> key,T value)          connect()方法设置到Channel的。具体取决于谁最先被调用。这个
                                       方法在Channel被创建后将不会有任何的效果
 
Bootstrap                              设置将被添加到ChannelPipeline以接受事件通知的ChannelHandler
handler(ChannelHandler)


Bootstrap clone()                      创建一个当前Bootstrap的克隆,其具有和原始的Bootstrap相同的设置信息

Bootstrap remoteAddress(               设置远程地址。或者，也可以通过connect()方法来指定它
   SocketAddress)
   
ChannelFuture connect()                连接到远程节点并返回一个ChannelFuture,其将会在连接操作完成后接受到通知

ChannelFuture bind()                   绑定Channel并返回一个ChannelFuture,其将会在绑定操作完成后接受到通知，
                                        在那之后必须调用Channel.connect()方法来建立连接
```

引导服务器

```
名称                                              描述
group                                  设置ServerBootstrap要用的EventLoopGroup。这个EventLoopGroup将用于ServerChannel
                                       和被接受的子channel的I/O处理

channel                                设置将要被实例化的ServerChannel类

channelFactory                         如果不能通过默认的构造函数创建Channel,那么可以提供一个ChannelFactory

localAddress                           指定ServerChannel应该绑定到的本地地址。如果没有指定，则将有操作系统使用一个随机地址。或者，
                                       可以通过bind()方法来指定该localAddress

option                                 指定要应用到新创建的ServerChannel的ChannelConfig的ChannelOption。这些选项将会通过bind()
                                       方法设置到Channel。在bind()方法被调用之后，设置或者改变ChannelOption都不会有任何的效果。所支持
                                       的ChannelOption都不会有任何的效果。所支持的ChannelOption取决于所使用的Channel类型。
                                       
childOption                            指定当子Channel被接受时，应用到子Channel的ChannelConfig的ChannelOption。所支持的ChannelOption
                                       取决于所使用的Channel的类型。参见正在使用的ChannelConfig的API文档
                                       
attr                                   指定ServerChannel上的属性，属性将会通过bind()方法设置给Channel。在调用bind()方法之后改变它们将不会
                                       有任何的效果
                                       
childAttr                              将属性设置给已经被接受的子Channel。接下来的调用将不会有任何的效果

handler                                设置被添加给ServerChannel的ChannelPipeline中的ChannelHandler。

childHandler                           设置将被添加到已将接受的子Channel的ChannelPipeline中的ChannelHandler。handler()方法和childHander()
                                       方法之间的区别是:前者所添加的ChannelHandler由接受子Channel的ServerChannel处理，而childHandler()方法所
                                       添加的ChannelHandler将由已被接受的子Channel处理，其代表一个绑定到远程节点的套接字
                                       
clone                                  克隆一个设置和原始的ServerBootstrap相同的ServerBootstrap

bind                                   绑定ServerChannel并且返回一个ChannelFuture，其将会在绑定操作完成后收到通知(带着成功或者失败的结果)
```

## 单元测试
ChannelHandler是Netty应用程序的关键元素，所以彻底地测试它们应该是你的开发过程的一个标准部分。最佳实践要求你的测试不仅要能够证明你的实现是正确的，
<br/>
而且还要能够很容易地隔离那些因修改代码而突然出现的问题。这种类型的测试叫做单元测试。
<br/>
虽然单元测试没有统一的定义，但是大多数的从业者都有基本的共识。其基本思想是,以可能小的区块测试你的代码，并且尽可能地和其他的代码模块以及运行时的依赖
<br/>
(如数据库和网络)相隔离。如果你的应用程序能通过测试验证每个单元本身都能够正常的工作，那么在出了问题时将可以更加容易地找出根本原因。
<br/>
在本章中，我们将学习一种特殊的Channel实现--EmbeddedChannel,它是Netty专门为改进针对ChannelHandler的单元测试而提供的。
<br/>
因为正在被测试的代码模块或者单元将在它正常的运行时环境之外被执行，所以你需要一个框架或者脚手架以便在其中运行它。在我们的例子中。我们将使用JUnit4
<br/>
作为我们的测试框架，所以你需要对它的用法有一个基本的了解。如果它对你来说比较陌生，不要害怕；虽然它功能强大，但却很简单，

```
  Netty提供了它所谓的Embedded传输，用于测试ChannelHandler。这个传输是一种特殊的Channel实现 - -EmbeddedChannel- -的功能，这个实现提供了通过ChannelPineline
传播事件的简便方法。
  这个想法是直截了当的:将入站数据或者出站数据写入到EmbeddedChannnel中，然后检查是否有任何东西到达了ChannelPipeline的尾端。以这种方式，你便可以确定消息是否
已经被编码或者被解码过了，以及是否触发了任何的ChannelHandler动作。
下表列出了EmbeddedChannel的相关方法。
名称                                                职责
writeInbound(                            将入站信息写到EmbeddedChannel中。如果可以通过readInbound()方法从EmbeddedChannel中读取数据，则返回true
     Object.....msgs)

readInbound()                           从EmbeddedChannel中读取一个入站信息。任何返回的东西都穿越了整个ChannelPipeline。如果没有可供读取的东西，则返回null

writeOutbound(                          将出站消息写到EmbeddedChannel中。如果现在可以通过readOutbound()方法从EmbeddedChannel中读取到什么东西，则返回true
     Object ... msgs)                  

readOutbound()                          从EmbeddedChannel中读取一个出站消息。任何返回的东西都穿越了整个ChannelPipeline。如果没有任何可供读取的，则返回null

finish()                                将EmbeddedChannel标记为完成，并且如果有可被读取的入站数据或者出站数据，则返回true。这个方法还将会调用EmbeddedChannel上的
                                        close()方法


    入站数据由ChannelInboundHandler处理，代表从远程节点读取的数据。出站数据由ChannelOutboundHandler处理，代表将要写到远程节点的数据
根据你要测试的ChannelHandler,你将使用*Inbound()或者*Outbound(）方法对，或者兼而有之。
    使用EmbeddedChannel的方法，数据是如何流经ChannelPipeline的。你可以使用writeOutbound()方法将消息写到Channel中，并通过ChannelPipeline沿着出站的方向传递。随后，
你可以使用readOutbound()方法来读取已被处理过的消息，以确定结果是否和预期一样。类似地，对于入站数据，你需要使用writeInbound()和readInbound()方法。
    在每种情况下，消息都将会传递过ChannelPipeline,并且被相关的的ChannelInboundHandler或者ChannelOutboundHandler处理。如果消息没有被消费，那么你可以使用readInbound()
或者readOutbound()方法来在处理过了这些消息之后，酌情把它们从Channel中读出来。
```
## 预置的ChannelHandler和编解码器

```
   通过SSL/TLS保护Netty应用程序
   我们应该熟悉像SSL和TLS这样的安全协议，它们层叠在其他协议之上，用以实现数据安全。我们在访问安全网站时遇到过这些协议，
但是它们也可用于其他不是基于HTTP的应用程序，如安全SMTP(SMTPS)邮件服务器甚至是关系型数据库。
   为了支持SSL/TLS,Java提供了Javax.net.ssl包，它的SSLContext和SSLEngine类使得实现解密和加密相当简单直接。Netty通过一个名
为SslHandler的ChannelHandler实现利用了这个API,其中SslHanlder在内部使用SSLEngine来完成实际的工作。
   Netty的OpenSSL/SSLEngine实现
   Netty还提供了使用了OpenSSL工具包(www.openssl.org)的SSLEngine实现。这个OpenSslEngine类提供了比JDK提供的SSLEngine实现更
好的性能。
   如果OpenSSL库可用，可以将Netty应用程序(客户端和服务器)配置为默认使用OpenSslEngine。如果不可用，Netty将会回退到JDK实现。
有关配置OpenSSL支持的详细说明，参见Netty文档:
   注意,无论你使用JDK的SSLEngine还是使用Netty的OpenSslEngine,SSL API和数据流都是一致的。

```

<p align="center"><img src ="picture/OpenSSL.PNG" alt="OpenSSL logo" /></p>