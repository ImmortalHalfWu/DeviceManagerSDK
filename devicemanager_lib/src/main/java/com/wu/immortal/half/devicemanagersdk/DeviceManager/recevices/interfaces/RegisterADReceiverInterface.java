// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegisterADReceiverInterface.java

package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces;

import android.content.Context;

// Referenced classes of package com.wu.immortal.half.devicemanagersdk.DeviceManager.recevices.interfaces:
//			ADReceiverCallBack

public interface RegisterADReceiverInterface
{

	public abstract void registe(Context context, ADReceiverCallBack adreceivercallback);

	public abstract void unRegiste(Context context);

	public abstract boolean isRegiste();
}
