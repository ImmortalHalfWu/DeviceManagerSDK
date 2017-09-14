package com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.annotation.NonNull;

import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.ExceptionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.enums.ExceptionCodeEnum;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Exceptions.interfaces.DeviceException;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.ConnectHostRunnableCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.Senders.IO.interfaces.OpenHostDeviceCallBack;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.DevicePermissionHelp;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.helps.Loger;
import com.wu.immortal.half.devicemanagersdk.DeviceManager.usbserial.driver.UsbSerialPort;

/**
 * Do :
 * Created : immortalHalfWu
 * Time : 2017/9/13  13:48
 */

public class OpenHostUtil {

    private static volatile OpenHostUtil openHostUtil;
    private Context context;
    private ConnectHostRunnableCallBack outCallBack;
    private boolean isFree = false;
    private OpenHostDeviceCallBack openHostDeviceCallBack = new OpenHostDeviceCallBack() {
        public void resetHostSucc(UsbDevice hostDevice, UsbSerialPort hostPort) {
            Loger.i("OpenHostUtil : 1,succ, port class " + hostPort.getClass());
            Loger.i("OpenHostUtil : open() 2,获取hostDevice使用权限");
            DevicePermissionHelp.getPermission(OpenHostUtil.this.context, hostDevice, OpenHostUtil.this.permissionCallBack, hostPort);
        }

        public void resetHostErro(Exception e) {
            Loger.i("OpenHostUtil : open() 1,Erro, e = " + e.getMessage());
            OpenHostUtil.this.free();
        }
    };
    private DevicePermissionHelp.CallBack<UsbSerialPort> permissionCallBack = new DevicePermissionHelp.CallBack<UsbSerialPort>() {
        public void success(UsbDevice device, UsbSerialPort object) {
            if(!OpenHostUtil.this.isFree) {
                Loger.i("OpenHostUtil : 2,succ");
                Loger.i("OpenHostUtil : 3,连接HostDevice");
                (new Thread(new ConnectHostRunnable(OpenHostUtil.this.openCallBack, (UsbManager)OpenHostUtil.this.context.getSystemService(Context.USB_SERVICE), object))).start();
            }
        }

        public void erro(UsbDevice device, UsbSerialPort object, DeviceException e) {
            if(!OpenHostUtil.this.isFree) {
                Loger.i("OpenHostUtil : 2,Erro");
                OpenHostUtil.this.outCallBack.openFail(e);
                OpenHostUtil.this.free();
            }
        }
    };
    private ConnectHostRunnableCallBack openCallBack = new ConnectHostRunnableCallBack() {
        public void openSuc(UsbSerialPort usbSerialPort) {
            if(!OpenHostUtil.this.isFree) {
                Loger.i("OpenHostUtil : 3,succ");
                Loger.i("OpenHostUtil : host device 开启完毕。");
                OpenHostUtil.this.outCallBack.openSuc(usbSerialPort);
                OpenHostUtil.this.free();
            }
        }

        public void openFail(DeviceException e) {
            if(!OpenHostUtil.this.isFree) {
                Loger.i("OpenHostUtil : 3,erro, e = " + e.getMessage());
                OpenHostUtil.this.outCallBack.openFail(e);
                OpenHostUtil.this.free();
            }
        }
    };

    public static OpenHostUtil instance() {
        return openHostUtil == null?(openHostUtil = new OpenHostUtil()):openHostUtil;
    }

    private OpenHostUtil() {
    }

    public synchronized void open(@NonNull Context context, @NonNull ConnectHostRunnableCallBack callBack) {
        if(this.isFree) {
            Loger.i("OpenHostUtil : open() DeviceManager已经全部释放");
            callBack.openFail(ExceptionHelp.createExceptionFromEnum(ExceptionCodeEnum.EXCEPTION_MANAGER_FREE));
        } else {
            this.context = context;
            this.outCallBack = callBack;
            Loger.i("OpenHostUtil : open() 1,重置插板 + 获取host device port");
            ResetHostDeviceRunnable.newInstansAndStart(context, this.openHostDeviceCallBack);
        }
    }

    private void free() {
        this.isFree = true;
        this.context = null;
        this.outCallBack = null;
        this.openHostDeviceCallBack = null;
        this.permissionCallBack = null;
        this.openCallBack = null;
        Loger.i("OpenHostUtil ； free() ");
    }

}
