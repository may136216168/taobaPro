/**
 * 
 */
package com.may.taoba.module.jsinterface;



/**
 * @author Administrator
 *
 */
public class LogInterface extends ScriptInterface {
	public LogInterface()
	{
	}

	public void d(String s)
	{
		//LogUtil.d(s);
	}

	public void e(String s)
	{
		//LogUtil.e(null, s);
	}
	
	public void println(String log){
		System.out.println("javaScrpit: log = "+log);
	}
	
	public void println(String log,int flag){
		System.out.println("javaScrpit: log = "+log +" flag = "+flag);

	}
	public void println(String log,String flag){
		System.out.println("javaScrpit: log = "+log +" flag = "+flag);
		
	}
	
	public void println(String log,boolean flag){
		System.out.println("javaScrpit: log = "+log +" flag = "+flag);
	}
	public void println(String log,Object flag){
		System.out.println("javaScrpit: log = "+log +" flag = "+flag);
	}
}
