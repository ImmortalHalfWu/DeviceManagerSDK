// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceManager.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.manager;

import android.app.Application;
import android.content.Context;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.DeviceOperator;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.OpenHostUtil;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.ConnectHostRunnableCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.*;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceManagerEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Utils;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.manager.interfaces.ManagerStateMachineInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import java.io.IOException;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.manager:
//			DeviceManagerManagerStateMachine

public class DeviceManager
{
	private class MyCloseAllPluginCallBack
		implements CloseAllPluginCallBack
	{

		private CloseAllPluginCallBack outCallback;
		final DeviceManager this$0;

		public void closeAllStart()
		{
			managerStateMachineInterface.operationPluginDevice();
			outCallback.closeAllStart();
		}

		public void closeDeviceSuc(DeviceEnum deviceEnum)
		{
			log(deviceEnum, "关闭成功");
			outCallback.closeDeviceSuc(deviceEnum);
		}

		public void closeDeviceErro(DeviceEnum deviceEnum, DeviceException e)
		{
			log(deviceEnum, "关闭失败");
			outCallback.closeDeviceErro(deviceEnum, e);
		}

		public void closeAllOver()
		{
			managerStateMachineInterface.operationPluginSucc(DeviceEnum.DEVICE_ALL_PLUGIN);
			outCallback.closeAllOver();
			free();
		}

		private void free()
		{
			outCallback = null;
		}

		private void logSucc(DeviceEnum deviceEnum, String tag)
		{
			Loger.i("=================================================================");
			Loger.i((new StringBuilder()).append("=====================").append(deviceEnum).append(tag).append("=========================").toString());
			Loger.i("=================================================================");
		}

		public MyCloseAllPluginCallBack(CloseAllPluginCallBack outCallback)
		{
			this$0 = DeviceManager.this;
			this.outCallback = outCallback;
		}
	}

	private class MyOperationPluginCallBack
		implements OperationPluginCallBack
	{

		private static final int TAG_OPEN = 327;
		private static final int TAG_CLOSE = 600;
		private OperationPluginCallBack outCallback;
		private int nowTag;
		final DeviceManager this$0;

		public void operationPluginSucc(DeviceEnum deviceEnum)
		{
			if (managerStateMachineInterface.getDeviceManagerState() != DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_ING)
				throw new SecurityException((new StringBuilder()).append("MyOperationPluginCallBack : operationPluginSucc() ").append(deviceEnum).append("�����ɹ�����DeviceManager״̬�쳣 �� ").append(managerStateMachineInterface.getDeviceManagerState()).toString());
			managerStateMachineInterface.operationPluginSucc(deviceEnum);
			outCallback.operationPluginSucc(deviceEnum);
			if (nowTag == 327)
				log(deviceEnum, "�ѿ���");
			else
				log(deviceEnum, "�ѹر�");
			free();
		}

		public void operationPluginErro(DeviceEnum deviceEnum, DeviceException e)
		{
			Loger.i((new StringBuilder()).append("MyOperationPluginCallBack : operationPluginErro + ").append(e.getMessage()).toString());
			if (managerStateMachineInterface.getDeviceManagerState() != DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_ING)
				throw new SecurityException((new StringBuilder()).append("MyOperationPluginCallBack : operationPluginSucc() ����ʧ�ܣ�DeviceManager״̬�쳣 �� ").append(managerStateMachineInterface.getDeviceManagerState()).toString());
			managerStateMachineInterface.operationPluginErro(deviceEnum, e);
			outCallback.operationPluginErro(deviceEnum, e);
			if (nowTag == 327)
				log(deviceEnum, "����ʧ��");
			else
				log(deviceEnum, "�ر�ʧ��");
			free();
		}

		private void free()
		{
			outCallback = null;
		}

		private void logSucc(DeviceEnum deviceEnum, String tag)
		{
			Loger.i("=================================================================");
			Loger.i((new StringBuilder()).append("=====================").append(deviceEnum).append(tag).append("=========================").toString());
			Loger.i("=================================================================");
		}

		public MyOperationPluginCallBack(OperationPluginCallBack outCallbace, int tag)
		{
			this$0 = DeviceManager.this;
//			super();
			outCallback = outCallbace;
			nowTag = tag;
		}
	}

	private class MyConnectHostCallBack
		implements ConnectHostRunnableCallBack
	{

		private ConnectHostRunnableCallBack outCallBack;
		final DeviceManager this$0;

		public void openSuc(UsbSerialPort usbSerialPort)
		{
			DeviceManager.this.usbSerialPort = usbSerialPort;
			if (outCallBack != null)
				outCallBack.openSuc(usbSerialPort);
			free();
			managerStateMachineInterface.openSuc(usbSerialPort);
		}

		public void openFail(DeviceException e)
		{
			if (outCallBack != null)
				outCallBack.openFail(e);
			free();
			managerStateMachineInterface.openFail(e);
		}

		private void free()
		{
			outCallBack = null;
		}

		private MyConnectHostCallBack(ConnectHostRunnableCallBack outCallBack)
		{
			this$0 = DeviceManager.this;
			this.outCallBack = outCallBack;
		}

	}


