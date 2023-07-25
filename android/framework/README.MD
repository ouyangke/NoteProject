# Framework笔记

## Binder

### 跨进程通讯实现方式

- Android:
    - intent+bundle(activity、Service、Broadcast之间启动传参,通过序列化bundle实现,支持数据类型有限)
    - ContentProvider(跨进程数据共享,主要用于CRUD,支持1vN)
    - AIDL(主要用于Service通讯,基于binder,即时高效,1vN并发)
- Linux:
    - 管道(半双工,单向)
    - socket(全双工,双向)
    - 信号(单向)
    - 共享内存(效率高,0拷贝,需要处理并发问题)

### 跨进程通讯方式对比

- 共享内存(0次拷贝,有并发问题)
- Binder(1次拷贝,基于C/S架构,实现复杂)
- socket、管道(2次拷贝,C/S架构,使用简单)

### AIDL

- 使用
    - 服务端新建AIDL文件
    - build或编译生产对应的java文件,文件中自动生成了stub类与定义的方法
    - 新建Service,在其中自定binder继承stub抽象类并实现其方法,返回binder
    - 拷贝带路径的服务端AIDL文件到客户端
    - 在客户端绑定服务并且获得binder代理,通过代理调用方法
- 参数为对象时注意事项
    - 对象参数必须实现Parcelable接口
    - 对象参数需要in(C->S单向流通),out(C<-S单向),inout(C<->S双向流通)三个当中的其中一个修饰符
    - 需要新建一个对象.aidl,在其中声明对象为parcelable
    - 在使用到对象参数的aidl文件导入对象类
### 

## Handler

## Zygote

## AMS

## WMS

## PMS

