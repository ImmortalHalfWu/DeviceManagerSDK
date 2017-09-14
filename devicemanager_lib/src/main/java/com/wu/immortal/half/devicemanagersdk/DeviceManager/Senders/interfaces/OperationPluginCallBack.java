// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OperationPluginCallBack.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;

public interface OperationPluginCallBack
{

	public abstract void operationPluginSucc(DeviceEnum deviceenum);

	public abstract void operationPluginErro(DeviceEnum deviceenum, DeviceException deviceexception);
}
