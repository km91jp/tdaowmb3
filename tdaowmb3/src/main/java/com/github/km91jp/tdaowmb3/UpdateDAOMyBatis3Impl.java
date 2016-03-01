package com.github.km91jp.tdaowmb3;

import static org.springframework.util.Assert.notNull;

import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * UpdateDAOのMyBatis3実装
 * 
 * @see jp.terasoluna.fw.dao.UpdateDAO
 */
public class UpdateDAOMyBatis3Impl extends SqlSessionDaoSupport implements UpdateDAO {

    /**
     * ロガー
     */
    static Log log = LogFactory.getLog(UpdateDAOMyBatis3Impl.class);

    /**
     * バッチ更新用のSQLSessionTemplate(必須)
     */
    SqlSessionTemplate batchSqlSessionTemplate;

    /**
     * バッチ更新用のトランザクションマネージャ(必須)
     */
    private PlatformTransactionManager transactionManager;

    /**
     * {@inheritDoc}
     */
    public int execute(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("execute Start.");
        }

        int row = getSqlSession().update(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("execute End. success count:" + row);
        }
        return row;
    }

    /**
     * {@inheritDoc}
     */
    public int executeBatch(final List<SqlHolder> sqlHolders) {

        TransactionStatus transactionStatus = null;

        try {
            transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute(
                    Propagation.REQUIRES_NEW.value()));

            int result = executeBatchInternal(sqlHolders);

            transactionManager.commit(transactionStatus);

            return result;

        } catch (DataAccessException e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }

    }

    /**
     * バッチ更新を実行する
     * 
     * @param sqlHolders
     *            バッチ更新対象のSQLリスト
     * @return バッチ更新件数(JDBCドライバ依存)
     */
    private int executeBatchInternal(final List<SqlHolder> sqlHolders) {
        // SQLを送信
        StringBuilder logStr = new StringBuilder();
        if (log.isDebugEnabled()) {
            log.debug("Batch SQL count:" + sqlHolders.size());
        }
        for (SqlHolder sqlHolder : sqlHolders) {
            batchSqlSessionTemplate.update(sqlHolder.getSqlID(), sqlHolder.getBindParams());
            if (log.isDebugEnabled()) {
                logStr.setLength(0);
                logStr.append("Call update sql. - SQL_ID:'");
                logStr.append(sqlHolder.getSqlID());
                logStr.append("' Parameters:");
                logStr.append(sqlHolder.getBindParams());
                log.debug(logStr.toString());
            }
        }

        // バッチ更新を実行
        List<BatchResult> batchResultList = batchSqlSessionTemplate.flushStatements();

        // 処理件数カウント
        int result = getUpdateCount(batchResultList);

        if (log.isDebugEnabled()) {
            log.debug("ExecuteBatch complete. Result:" + result);
        }

        return result;

    }

    private int getUpdateCount(List<BatchResult> batchResultList) {
        int result = 0;
        for (BatchResult batchResult : batchResultList) {
            int[] updateCounts = batchResult.getUpdateCounts();
            for (int updateCount : updateCounts) {
                result += updateCount;
            }
        }
        return result;
    }

    /**
     * バッチ更新を別トランザクションで実行する
     * 
     * @param transactionManager
     *            トランザクションマネージャ
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 今バージョンではサポートしない
     */
    @Deprecated
    public void addBatch(String arg0, Object arg1) {
        throw new UnsupportedOperationException();
    }

    /**
     * 今バージョンではサポートしない
     */
    @Deprecated
    public int executeBatch() {
        throw new UnsupportedOperationException();
    }

    /**
     * バッチ更新用のSqlSessionTemplateを設定する(必須)
     * 
     * @param batchSqlSessionTemplate
     *            バッチ更新モードのSqlSessionTemplate
     */
    public void setBatchSqlSessionTemplate(SqlSessionTemplate batchSqlSessionTemplate) {
        this.batchSqlSessionTemplate = batchSqlSessionTemplate;
    }

    /**
     * バッチ更新用のSqlSessionを生成できるか確認する。
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        notNull(this.batchSqlSessionTemplate, "Property 'sqlSessionFactory' or 'batchSqlSessionTemplate' are required");
    }
    /**
     * SqlSessionFactory設定時にバッチ更新用のSqlSessionを生成する。
     * 
     * @param sqlSessionFactory
     *            SqlSessionDaoSupportに必要とされるSqlSessionFactory
     */
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        // SqlSessionTemplateのクローズはSpringが管理する
        this.batchSqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        super.setSqlSessionFactory(sqlSessionFactory);
    }

}
