package com.projects.ski_resort_project.utility;

import com.projects.ski_resort_project.config.SecurityConfiguration;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@UtilityClass
public class Utility {
    public <F, S, R> R getObjectImplementingMultipleInterfaces(F first, S second, Class<R> result) {
        Set<Method> userMethods = Set.of(UserDetails.class.getMethods());
        return result.cast(Proxy.newProxyInstance(
                SecurityConfiguration.class.getClassLoader(),
                new Class[]{UserDetails.class, second.getClass().getInterfaces()[0]},
                (proxy, method, args) -> {
                    if (userMethods.contains(method)) {
                        return method.invoke(first, args);
                    }
                    return method.invoke(second, args);
                }));
    }

    public <F, S, T, R> R getObjectImplementingMultipleInterfaces(
            F first, S second,
            T third, Class<R> result) {
        Set<Method> userMethods = Set.of(UserDetails.class.getMethods());
        Set<Method> thirdMethods = Set.of(FullNameInterface.class.getMethods());
        return result.cast(Proxy.newProxyInstance(
                SecurityConfiguration.class.getClassLoader(),
                new Class[]{UserDetails.class, second.getClass().getInterfaces()[0],
                        third.getClass().getInterfaces()[0]},
                (proxy, method, args) -> {
                    if (userMethods.contains(method)) {
                        return method.invoke(first, args);
                    }
                    if (thirdMethods.contains(method)) {
                        return method.invoke(third, args);
                    }
                    return method.invoke(second, args);
                }));
    }
}
