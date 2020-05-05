package cn.sunline.myrpc.processer;

import cn.sunline.myrpc.Exception.NoConfigException;
import cn.sunline.myrpc.factory.Cache;
import cn.sunline.myrpc.factory.ServiceFactory;
import cn.sunline.myrpc.provider.myinterface.Service;
import cn.sunline.myrpc.provider.myinterface.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author dinghang
 */
public class serviceProcessor {
    static Logger logger= LoggerFactory.getLogger(serviceProcessor.class);
   // private ResourceLoader resourceLoader;
/*    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory;
    public static ConcurrentHashMap<String,Object> map= ServiceFactory.getServiceRegistreMap();*/

    public boolean registry(ApplicationContext applicationContext) throws BeansException {
            Map<String,Object> beans =applicationContext.getBeansWithAnnotation(ServiceImpl.class);
            Cache cache=Cache.getCache();
            for (Object service:beans.values()){
                Class<?> clazz=service.getClass();
                Class<?> []interfaces=clazz.getInterfaces();
                if(interfaces.length!=1){
                    logger.warn("实现了多个接口: {}",clazz);
                    break;
                }
                Class<?> interfaceClass=interfaces[0];
                if(!clazz.getAnnotation(ServiceImpl.class).ref().getClass().isInstance(interfaceClass)){
                    logger.warn("指定rpc服务类型不匹配 {}->{}",clazz,interfaceClass);
                    break;
                }
                cache.put(interfaceClass.getName(),clazz);
                logger.info("加载rpc服务:({}={})",interfaces,clazz);
            }

        return true;
    }
  /*  @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        //this.resourceLoader=resourceLoader;
        this.resourcePatternResolver= ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory=new CachingMetadataReaderFactory(resourceLoader);
    }*/

    //@Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    /*    String defaultBasePackage="cn.sunline.myrpc";
        String basePackage= getEnvironment().getProperty("my.rpc.zookeeper.basePackages");
        if(basePackage.isEmpty()){
           basePackage=defaultBasePackage;
           logger.info("使用默认包路径",basePackage);
        }
        Set<Class<?>> set=scanPackage(basePackage);
        for (Class beanClazz:set
             ) {
            BeanDefinitionBuilder builder=BeanDefinitionBuilder.genericBeanDefinition();
            GenericBeanDefinition definition=(GenericBeanDefinition)builder.getRawBeanDefinition();
            Service service= getServiceAnnotations(beanClazz);
            String beanName="";
            if(service==null){
                break;
            }
            beanName =service.Serviceid().isEmpty()?beanClazz.getSimpleName():service.Serviceid();
            //使用set方法
            definition.getPropertyValues().add("interfaceType",beanClazz);
            //使用构造器
            // definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
            //这里beanClass指定的是生成bean的实例工厂 不是bean本身 这个工厂会构造接口的代理对象
            //FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，是返回的 getObject方法返回的对象
            definition.setBeanClass(ServiceFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            beanDefinitionRegistry.registerBeanDefinition(beanName,definition);
        }*/
    }

   /* @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }*/
    private static final String DEFAULT_RESOURCE_PATTERN="**/*.class";

    /**
     * 利用spring 根据包路径获取包及子包下的所有类
     * 可以避免自己去写的时候要判断jar包下还是类路径下还有linux和windows的差别
     * 这些spring已经做好了
     * @param basePackage
     * @return
     */
/*    private Set<Class<?>> scanPackage(String basePackage){
        Set<Class<?>> set=new HashSet<>();
        String packageSearchPath=ResourcePatternResolver.CLASSPATH_URL_PREFIX+ResolvePackages(basePackage)
                +'/'+DEFAULT_RESOURCE_PATTERN;
        try{
            Resource[] resources=this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource:resources
                 ) {
                if(resource.isReadable()){
                    MetadataReader metadataReader= this.metadataReaderFactory.getMetadataReader(resource);
                    String className=metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz;
                    try {
                        clazz=Class.forName(className);
                        set.add(clazz);

                    }catch (ClassNotFoundException e){
                        logger.error("根据类名找不到当前类",className);
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return  set;
    }*/
 /*   protected String ResolvePackages(String basePackages){
        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().
                resolveRequiredPlaceholders(basePackages));
    }*/
/*
    private Environment getEnvironment(){
        return applicationContext.getEnvironment();
    };
*/

/*    private Service getServiceAnnotations(Class<?> clazz){
        Service service= clazz.getAnnotation(Service.class);
        return  service;
    }*/
}
