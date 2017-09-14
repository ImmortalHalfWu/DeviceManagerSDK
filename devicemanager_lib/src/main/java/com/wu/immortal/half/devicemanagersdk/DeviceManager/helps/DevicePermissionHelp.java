// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DevicePermissionHelp.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps;

import android.app.PendingIntent;
import android.content.*;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.helps:
//			Loger

public class DevicePermissionHelp extends BroadcastReceiver
{
	public interface CallBack<T>
	{

		void success(UsbDevice usbdevice, T obj);

		void erro(UsbDevice usbdevice, T obj, DeviceException deviceexception);
	}


	public static String USBPermissionString = "com.linc.USB_PERMISSION";
	private CallBack callBack;
	private Context context;
	private Object object;

	public DevicePermissionHelp(Context context, UsbDevice device, CallBack callBack, Object object)
	{
		this.object = object;
		this.callBack = callBack;
		PendingIntent permissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(USBPermissionString), 0);
		IntentFilter permissionFilter = new IntentFilter(USBPermissionString);
		permissionFilter.setPriority(0x7fffffff);
		this.context = context;
		if (device != null)
		{
			Loger.i((new StringBuilder()).append("DevicePermissionHelp : V").append(device.getVendorId()).append("__P").append(device.getProductId()).toString());
			context.registerReceiver(this, permissionFilter);
			UsbManager usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
			if (!usbManager.hasPermission(device))
			{
				Loger.i("DevicePermissionHelp : 准备获取权限请求.....");
				usbManager.requestPermission(device, permissionIntent);
			} else
			{
				Loger.i("DevicePermissionHelp : 已有权限");
				callBack.success(device, object);
			}
		}
	}

	public static void getPermission(Context context, UsbDevice device, CallBack callBack, Object object)
	{
		new DevicePermissionHelp(context, device, callBack, object);
	}

	/**
	 * @deprecated Method getPermission is deprecated
	 */

	public static void getPermission(@NonNull Context context, @NonNull UsbDevice device, @NonNull final CallBack callBack)
	{
		new DevicePermissionHelp(context, device, new CallBack() {
			@Override
			public void success(UsbDevice usbdevice, Object obj) {
				callBack.success(usbdevice, obj);
			}

			@Override
			public void erro(UsbDevice usbdevice, Object obj, DeviceException deviceexception) {
                callBack.erro(usbdevice,obj,deviceexception);
			}
		},"");
	}

	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		Loger.i((new StringBuilder()).append("PermissionReveiver : action = ").append(action).toString());
		if (USBPermissionString.equals(action))
			synchronized (this)
			{
				UsbDevice device = intent.getParcelableExtra("device");
				if (intent.getBooleanExtra("permission", false))
				{
					if (device != null)
					{
						Loger.i((new StringBuilder()).append("权限获取成功_PermissionReveiver : VID=").append(device.getVendorId()).append("__PID=").append(device.getProductId()).toString());
						if (callBack != null)
							callBack.success(device, object);
					}
				} else
				{
					Loger.i((new StringBuilder()).append("权限获取失败_PermissionReveiver : VID=").append(device.getVendorId()).append("__PID=").append(device.getProductId()).toString());
					if (callBack != null)
						callBack.erro(device, object, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_PERMISSION));
				}
			}
		free();
	}

	public void free()
	{
		context.unregisterReceiver(this);
		callBack = null;
		object = null;
	}

}
