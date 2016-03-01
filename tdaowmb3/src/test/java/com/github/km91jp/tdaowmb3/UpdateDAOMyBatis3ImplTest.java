package com.github.km91jp.tdaowmb3;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.github.km91jp.tdaowmb3.dto.Tdaowmb3test;

@ContextConfiguration(locations = "classpath:beansDef/tdaowmb3.xml")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class UpdateDAOMyBatis3ImplTest {

    @Inject
    UpdateDAO updateDAO;

    @Inject
    DataSource dataSource;

    @Inject
    PlatformTransactionManager txManager;

    /**
     * QueryDAOを使用せずに確認用データを取得するためのSQL
     */
    private static final String CONFIRM_SQL = "select id, body from tdaowmb3_test where id = ?";

    /**
     * UpdateDAOを使用せずにデータを削除するためのSQL
     */
    private static final String DELETE_SQL = "delete from tdaowmb3_test where id = ?";

    /**
     * 確認用データを取得できなかった場合はnullとする
     */
    private static final Tdaowmb3test NOT_FOUND = null;

    /**
     * 確認用データをJdbcTemplateの実行結果(ResultSet)から作成するためのクラス
     */
    ResultSetExtractor<Tdaowmb3test> extractor = new ResultSetExtractor<Tdaowmb3test>() {

        public Tdaowmb3test extractData(ResultSet arg0) throws SQLException, DataAccessException {
            if (!arg0.next()) {
                return NOT_FOUND;
            }
            Tdaowmb3test dto = new Tdaowmb3test();
            dto.setId(arg0.getInt(1));
            dto.setBody(arg0.getString(2));
            return dto;
        }

    };

    /**
     * QueryDAOはテスト対象なのでJdbcTemplateを使用して確認用データを取得する。
     * 
     * @param confirmDataId
     *            対象ID
     * @return 確認用データ
     */
    private Tdaowmb3test selectDataToConfirmById(int confirmDataId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query(CONFIRM_SQL, new Object[]{confirmDataId}, extractor);
    }

    /**
     * UpdateDAOはテスト対象なのでJdbcTemplateを使用してデータを削除する。
     * 
     * @param confirmDataId
     *            対象ID
     * @return 確認用データ
     */
    private int deleteDataToConfirmById(int deleteDataId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(DELETE_SQL, new Object[]{deleteDataId});
    }

    @Test
    public void executeで挿入できること() {
        Tdaowmb3test insertParam = new Tdaowmb3test();
        insertParam.setId(6);
        insertParam.setBody("sixth");
        int insertCount = updateDAO.execute("UpdateDAOMyBatis3ImplTest.insert", insertParam);
        assertEquals(1, insertCount);
        Tdaowmb3test actualObj = selectDataToConfirmById(6);
        assertEquals(6, actualObj.getId());
        assertEquals("sixth", actualObj.getBody());
    }

    @Test
    public void executeで更新できること() {
        Tdaowmb3test updateParam = new Tdaowmb3test();
        updateParam.setId(1);
        updateParam.setBody("1st");
        int updateCount = updateDAO.execute("UpdateDAOMyBatis3ImplTest.update", updateParam);
        assertEquals(1, updateCount);
        Tdaowmb3test actualObj = selectDataToConfirmById(1);
        assertEquals(1, actualObj.getId());
        assertEquals("1st", actualObj.getBody());
    }

    @Test
    public void executeで削除できること() {
        Tdaowmb3test deleteParam = new Tdaowmb3test();
        deleteParam.setId(3);
        int deleteCount = updateDAO.execute("UpdateDAOMyBatis3ImplTest.delete", deleteParam);
        assertEquals(1, deleteCount);
        Tdaowmb3test actualObj = selectDataToConfirmById(3);
        assertEquals(NOT_FOUND, actualObj);
    }

    @Test
    public void executeBatchでバッチ更新できること() {
        try {
            List<SqlHolder> sqlHolderList = new ArrayList<SqlHolder>();
            Tdaowmb3test insertParam1 = new Tdaowmb3test();
            insertParam1.setId(6);
            insertParam1.setBody("sixth");
            sqlHolderList.add(new SqlHolder("UpdateDAOMyBatis3ImplTest.insert", insertParam1));

            Tdaowmb3test insertParam2 = new Tdaowmb3test();
            insertParam2.setId(7);
            insertParam2.setBody("seventh");
            sqlHolderList.add(new SqlHolder("UpdateDAOMyBatis3ImplTest.insert", insertParam2));

            // 件数はJDBCドライバ依存のため確認しない
            updateDAO.executeBatch(sqlHolderList);

            Tdaowmb3test actualObj;
            actualObj = selectDataToConfirmById(6);
            assertEquals(6, actualObj.getId());
            assertEquals("sixth", actualObj.getBody());
            actualObj = selectDataToConfirmById(7);
            assertEquals(7, actualObj.getId());
            assertEquals("seventh", actualObj.getBody());
        } finally {
            // REQUURED_NEWでトランザションを実行したため、
            // 手動でクリーンアップする
            cleanupDatabaseManually();
        }
    }

    /**
     * データベースを手動でクリーンアップする。<br>
     * finally句で呼ばれることを想定しているので、例外をスローさせない。
     */
    private void cleanupDatabaseManually() {
        try {
            TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition(
                    Propagation.REQUIRES_NEW.ordinal()));
            deleteDataToConfirmById(6);
            deleteDataToConfirmById(7);
            txManager.commit(status);
        } catch (Exception e) {
            // finally句からは例外をスローしない
            e.printStackTrace();
        }
    }
}
