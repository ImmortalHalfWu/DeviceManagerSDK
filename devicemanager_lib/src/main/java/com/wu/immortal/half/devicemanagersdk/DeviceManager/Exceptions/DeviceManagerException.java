// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceManagerException.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;

class DeviceManagerException extends Exception
	implements DeviceException
{

	private final ExceptionCodeEnum code;

	public static DeviceManagerException newInstance(String message, ExceptionCodeEnum code)
	{
		return new DeviceManagerException(message, code);
	}

	private DeviceManagerException(String message, ExceptionCodeEnum code)
	{
		super(message);
		this.code = code;
	}

	public ExceptionCodeEnum getCode()
	{
		return code;
	}

	public Exception getException()
	{
		return this;
	}
}
