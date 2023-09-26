package org.springframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author derekyi
 * @date 2020/11/23
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

	/**
	 * 使用CGLIB动态生成子类
	 *
	 * @param beanDefinition
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanDefinition.getBeanClass());
		enhancer.setCallback((MethodInterceptor) (obj, method, argsTemp, proxy) -> proxy.invokeSuper(obj,argsTemp));

		// 非lamdba写法
		/*
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] argsTemp, MethodProxy methodProxy) throws Throwable {
				return methodProxy.invokeSuper(obj,argsTemp);
			}
		});
		*/
		return enhancer.create();
	}
}
