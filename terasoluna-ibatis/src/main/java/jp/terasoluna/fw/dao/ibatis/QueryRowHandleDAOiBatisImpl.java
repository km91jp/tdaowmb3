/*
 * Copyright (c) 2008 NTT DATA Corporation
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

import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.dao.event.DataRowHandler;
import jp.terasoluna.fw.dao.ibatis.event.RowHandlerWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * <p>
 * QueryRowHandleDAO�C���^�t�F�[�X��iBATIS�����N���X�B<br>
 * �Q�ƌnSQL�̌��ʂ�1�s����������B
 * </p>
 * 
 * <p>
 * executeWithRowHandler���\�b�h�̈�����DataRowHandler�����N���X��n���Ďg�p����B<br>
 * executeWithRowHandler���\�b�h���̂́ASQL�̎��s���ʂ�Ԃ��Ȃ����Ƃɒ��ӂ���B<br>
 * SQL�̎��s���ʂ�1�s���Ƃ�DataRowHandler#handleRow()���Ă΂�A
 * ������1�s�̃f�[�^���i�[�����I�u�W�F�N�g���n�����B<br>
 * DataRowHandler#handleRow()�ɂ́A1�s���̃f�[�^��������������K�v������B<br>
 * </p>
 * 
 * <p>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>���ӎ���</legend>
 * iBATIS�}�b�s���O��`�t�@�C����&lt;statement&gt;�v�f�A&lt;select&gt;�v�f�A
 * &lt;procedure&gt;�v�f�ɂđ�ʃf�[�^��Ԃ��悤�ȃN�G�����L�q����ꍇ�ɂ́A
 * fetchSize�����ɓK�؂Ȓl��ݒ肵�Ă������ƁB<br>
 * fetchSize�����ɂ�JDBC�h���C�o�ƃf�[�^�x�[�X�Ԃ̒ʐM�ɂ����āA
 * ��x�̒ʐM�Ŏ擾����f�[�^�̌�����ݒ肷��B<br>
 * fetchSize�������ȗ������ꍇ�͊eJDBC�h���C�o�̃f�t�H���g�l�����p�����B
 * </fieldset>
 * </p>
 * 
 * @see jp.terasoluna.fw.dao.event.DataRowHandler
 * @see jp.terasoluna.fw.dao.QueryRowHandleDAO
 */
public class QueryRowHandleDAOiBatisImpl extends SqlMapClientDaoSupport
        implements QueryRowHandleDAO {

    /**
     * ���O�C���X�^���X
     */
    private static Log log = LogFactory.
            getLog(QueryRowHandleDAOiBatisImpl.class);

    /**
     * SQL�̎��s���ʂ�DataRowHandler��1�s����������B
     *
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param rowHandler 1�s�擾���Ƃɏ�������n���h��
     */
    public void executeWithRowHandler(final String sqlID,
            final Object bindParams, final DataRowHandler rowHandler) {
        if (log.isDebugEnabled()) {
            log.debug("executeWithRowHandler Start.");
        }

        // SqlMapClientTemplate�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s
        sqlMapTemp.queryWithRowHandler(sqlID, bindParams,
                new RowHandlerWrapper(rowHandler));

        if (log.isDebugEnabled()) {
            log.debug("executeWithRowHandler End.");
        }
    }

}
