// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceOperationEnum.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.enums;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.DeviceFinalString;


public enum DeviceOperationEnum
{
	SCREEN_ON,
	SCREEN_OFF,
	ID_CARD,
	PRINTER,
	FINGER,
	STOP_MESHION,
	STOP_VOICE,
	STOP_PRINTER,
	CLOSE_CAMERA,
	CLOSE_OCR_CAMERA,
	CLOSE_FACE_CAMERA,
	CLOSE_FINGER,
	CLOSE_IDCARD,
	START_SCREEN,
	START_FACE_CAMERA,
	START_OCR_CAMERA,
	START_VOICE,
	START_MIC;

	public String[] getOperationSet() {
		switch(this) {
			case SCREEN_ON:
				return DeviceFinalString.SCREEN_ON;
			case SCREEN_OFF:
				return DeviceFinalString.SCREEN_OFF;
			case ID_CARD:
				return DeviceFinalString.ID_CARD;
			case PRINTER:
				return DeviceFinalString.PRINTER;
			case FINGER:
				return DeviceFinalString.FINGER;
			case START_SCREEN:
				return DeviceFinalString.START_SCREEN;
			case START_FACE_CAMERA:
				return DeviceFinalString.START_FACE_CAMERA;
			case START_OCR_CAMERA:
				return DeviceFinalString.START_OCR_CAMERA;
			case START_VOICE:
				return DeviceFinalString.STAERT_VOICE;
			case START_MIC:
				return DeviceFinalString.START_MIC;
			case STOP_MESHION:
				return DeviceFinalString.STOP_MESHION;
			case STOP_VOICE:
				return DeviceFinalString.STOP_VOICE;
			case STOP_PRINTER:
				return DeviceFinalString.STOP_PRINTER;
			case CLOSE_CAMERA:
				return DeviceFinalString.CLOSE_CAMERA;
			case CLOSE_OCR_CAMERA:
				return DeviceFinalString.CLOSE_OCR_CAMERA;
			case CLOSE_FACE_CAMERA:
				return DeviceFinalString.CLOSE_FACE_CAMERA;
			case CLOSE_IDCARD:
				return DeviceFinalString.CLOSE_IDCARD;
			case CLOSE_FINGER:
				return DeviceFinalString.CLOSE_FINGER;
			default:
				throw new IllegalArgumentException("DeviceOperationEnum : getOperationSet()" + this + "找不到指令集？");
		}
	}
}
