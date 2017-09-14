// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ManagerStateMachineInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.manager.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.ConnectHostRunnableCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceManagerEnum;

public interface ManagerStateMachineInterface
	extends ConnectHostRunnableCallBack, OperationPluginCallBack
{

	public abstract void openHost();

	public abstract void operationPluginDevice();

	public abstract DeviceManagerEnum getDeviceManagerState();

	public abstract boolean canResetHost();

	public abstract boolean canOperationPluginDevice(DeviceEnum deviceenum);

	public abstract void free();
}
