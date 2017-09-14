// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MsgSenderCallBacker.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.hardware.usb.UsbDevice;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.MsgSenderCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;

class MsgSenderCallBacker
	implements MsgSenderCallBack
{

	private OperationPluginCallBack openCallBack;
	private boolean runADCallBack;
	private boolean runPermissionCallBack;

	private MsgSenderCallBacker(OperationPluginCallBack openCallBack, boolean runADCallBack, boolean runPermissionCallBack)
	{
		this.openCallBack = openCallBack;
		this.runADCallBack = runADCallBack;
		this.runPermissionCallBack = runPermissionCallBack;
	}

	public boolean msgSendSucc(DeviceEnum deviceEnum)
	{
		if (!runADCallBack)
		{
			openCallBack.operationPluginSucc(deviceEnum);
			free();
		}
		return runADCallBack;
	}

	public void msgSendErro(DeviceEnum deviceEnum, DeviceException e)
	{
		openCallBack.operationPluginErro(deviceEnum, e);
		free();
	}

	public boolean permissionSuccess(UsbDevice device, DeviceEnum object)
	{
		openCallBack.operationPluginSucc(object);
		return true;
	}

	public void permissionErro(UsbDevice device, DeviceEnum object, DeviceException e)
	{
		openCallBack.operationPluginErro(object, e);
		free();
	}

	public boolean attachedDevice(DeviceEnum deviceEnum, UsbDevice attachedDevice)
	{
		if (!runPermissionCallBack)
		{
			openCallBack.operationPluginSucc(deviceEnum);
			free();
		}
		return runPermissionCallBack;
	}

	private void free()
	{
		openCallBack = null;
	}

	public static MsgSenderCallBack newInstanceEveryStepCallBack(OperationPluginCallBack outMsgCallBack)
	{
		return new MsgSenderCallBacker(outMsgCallBack, true, true);
	}

	public static MsgSenderCallBack newInstanceOnlySendMsgCallBack(OperationPluginCallBack outMsgCallBack)
	{
		return new MsgSenderCallBacker(outMsgCallBack, false, false);
	}
}
