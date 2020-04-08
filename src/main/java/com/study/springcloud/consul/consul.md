# docker安装consul集群
1. docker拉取consul镜像（默认最新）
 docker pull consul 

2. consul参数详解
    –net=host docker参数, 使得docker容器越过了net namespace的隔离，免去手动指定端口映射的步骤
    -server consul支持以server或client的模式运行, server是服务发现模块的核心, client主要用于转发请求
    -advertise 通告地址用于更改我们通告给集群中其他节点的地址。默认情况下，-bind地址是通告的。
    -retry-join 指定要加入的consul节点地址，失败后会重试, 可多次指定不同的地址
    -client Consul将绑定客户端接口的地址，包括HTTP和DNS服务器。默认情况下，这是“127.0.0.1”，只允许回送连接。
    -bind 内部集群通信绑定的地址。这是集群中所有其他节点都应该可以访问的IP地址。默认情况下，这是“0.0.0.0”，集群内的所有节点到地址必须是可达的
    -bootstrap-expect 此标志提供数据中心中预期服务器的数量。不应该提供此值，或者该值必须与群集中的其他服务器一致。指定后，Consul将等待指定数量的服务器可用，然后启动群集。允许自动选举leader，但不能与传统-bootstrap标志一起使用, 需要在server模式下运行。
    -data-dir 此标志为代理存储状态提供了一个数据目录。这对所有代理都是必需的。该目录在重新启动时应该是持久的。这对于在服务器模式下运行的代理尤其重要，因为它们必须能够保持群集状态。此外，该目录必须支持使用文件系统锁定，这意味着某些类型的已装入文件夹（例如VirtualBox共享文件夹）可能不合适
    -node 群集中此节点的名称，这在群集中必须是唯一的，默认情况下是节点的主机名。
    -config-dir 指定配置文件，当这个目录下有 .json 结尾的文件就会被加载
    -enable-script-checks 检查服务是否处于活动状态，类似开启心跳
    -datacenter 数据中心名称。如果未提供，则默认为“dc1”。Consul对多个数据中心拥有一流的支持，但它依赖于正确的配置。同一个数据中心内的节点应该位于单个局域网中。
    -ui - 启用内置的Web UI服务器和所需的HTTP路由。这消除了将Consul Web UI文件与二进制文件分开维护的需要。
    -join 指定ip, 加入到已有的集群中
3. 使用consul
    - 启动第一个节点, 叫consul1
    docker run --name consul1 -d -p 8500:8500 -p 8300:8300 -p 8301:8301 -p 8302:8302 -p 8600:8600 consul agent -server -bootstrap-expect 2 -ui -bind=0.0.0.0 -client=0.0.0.0 

    端口详解
    8500 : http 端口，用于 http 接口和 web ui访问；
    8300 : server rpc 端口，同一数据中心 consul server 之间通过该端口通信
    8301 : serf lan 端口，同一数据中心 consul client 通过该端口通信; 用于处理当前datacenter中LAN的gossip通信
    8302 : serf wan 端口，不同数据中心 consul server 通过该端口通信; agent Server使用，处理与其他datacenter的gossip通信；
    8600 : dns 端口，用于已注册的服务发现；
    - 查看容器ip地址
    查看consul1的ip地址  docker inspect --format='{{.NetworkSettings.IPAddress}}' consul1
    - 输出172.17.0.5
4. 启动第二个consul服务(端口8501)：consul2， 并加入consul1（使用join命令）
    docker run --name consul2 -d -p 8501:8500 consul agent -server -ui -bind=0.0.0.0 -client=0.0.0.0 -join 172.17.0.5  

5. 启动第三个consul服务(端口8502)：consul3， 并加入consul1（使用join命令）
    docker run --name consul3 -d -p 8502:8500 consul agent -server -ui -bind=0.0.0.0 -client=0.0.0.0 -join 172.17.0.5  

6. 查看consul集群信息
    docker exec -it consul1 consul members 

    // 可以看到集群里的三个节点
    Node          Address          Status  Type    Build  Protocol  DC   Segment
    618e9f617509  172.17.0.5:8301  alive   server  1.6.2  2         dc1  <all>
    6ba34e2feb66  172.17.0.6:8301  alive   server  1.6.2  2         dc1  <all>
    8cba36da0384  172.17.0.7:8301  alive   server  1.6.2  2         dc1  <all>
7. 通过访问8500/8501/8502端口查看web界面
我是在阿里云上,记得开放安全端口

http://localhost:8500  /  http://localhost:8501  /  http://localhost:8502

8. springcloud配置文件，和单机版一样只要注册到一个上面即可
```java
    ###consul服务端口号
    server:
      port: 8006
    
    spring:
      application:
        name: consul-provider-payment
    ####consul注册中心地址
      cloud:
        consul:
          host: 10.21.17.121
          port: 8502
          discovery:
            #hostname: 127.0.0.1
            service-name: ${spring.application.name}
```



