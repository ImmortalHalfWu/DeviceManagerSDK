// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimeOutMsgSenderInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.interfaces;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.timer.interfaces.TimeOutCallback;

public interface TimeOutMsgSenderInterface
{

	public abstract void runAndTimeOut(Runnable runnable, TimeOutCallback timeoutcallback, long l);

	public abstract void runAndDefaultTimeOut(Runnable runnable, TimeOutCallback timeoutcallback);
}
