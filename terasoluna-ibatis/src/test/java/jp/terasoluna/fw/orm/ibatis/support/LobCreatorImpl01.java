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

package jp.terasoluna.fw.orm.ibatis.support;

import java.io.InputStream;
import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.support.lob.LobCreator;

/**
 * LobCreator�����N���X�B
 * 
 */
public class LobCreatorImpl01 implements LobCreator {

    /**
     * setBlobAsBinaryStream�̌Ăяo���m�F�t���O
     */
    public boolean isSetBlobAsBinaryStream = false;

    /**
     * setClobAsCharacterStream�̌Ăяo���m�F�t���O
     */
    public boolean isSetClobAsCharacterStream = false;
    
    /**
     * BlobInputStreamTypeHandler#setParameterInternal�e�X�g
     * �Ăяo���m�F
     */
    public void setBlobAsBinaryStream(PreparedStatement arg0, int arg1,
            InputStream arg2, int arg3) throws SQLException {
        this.isSetBlobAsBinaryStream = true;
    }
    
    /**
     * ClobReaderTypeHandler#setParameterInternal�e�X�g
     * �Ăяo���m�F
     */
    public void setClobAsCharacterStream(PreparedStatement arg0, int arg1,
            Reader arg2, int arg3) throws SQLException {
        this.isSetClobAsCharacterStream = true;
    }
    
    public void setBlobAsBytes(PreparedStatement arg0, int arg1, byte[] arg2)
            throws SQLException {
    }

    public void setClobAsString(PreparedStatement arg0, int arg1, String arg2)
            throws SQLException {
    }

    public void setClobAsAsciiStream(PreparedStatement arg0, int arg1,
            InputStream arg2, int arg3) throws SQLException {
    }



    public void close() {
    }

}
