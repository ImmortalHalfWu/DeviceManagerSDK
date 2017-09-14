// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Loger.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps;

import android.util.Log;

public class Loger
{

	private static final String TAG = "DeviceManager";

	public Loger()
	{
	}

	public static void i(String log)
	{
		Log.e("DeviceManager", log);
	}
}
