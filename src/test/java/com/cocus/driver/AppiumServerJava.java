package com.cocus.driver;

import java.io.IOException;
import java.net.ServerSocket;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.cocus.BaseTest.BaseClass;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerJava extends BaseClass {

	private static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;
	private static DesiredCapabilities cap;

	public static void startServer() 
	{
		try
		{
			try 
			{
				Runtime.getRuntime().exec("TASKKILL /F /IM node.exe");
			} 
			catch (IOException e) 
			{
				System.out.println("Starting NodeJS Appium Server");
			}
			
				cap = new DesiredCapabilities();
				cap.setCapability("noReset", true);
				
				// Build the Appium service
				builder = new AppiumServiceBuilder();
				builder.withIPAddress("127.0.0.1");
				builder.usingAnyFreePort();
				//builder.usingPort(4723);
				builder.withCapabilities(cap);
				builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
				builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
				builder.withArgument(GeneralServerFlag.LOG_LEVEL,"debug");
		
				try 
				{
					Thread.sleep(3000); //Wait for loading the node.js exe
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					System.out.println("Failed To Start Appium Server With NodeJS. Check Your NodeJs Installation.");
					e.printStackTrace();
				}
				// Start the server with the builder
				service = AppiumDriverLocalService.buildDefaultService();
				service.start();
				System.out.println("Appium Server Started");
		//		return service;			
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public static void stopServer() 
	{
		try
		{
			service.stop();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
				
	}
	
	public static boolean checkIfServerIsRunnning(int port) {

	    boolean isServerRunning = false;
	    ServerSocket serverSocket;
	    try {
	        serverSocket = new ServerSocket(port);
	        serverSocket.close();
	    } catch (IOException e) {
	        //If control comes here, then it means that the port is in use
	        isServerRunning = true;
	    } finally {
	        serverSocket = null;
	    }
	    return isServerRunning;
	}

	/**
	 * This method will setup the environment such as starting server and getting conncted deviceId,
	 * deviceName and device version
	 */
	public static void setUpMobileEnvironment() {
		try
		{
			if(System.getenv("JENKINS_HOME") != null) //This check ensure execution is triggered from Jenkins
			{
				deviceEnvironment = configProp.getProperty("DeviceEnvironment");
				if(deviceEnvironment!=null)
				{
					if(!deviceEnvironment.equalsIgnoreCase("Remote"))
					{
						startServer();
						deviceID = adbCommands.getConnectedDeviceID();
						androidVersion = adbCommands.getConnectedDeviceAndroidVersion();
						deviceName = adbCommands.getConnectedDeviceName();		
					}
				}
				else
				{
					startServer();
					deviceID = adbCommands.getConnectedDeviceID();
					androidVersion = adbCommands.getConnectedDeviceAndroidVersion();
					deviceName = adbCommands.getConnectedDeviceName();	
				}
			}
			else
			{
				AppiumServerJava.startServer();
				deviceID = adbCommands.getConnectedDeviceID();
				androidVersion = adbCommands.getConnectedDeviceAndroidVersion();
				deviceName = adbCommands.getConnectedDeviceName();
			}
		}
		catch(Exception e)
		{
			System.out.println("Not able to set the Environment for DA Execution");
		}
		
	}

}
