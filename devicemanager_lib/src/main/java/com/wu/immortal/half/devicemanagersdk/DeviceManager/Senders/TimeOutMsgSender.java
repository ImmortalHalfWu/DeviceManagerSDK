// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimeOutMsgSender.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces.TimeOutMsgSenderInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.threads.ThreadScheduler;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.threads.interfaces.ThreadSchedulerInterface;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.Timer;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutCallback;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutInterface;

class TimeOutMsgSender
	implements TimeOutMsgSenderInterface
{

	private static TimeOutMsgSender timeOutMsgSender;
	private TimeOutInterface timeOutInterface;
	private ThreadSchedulerInterface threadScheduler;
	private final long DEFAULT_TIME = 3000L;

	public static TimeOutMsgSender newInstance()
	{
		return timeOutMsgSender != null ? timeOutMsgSender : (timeOutMsgSender = new TimeOutMsgSender());
	}

	private TimeOutMsgSender()
	{
		timeOutInterface = Timer.newInstance();
		threadScheduler = ThreadScheduler.newInstance();
	}

	public void runAndTimeOut(Runnable msgSenderRunnable, TimeOutCallback timeOutCallback, long time)
	{
		timeOutInterface.schedule(timeOutCallback, time);
		threadScheduler.runningMain(msgSenderRunnable);
	}

	public void runAndDefaultTimeOut(Runnable msgSenderRunnable, TimeOutCallback timeOutCallback)
	{
		runAndTimeOut(msgSenderRunnable, timeOutCallback, 3000L);
	}
}
