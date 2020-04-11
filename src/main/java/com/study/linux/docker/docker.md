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



docker run  -itd -p 8700:8700 helloworld运行已经打包好的项目镜像

    
    
   