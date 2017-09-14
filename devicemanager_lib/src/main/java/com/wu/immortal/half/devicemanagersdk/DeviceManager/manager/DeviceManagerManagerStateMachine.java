// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DeviceManagerManagerStateMachine.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.manager;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.enums.DeviceManagerEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.manager.interfaces.ManagerStateMachineInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

class DeviceManagerManagerStateMachine
	implements ManagerStateMachineInterface
{

	private static final String TAG = "DeviceManagerStateMachi : ";
	private static volatile DeviceManagerManagerStateMachine deviceManagerStateMachine;
	private DeviceManagerEnum nowState;

	static DeviceManagerManagerStateMachine install()
	{
		return deviceManagerStateMachine != null ? deviceManagerStateMachine : (deviceManagerStateMachine = new DeviceManagerManagerStateMachine());
	}

	private DeviceManagerManagerStateMachine()
	{
		nowState = DeviceManagerEnum.ACTION_CREATE;
	}

	public boolean canResetHost()
	{
		if (nowState == DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_ING || nowState == DeviceManagerEnum.ACTION_OPEN_HOST_ING || nowState == DeviceManagerEnum.ACTION_FREE)
		{
			logi((new StringBuilder()).append("DeviceManagerManagerStateMachine : canResetHost() DeviceManager当前状态 ： ").append(nowState).append(",无法重置").toString());
			return false;
		} else
		{
			return true;
		}
	}

	public boolean canOperationPluginDevice(DeviceEnum deviceEnum)
	{
		logi((new StringBuilder()).append("canOperationPluginDevice() 想开启").append(deviceEnum).append("? DeviceManager 状态为=").append(nowState).toString());
		return nowState == DeviceManagerEnum.ACTION_OPEN_HOST_SUCC || nowState == DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_OVER;
	}

	public void free()
	{
		deviceManagerStateMachine = null;
		nowState = null;
	}

	public DeviceManagerEnum getDeviceManagerState()
	{
		return nowState;
	}

	public void openHost()
	{
		logi((new StringBuilder()).append("openHost() : 当前状态").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPEN_HOST_ING).toString());
		nowState = DeviceManagerEnum.ACTION_OPEN_HOST_ING;
	}

	public void operationPluginDevice()
	{
		logi((new StringBuilder()).append("operationPluginDevice() : 当前状态").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_ING).toString());
		nowState = DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_ING;
	}

	public void openSuc(UsbSerialPort usbSerialPort)
	{
		logi((new StringBuilder()).append("openSuc() : 当前状态").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPEN_HOST_SUCC).toString());
		nowState = DeviceManagerEnum.ACTION_OPEN_HOST_SUCC;
	}

	public void openFail(DeviceException e)
	{
		logi((new StringBuilder()).append("openFail() : 当前状态̬").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPEN_HOST_ERRO).toString());
		nowState = DeviceManagerEnum.ACTION_OPEN_HOST_ERRO;
	}

	public void operationPluginSucc(DeviceEnum deviceEnum)
	{
		logi((new StringBuilder()).append("operationPluginSucc() : 当前状态").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_OVER).toString());
		nowState = DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_OVER;
	}

	public void operationPluginErro(DeviceEnum deviceEnum, DeviceException e)
	{
		logi((new StringBuilder()).append("operationPluginErro() : 当前状态").append(getDeviceManagerState()).append("转换为").append(DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_OVER).toString());
		nowState = DeviceManagerEnum.ACTION_OPERATOR_PLUGIN_OVER;
	}

	private void logi(String msg)
	{
		Loger.i((new StringBuilder()).append("DeviceManagerStateMachi : ").append(msg).toString());
	}
}
