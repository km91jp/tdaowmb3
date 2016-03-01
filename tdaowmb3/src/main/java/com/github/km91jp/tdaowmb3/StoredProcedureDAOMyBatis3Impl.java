package com.github.km91jp.tdaowmb3;

import jp.terasoluna.fw.dao.StoredProcedureDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
/**
 * StoredProcedureDAOのMyBatis3実装
 * 
 * @see jp.terasoluna.fw.dao.StoredProcedureDAO
 */
public class StoredProcedureDAOMyBatis3Impl extends SqlSessionDaoSupport implements StoredProcedureDAO {

    /**
     * ロガー
     */
    private static Log log = LogFactory.getLog(StoredProcedureDAOMyBatis3Impl.class);

    /**
     * {@inheritDoc}
     */
    public void executeForObject(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }

        getSqlSession().update(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
        }

    }

}
