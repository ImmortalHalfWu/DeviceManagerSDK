// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ADReceiverDispenserInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;

public interface ADReceiverDispenserInterface
{

	public abstract void attachedDevice(Intent intent, DeviceEnum deviceenum, UsbDevice usbdevice);
}
