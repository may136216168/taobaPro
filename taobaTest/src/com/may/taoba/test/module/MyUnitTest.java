package com.may.taoba.test.module;

import android.util.Log;
import junit.framework.TestCase;

public class MyUnitTest extends TestCase {

	public MyUnitTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.e("setUp", "setUp method is running!");
	}
	
	
	public void testSomething() throws Exception{
		Log.e("testSomething", "The test something is running::>>>>>");
		
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		Log.e("tearDown", "tearDown method is running!");
	}
}
