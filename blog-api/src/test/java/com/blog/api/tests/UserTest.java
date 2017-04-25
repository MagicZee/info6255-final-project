package com.blog.api.tests;

import static org.mockito.Mockito.*;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.testng.annotations.Test;
import com.blog.api.domain.User;

public class UserTest {

	@Test
	public void testA(){
		String dummy = null;
		User user = new User();
		user.setFirstName(dummy);
		
		assertNull(user.getFirstName());
	}
	
	
}
