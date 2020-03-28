#for循环
let 后面指定算数运算符
``` shell script
!/bin/bash
sum=0;
for((i=0;i<100;i++))
do
let "sum+=i";
done
echo "sum:$sum";
```
