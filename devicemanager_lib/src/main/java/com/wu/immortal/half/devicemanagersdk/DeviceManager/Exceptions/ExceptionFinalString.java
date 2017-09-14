// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionFinalString.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;

class ExceptionFinalString
{

	private static final int CODE_OPENING = 1;
	private static final int CODE_OPERATION_SEND = 17;
	private static final int CODE_TIME_OUT = 273;
	private static final int CODE_PERMISSION = 4369;
	private static final int CODE_OPERATION_NOT_FOUND = 0x11111;
	private static final int CODE_MANAGER_FREE = 0x111111;
	private static final int CODE_CONNECT_HOST_FAIL = 0x1111111;
	private static final String MSG_OPENING = "have device opening";
	private static final String MSG_OPERATION_SEND = "poeration send fail";
	private static final String MSG_TIME_OUT = "time out";
	private static final String MSG_PERMISSION = "don't have permission";
	private static final String MSG_OPERATION_NOT_FOUND = "operation not found";
	private static final String MSG_MANAGER_FREE = "DeviceManager is free";
	private static final String MSG_CONNECT_HOST_FAIL = "DeviceManager connect host device fail";

	ExceptionFinalString()
	{
	}

	public static String getMsgFromEnum(ExceptionCodeEnum codeEnum)
		throws IllegalArgumentException
	{

		switch (codeEnum){
			case EXCEPRION_OPERATION_SEND:
				return MSG_OPERATION_SEND;
			case EXCEPTION_CONNECT_HOST:
				return MSG_CONNECT_HOST_FAIL;
			case EXCEPTION_MANAGER_FREE:
				return MSG_MANAGER_FREE;
			case EXCEPTION_NOT_FOUND_OPERATION:
				return MSG_OPERATION_NOT_FOUND;
			case EXCEPTION_OPERATIONING:
				return MSG_OPENING;
			case EXCEPTION_PERMISSION:
				return MSG_PERMISSION;
			case EXCEPTION_TIME_OUT:
				return MSG_TIME_OUT;
			default:
				throw new IllegalArgumentException((new StringBuilder()).append(codeEnum).append(" is Illegal").toString());
		}
	}

	public static int getCodeFromEnum(ExceptionCodeEnum codeEnum)
		throws IllegalArgumentException
	{
		switch (codeEnum){
			case EXCEPRION_OPERATION_SEND:
				return CODE_OPERATION_SEND;
			case EXCEPTION_CONNECT_HOST:
				return CODE_CONNECT_HOST_FAIL;
			case EXCEPTION_MANAGER_FREE:
				return CODE_MANAGER_FREE;
			case EXCEPTION_NOT_FOUND_OPERATION:
				return CODE_OPERATION_NOT_FOUND;
			case EXCEPTION_OPERATIONING:
				return CODE_OPENING;
			case EXCEPTION_PERMISSION:
				return CODE_PERMISSION;
			case EXCEPTION_TIME_OUT:
				return CODE_TIME_OUT;
			default:
				throw new IllegalArgumentException((new StringBuilder()).append(codeEnum).append(" is Illegal").toString());
		}
	}
}
