// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionConfigInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;

public interface ExceptionConfigInterface
{

	public abstract ExceptionCodeEnum getCode();

	public abstract String getMsg();

	public abstract ExceptionConfigInterface setCode(ExceptionCodeEnum exceptioncodeenum);

	public abstract ExceptionConfigInterface setMsg(String s);
}
