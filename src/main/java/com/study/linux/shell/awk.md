# awk
## 定义
awk是行处理器: 相比较屏幕处理的优点，在处理庞大文件时不会出现内存溢出或是处理缓慢的问题，通常用来格式化文本信息
awk处理过程: 依次对每一行进行处理，然后输出
awk命令形式:
awk [-F|-f|-v] ‘BEGIN{} //{command1; command2} END{}’ file
## 常用参数
特殊要点:

$0           表示整个当前行

$1           每行第一个字段

NF          字段数量变量

NR          每行的记录号，多文件记录递增

FNR        与NR类似，不过多文件记录不递增，每个文件都从1开始

\t            制表符

\n           换行符

FS          BEGIN时定义分隔符

RS       输入的记录分隔符， 默认为换行符(即文本是按一行一行输入)

~            匹配，与==相比不是精确比较

!~           不匹配，不精确比较

==         等于，必须全部相等，精确比较

!=           不等于，精确比较

&&　     逻辑与

||             逻辑或

+            匹配时表示1个或1个以上

/[0-9][0-9]+/   两个或两个以上数字

/[0-9][0-9]*/    一个或一个以上数字

FILENAME 文件名

OFS       输出字段分隔符， 默认也是空格，可以改为制表符等

ORS         输出的记录分隔符，默认为换行符,即处理结果也是一行一行输出到屏幕

-F'[:#/]'   自定义三个分隔符 单引号可要可不要

“” 双引号里面的特殊字符不会转换
‘’ 单引号里面的特殊字符会转换
`` 执行里面的命令


测试：
awk '{print}'  /etc/passwd   ==   awk '{print $0}'  /etc/passwd

awk -F: '{print $1,$2}' OFS="\t" /etc/passwd

awk -F: '{print $NF}' /etc/passwd                              //将每行第NF个字段的值打印出来

awk '{print NR,$0}' /etc/passwd                                 //输出每行的行号

awk -F: 'NR==5 || NR==6{print}'  /etc/passwd       //显示第5行和第6行

awk -F: '$1~/mail/{print $1}' /etc/passwd = awk -F: '{if($1~/mail/) print $1}' /etc/passwd

awk -F: '{if($1~/mail/) {print $1} else {print $2}}' /etc/passwd