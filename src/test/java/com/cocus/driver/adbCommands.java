package com.cocus.driver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.cocus.BaseTest.BaseClass;
import com.cocus.utils.Util;



public class adbCommands extends BaseClass{
	
	public static String getConnectedDeviceID()
	{
		String currentdeviceID = "";
		try
		{/*
			Runtime rt = Runtime.getRuntime();
			//Process p = rt.exec("adb -d get-serialno");
			Process p = rt.exec("adb get-serialno");
			//Process p = rt.exec("adb devices");
			//Process p = rt.exec("adb shell getprop ro.serialno");
			
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			currentdeviceID = reader.readLine().trim();*/
			
			Runtime rt = Runtime.getRuntime();
			rt.exec("adb kill-server");
			Runtime.getRuntime().exec("TASKKILL /F /IM adb.exe");
			//Process p = rt.exec("adb get-serialno");
			Util.wait_inMinutes(0.125);
			Process p = rt.exec("adb shell getprop ro.serialno");
			//Process p = rt.exec("adb devices");
			Util.wait_inMinutes(0.125);
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			currentdeviceID = reader.readLine().toString();
			reader.close();
			
			if (currentdeviceID.isEmpty())
			{
				//Report.infoTest("Currently no Mobile Device is connected");
				System.out.println("Currently no Mobile Device is connected");
				deviceID = "emulator-5554";
			}
			else if (currentdeviceID.contains("EMULATOR"))
			{
				p = rt.exec("adb get-serialno");
				Util.wait_inMinutes(0.125);
				is = p.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is));
				currentdeviceID = reader.readLine().toString();
				reader.close();
				System.out.println("Emulator has been launched for this execution");
			}
			else
			{
				//Report.infoTest("Physical Device with UDID : "+deviceID+" is connected");
				System.out.println("Physical Device with UDID : "+currentdeviceID+" is connected");
			}
			
			return currentdeviceID;
		}
		catch (Exception e)
		{
			System.out.println("Default Device Emulator is being used");
			currentdeviceID = "emulator-5554";	
			//Report.failTest("Failed to get the Device ID");
			//System.out.println(e.getMessage());
		}
		return currentdeviceID;
		
	}
	
	public static String getConnectedDeviceAndroidVersion()
	{
		String currentAndroidVersion = "";
		try
		{
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("adb shell getprop ro.build.version.release");
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			currentAndroidVersion = reader.readLine().trim();
			reader.close();
			
			if (currentAndroidVersion.equals("error: no devices/emulators found") || currentAndroidVersion.isEmpty())
			{
				//Report.infoTest("Currently no Mobile Device is connected");
				System.out.println("Currently no Mobile Device is connected");
				currentAndroidVersion = "";
			}
			else
			{
				//Report.infoTest("Physical Device with Android Version "+androidVersion+" is connected");
				System.out.println("Physical Device with Android Version "+currentAndroidVersion+" is connected");
			}
		}
		catch (Exception e)
		{
			currentAndroidVersion = "No Device Connected";
			//Report.failTest("Failed to get the Android Version of Connected Device");
			//System.out.println(e.getMessage());
		}
		return currentAndroidVersion;
	}
	
	public static String getConnectedDeviceName()
	{
		String currentDeviceName = "";
		try
		{
			Runtime rt = Runtime.getRuntime();
			//Process p = rt.exec("adb -d get-serialno");
			Process p = rt.exec("adb shell getprop ro.product.model");
			//Process p = rt.exec("adb devices");
			InputStream is = p.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			currentDeviceName = reader.readLine().trim();
			reader.close();
			
			if (currentDeviceName.contains("Android SDK"))
			{
				System.out.println("Currently Emulator is connected");
				currentDeviceName = "Android Emulator";
			}
			else
			{
				switch (currentDeviceName.trim())
				{
				case "SM-T387V":
				{
					currentDeviceName = "Tab A8";
					System.out.println("Connected Device is "+currentDeviceName);
					break;
				}
				case "SM-T377V":
				{
					currentDeviceName = "Tab E";
					System.out.println("Connected Device is "+currentDeviceName);
					break;
				}
				case "SM-T577U":
					currentDeviceName = "Galaxy Tab Active3";
					System.out.println("Connected Device is "+currentDeviceName);
					break;
				}
				
			}
			
		}
		catch (Exception e)
		{
			currentDeviceName = "No Device Connected";
			//Report.failTest("Failed to get the Device Name of Connected Device");
			//System.out.println(e.getMessage());
		}
		return currentDeviceName;
	}
	
}
