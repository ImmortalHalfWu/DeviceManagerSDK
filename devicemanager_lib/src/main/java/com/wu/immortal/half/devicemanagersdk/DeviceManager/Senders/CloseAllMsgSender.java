// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CloseAllMsgSender.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.CloseAllPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.CloseAllPluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.ClosePluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import java.util.ArrayList;
import java.util.List;

class CloseAllMsgSender
	implements CloseAllPluginInterface, OperationPluginCallBack
{

	private Context context;
	private UsbSerialPort usbSerialPort;
	private ClosePluginInterface closePluginInterface;
	private CloseAllPluginCallBack callBack;
	private int closeingIndex;
	private int maxIndex;
	private List<DeviceEnum> runningPluginDevice;

	static CloseAllMsgSender newInstance()
	{
		return new CloseAllMsgSender();
	}

	private CloseAllMsgSender()
	{
		closeingIndex = 0;
	}

	public void CloseAllPluginDevice(Context context, UsbSerialPort hostPort, CloseAllPluginCallBack closeAllPluginCallBack, List<DeviceEnum> devices, ClosePluginInterface closePluginInterface)
	{
		this.context = context;
		usbSerialPort = hostPort;
		this.closePluginInterface = closePluginInterface;
		callBack = closeAllPluginCallBack;
		runningPluginDevice = new ArrayList<>(devices);
		start();
	}

	private void start()
	{
		maxIndex = runningPluginDevice.size();
		logi((new StringBuilder()).append("start() 开始关闭所有开启中的设备：").append(runningPluginDevice).toString());
		callBack.closeAllStart();
		closeDevice();
	}

	private boolean closeDevice()
	{
		if (isIndexOut())
		{
			return false;
		} else
		{
			DeviceEnum deviceEnum = (DeviceEnum)runningPluginDevice.get(closeingIndex++);
			logi((new StringBuilder()).append("closeDevice() 准备关闭设备：").append(deviceEnum).toString());
			closePluginInterface.closePluginDevice(context, deviceEnum, usbSerialPort, this);
			return true;
		}
	}

	public void operationPluginSucc(DeviceEnum deviceEnum)
	{
		logi((new StringBuilder()).append("operationPluginSucc() ").append(deviceEnum).append("关闭成功").toString());
		callBack.closeDeviceSuc(deviceEnum);
		if (!closeDevice())
			callBack();
	}

	public void operationPluginErro(DeviceEnum deviceEnum, DeviceException e)
	{
		logi((new StringBuilder()).append("operationPluginErro() ").append(deviceEnum).append("关闭失败，e=").append(e.getMessage()).toString());
		callBack.closeDeviceErro(deviceEnum, e);
		if (!closeDevice())
			callBack();
	}

	public void callBack()
	{
		logi((new StringBuilder()).append("callBack() close device all  完毕，关闭设备").append(closeingIndex).append("，共有设备").append(maxIndex).toString());
		callBack.closeAllOver();
		free();
	}

	private boolean isIndexOut()
	{
		logi((new StringBuilder()).append("isIndexOut() 已关闭设备").append(closeingIndex).append("，共有设备").append(maxIndex).toString());
		return maxIndex == closeingIndex;
	}

	private void free()
	{
		logi("free() ");
		context = null;
		usbSerialPort = null;
		closePluginInterface = null;
		callBack = null;
		runningPluginDevice.clear();
		runningPluginDevice = null;
	}

	private void logi(String msg)
	{
		Loger.i((new StringBuilder()).append("CloseAllMsgSender : ").append(msg).toString());
	}
}
