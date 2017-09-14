// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MsgSenderCallBack.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import android.hardware.usb.UsbDevice;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;

public interface MsgSenderCallBack
{

	public abstract boolean msgSendSucc(DeviceEnum deviceenum);

	public abstract void msgSendErro(DeviceEnum deviceenum, DeviceException deviceexception);

	public abstract boolean permissionSuccess(UsbDevice usbdevice, DeviceEnum deviceenum);

	public abstract void permissionErro(UsbDevice usbdevice, DeviceEnum deviceenum, DeviceException deviceexception);

	public abstract boolean attachedDevice(DeviceEnum deviceenum, UsbDevice usbdevice);
}
