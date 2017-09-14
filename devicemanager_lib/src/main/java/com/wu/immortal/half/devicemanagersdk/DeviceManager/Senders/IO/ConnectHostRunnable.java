// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ConnectHostRunnable.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Looper;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.ConnectHostRunnableCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

import java.io.IOException;

class ConnectHostRunnable
		implements Runnable {
	private static final int MAX_SLEEP_TIME = 1;
	private UsbDevice usbDevice;
	private ConnectHostRunnableCallBack connectHostRunnableCallBack;
	private UsbManager usbManager;
	private UsbSerialPort usbSerialPort;

	ConnectHostRunnable(ConnectHostRunnableCallBack connectHostRunnableCallBack, UsbManager usbManager, UsbSerialPort usbSerialPort) {
		this.usbDevice = usbSerialPort.getDriver().getDevice();
		this.connectHostRunnableCallBack = connectHostRunnableCallBack;
		this.usbManager = usbManager;
		this.usbSerialPort = usbSerialPort;
	}

	public void run() {
		if(Looper.getMainLooper() == Looper.myLooper()) {
			throw new RuntimeException("open device can\'t runing mainThread.");
		} else {
			Loger.i("ConnectHostRunnable ： 准备open DeviceManager....." + this.usbDevice);
			UsbDeviceConnection usbDeviceConnection = this.usbManager.openDevice(this.usbDevice);
			if(usbDeviceConnection == null) {
				Loger.i("ConnectHostRunnable : open DeviceManager 失败. 是否有权限？");
				this.connectHostRunnableCallBack.openFail(ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_PERMISSION));
			}

			Loger.i("ConnectHostRunnable : open DeviceManager 成功.");

			try {
				Loger.i("ConnectHostRunnable : 准备连接port 0....." + this.usbSerialPort);
				this.usbSerialPort.open(usbDeviceConnection);
				this.usbSerialPort.setParameters(115200, 8, 1, 0);
				Thread.sleep(1L);
				Loger.i("ConnectHostRunnable : 连接port 0 " + (this.usbDevice.getInterfaceCount() > 0?"成功":"失败"));
			} catch (InterruptedException | IOException var3) {
				var3.printStackTrace();
				Loger.i("ConnectHostRunnable : 连接port 0异常，e = " + var3.getMessage());
				this.connectHostRunnableCallBack.openFail(ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_CONNECT_HOST));
			}

			this.connectHostRunnableCallBack.openSuc(this.usbSerialPort);
			this.connectHostRunnableCallBack = null;
			this.usbDevice = null;
			this.usbManager = null;
			this.usbSerialPort = null;
		}
	}
}