	private static volatile DeviceManager deviceManager;
	private volatile UsbSerialPort usbSerialPort;
	private Context context;
	private volatile DeviceOperatorInterface deviceOperatorInterface;
	private volatile ManagerStateMachineInterface managerStateMachineInterface;

	private DeviceManager(Application app)
	{
		context = app;
		deviceOperatorInterface = DeviceOperator.install();
		managerStateMachineInterface = DeviceManagerManagerStateMachine.install();
		root();
		initHost(new ConnectHostRunnableCallBack() {

			final DeviceManager this$0;

			public void openSuc(UsbSerialPort usbserialport)
			{
			}

			public void openFail(DeviceException deviceexception)
			{
			}

			
			{
				this.this$0 = DeviceManager.this;
			}
		});
	}

	private void root()
	{
		Utils.rootPermission(context);
	}

	public static DeviceManager install(Application context)
	{
		Loger.i((new StringBuilder()).append("DeviceManager install() deviceManager == null ? ").append(deviceManager == null).toString());
		return deviceManager != null ? deviceManager : (deviceManager = new DeviceManager(context));
	}

	public void openPluginDevice(DeviceEnum deviceEnum, OperationPluginCallBack operationPluginCallBack)
	{
		if (managerStateMachineInterface.canOperationPluginDevice(deviceEnum))
		{
			managerStateMachineInterface.operationPluginDevice();
			log(deviceEnum, "׼������");
			deviceOperatorInterface.openPluginDevice(context, deviceEnum, usbSerialPort, new MyOperationPluginCallBack(operationPluginCallBack, 327));
		} else
		{
			operationPluginCallBack.operationPluginErro(deviceEnum, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_OPERATIONING));
		}
	}

	public void closePluginDevice(DeviceEnum deviceEnum, OperationPluginCallBack operationPluginCallBack)
	{
		if (managerStateMachineInterface.canOperationPluginDevice(deviceEnum))
		{
			managerStateMachineInterface.operationPluginDevice();
			log(deviceEnum, "׼���ر�");
			deviceOperatorInterface.closePluginDevice(context, deviceEnum, usbSerialPort, new MyOperationPluginCallBack(operationPluginCallBack, 600));
		} else
		{
			operationPluginCallBack.operationPluginErro(deviceEnum, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_OPERATIONING));
		}
	}

	private void initHost(ConnectHostRunnableCallBack outCallBack)
	{
		managerStateMachineInterface.openHost();
		OpenHostUtil.instance().open(context, new MyConnectHostCallBack(outCallBack));
	}

	public void resetDevice(final ConnectHostRunnableCallBack outCallBack)
	{
		if (!managerStateMachineInterface.canResetHost())
		{
			outCallBack.openFail(ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_OPERATIONING));
		} else
		{
			closeAllDevice(new CloseAllPluginCallBack() {
				@Override
				public void closeAllStart() {

				}

				@Override
				public void closeDeviceSuc(DeviceEnum deviceenum) {

				}

				@Override
				public void closeDeviceErro(DeviceEnum deviceenum, DeviceException deviceexception) {

				}

				@Override
				public void closeAllOver() {
					closePort();
					initHost(outCallBack);
				}
			});

		}
	}

	private void closeAllDevice(CloseAllPluginCallBack closeAllPluginCallBack)
	{
		deviceOperatorInterface.closeAllPluginDevice(context, usbSerialPort, new MyCloseAllPluginCallBack(closeAllPluginCallBack));
	}

	public static void freeManager()
	{
		Loger.i((new StringBuilder()).append("DeviceManager.freeManager(),isFree ? ").append(deviceManager == null).toString());
		if (deviceManager != null)
			deviceManager.free();
	}

	private void free()
	{
		closeAllDevice(new CloseAllPluginCallBack() {

			final DeviceManager this$0;

			public void closeAllStart()
			{
			}

			public void closeDeviceSuc(DeviceEnum deviceenum)
			{
			}

			public void closeDeviceErro(DeviceEnum deviceenum, DeviceException deviceexception)
			{
			}

			public void closeAllOver()
			{
				deviceOperatorInterface.free();
				deviceOperatorInterface = null;
				managerStateMachineInterface.free();
				managerStateMachineInterface = null;
				context = null;
				DeviceManager.deviceManager = null;
				closePort();
				Utils.connectManager();
			}

			
			{
				this.this$0 = DeviceManager.this;
			}
		});
	}

	private void closePort()
	{

		if (usbSerialPort == null) return;

		try {
			usbSerialPort.close();
		} catch (IOException ignored) {}
		finally {
			try {
				usbSerialPort.close();
			} catch (IOException ignored) {}
		}

	}

	private void log(DeviceEnum deviceEnum, String tag)
	{
		Loger.i("\n================================================================================");
		Loger.i((new StringBuilder()).append("===============================").append(deviceEnum).append(tag).append("===============================").toString());
		Loger.i("================================================================================\n");
	}










}
