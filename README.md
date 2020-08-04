# starfish

## 研究
- 数据库
- 设计模式
- 主从集群调优 分布式 集群 大数据 高并发 海量分布式事务的处理策略

## 问题
- 打印日志工具类，支持打印异常堆栈
- 使用java config方式加载的bean如何解决初始化等问题

## 知识
- Future V get(long timeout, TimeUnit unit)方法是从get的时候开始算超时时间的，而不是从submit的时候开始计算的。
- Integer.compare(o1, o2) 排序是从小到大
- Spring BeanUtils.copyProperties();拷贝属性的时候，model类型不一致时可以拷贝，但是model中的类型不一致时无法拷贝，会设置为null
- List中不能存放float类型数据，需要设置为Float
- JSON.toJSONString(a)，参数中a为null时不会报错
- Result.class.getDeclaredClasses()，返回一个反映所有被这个类对象表示的类的成员声明的类和类对象的数组。简单的说就是返回类中定义的公共、私有、保护的内部类。
- Result.class.getClasses()，返回类定义的公共的内部类,以及从父类、父接口那里继承来的内部类
