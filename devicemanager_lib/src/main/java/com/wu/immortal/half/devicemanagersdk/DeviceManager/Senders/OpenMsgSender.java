// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OpenMsgSender.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OpenPluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.DeviceOperationSetHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders:
//			MsgSender, MsgSenderCallBacker, TimeOutMsgSender

class OpenMsgSender
	implements OpenPluginInterface
{

	private static volatile OpenMsgSender openMsgSender;
	private Handler mainHandler;
	private volatile boolean isFree;

	private OpenMsgSender()
	{
		isFree = false;
		mainHandler = new Handler(Looper.getMainLooper());
	}

	static OpenMsgSender install()
	{
		return openMsgSender != null ? openMsgSender : (openMsgSender = new OpenMsgSender());
	}

	public void openPluginDevice(Context context, DeviceEnum deviceEnum, UsbSerialPort hostPort, OperationPluginCallBack callBack)
	{
		Loger.i((new StringBuilder()).append("OpenMsgSender : openPluginDevice 开启设备").append(deviceEnum).toString());
		String msg[] = DeviceOperationSetHelp.getOpenOperationSet(deviceEnum);
		if (isFree)
		{
			Loger.i("OpenMsgSender : isfree单例都释放了还开哪门子设备");
			callBack.operationPluginErro(deviceEnum, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_MANAGER_FREE));
			return;
		}
		MsgSender msgSender;
		if (deviceEnum == DeviceEnum.DEVICE_FINGER || deviceEnum == DeviceEnum.DEVICE_IDCARD || deviceEnum == DeviceEnum.DEVICE_PRINT)
			msgSender = new MsgSender(context, hostPort, deviceEnum, msg, MsgSenderCallBacker.newInstanceEveryStepCallBack(callBack));
		else
			msgSender = new MsgSender(context, hostPort, deviceEnum, msg, MsgSenderCallBacker.newInstanceOnlySendMsgCallBack(callBack));
		TimeOutMsgSender.newInstance().runAndDefaultTimeOut(msgSender, msgSender);
	}

	public void free()
	{
		isFree = true;
		openMsgSender = null;
		mainHandler = null;
	}
}
