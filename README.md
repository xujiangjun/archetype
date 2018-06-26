- 骨架工程使用《阿里巴巴开发手册》中推荐的应用分层结构，模块依赖简单说明：

> 1. Web 层：主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等。
> 2. Service 层：相对具体的业务逻辑服务层。
> 3. Manager 层：通用业务处理层，它有如下特征:
>    - 对第三方平台封装的层，预处理返回结果及转化异常信息;
>    - 对Service层通用能力的下沉，如缓存方案、中间件通用处理; 
>    - 与DAO层交互，对多个DAO的组合复用。
> 4. DAO 层：数据访问层，与底层 MySQL、Oracle、Hbase 等进行数据交互。

- 创建和使用骨架工程：

> 1. 创建maven多模块项目，将项目调整为自己想要的骨架工程结构。
> 2. 在根目录archetype执行命令：
> `mvn archetype:create-from-project`
> 3. 修改target/generated-sources/archetype/src下的web模块pom文件，修改finalName、另修改META-INF/maven/archetype-metadata.xml。
> 4. 进入目录target/generated-sources/archetype执行命令：
> `mvn clean install`
> 5. 在需要生成项目的地方执行命令：
> `mvn archetype:generate -DarchetypeCatalog=local`
> 6. 根据骨架工程生产新的工程后需要作出一些修改：去除api模块pom文件的parent的引用、拷贝.gitignore文件至新项目、修改finalName、修改sql的文件名、修改新项目中各模块的版本号等等，最后全局搜索archetype，看是否有还需替换的东西。

- 日志打印规范：

> 1. info级别的日志用于记录调用情况、参数信息和返回值的情况。
> 2. warn级别的日志用于记录参数校验不通过、流程状态校验和数据库操作失败等不影响数据正确性的情形。
> 3. error级别的日志指出发生错误事件，需要人为解决但仍然不影响系统继续运行的情形。
> 4. fatal级别的日志指出每个严重的错误事件将会导致应用程序的退出。 
> 5. 如果服务是作为dubbo接口，即使数据项不存在也不应该返回success为false，提供方不做调用方的判断逻辑，只做数据查询工作，但是系统内使用可以自己视情况而定。