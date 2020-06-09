package com.saggezza.shoppingcart.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.productservice.entity.Product;

import pl.pojo.tester.api.assertion.Method;

@RunWith(SpringRunner.class)
public class ModelClassTest {

	@Test
	public void testProductModel() {
		final Class<?> classUnderTest = Product.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
				.testing(Method.EQUALS).testing(Method.HASH_CODE).testing(Method.CONSTRUCTOR).areWellImplemented();
	}
}
