# docker 
## docker安装好一定要配置镜像加速器，不然下载东西会失败，阿里加速器地址 
- https://www.aliyun.com/product/acr?spm=5176.202918.1263681.2.1bf54ef52m9jSF，往下拖点击管理控制平台；
- https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors ==> 镜像中心 ==> 镜像加速
    
    
## docker安装mysql
1. docker search mysql 寻找要安装的版本 如果直接写docker pull mysql则默认最新版本
2. docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs  -v $PWD/data:/mysql_data -e MYSQL_ROOT_PASSWORD=root123 -d centos/mysql-57-centos7 创建运行实例
    - -p 3306:3306：将容器的 3306 端口映射到主机的 3306 端口
    - -v $PWD/conf:/etc/mysql/conf.d：将主机当前路径下的conf目录挂载到容器的/etc/mysql/conf.d目录
    - -v $PWD/logs:/logs：将主机当前目录下的logs目录挂载到容器的/logs
    - -v $PWD/data:/mysql_data：将主机当前目录下的data目录挂载到容器的/mysql_data
    - -e MYSQL_ROOT_PASSWORD=root123：初始化root用户的密码
    - -d 让容器在后台运行
3. 进入mysql容器
docker exec -it id /bin/bash 不知为何mysql（应该是字符集的问题） -uroot -proot123进入不了, mysql -uroot可以进，进去后创建新用户并赋权，
- CREATE USER 'zk'@'%' IDENTIFIED BY 'root123';
- GRANT ALL ON *.* TO 'zk'@'%';
- flush privileges;
- 这样zk用户就可以通过密码进入


## docker 部署springboot hello项目
1. 配置Docker的远程访问
    - vim /lib/systemd/system/docker.service
    - 将
      ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
      替换为
      ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
    - systemctl daemon-reload
      systemctl restart docker
    - curl http://127.0.0.1:2375/version
2. IDEA中配置Docker插件信息
    - ![idea-docker-setting](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-setting1.png)
    - ![idea-docker-setting](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-setting2.png)
    - ![idea-docker-setting](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-setting3.png)
3. 集成Maven插件
    - 修改项目的pom文件
    ```java
    <properties>
    		<!--docker镜像的前缀-->
    		<docker.image.prefix>docker</docker.image.prefix>
    </properties>
    <plugin>
    	<groupId>com.spotify</groupId>
    	<artifactId>docker-maven-plugin</artifactId>
    	<version>1.0.0</version>
    
    	<configuration>
    		<!--远程Docker的地址-->
    		<dockerHost>http://服务器地址:2375</dockerHost>
    		<!--镜像名称，前缀/项目名-->
    		<imageName>${docker.image.prefix}/${project.artifactId}</imageName>
    		<dockerDirectory>src/main/docker</dockerDirectory>
    		<resources>
    			<resource>
    				<targetPath>/</targetPath>
    				<directory>${project.build.directory}</directory>
    				<include>${project.build.finalName}.jar</include>
    			</resource>
    		</resources>
    	</configuration>
    </plugin>
   ```
   - 在src的main下新建docker文件夹，将编写好的Dockerfile放到这个文件夹
   ```java
   # 基于java镜像创建新镜像
   FROM java:8
   # 挂载
   VOLUME /tmp
   # 将jar包添加到容器中并更名为app.jar
   ADD  dockerhello-0.0.1-SNAPSHOT.jar /app.jar
   # 运行jar包
   ENTRYPOINT ["nohup","java","-jar","/app.jar","&"]
    ```
4. 构建镜像
    - 依次使用 clean、package、docker:build 命令
    ![idea-docker-build](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-build.png)
    - 查看Docker控制台:services里的docker
    ![docker-services.png](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-services.png)
5. 创建容器
    - Docker控制台选中镜像右键，点击创建镜像，填写参数，点击run
    ![docker-container1.png](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-container1.png)
    ![docker-container2.png](https://gitee.com/zhangkai108/jvm_bottom_study/blob/master/src/main/resources/mdImages/docker-container2.png)
    - 这里点击run，启动container的时候可能会出现出现iptables: No chain/target/match by that name。容器启动失败的问题。只需要重启一下docker服务就行。
     systemctl restart docker
    
    
   