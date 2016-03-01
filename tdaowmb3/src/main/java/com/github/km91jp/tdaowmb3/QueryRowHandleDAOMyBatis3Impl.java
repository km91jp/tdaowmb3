package com.github.km91jp.tdaowmb3;

import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.dao.event.DataRowHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.github.km91jp.tdaowmb3.event.RowHandlerWrapper;

/**
 * QueryRowHandleDAOのMyBatis3実装
 * 
 * @see jp.terasoluna.fw.dao.event.DataRowHandler
 * @see jp.terasoluna.fw.dao.QueryRowHandleDAO
 */
public class QueryRowHandleDAOMyBatis3Impl extends SqlSessionDaoSupport implements QueryRowHandleDAO {

    /**
     * ロガー
     */
    private static Log log = LogFactory.getLog(QueryRowHandleDAOMyBatis3Impl.class);

    /**
     * {@inheritDoc}
     */
    public void executeWithRowHandler(final String sqlID, final Object bindParams, final DataRowHandler rowHandler) {
        if (log.isDebugEnabled()) {
            log.debug("executeWithRowHandler Start.");
        }

        getSqlSession().select(sqlID, bindParams, new RowHandlerWrapper(rowHandler));

        if (log.isDebugEnabled()) {
            log.debug("executeWithRowHandler End.");
        }
    }

}
