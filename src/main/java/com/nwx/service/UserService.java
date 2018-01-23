package com.nwx.service;

import com.nwx.bean.UserBean;
import com.nwx.utils.ConnectionPool;
import com.nwx.utils.InitLoad;
import com.nwx.utils.LogUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by WCL on 2018/1/23.
 */
public class UserService
{

    private ConnectionPool mConnPool = null;
    public UserService(ConnectionPool mConnPool)
    {
        this.mConnPool = mConnPool;
    }

    public void loadUserInfo()
    {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try
        {
            conn = mConnPool.getConnection();
            if (conn != null)
            {
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery("SELECT USERID, USERNAME, PASSWD FROM SYSUSER");
                if (rs != null)
                {
                    while (rs.next())
                    {
                        UserBean mUserBean = new UserBean();
                        mUserBean.setUserId(rs.getInt("USERID"));
                        mUserBean.setUserName(rs.getString("USERNAME"));
                        mUserBean.setUserPwd(rs.getString("PASSWD"));
                        InitLoad.mUserManage.add(mUserBean);
                    }
                }
            }
        }
        catch (Exception e)
        {
            LogUtil.getLogger().error("加载用户数据失败", e);
        }
        finally
        {
            mConnPool.returnConnection(conn, stmt, rs);
        }
    }

}
