
### 打印基本GC信息
```shell
-XX:+PrintGCDetails -XX:+PrintGCDateStamps
```

### 打印对象分布
```shell
-XX:+PrintTenuringDistribution
```

输出示例
```shell
Desired survivor size 59244544 bytes, new threshold 15 (max 15)
- age   1:     963176 bytes,     963176 total
- age   2:     791264 bytes,    1754440 total
- age   3:     210960 bytes,    1965400 total
- age   4:     167672 bytes,    2133072 total
- age   5:     172496 bytes,    2305568 total
- age   6:     107960 bytes,    2413528 total
- age   7:     205440 bytes,    2618968 total
- age   8:     185144 bytes,    2804112 total
- age   9:     195240 bytes,    2999352 total
- age  10:     169080 bytes,    3168432 total
- age  11:     114664 bytes,    3283096 total
- age  12:     168880 bytes,    3451976 total
- age  13:     167272 bytes,    3619248 total
- age  14:     387808 bytes,    4007056 total
- age  15:     168992 bytes,    4176048 total
```

### GC后打印堆数据
```shell
-XX:+PrintHeapAtGC
```

### 打印STW时间
```shell
-XX:+PrintGCApplicationStoppedTime
```

### 打印Safepoint信息
```shell
-XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1
```

### 打印References处理信息
```shell
-XX:+PrintReferenceGC
```

### 完整参数
```shell
# requireds
-XX:+PrintGCDetails 
-XX:+PrintGCDateStamps 
-XX:+PrintTenuringDistribution 
-XX:+PrintHeapAtGC 
-XX:+PrintReferenceGC 
-XX:+PrintGCApplicationStoppedTime

# optional
-XX:+PrintSafepointStatistics 
-XX:PrintSafepointStatisticsCount=1
```

### GC日志的分割
```shell
# GC日志输出的文件路径
-Xloggc:/path/to/gc.log
# 开启日志文件分割
-XX:+UseGCLogFileRotation 
# 最多分割几个文件，超过之后从头开始写
-XX:NumberOfGCLogFiles=14
# 每个文件上限大小，超过就触发分割
-XX:GCLogFileSize=100M
```

### 使用时间戳命名文件
```shell
# 使用-%t作为日志文件名
-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/path/to/gc-%t.log

# 生成的文件名是这种：gc-2021-03-29_20-41-47.log
```

### 两者结合
```shell
# GC日志输出的文件路径
-Xloggc:/path/to/gc-%t.log
# 开启日志文件分割
-XX:+UseGCLogFileRotation 
# 最多分割几个文件，超过之后从头开始写
-XX:NumberOfGCLogFiles=14
# 每个文件上限大小，超过就触发分割
-XX:GCLogFileSize=100M
```

### 最佳实践
```shell
# 必备
-XX:+PrintGCDetails 
-XX:+PrintGCDateStamps 
-XX:+PrintTenuringDistribution 
-XX:+PrintHeapAtGC 
-XX:+PrintReferenceGC 
-XX:+PrintGCApplicationStoppedTime

# 可选
-XX:+PrintSafepointStatistics 
-XX:PrintSafepointStatisticsCount=1

# GC日志输出的文件路径
-Xloggc:/path/to/gc-%t.log
# 开启日志文件分割
-XX:+UseGCLogFileRotation 
# 最多分割几个文件，超过之后从头文件开始写
-XX:NumberOfGCLogFiles=14
# 每个文件上限大小，超过就触发分割
-XX:GCLogFileSize=100M
```

### ss
```shell
-XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/log/k8sapplication/gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=1M
```

## 参考
> https://www.jianshu.com/p/d4e690085c54
> 
> https://juejin.cn/post/6844903793021157383
> 
> https://blog.csdn.net/austin1000/article/details/110635926


## JVM 调优

JVM内存参数设置
- -Xms设置堆的最小空间大小
- -Xmx设置堆的最大空间大小
- -Xmn:设置年轻代大小
- -XX:NewSize设置新生代最小空间大小。
- -XX:MaxNewSize设置新生代最大空间大小
- -XX:PermSize设置永久代最小空间大小
- -XX:MaxPermSize设置永久代最大空间大小
- -Xss设置每个线程的堆栈大小
- -XX:+UseParallelGC:选择垃圾收集器为并行收集器。此配置仅对年轻代有效。即上述配置下,年轻代使用并发收集,而年老代仍旧使用串行收集
- -XX:ParallelGCThreads=20:配置并行收集器的线程数,即:同时多少个线程一起进行垃圾回收。此值最好配置与处理器数目相等

典型JVM参数配置
- java -Xmx3550m -Xms3550m -Xmn2g -Xss128k
- -XX:ParallelGCThreads=20
- -XX:+UseConcMarkSweepGC-XX:+UseParNewGC

> https://cloud.tencent.com/developer/article/1508183
> 
> https://aijishu.com/a/1060000000080316
> 
> https://blog.csdn.net/weixin_40792878/article/details/86563969
> 
> https://blog.csdn.net/luzhensmart/article/details/82563734
> 
> https://www.jianshu.com/p/9c4e36eae9b2