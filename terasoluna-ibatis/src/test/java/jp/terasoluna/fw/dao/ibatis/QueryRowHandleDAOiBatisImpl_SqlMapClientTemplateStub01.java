/**
 * 
 */
package jp.terasoluna.fw.dao.ibatis;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * {@link QueryRowHandleDAOiBatisImpl}�̎����̂��߂Ɏg�p�����X�^�u�B
 * 
 * {@link QueryRowHandleDAOiBatisImpl}����̌Ăяo���m�F�p�Ɏg�p�����B
 * 
 */
public class QueryRowHandleDAOiBatisImpl_SqlMapClientTemplateStub01 extends
        SqlMapClientTemplate {
    /**
     * �e�X�g�pqueryWithRowHandler���\�b�h
     */
    @Override
    public void queryWithRowHandler(String statementName,
            Object parameterObject, RowHandler rowHandler)
            throws DataAccessException {
        called = true;
        this.statementName = statementName;
        this.parameterObject = parameterObject;
        rowHandler.handleRow(parameterObject);
    }

    /*
     * �Ăяo���m�F�p�ϐ�
     */
    private boolean called = false;
    private String statementName = null;
    private Object parameterObject = null;
    public boolean isCalled() {
        return called;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    public String getStatementName() {
        return statementName;
    }

}
