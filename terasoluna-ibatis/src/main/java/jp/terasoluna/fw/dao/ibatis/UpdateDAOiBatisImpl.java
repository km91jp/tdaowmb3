/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.dao.ibatis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.fw.dao.UpdateDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * UpdateDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p/>
 * ���̃N���X�́ABean��`�t�@�C����Bean��`���s���T�[�r�X�w��
 * �C���W�F�N�V�������Ďg�p����B�ȉ��ɐݒ�Ⴈ��ю�����������B<br/>
 * <p/>
 * <b>���ӓ_</b><br/>
 * executeBatch��iBATIS�̃o�b�`���s�@�\���g�p���Ă���B
 * executeBatch�̖߂�l�́A���s����SQL�ɂ��ύX�s�����ԋp���邪�A
 * java.sql.PreparedStatement���g�p���Ă��邽�߁A
 * �h���C�o�ɂ�萳�m�ȍs�����擾�ł��Ȃ��P�[�X������B<br/>
 * �ύX�s�������m�Ɏ擾�ł��Ȃ��h���C�o���g�p����ꍇ�A
 * �ύX�s�����g�����U�N�V�����ɉe����^����Ɩ��ł�
 * (�ύX�s����0���̏ꍇ�G���[����������P�[�X��)�A
 * �o�b�`�X�V�͎g�p���Ȃ����ƁB<br/>
 * �Q�l�����j<br/>
 * <a href="http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf#page=450"
 * target="_blank">
 * http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf</a>
 * <br/>
 * 450�y�[�W�u�W���o�b�`������Oracle �����̍X�V�����v���Q�Ƃ̂��ƁB<br/>
 * 
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Bean��`�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;bean id="registBLogic"
 * class="jp.strutspring.blogic.RegistBLogic"&gt;<br/>
 * &nbsp;&nbsp;&lt;property
 * name="dao"&gt;&lt;ref local="<b>updateDAO</b>"/&gt;
 * &lt;/property&gt;<br/>
 * &lt;/bean&gt;<br/>
 * <br/>
 * &lt;bean id="<b>updateDAO</b>"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * class="<b>jp.terasoluna.
 * fw.dao.ibatis.UpdateDAOiBATISImpl</b>"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="sqlMapClient"&gt;
 * &lt;ref local="sqlMapClient"/&gt;&lt;/property&gt;<br/>
 * &lt;/bean&gt;<br/>
 * <br/>
 * &lt;bean id="sqlMapClient"<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * class="org.springframework.orm.ibatis.SqlMapClientFactoryBean"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="configLocation"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;value&gt;sqlMapConfig.xml&lt;/value&gt;<br/>
 * &nbsp;&nbsp;&lt;/property&gt;<br/>
 * &lt;/bean&gt;
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * �T�[�r�X�w�ł̎g�p���@�F�f�[�^�ꌏ�̍X�V����
 * </legend>
 * <p/>
 * <code>
 * public class RegistBLogic {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;private UpdateDAO dao = null;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public UpdateDAO getDao() {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public void setDao(UpdateDAO dao) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this.dao = dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public String execute() {<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * UserBean bean = new UserBean();<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bean.setId("1");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bean.setName("N.OUNO");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bean.setAge("20");<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <b>dao.execute("insertUser", bean);</b><br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return "success";<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * }
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * �T�[�r�X�w�ł̎g�p���@�F�I�����C���o�b�`����
 * </legend>
 * <p/>
 * <code>
 * public String execute() {<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;List&lt;SqlHolder&gt; sqlHolders = new ArrayList&lt;SqlHolder&gt;();<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;UserBean bean = new UserBean();<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setId("1");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setName("N.OUNO");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setAge("20");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;SqlHolder holder = new SqlHolder("insertUser", bean);<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;sqlHolders.add(holder);<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;UserBean bean2 = new UserBean();<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setId("2");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setName("K.FUJIMOTO");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;bean.setAge("21");<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;SqlHolder holder2 = new SqlHolder("insertUser", bean2);<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;sqlHolders.add(holder2);<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;<b>dao.executeBatch(sqlHolders);</b><br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;...<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;return "success";<br/>
 * }
 * </code>
 * </fieldset>
 * <p/>
 *
 */
public class UpdateDAOiBatisImpl extends SqlMapClientDaoSupport implements
        UpdateDAO {
    /**
     * ���O�C���X�^���X
     */
    static Log log
        = LogFactory.getLog(UpdateDAOiBatisImpl.class);

    /**
     * �o�b�`���s�p��SQL
     * @deprecated ���̕ϐ��͏����폜����܂�
     */
    @Deprecated
    protected final ThreadLocal<List<SqlHolder>> batchSqls 
        = new ThreadLocal<List<SqlHolder>>();
    
    /**
     * ����sqlID�Ŏw�肳�ꂽSQL�����s���āA���ʌ�����ԋp����B
     * ���s����SQL�́uinsert, update delete�v��3��ނƂ���B
     *
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s���ʌ�����ԋp
     */
    public int execute(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("execute Start.");
        }

        //SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�f�[�^�ǉ��B
        int row = sqlMapTemp.update(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("execute End. success count:" + row);
        }
        return row;
    }

    /**
     * �o�b�`�ǉ����\�b�h�B
     * ������SQL���X���b�h���[�J���ɕێ�����B
     * �����̃��N�G�X�g���܂�����SQL��ێ����邱�Ƃ͂ł��Ȃ��B
     * �ǉ���ɁA<code>UpdateDAO#executeBatch()</code>�ŁA�ꊇ���s���s���B
     * 
     * <b>���ӁF</b>���̃��\�b�h���g�p����ƁA�o�b�`�X�V�Ώۂ�SQL��
     * �N���A����Ȃ��\��������B{@link #executeBatch(List)}���g�p���邱�ƁB
     *
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @deprecated addBatch�̑����{@link #executeBatch(List)}
     * ���g�p���邱��
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public void addBatch(final String sqlID, final Object bindParams) {
        // �X���b�h���[�J���̎擾
        List<SqlHolder> sqlHolders = batchSqls.get();
        if (sqlHolders == null) {
            sqlHolders = new ArrayList<SqlHolder>();
            batchSqls.set(sqlHolders);
            if (log.isDebugEnabled()) {
                log.debug("Create new SqlHolder in ThreadLocal.");
            }
        }
        
        // �o�b�`�p��SQL���X���b�h���[�J���ɕێ�����    
        sqlHolders.add(new SqlHolder(sqlID, bindParams));
        if (log.isDebugEnabled()) {
            log.debug("Add batch sql.  SQL_ID='" + sqlID 
                    + "' Parameters:" + bindParams);
        }
    }

    /**
     * �o�b�`�����̎��s���\�b�h�B
     * <code>{@link #addBatch(String, Object)}</code>�Œǉ����ꂽSQL��
     * �ꊇ���s����B�o�b�`���s���SQL���N���A����B
     * <code>{@link #addBatch(String, Object)}</code>��SQL��ǉ����Ă��Ȃ��ꍇ�A
     * ���s����O����������B
     * 
     * <b>���ӁF</b>���̃��\�b�h���g�p����ƁA�o�b�`�X�V�Ώۂ�SQL��
     * �N���A����Ȃ��\��������B{@link #executeBatch(List)}���g�p���邱�ƁB
     *
     * @return SQL�̎��s����
     * @deprecated addBatch�̑����{@link #executeBatch(List)}
     * ���g�p���邱��
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public int executeBatch() {
        // �X���b�h���[�J������SQL�����o��
        final List<SqlHolder> sqlHolders = batchSqls.get();
        
        // SQL�o�b�`���s
        Integer result = 0;
        try {
            result = (Integer) getSqlMapClientTemplate().execute(
                    new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor)
                      throws SQLException {
                    StringBuilder logStr = new StringBuilder();
                    if (log.isDebugEnabled()) {
                        log.debug("Batch SQL count:" + sqlHolders.size());
                    }
                    executor.startBatch();
                    for (SqlHolder sqlHolder : sqlHolders) {
                        executor.update(
                            sqlHolder.getSqlID(), sqlHolder.getBindParams());
                        
                        if (log.isDebugEnabled()) {
                            logStr.setLength(0);
                            logStr.append("Call update sql. - SQL_ID:'");
                            logStr.append(sqlHolder.getSqlID());
                            logStr.append("' Parameters:");
                            logStr.append(sqlHolder.getBindParams());
                            log.debug(logStr.toString());
                        }
                    }
                    return executor.executeBatch();
                }
            });
        } finally {
            // �X���b�h���[�J������SQL���폜
            batchSqls.remove();
            if (log.isDebugEnabled()) {
                log.debug("Remove SqlHolder in ThreadLocal.");
            }
        }
        
        if (log.isDebugEnabled()) {
            log.debug("ExecuteBatch complete. Result:" + result);
        }
        return result.intValue();
    }

    /**
     * �o�b�`�����̎��s���\�b�h�B<br/>
     * 
     * ������{@link SqlHolder}�̃��X�g�Ŏw�肳�ꂽ���ׂĂ�SQL�����s����B<br/>
     * 
     * <br/>
     * <b>���ӓ_</b><br/>
     * executeBatch��iBATIS�̃o�b�`���s�@�\���g�p���Ă���B
     * executeBatch�̖߂�l�́A���s����SQL�ɂ��ύX�s�����ԋp���邪�A
     * java.sql.PreparedStatement���g�p���Ă��邽�߁A
     * �h���C�o�ɂ�萳�m�ȍs�����擾�ł��Ȃ��P�[�X������B<br/>
     * �ύX�s�������m�Ɏ擾�ł��Ȃ��h���C�o���g�p����ꍇ�A
     * �ύX�s�����g�����U�N�V�����ɉe����^����Ɩ��ł�
     * (�ύX�s����0���̏ꍇ�G���[����������P�[�X��)�A
     * �o�b�`�X�V�͎g�p���Ȃ����ƁB<br/>
     * �Q�l�����j<br/>
     * <a href="http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf#page=450"
     * target="_blank">
     * http://otndnld.oracle.co.jp/document/products/oracle10g/101/doc_v5/java.101/B13514-02.pdf</a>
     * <br/>
     * 450�y�[�W�u�W���o�b�`������Oracle �����̍X�V�����v���Q�Ƃ̂��ƁB<br/>
     * 
     * @param sqlHolders �o�b�`�X�V�Ώۂ�sqlId�A�p�����[�^���i�[����
     * SqlHolder�C���X�^���X�̃��X�g
     * @return SQL�̎��s���ʌ���
     */
    public int executeBatch(final List<SqlHolder> sqlHolders) {
        // SQL�o�b�`���s
        Integer result = 0;
        result = (Integer) getSqlMapClientTemplate().execute(
                new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor)
                  throws SQLException {
                StringBuilder logStr = new StringBuilder();
                if (log.isDebugEnabled()) {
                    log.debug("Batch SQL count:" + sqlHolders.size());
                }
                executor.startBatch();
                for (SqlHolder sqlHolder : sqlHolders) {
                    executor.update(
                        sqlHolder.getSqlID(), sqlHolder.getBindParams());
                    
                    if (log.isDebugEnabled()) {
                        logStr.setLength(0);
                        logStr.append("Call update sql. - SQL_ID:'");
                        logStr.append(sqlHolder.getSqlID());
                        logStr.append("' Parameters:");
                        logStr.append(sqlHolder.getBindParams());
                        log.debug(logStr.toString());
                    }
                }
                return executor.executeBatch();
            }
        });
        
        if (log.isDebugEnabled()) {
            log.debug("ExecuteBatch complete. Result:" + result);
        }
        return result.intValue();
    }
}
