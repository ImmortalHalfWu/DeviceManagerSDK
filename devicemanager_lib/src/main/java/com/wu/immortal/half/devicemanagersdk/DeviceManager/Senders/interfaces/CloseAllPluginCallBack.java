// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CloseAllPluginCallBack.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;

public interface CloseAllPluginCallBack
{

	public abstract void closeAllStart();

	public abstract void closeDeviceSuc(DeviceEnum deviceenum);

	public abstract void closeDeviceErro(DeviceEnum deviceenum, DeviceException deviceexception);

	public abstract void closeAllOver();
}
