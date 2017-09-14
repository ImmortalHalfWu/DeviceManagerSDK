// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionConfigBuilder.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.ConfigBuilderInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.ExceptionConfigInterface;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions:
//			ExceptionConfiger

class ExceptionConfigBuilder
	implements ConfigBuilderInterface
{

	private ExceptionConfigInterface exceptionConfigInterface;

	public static ConfigBuilderInterface newInstance()
	{
		return new ExceptionConfigBuilder();
	}

	private ExceptionConfigBuilder()
	{
		exceptionConfigInterface = ExceptionConfiger.newInstance();
	}

	public ConfigBuilderInterface setCode(ExceptionCodeEnum exceptionCodeEnum)
	{
		exceptionConfigInterface.setCode(exceptionCodeEnum);
		return this;
	}

	public ConfigBuilderInterface setMsg(String msg)
	{
		exceptionConfigInterface.setMsg(msg);
		return this;
	}

	public ExceptionConfigInterface build()
	{
		return exceptionConfigInterface;
	}
}
