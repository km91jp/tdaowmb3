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

import java.sql.Connection;

import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

/**
 * {@link StoredProcedureDAOiBatisImpl}�̎����̂��߂Ɏg�p�����X�^�u�B
 * 
 * {@link StoredProcedureDAOiBatisImpl}����̌Ăяo���m�F�p�Ɏg�p�����B
 * 
 */
public class StoredProcedureDAOiBatisImpl_SqlMapClientStub01 extends SqlMapClientImpl {

    /*
     * �Ăяo���m�F�p�ϐ�
     */
    private boolean called = false;
    
    /**
     * �e�X�g�p�R���X�g���N�^
     *
     */
    public StoredProcedureDAOiBatisImpl_SqlMapClientStub01() {
        this(new SqlMapExecutorDelegate());
        called = false;
    }

    public StoredProcedureDAOiBatisImpl_SqlMapClientStub01(SqlMapExecutorDelegate delegate) {
        super(delegate);
    }

    /**
     * �e�X�g�p���\�b�h
     */
    @Override
    public SqlMapSession openSession(Connection conn) {
        called = true;
        return null;
    }

    

    public boolean isCalled() {
        return called;
    }

}
