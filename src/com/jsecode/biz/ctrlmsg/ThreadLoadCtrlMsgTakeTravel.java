package com.jsecode.biz.ctrlmsg;

import com.jsecode.biz.IAddDataToQueue;
import com.jsecode.bean.ctrlmsg.CtrlMsgTakeTravelBean;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 项目名称：gpsGW809
 * 类名称： com.jsecode.biz.ctrlmsg
 * 类描述：
 * ****************************
 * *
 * *
 * *
 * ****************************
 * 创建人：zhaorg
 * 创建时间：14-3-12 上午9:39
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ThreadLoadCtrlMsgTakeTravel  implements Runnable{
    private IAddDataToQueue<CtrlMsgTakeTravelBean> sendData;
    private CountDownLatch dataLoadSignal;
    public ThreadLoadCtrlMsgTakeTravel(IAddDataToQueue<CtrlMsgTakeTravelBean> sendData,CountDownLatch dataLoadSignal){
        this.sendData=sendData;
        this.dataLoadSignal=dataLoadSignal;
    }
    @Override
    public void run() {
        try {//wait the terminal info loaded
            this.dataLoadSignal.await();
        } catch (InterruptedException e) {
        }

        List<CtrlMsgTakeTravelBean> list = null;
        //TODO load CtrlMsgTakeTravel info from database or memcached

        addInfoToQueue(list);
    }
    private void addInfoToQueue(List<CtrlMsgTakeTravelBean> list) {
        if (list != null && list.size() > 0) {
            this.sendData.addListToQueue(list);
        }
    }
}
