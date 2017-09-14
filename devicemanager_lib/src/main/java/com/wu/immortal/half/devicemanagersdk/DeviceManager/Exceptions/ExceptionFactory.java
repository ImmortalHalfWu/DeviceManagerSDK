// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionFactory.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.ExceptionConfigInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.FactoryInterface;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions:
//			DeviceManagerException

class ExceptionFactory
	implements FactoryInterface
{

	private static FactoryInterface factory;

	static FactoryInterface Instance()
	{
		return factory != null ? factory : (factory = new ExceptionFactory());
	}

	private ExceptionFactory()
	{
	}

	public DeviceException create(ExceptionConfigInterface config)
	{
		return DeviceManagerException.newInstance(config.getMsg(), config.getCode());
	}
}
