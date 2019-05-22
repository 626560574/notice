package com.giveu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fox on 2018/11/6.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileServiceTest {

	@Autowired
	MobileService mobileService;


	@Test
	public void topApi() throws Exception {
		String json = mobileService.topApi("fox");
	}


}
