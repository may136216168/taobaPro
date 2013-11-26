package com.may.taoba.test.module;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import junit.framework.TestCase;

public class MyFirstProjectTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@SmallTest
	public void testSomething() throws Exception{
		Log.e("testSomething", "testSomething:::::::>>>");
	}
}
