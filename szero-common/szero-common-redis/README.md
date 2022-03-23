# @Lock 注解

@Lock注解参数说明（此注解使用在需要加锁的方法上）

@Lock可以标注的参数，作用分别如下
1. name：lock的name，对应redis的key值。默认为：类名+方法名+指定参数，如指定了名称，则直接使用指定名称
2. nameIsSpel: name是否为spel表达式，默认值false
3. lockType：锁的类型，目前支持（公平锁LockType.FAIR，可重入锁LockType.REENTRANT，读锁LockType.READ，写锁LockType.WRITE，红锁LockType.RED，联锁LockType.MULTI）。默认为：公平锁
4. waitTime：获取锁最长等待时间。默认为：60s。
5. leaseTime：获得锁后，自动释放锁的时间。默认为：60s。设置值为-1，会根据程序执行时间自动延长过期时间。
6. keys：自定义业务Key，针对参数为对象，可使用此种方式进行声明，如：keys = {"#user.name","#user.id"}

@LockKey注解参数说明（此注解使用在需要加锁方法的参数上）

@LockKey可以标注的参数如下：
1. value：spel表达式对应的参数名，默认为方法参数名称


# Redission