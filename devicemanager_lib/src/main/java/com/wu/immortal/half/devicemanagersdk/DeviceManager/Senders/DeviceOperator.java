// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceOperator.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.CloseAllPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.CloseAllPluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.ClosePluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.DeviceOperatorInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.DeviceStateMachineInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OpenPluginInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.OperationPluginCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import java.util.List;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders:
//			DeviceStateMachine, OpenMsgSender, CloseMsgSender, CloseAllMsgSender

public class DeviceOperator
	implements DeviceOperatorInterface
{
	private class MyOpenPluginCallBack
		implements OperationPluginCallBack
	{

		private OperationPluginCallBack outCallBack;
		final DeviceOperator this$0;

		public void operationPluginSucc(DeviceEnum deviceEnum)
		{
			deviceStateMachineInterface.openPlugin(deviceEnum);
			outCallBack.operationPluginSucc(deviceEnum);
		}

		public void operationPluginErro(DeviceEnum deviceEnum, DeviceException e)
		{
			Loger.i((new StringBuilder()).append("MyOpenPluginCallBack operationPluginErro").append(e.getMessage()).toString());
			outCallBack.operationPluginErro(deviceEnum, e);
		}

		MyOpenPluginCallBack(OperationPluginCallBack outCallBack)
		{
			this$0 = DeviceOperator.this;
			this.outCallBack = outCallBack;
		}
	}

	private class MyCloseAllPluginCallBack
		implements CloseAllPluginCallBack
	{

		private CloseAllPluginCallBack outCallBack;
		final DeviceOperator this$0;

		public void closeAllStart()
		{
			outCallBack.closeAllStart();
		}

		public void closeDeviceSuc(DeviceEnum deviceEnum)
		{
			deviceStateMachineInterface.closeDevice(deviceEnum);
			outCallBack.closeDeviceSuc(deviceEnum);
		}

		public void closeDeviceErro(DeviceEnum deviceEnum, DeviceException e)
		{
			outCallBack.closeDeviceErro(deviceEnum, e);
		}

		public void closeAllOver()
		{
			outCallBack.closeAllOver();
		}

		MyCloseAllPluginCallBack(CloseAllPluginCallBack outCallBack)
		{
			this$0 = DeviceOperator.this;
			this.outCallBack = outCallBack;
		}
	}

	private class MyClosePluginCallBack
		implements OperationPluginCallBack
	{

		private OperationPluginCallBack outCallBack;
		final DeviceOperator this$0;

		public void operationPluginSucc(DeviceEnum deviceEnum)
		{
			deviceStateMachineInterface.closeDevice(deviceEnum);
			outCallBack.operationPluginSucc(deviceEnum);
		}

		public void operationPluginErro(DeviceEnum deviceEnum, DeviceException e)
		{
			outCallBack.operationPluginErro(deviceEnum, e);
		}

		MyClosePluginCallBack(OperationPluginCallBack outCallBack)
		{
			this$0 = DeviceOperator.this;
			this.outCallBack = outCallBack;
		}
	}


	private static volatile DeviceOperator deviceOperator;
	private DeviceStateMachineInterface deviceStateMachineInterface;
	private OpenPluginInterface openPluginInterface;
	private ClosePluginInterface closePluginInterface;
	private CloseAllPluginInterface closeAllPluginInterface;
	private static final String TAG = "DeviceOperator : ";

	private DeviceOperator()
	{
		deviceStateMachineInterface = DeviceStateMachine.install();
		openPluginInterface = OpenMsgSender.install();
		closePluginInterface = CloseMsgSender.install();
		closeAllPluginInterface = CloseAllMsgSender.newInstance();
	}

	public static DeviceOperator install()
	{
		return deviceOperator != null ? deviceOperator : (deviceOperator = new DeviceOperator());
	}

	public void closePluginDevice(Context context, DeviceEnum deviceEnum, UsbSerialPort hostPort, OperationPluginCallBack callBack)
	{
		closePluginInterface.closePluginDevice(context, deviceEnum, hostPort, new MyClosePluginCallBack(callBack));
	}

	public void closeAllPluginDevice(Context context, UsbSerialPort hostPort, CloseAllPluginCallBack callBack)
	{
		List runningPluginDevice = deviceStateMachineInterface.canCloseAllPlugin();
		if (runningPluginDevice.size() == 0)
		{
			logi((new StringBuilder()).append("closeAllPluginDevice () 没有可以关闭的设备").append(deviceStateMachineInterface.getRunningPluginDevice()).toString());
			callBack.closeAllOver();
			return;
		} else
		{
			logi((new StringBuilder()).append("closeAllPluginDevice () 可以关闭的设备").append(runningPluginDevice).toString());
			closeAllPluginInterface.CloseAllPluginDevice(context, hostPort, new MyCloseAllPluginCallBack(callBack), runningPluginDevice, closePluginInterface);
			return;
		}
	}

	public void openPluginDevice(Context context, DeviceEnum deviceEnum, UsbSerialPort hostPort, OperationPluginCallBack callBack)
	{
		openPluginInterface.openPluginDevice(context, deviceEnum, hostPort, new MyOpenPluginCallBack(callBack));
	}

	public void free()
	{
		deviceStateMachineInterface.free();
		openPluginInterface.free();
		closePluginInterface.free();
		deviceOperator = null;
	}

	private void logi(String msg)
	{
		Loger.i((new StringBuilder()).append("DeviceOperator : ").append(msg).toString());
	}

}
