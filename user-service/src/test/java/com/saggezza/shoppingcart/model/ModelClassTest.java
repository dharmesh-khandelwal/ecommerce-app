package com.saggezza.shoppingcart.model;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.saggezza.userservice.entity.User;

import pl.pojo.tester.api.assertion.Method;

@RunWith(SpringRunner.class)
public class ModelClassTest {

	@Test
	public void testUserModel() {
		final Class<?> classUnderTest = User.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER).testing(Method.CONSTRUCTOR)
				.areWellImplemented();
	}

}
