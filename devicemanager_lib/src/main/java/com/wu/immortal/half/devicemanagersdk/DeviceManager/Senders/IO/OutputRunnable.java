package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.StreamCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.util.HexDump;

import java.io.IOException;
import java.util.Arrays;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:49
 */

public class OutputRunnable implements Runnable {
    private UsbSerialPort usbSerialPort;
    private byte[] msgBytes;
    private String msgString;
    private StreamCallBack callBack;

    OutputRunnable(UsbSerialPort port, String msg, StreamCallBack callBack) {
        this.msgString = msg;
        this.msgBytes = HexDump.hexStringToByteArray(msg);
        this.callBack = callBack;
        this.usbSerialPort = port;
    }

    public void run() {
        try {
            Loger.i("OutputThread : run() 收到数据=" + this.msgString);
            Loger.i("OutputThread : run() 数据转字节=" + Arrays.toString(this.msgBytes));
            Loger.i("OutputThread : run() 数据转字节后，长度=" + this.msgBytes.length);
            if(this.callBack.isCancle()) {
                Loger.i("OutputThread : run() 发送取消" + HexDump.dumpHexString(this.msgBytes));
                return;
            }

            Loger.i("OutputThread : run() 准备发送数据");
            this.usbSerialPort.write(this.msgBytes, 200);
            Loger.i("OutputThread : run() 数据发送完成");
            if(this.callBack != null && !this.callBack.isCancle()) {
                this.callBack.operationMsgSucc(this.msgString);
            }
        } catch (IOException var5) {
            Loger.i("OutputThread : run() 数据发送失败 e = " + var5.getMessage());
            var5.printStackTrace();
            if(this.callBack != null && !this.callBack.isCancle()) {
                this.callBack.operationMsgErro(this.msgString, ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPRION_OPERATION_SEND));
            }
        } finally {
            this.msgBytes = null;
            this.usbSerialPort = null;
            this.callBack = null;
            this.msgString = null;
        }

    }
}
