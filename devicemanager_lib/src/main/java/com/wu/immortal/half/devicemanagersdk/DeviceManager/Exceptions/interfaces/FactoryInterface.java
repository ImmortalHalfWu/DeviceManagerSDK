// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FactoryInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces;


// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces:
//			ExceptionConfigInterface, DeviceException

public interface FactoryInterface
{

	public abstract DeviceException create(ExceptionConfigInterface exceptionconfiginterface);
}
