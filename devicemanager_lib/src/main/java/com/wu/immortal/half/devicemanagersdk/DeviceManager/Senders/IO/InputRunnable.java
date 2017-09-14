package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.StreamCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.util.HexDump;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:48
 */

public class InputRunnable implements Runnable {
    private static final ByteBuffer mReadBuffer = ByteBuffer.allocate(2000);
    private UsbSerialPort usbSerialPort;
    private StreamCallBack callBack;

    InputRunnable(UsbSerialPort port, StreamCallBack callBack) {
        ByteBuffer var3 = mReadBuffer;
        synchronized(mReadBuffer) {
            this.usbSerialPort = port;
            this.callBack = callBack;
        }
    }

    public void run() {
        ByteBuffer var1 = mReadBuffer;
        synchronized(mReadBuffer) {
            try {
                Loger.i("InputThread : run() 准备读取....");
                if(!this.callBack.isCancle()) {
                    int e = this.usbSerialPort.read(mReadBuffer.array(), 200);
                    Loger.i("InputThread : run() 读取结束,长度 = " + e);
                    byte[] array = new byte[e];
                    mReadBuffer.get(array, 0, e);
                    Loger.i("InputThread : run() 数据为 = " + HexDump.dumpHexString(array));
                    if(this.callBack != null && !this.callBack.isCancle()) {
                        this.callBack.operationMsgSucc(HexDump.dumpHexString(array));
                    }

                    return;
                }

                Loger.i("InputThread : run() 取消读取....");
            } catch (IOException var9) {
                var9.printStackTrace();
                Loger.i("InputThread : run() 读取异常。 e = " + var9.getMessage());
                if(this.callBack != null && !this.callBack.isCancle()) {
                    this.callBack.operationMsgErro((String)null, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPRION_OPERATION_SEND));
                }

                return;
            } finally {
                mReadBuffer.clear();
                this.usbSerialPort = null;
                this.callBack = null;
            }

        }
    }
}
