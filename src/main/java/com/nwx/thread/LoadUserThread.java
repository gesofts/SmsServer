package com.nwx.thread;

import com.nwx.main.SmsServer;

/**
 * Created by WCL on 2018/1/23.
 */
public class LoadUserThread extends Thread
{

    private SmsServer smsServer = null;
    public LoadUserThread(SmsServer smsServer)
    {
        this.smsServer = smsServer;
    }

    @Override
    public void run()
    {
        this.smsServer.loadUserInfo();
    }
}
