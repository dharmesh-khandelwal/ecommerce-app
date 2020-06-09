package com.saggezza.shoppingcart.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.orderservice.entity.Order;

import pl.pojo.tester.api.assertion.Method;

@RunWith(SpringRunner.class)
public class ModelClassTest {

	@Test
	public void testOrderModel() {
		final Class<?> classUnderTest = Order.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
