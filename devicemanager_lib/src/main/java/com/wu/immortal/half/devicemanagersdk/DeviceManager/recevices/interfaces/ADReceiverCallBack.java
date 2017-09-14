// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ADReceiverCallBack.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces;

import android.content.Intent;
import android.hardware.usb.UsbDevice;

public interface ADReceiverCallBack
{

	public abstract void attached(Intent intent, UsbDevice usbdevice);

	public abstract void detached(Intent intent, UsbDevice usbdevice);
}
