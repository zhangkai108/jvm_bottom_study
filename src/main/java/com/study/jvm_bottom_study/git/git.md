# GIT绿皮书
## 第一次需要的准备工作
1. 下载 git下载Git软件：https://git-scm.com/downloads
2. 安装一直下一步，安装好右击打开Git Bash
3. 配置用户名
   1. git config --global user.name "user.name"
   2. git config --global user.email "yourmail@youremail.com.cn"
4. 查看是否存在密钥

    cd ~/.ssh  若出现“No such file or directory”,则表示需要创建一个ssh keys。

    如果什么都不出现能切换到.ssh目录说明已经创建过ssh密钥，则cd - 切换到原来目录，并跳过第5步

5. 创建ssh keys（不然git不能上传文件）
   1. ssh-keygen -t rsa -C "你的邮箱名"
   2. 指定目录: C:\deskbook\（说明：若在此处不输入路径，而直接按回车，则ssh keys生成后存放的路径为C:\User\.ssh）
   3. 输入密码: 123456
   4. 确认密码: 123456
   5. 如此即可在C:\User\下生成ssh keys。包括两个文件rd_rsa和id_rsa.pub
   6. 然后找到rd_rsa和id_rsa.pub所在目录打开idb_rsa.pub（可以把后缀名改成.txt),登录自己的GitHub账号，找到Settings,
   进入Settings后,点击SSH and GPG keys,然后再点击右上角添加新密钥按钮New SSH key,
   然后，将idb_rsa.pub里的内容拷贝到Key内，Title内容随便填，确定即可。
   密钥添加完成，下次在使用时就不需要再添加密钥了。
## 上传项目
1. git上创建一个项目名为空的文件夹（不要默认README文件，不然你第一次上传还需要合并分支）
2. 将项目删除到只剩要上传的文件（src+pom）
3. 进入项目目录
4. git init
5. git add .
6. git commit -m "first commit"
7. git remote add origin git地址
8. git push -u origin master

git status 查看文件状态





