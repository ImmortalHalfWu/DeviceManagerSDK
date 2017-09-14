// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MsgSender.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.DeviceIO2;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.SendMsgToPortCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.MsgSenderCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.DevicePermissionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.ADReceiverCallBacker;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.DeviceADReceiver;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces.ADReceiverDispenserInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutCallback;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

class MsgSender
	implements Runnable, TimeOutCallback
{

	private UsbSerialPort port;
	private DeviceEnum deviceEnum;
	private MsgSenderCallBack callBack;
	private Context context;
	private String msgs[];
	private DeviceADReceiver adReceiver;
	private volatile boolean isFree;
	private boolean isCancle;
	private SendMsgToPortCallBack sendMsgToPortCallBack;
	private ADReceiverDispenserInterface adReceiverDispenserInterface;
	private DevicePermissionHelp.CallBack permissionCallBack;

	MsgSender(final Context context, UsbSerialPort port, final DeviceEnum deviceEnum, String msgs[], final MsgSenderCallBack callBack)
	{
		isFree = false;
		isCancle = false;
		sendMsgToPortCallBack = new SendMsgToPortCallBack() {

			final MsgSender this$0;

			public void sendSucc()
			{
				Loger.i((new StringBuilder()).append(hashCode()).append("   MsgSender : 2,succ").toString());
				if (isCancle())
				{
					sendErro(ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_TIME_OUT));
					free();
				}
				if (!callBack.msgSendSucc(deviceEnum))
				{
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender : 3,取消插拔广播接收与权限请求").append(deviceEnum).append("").toString());
					free();
				}
			}

			public void sendErro(DeviceException e)
			{
				Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :2,erro e=").append(e.getMessage()).toString());
				callBack.msgSendErro(deviceEnum, e);
				free();
			}

			
			{
				this.this$0 = MsgSender.this;
			}
		};
		adReceiverDispenserInterface = new ADReceiverDispenserInterface() {

			final MsgSender this$0;

			public void attachedDevice(Intent intent, DeviceEnum deviceEnum, UsbDevice attachedDevice)
			{
				if (isCancle())
				{
					callBack.permissionErro(attachedDevice, deviceEnum, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_TIME_OUT));
					free();
					return;
				}
				if (isFree())
					return;
				Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender : 3,监听插拔").append(deviceEnum).toString());
				Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender : 3,监听插拔").append(deviceEnum).append(" succ").toString());
				if (!callBack.attachedDevice(deviceEnum, attachedDevice))
				{
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender : 4,取消权限请求").append(deviceEnum).toString());
					free();
					return;
				}
				if (MsgSender.this.deviceEnum == deviceEnum)
				{
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :4,申请权限").toString());
					DevicePermissionHelp.getPermission(context, attachedDevice, permissionCallBack, deviceEnum);
				}
			}

			
			{
				this.this$0 = MsgSender.this;
			}
		};
		permissionCallBack = new DevicePermissionHelp.CallBack() {

			final MsgSender this$0;

			void success(UsbDevice device, DeviceEnum object)
			{
				if (isCancle())
				{
					erro(device, object, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_TIME_OUT));
					free();
					return;
				}
				if (!isFree())
				{
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :3 succ").toString());
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender : ").append(object).append("开启成功").toString());
					callBack.permissionSuccess(device, object);
					free();
				}
			}

			void erro(UsbDevice device, DeviceEnum object, DeviceException e)
			{
				if (isFree())
				{
					return;
				} else
				{
					Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :3,erro").toString());
					callBack.permissionErro(device, object, e);
					free();
					return;
				}
			}

			public void erro(UsbDevice usbdevice, Object obj, DeviceException deviceexception)
			{
				erro(usbdevice, (DeviceEnum)obj, deviceexception);
			}

			public void success(UsbDevice usbdevice, Object obj)
			{
				success(usbdevice, (DeviceEnum)obj);
			}

			
			{
				this.this$0 = MsgSender.this;
			}
		};
		this.port = port;
		this.deviceEnum = deviceEnum;
		this.callBack = callBack;
		this.context = context;
		this.msgs = msgs;
		adReceiver = DeviceADReceiver.newInstans();
	}

	public void run()
	{
		Loger.i((new StringBuilder()).append(hashCode()).append("   MsgSender : 1, 注册插拔广播接收器").toString());
		adReceiver.registe(context, ADReceiverCallBacker.newInstans(adReceiverDispenserInterface));
		Loger.i((new StringBuilder()).append(hashCode()).append("   MsgSender : 1, succ").toString());
		Loger.i((new StringBuilder()).append(hashCode()).append("   MsgSender : 2, 发送指令").toString());
		DeviceIO2.install().sendMsgToPort(port, sendMsgToPortCallBack, msgs);
	}

	public void free()
	{
		Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :free()").toString());
		if (isFree())
			return;
		isFree = true;
		if (adReceiver.isRegiste())
			adReceiver.unRegiste(context);
		port = null;
		deviceEnum = null;
		callBack = null;
		context = null;
		adReceiver = null;
		msgs = null;
		adReceiverDispenserInterface = null;
		permissionCallBack = null;
	}

	public boolean isFree()
	{
		return isFree;
	}

	private boolean isCancle()
	{
		return isCancle;
	}

	public void timeOut()
	{
		if (!isFree())
		{
			Loger.i((new StringBuilder()).append(hashCode()).append("  MsgSender :  操作设备").append(deviceEnum).append("超时").toString());
			isCancle = true;
		}
	}





}
