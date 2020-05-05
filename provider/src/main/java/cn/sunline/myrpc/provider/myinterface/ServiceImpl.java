package cn.sunline.myrpc.provider.myinterface;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ServiceImpl {
    Class<?> ref();
}
