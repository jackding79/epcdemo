package cn.jackding.rpc.core;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Service {
     String serviceId()default "";
}
