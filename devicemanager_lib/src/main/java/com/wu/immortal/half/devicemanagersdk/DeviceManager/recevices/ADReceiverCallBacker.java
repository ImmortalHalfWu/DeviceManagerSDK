// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ADReceiverCallBacker.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.ADReceiverCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.ADReceiverDispenserInterface;

public class ADReceiverCallBacker
	implements ADReceiverCallBack
{

	private ADReceiverDispenserInterface adReceiverDispenserInterface;

	public static ADReceiverCallBacker newInstans(ADReceiverDispenserInterface adReceiverDispenserInterface)
	{
		return new ADReceiverCallBacker(adReceiverDispenserInterface);
	}

	private ADReceiverCallBacker(ADReceiverDispenserInterface adReceiverDispenserInterface)
	{
		this.adReceiverDispenserInterface = adReceiverDispenserInterface;
	}

	public void attached(Intent intent, UsbDevice attachedDevice)
	{
		DeviceEnum deviceEnum = null;
		int PID = attachedDevice.getProductId();
		switch (PID)
		{
		case 24597: 
			deviceEnum = DeviceEnum.DEVICE_HOST;
			break;

		case 288: 
			deviceEnum = DeviceEnum.DEVICE_FINGER;
			break;

		case 50010: 
			deviceEnum = DeviceEnum.DEVICE_IDCARD;
			break;
		}
		Loger.i((new StringBuilder()).append(deviceEnum).append("设备插入。").toString());
		Loger.i((new StringBuilder()).append("设备信息：").append(attachedDevice).toString());
		adReceiverDispenserInterface.attachedDevice(intent, deviceEnum, attachedDevice);
	}

	public void detached(Intent intent1, UsbDevice usbdevice)
	{
	}
}
