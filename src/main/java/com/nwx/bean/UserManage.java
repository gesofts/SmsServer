package com.nwx.bean;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WCL on 2018/1/23.
 */
public class UserManage
{
    public ConcurrentHashMap<Integer, UserBean> mHashMap;

    public UserManage()
    {
        mHashMap = new ConcurrentHashMap<>();
    }


    public UserBean add(int userId)
    {
        UserBean mUserBean = mHashMap.get(userId);
        if (mUserBean == null)
        {
            mUserBean = new UserBean();
            mUserBean.setUserId(userId);
            mHashMap.put(userId, mUserBean);
        }
        return mUserBean;
    }


    public UserBean add(UserBean mUserBean)
    {
        if (mHashMap.get(mUserBean.getUserId()) == null)
        {
            mHashMap.put(mUserBean.getUserId(), mUserBean);
        }
        return mUserBean;
    }

    public int size()
    {
        return mHashMap.size();
    }

    public void clear()
    {
        mHashMap.clear();
    }

    public UserBean get(int userId)
    {
        return mHashMap.get(userId);
    }
}
