// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionConfiger.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.ExceptionConfigInterface;

class ExceptionConfiger
	implements ExceptionConfigInterface
{

	private ExceptionCodeEnum codeEnum;
	private String msg;

	public static ExceptionConfigInterface newInstance()
	{
		return new ExceptionConfiger();
	}

	private ExceptionConfiger()
	{
	}

	public ExceptionCodeEnum getCode()
	{
		return codeEnum;
	}

	public String getMsg()
	{
		return msg;
	}

	public ExceptionConfigInterface setCode(ExceptionCodeEnum code)
	{
		codeEnum = code;
		return this;
	}

	public ExceptionConfigInterface setMsg(String msg)
	{
		this.msg = msg;
		return this;
	}
}
