package cn.jackding.rpc.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ServiceImpl {
    Class<?> ref();
}
