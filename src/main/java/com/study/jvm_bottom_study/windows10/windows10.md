#系统相关
##win10系统安装
1. 8g U盘官网制作启动盘，[官网地址](https://www.microsoft.com/zh-cn/software-download/windows10)；
2. 点击立即下载工具制作启动盘，制作的时候要将关于本机windows更新等操作都去除掉；
3. 插到要安装系统的电脑上，联想长按F12（F2进入BIOS界面，然后点击ROOTS菜单，进入U盘，但有时候不管就按F12试试吧）；
4. 安装完激活去咸鱼上买激活买（我的台式机是YQJVP-PTPFY-P3Y7G-CTKWG-7CGC2），可以试试。
##Office安装
1. 先去[msdn](https://msdn.itellyou.cn/),通过百度云下载；








##git
1. 这一步是配置上传文件的用户名，并不是与真正的git账户名密码一样，防止混淆，一般都取一样的：
	- git config --global user.name "用户名"
	- git config --global user.password "密码"
	- git config --global user.email "邮箱"
2. 配置自动读取账户名和密码，不然每次pull/push都要手动输入账户名密码，比较麻烦：
	- git config --global credential.helper store
	- git pull /git push (第一次输入，后续就不用再次数据)

push你的代码 (git push), 这时会让你输入用户名和密码, 这一步输入的用户名密码会被记住, 下次再push代码时就不用输入用户名密码 ! 这一步会在用户目录下生成文件.git-credential记录用户名密码的信息。