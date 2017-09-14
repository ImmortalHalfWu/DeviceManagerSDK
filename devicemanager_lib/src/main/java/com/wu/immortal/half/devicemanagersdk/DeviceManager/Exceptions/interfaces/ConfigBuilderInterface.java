// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConfigBuilderInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces:
//			ExceptionConfigInterface

public interface ConfigBuilderInterface
{

	public abstract ConfigBuilderInterface setCode(ExceptionCodeEnum exceptioncodeenum);

	public abstract ConfigBuilderInterface setMsg(String s);

	public abstract ExceptionConfigInterface build();
}
