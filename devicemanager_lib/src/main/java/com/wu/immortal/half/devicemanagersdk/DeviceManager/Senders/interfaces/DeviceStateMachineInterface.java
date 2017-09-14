// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceStateMachineInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import java.util.List;

public interface DeviceStateMachineInterface
{

	public abstract List getRunningPluginDevice();

	public abstract boolean isRunning(DeviceEnum deviceenum);

	public abstract void closeDevice(DeviceEnum deviceenum);

	public abstract void openPlugin(DeviceEnum deviceenum);

	public abstract List canCloseAllPlugin();

	public abstract void free();
}
