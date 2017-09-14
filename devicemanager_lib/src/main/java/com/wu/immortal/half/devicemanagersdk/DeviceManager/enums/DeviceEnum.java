// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceEnum.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.enums;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public enum  DeviceEnum
{

	DEVICE_ALL_PLUGIN,
	DEVICE_HOST,
	DEVICE_FINGER,
	DEVICE_IDCARD,
	DEVICE_FACE,
	DEVICE_OCR,
	DEVICE_PRINT,
	DEVICE_LIVE,
	DEVICE_VOICE;


	public static @NonNull List<DeviceEnum> getAllCanCloseDevice(){
		List<DeviceEnum> list = new ArrayList<>(5);
		list.add(DEVICE_FACE);
		list.add(DEVICE_LIVE);
		list.add(DEVICE_OCR);
		list.add(DEVICE_PRINT);
		list.add(DEVICE_VOICE);
		return list;
	}



//	public static final DeviceEnum DEVICE_ALL_PLUGIN;
//	public static final DeviceEnum DEVICE_HOST;
//	public static final DeviceEnum DEVICE_FINGER;
//	public static final DeviceEnum DEVICE_IDCARD;
//	public static final DeviceEnum DEVICE_FACE;
//	public static final DeviceEnum DEVICE_OCR;
//	public static final DeviceEnum DEVICE_PRINT;
//	public static final DeviceEnum DEVICE_LIVE;
//	public static final DeviceEnum DEVICE_VOICE;
//	private static final List<DeviceEnum> tList = new ArrayList<>();
//	private static final DeviceEnum $VALUES[];
//
//	public static DeviceEnum[] values()
//	{
//		return $VALUES.clone();
//	}
//
//	private DeviceEnum(String s, int i)
//	{
//		super(s, i);
//	}
//
//	public static List getAllCanCloseDevice()
//	{
//		if (tList.size() == 0)
//		{
//			tList.add(DEVICE_FACE);
//			tList.add(DEVICE_LIVE);
//			tList.add(DEVICE_OCR);
//			tList.add(DEVICE_PRINT);
//			tList.add(DEVICE_VOICE);
//		}
//		return tList;
//	}
//
//	static
//	{
//		DEVICE_ALL_PLUGIN = new DeviceEnum("DEVICE_ALL_PLUGIN", 0);
//		DEVICE_HOST = new DeviceEnum("DEVICE_HOST", 1);
//		DEVICE_FINGER = new DeviceEnum("DEVICE_FINGER", 2);
//		DEVICE_IDCARD = new DeviceEnum("DEVICE_IDCARD", 3);
//		DEVICE_FACE = new DeviceEnum("DEVICE_FACE", 4);
//		DEVICE_OCR = new DeviceEnum("DEVICE_OCR", 5);
//		DEVICE_PRINT = new DeviceEnum("DEVICE_PRINT", 6);
//		DEVICE_LIVE = new DeviceEnum("DEVICE_LIVE", 7);
//		DEVICE_VOICE = new DeviceEnum("DEVICE_VOICE", 8);
//		$VALUES = (new DeviceEnum[] {
//			DEVICE_ALL_PLUGIN, DEVICE_HOST, DEVICE_FINGER, DEVICE_IDCARD, DEVICE_FACE, DEVICE_OCR, DEVICE_PRINT, DEVICE_LIVE, DEVICE_VOICE
//		});
//	}
}
