package com.wbs.wbs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wbs.wbs.repository.TotalRepository;

@SpringBootTest
class WbsApplicationTests {

	@Autowired
	private TotalRepository totalRepository;

	@Test
	void contextLoads() {
	}


	@Test
	void getTest(){
		
		assertEquals(0, 0);

	}

}