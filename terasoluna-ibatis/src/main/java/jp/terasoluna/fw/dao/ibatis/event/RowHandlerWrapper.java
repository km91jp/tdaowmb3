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

package jp.terasoluna.fw.dao.ibatis.event;

import jp.terasoluna.fw.dao.event.DataRowHandler;

import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * <p>
 * iBATIS��RowHandler�̃��b�p�[�N���X�B
 * </p>
 * 
 * <p>
 * TERASOLUNA��DataRowHandler��iBATIS��RowHandler�֕ϊ�����B<br>
 * 1�s�̃f�[�^������DataRowHandler�Ɏ������邱�ƁB
 * </p>
 *
 * @see jp.terasoluna.fw.dao.ibatis.QueryRowHandleDAOiBatisImpl
 */
public class RowHandlerWrapper implements RowHandler {

    /**
     * DataRowHandler
     */
    protected DataRowHandler dataRowHandler = null;

    /**
     * �R���X�g���N�^
     * 
     * @param dataRowHandler DataRowHandler
     */
    public RowHandlerWrapper(DataRowHandler dataRowHandler) {
        super();
        this.dataRowHandler = dataRowHandler;
    }

    /**
     * 1�s���ɌĂ΂�郁�\�b�h
     *
     * @param valueObject 1�s�̃f�[�^
     * @see com.ibatis.sqlmap.client.event.RowHandler#handleRow(java.lang.Object)
     */
    public void handleRow(Object valueObject) {
        this.dataRowHandler.handleRow(valueObject);
    }

}
