// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceADReceiver.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices;

import android.content.*;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.ADReceiverCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.RegisterADReceiverInterface;

public class DeviceADReceiver extends BroadcastReceiver
	implements RegisterADReceiverInterface
{

	private static final IntentFilter usbFilter = new IntentFilter();
	private ADReceiverCallBack adReceiverCallBack;
	private volatile boolean isRegiste;

	public static DeviceADReceiver newInstans()
	{
		return new DeviceADReceiver();
	}

	private DeviceADReceiver()
	{
		isRegiste = false;
		if (!isRegiste)
		{
			usbFilter.setPriority(0x7fffffff);
			usbFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
			usbFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
		}
	}

	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		UsbDevice parcelableExtra = (UsbDevice)intent.getParcelableExtra("device");
		Loger.i((new StringBuilder()).append("DeviceADReceiver: action = ").append(action).toString());
		if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action))
		{
			if (adReceiverCallBack != null)
				adReceiverCallBack.attached(intent, parcelableExtra);
		} else
		if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && adReceiverCallBack != null)
			adReceiverCallBack.detached(intent, parcelableExtra);
	}

	public void registe(Context context, ADReceiverCallBack callBack)
	{
		adReceiverCallBack = callBack;
		if (!isRegiste())
			context.registerReceiver(this, usbFilter);
		isRegiste = true;
	}

	public void unRegiste(Context context)
	{
		if (isRegiste())
			context.unregisterReceiver(this);
		adReceiverCallBack = null;
		isRegiste = false;
	}

	public boolean isRegiste()
	{
		return isRegiste;
	}

}
