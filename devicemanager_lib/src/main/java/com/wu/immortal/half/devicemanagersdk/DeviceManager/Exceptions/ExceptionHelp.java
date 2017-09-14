// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionHelp.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions:
//			ExceptionFactory, ExceptionConfigBuilder, ExceptionFinalString

public class ExceptionHelp
{

	public ExceptionHelp()
	{
	}

	public static DeviceException createExceptionFromEnum(ExceptionCodeEnum codeEnum)
	{
		return ExceptionFactory.Instance().create(ExceptionConfigBuilder.newInstance().setCode(codeEnum).setMsg(ExceptionFinalString.getMsgFromEnum(codeEnum)).build());
	}
}
