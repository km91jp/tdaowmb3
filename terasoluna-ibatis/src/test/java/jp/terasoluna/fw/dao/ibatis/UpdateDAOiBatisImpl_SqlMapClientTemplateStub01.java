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

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * {@link UpdateDAOiBatisImpl}�̎����̂��߂Ɏg�p�����X�^�u�B
 *
 * {@link UpdateDAOiBatisImpl}����̌Ăяo���m�F�p�Ɏg�p�����B
 *
 */
public class UpdateDAOiBatisImpl_SqlMapClientTemplateStub01 extends SqlMapClientTemplate {

    /**
     * �R���X�g���N�^
     */
    public UpdateDAOiBatisImpl_SqlMapClientTemplateStub01() {

        //SqlMapClient�̐ݒ�
        SqlMapClient sqlMapClient = new UpdateDAOiBatisImpl_SqlMapClientStub01();
        setSqlMapClient(sqlMapClient);

        //�f�[�^�\�[�X�̐ݒ�
        DataSource dataSource = new UpdateDAOiBatisImpl_DataSourceStub01();
        setDataSource(dataSource);
    }

    /**
     * UpdateDAOiBatisImpl�e�X�g�pqueryForList���\�b�h
     */
    @Override
    public int update(String statementName, Object parameterObject) throws DataAccessException {
        called = true;
        this.statementName = statementName;
        this.parameterObject = parameterObject;
        return 1;
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
