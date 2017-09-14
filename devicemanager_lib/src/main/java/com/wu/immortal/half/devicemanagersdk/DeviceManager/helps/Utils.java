// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Utils.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialDriver;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialProber;

import java.io.DataOutputStream;
import java.util.Iterator;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps:
//			Loger

public class Utils
{

	public Utils()
	{
	}

	public static boolean rootPermission(Context context)
	{
		return upgradeRootPermission((new StringBuilder()).append("chmod 777 ").append(context.getPackageCodePath()).toString());
	}

	private static boolean upgradeRootPermission(String cmd)
	{
		Process process = null;
		DataOutputStream os = null;

		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
			return true;
		} catch (Exception var14) {
			return false;
		} finally {
			try {
				if(os != null) {
					os.close();
				}

				process.destroy();
			} catch (Exception ignored) {}

		}

	}

	public static void connectManager()
	{
		String cmd = "echo \"1\">/sys/class/gpio_sw/normal_led/data";
		String cmd1 = "echo \"0\">/sys/class/gpio_sw/normal_led/data";
		upgradeRootPermission(cmd);
		upgradeRootPermission(cmd1);
		upgradeRootPermission(cmd);
		upgradeRootPermission(cmd1);
		upgradeRootPermission(cmd);
		upgradeRootPermission(cmd1);
		Loger.i("Communication:connectManager: connectManager");
	}

	public static byte[] hexStr2Bytes(String hexString)
	{
		if (hexString != null && !hexString.equals(""))
		{
			hexString = hexString.toUpperCase();
			int length = hexString.length() / 2;
			char hexChars[] = hexString.toCharArray();
			byte d[] = new byte[length];
			for (int i = 0; i < length; i++)
			{
				int pos = i * 2;
				d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			}

			return d;
		} else
		{
			return null;
		}
	}

	private static byte charToByte(char c)
	{
		return (byte)"0123456789ABCDEF".indexOf(c);
	}

	public static UsbSerialDriver findHostDevice(UsbManager usbManager)
	{
		Iterator iterator = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager).iterator();
		Loger.i((new StringBuilder()).append("�����豸").append(UsbSerialProber.getDefaultProber().findAllDrivers(usbManager).size()).toString());
		while (iterator.hasNext()) 
		{
			UsbSerialDriver usbSerialDriver = (UsbSerialDriver)iterator.next();
			UsbDevice device = usbSerialDriver.getDevice();
			if (device.getProductId() == 24597 && device.getVendorId() == 1027)
			{
				Loger.i((new StringBuilder()).append("attached : 24597_1027 device = ").append(device.toString()).toString());
				if (usbSerialDriver.getPorts().size() == 1)
					Loger.i("attached24597_1027 ��port==1");
				return usbSerialDriver;
			}
		}
		return null;
	}
}
