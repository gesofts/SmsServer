package com.nwx.main;

import com.nwx.bean.UserBean;
import com.nwx.config.Config;
import com.nwx.service.UserService;
import com.nwx.thread.LoadUserThread;
import com.nwx.utils.ConnectionPool;
import com.nwx.utils.InitLoad;
import com.nwx.utils.LogUtil;

import java.io.File;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by WCL on 2018/1/23.
 */
public class SmsServer
{

    private Config mConfig = null;
    private ConnectionPool mConnPool = null;

    private LoadUserThread mLoadUserThread = null;
    private UserService userService;

    public SmsServer()
    {
        String rootpath = System.getProperty("user.dir");
        Config.setConfigFileName(rootpath+ File.separatorChar+"app.properties");
        mConfig = Config.getInstance();
    }

    /**
     * 启动服务
     */
    public void startServer()
    {
        // 初始化
        init();

        userService = new UserService(mConnPool);

        // 定时任务
        scheduleTask();
    }


    /**
     * 定时加载任务
     */
    public void scheduleTask()
    {
        mLoadUserThread = new LoadUserThread(this);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(mLoadUserThread, 0, 10, TimeUnit.SECONDS);
    }



    /**
     * 初始化
     */
    public void init()
    {
        try
        {
            // 数据库连接对象
            mConnPool = new ConnectionPool("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@" + mConfig.getProperty("SQLURL") + ":" + mConfig.getProperty("SQLDB"), mConfig.getProperty("SQLOP"), mConfig.getProperty("SQLPASS"));
            LogUtil.getLogger().info("创建数据库连接池");
            mConnPool.createPool();
            LogUtil.getLogger().info("创建数据库连接池完成！");
        }
        catch (Exception e)
        {
            LogUtil.getLogger().error("数据库连接失败", e);
        }
    }


    /**
     * 加载信息
     */
    public void loadUserInfo()
    {

        Enumeration<UserBean> en2 = InitLoad.mUserManage.mHashMap.elements();
        while (en2.hasMoreElements())
        {
            UserBean mUserBean = en2.nextElement();
            LogUtil.getLogger().info(mUserBean.toString());
        }

        userService.loadUserInfo();
    }

}
