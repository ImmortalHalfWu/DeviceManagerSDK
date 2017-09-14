// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceOperationSetHelp.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceOperationEnum;

public class DeviceOperationSetHelp
{

	public DeviceOperationSetHelp()
	{
	}

	public static String[] getCloseOperationSet(DeviceEnum deviceEnum)
	{
		switch (deviceEnum){
			case DEVICE_IDCARD:
				return DeviceOperationEnum.CLOSE_IDCARD.getOperationSet();
			case DEVICE_FINGER:
				return DeviceOperationEnum.CLOSE_FINGER.getOperationSet();
			case DEVICE_FACE:
				return DeviceOperationEnum.CLOSE_FACE_CAMERA.getOperationSet();
			case DEVICE_OCR:
				return DeviceOperationEnum.CLOSE_OCR_CAMERA.getOperationSet();
			case DEVICE_PRINT:
				return DeviceOperationEnum.STOP_PRINTER.getOperationSet();
			case DEVICE_VOICE:
				return DeviceOperationEnum.STOP_VOICE.getOperationSet();
			default:
				throw new IllegalArgumentException((new StringBuilder()).append("DeviceOperationSetHelp : getCloseOperationSet() not found set ").append(deviceEnum).toString());
		}
	}

	public static String[] getOpenOperationSet(DeviceEnum deviceEnum)
	{
		switch (deviceEnum){
			case DEVICE_IDCARD:
				return DeviceOperationEnum.ID_CARD.getOperationSet();
			case DEVICE_FINGER:
				return DeviceOperationEnum.FINGER.getOperationSet();
			case DEVICE_PRINT:
				return DeviceOperationEnum.PRINTER.getOperationSet();
			case DEVICE_FACE:
				return DeviceOperationEnum.START_FACE_CAMERA.getOperationSet();
			case DEVICE_OCR:
				return DeviceOperationEnum.START_OCR_CAMERA.getOperationSet();
			case DEVICE_VOICE:
				return DeviceOperationEnum.START_VOICE.getOperationSet();
			default:
				throw new IllegalArgumentException((new StringBuilder()).append("DeviceOperationSetHelp : getOpenOperationSet() not found set ").append(deviceEnum).toString());
		}
	}
}
