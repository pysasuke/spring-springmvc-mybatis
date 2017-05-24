spring-springmvc
===
## 项目介绍
单纯的spring整合springmvc+mybatis，整合所需算是最简配置

## 项目结构
main
- controller:控制层，UserController展示了两种返回而类型情况:跳转页面和返回对象  

  部分代码展示:
```
 //    @RequestMapping("getUser")
    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    public String getUser(@RequestParam("id") Long id, Model model) {
        User user = userServicre.getById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping("getById")
    @ResponseBody
    /*
    POJO对象要转成Json，则要求POJO中的属性必须都有getter方法
    需要有json对应的包
    不加返回时406报错：
    The resource identified by this request is only capable of generating responses with characteristics not acceptable according to the request "accept" headers.-->
    */
    public User getById(@RequestParam("id") Long id) {
        User user = userServicre.getById(id);
        return user;
    }
```    
- service:业务处理层，包含一个impl包，Service以接口类型存在，impl包下存放Service接口的实现类
- dao:数据库交互层
- model:实体对象层

resources
- application.xml:spring配置文件入口，加载spring-config.xml
- spring-mvc.xml:springmvc配置相关文件

  部分代码展示:
```
    <!-- 自动扫描该包，使SpringMVC认为包下用了@controller注解的类是控制器 -->
    <context:component-scan base-package="com.py.controller">
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--注解方式(处理请求)-->
    <mvc:annotation-driven/>
 
<!--静态资源默认servlet配置a
    	1、加入对静态资源的处理:js,css,gif,png
    	2、允许使用"/"做整体映射
    -->
    <mvc:default-servlet-handler/>
     <!-- 静态资源处理  css js imgs 可以直接访问而不被拦截-->
    <mvc:resources mapping="/html/**" location="/WEB-INF/html/"/>

    <!-- 定义跳转的文件的前后缀 ，视图模式配置  解析控制层return "index" 一类的操作-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
```
- spring-config.xml:加载其他集成的配置文件，这里加载spring-mybatis.xml和db.properties
- spring-mybatis.xml：mybatis相关配置文件

  部分代码展示
```
 <!-- 自动扫描(自动注入) -->
    <context:component-scan base-package="com.py.*"/>

    <!-- 配置数据源 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource"
          init-method="init" destroy-method="close">
        <property name="driverClassName" value="${db.driver}"/>
        <property name="url" value="${db.url}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>
        <!-- 初始化连接大小 -->
        <property name="initialSize" value="${initialSize}"></property>
        <!-- 连接池最大数量 -->
        <property name="maxActive" value="${maxActive}"></property>
        <!-- 连接池最大空闲 -->
        <!--<property name="maxIdle" value="${maxIdle}"></property>-->
        <!-- 连接池最小空闲 -->
        <property name="minIdle" value="${minIdle}"></property>
        <!-- 获取连接最大等待时间 -->
        <property name="maxWait" value="${maxWait}"></property>

        <property name="filters" value="stat,log4j,wall"/>

    </bean>


    <!-- myBatis文件 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 自动扫描mapping.xml文件 -->
        <!--以mapper命名时报错：
        org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)-->
        <!--<property name="mapperLocations" value="classpath*:mapping/*.xml"/>-->
        <property name="mapperLocations" value="classpath*:mapping/*.xml"/>
    </bean>

    <!-- DAO接口所在包名，Spring会自动查找其下的类 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.py.dao"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>
```
- db.properties：数据库相关参数
- mapping:存放mybatis映射文件，以UserMapper.xml为例

  部分代码展示：
```
<!--与dao中的接口类对应-->
<mapper namespace="com.py.dao.UserMapper">

    <select id="getById" resultType="com.py.model.User">
        select id,username,password,email from user where id=#{id,jdbcType=BIGINT}
    </select>

</mapper>
```
webapp
- web.xml

  部分代码展示:
```
<!-- SpringMVC核心 -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!-- Spring的配置文件 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath*:application.xml</param-value>
    </context-param>

    <!-- Spring监听器 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- SpringMVC拦截设置 -->
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <!-- 由SpringMVC拦截所有请求 -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <!-- SpringMVC拦截设置结束 -->
 ```
