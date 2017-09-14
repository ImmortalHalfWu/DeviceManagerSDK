// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceStateMachine.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.DeviceStateMachineInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import java.util.ArrayList;
import java.util.List;

class DeviceStateMachine
	implements DeviceStateMachineInterface
{

	private static DeviceStateMachine deviceStateMachine;
	private final List runningDevices = new ArrayList();

	private DeviceStateMachine()
	{
	}

	public static DeviceStateMachine install()
	{
		return deviceStateMachine != null ? deviceStateMachine : (deviceStateMachine = new DeviceStateMachine());
	}

	public List getRunningPluginDevice()
	{
		return runningDevices;
	}

	public boolean isRunning(DeviceEnum deviceEnum)
	{
		return runningDevices.contains(deviceEnum);
	}

	public void closeDevice(DeviceEnum deviceEnum)
	{
		runningDevices.remove(deviceEnum);
	}

	public void openPlugin(DeviceEnum deviceEnum)
	{
		runningDevices.add(deviceEnum);
	}

	public List canCloseAllPlugin()
	{
		return DeviceEnum.getAllCanCloseDevice();
	}

	public void free()
	{
		runningDevices.clear();
		deviceStateMachine = null;
	}
}
