package com.defineview.bill.mysqlitedemo.greendao.manger;

import com.defineview.bill.mysqlitedemo.greendao.gen.UserOneDao;
import com.defineview.bill.mysqlitedemo.greendao.gen.UserTwoDao;

/**
 * 实体类管理（表格管理）
 */
public class EntityManager {

    private static EntityManager entityManager;

    /**
     * 创建User1表实例
     *
     * @return
     */
    public UserOneDao getUserOneDao() {
        return DaoManager.getInstance().getSession().getUserOneDao();
    }

    /**
     * 创建User2表实例
     *
     * @return
     */
    public UserTwoDao getUserTwoDao() {
        return DaoManager.getInstance().getSession().getUserTwoDao();
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