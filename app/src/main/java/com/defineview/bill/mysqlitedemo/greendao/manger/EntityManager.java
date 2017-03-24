package com.defineview.bill.mysqlitedemo.greendao.manger;

import com.defineview.bill.mysqlitedemo.greendao.gen.UserOneDao;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserTwoDao;

/**
 * 实体类管理
 */
public class EntityManager {

    private static EntityManager entityManager;
    public UserOneDao userDaoOne;
    public UserTwoDao userDao;

    /**
     * 创建User1表实例
     *
     * @return
     */
    public UserOneDao getUserOneDao() {
        userDaoOne = DaoManager.getInstance().getSession().getUserOneDao();
        return userDaoOne;
    }

    /**
     * 创建User2表实例
     *
     * @return
     */
    public UserTwoDao getUserTwoDao() {
        userDao = DaoManager.getInstance().getSession().getUserTwoDao();
        return userDao;
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}  