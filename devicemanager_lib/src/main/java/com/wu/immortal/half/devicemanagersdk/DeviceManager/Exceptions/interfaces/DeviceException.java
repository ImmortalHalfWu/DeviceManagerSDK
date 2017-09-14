// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceException.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;

public interface DeviceException
{

	public abstract String getMessage();

	public abstract ExceptionCodeEnum getCode();

	public abstract Exception getException();
}
