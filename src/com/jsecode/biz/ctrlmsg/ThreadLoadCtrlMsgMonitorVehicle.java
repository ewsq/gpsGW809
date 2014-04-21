package com.jsecode.biz.ctrlmsg;

import com.jsecode.biz.IAddDataToQueue;
import com.jsecode.bean.ctrlmsg.CtrlMsgMonitorVehicleBean;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 项目名称：gpsGW809
 * 类名称： com.jsecode.biz.ctrlmsg.ThreadLoadCtrlMsgMonitorVehicle
 * 类描述：
 * ****************************
 * *加载车辆单向监听应答消息
 * ****************************
 * 创建人：zhaorg
 * 创建时间：14-3-12 上午9:39
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ThreadLoadCtrlMsgMonitorVehicle  implements Runnable{
    private IAddDataToQueue<CtrlMsgMonitorVehicleBean> sendData;
    private CountDownLatch dataLoadSignal;
    public ThreadLoadCtrlMsgMonitorVehicle(IAddDataToQueue<CtrlMsgMonitorVehicleBean> sendData,CountDownLatch dataLoadSignal){
        this.sendData=sendData;
        this.dataLoadSignal=dataLoadSignal;
    }
    @Override
    public void run() {
        try {//wait the terminal info loaded
            this.dataLoadSignal.await();
        } catch (InterruptedException e) {
        }

        List<CtrlMsgMonitorVehicleBean> list = null;
        //TODO load CtrlMsgMonitorVehicle info from database or memcached

        addInfoToQueue(list);
    }
    private void addInfoToQueue(List<CtrlMsgMonitorVehicleBean> list) {
        if (list != null && list.size() > 0) {
            this.sendData.addListToQueue(list);
        }
    }
}
