// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CloseMsgSender.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.ClosePluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.DeviceOperationSetHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders:
//			MsgSender, MsgSenderCallBacker, TimeOutMsgSender

class CloseMsgSender
	implements ClosePluginInterface
{

	private static CloseMsgSender closeMsgSender;

	private CloseMsgSender()
	{
	}

	static CloseMsgSender install()
	{
		return closeMsgSender != null ? closeMsgSender : (closeMsgSender = new CloseMsgSender());
	}

	public void closePluginDevice(Context context, DeviceEnum deviceEnum, UsbSerialPort hostPort, OperationPluginCallBack callBack)
	{
		logi((new StringBuilder()).append("closePluginDevice()  关闭设备:").append(deviceEnum).toString());
		String msg[] = DeviceOperationSetHelp.getCloseOperationSet(deviceEnum);
		MsgSender msgSender = new MsgSender(context, hostPort, deviceEnum, msg, MsgSenderCallBacker.newInstanceOnlySendMsgCallBack(callBack));
		TimeOutMsgSender.newInstance().runAndDefaultTimeOut(msgSender, msgSender);
	}

	public void free()
	{
		closeMsgSender = null;
	}

	private void logi(String msg)
	{
		Loger.i((new StringBuilder()).append("CloseMsgSender ： ").append(msg).toString());
	}
}
