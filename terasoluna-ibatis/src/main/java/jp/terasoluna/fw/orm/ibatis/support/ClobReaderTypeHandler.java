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

import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.ibatis.support.AbstractLobTypeHandler;

/**
 * iBATIS���痘�p�����CLOB�ƕ����X�g���[�����}�b�s���O���������iBATIS�̃^�C�v�n���h���B
 *
 * <p>
 * Oracle��CLOB�^�𕶎��X�g���[���ň����ꍇ�͖{API���g�p����B�i���{�N���X��PostgreSQL�ł͗��p�ł��Ȃ��j<br>
 * CLOB����o�C�g�z��Ƃ��Ĉ����ꍇ�A�{�N���X�𗘗p����K�v�͂Ȃ��B<br>
 * �o�C�g�z��Ƃ��Ĉ����ƁA
 * CLOB��ň����f�[�^������Ń������G���[������������A
 * ���\�v�����������Ȃ��ꍇ�̂݁A�{�N���X�𗘗p���邱�ƁB
 * </p>
 *
 * <p>
 * �{�N���X�𗘗p���邽�߂ɂ�OracleLobHandler��Bean��`��sqlMapClientFactoryBean�ւ̐ݒ���s�����ƁB<br>
 * �܂��AOracleLobHandler�ɂ�NativeJdbcExtractor�����N���X��ݒ肷�邱�ƁB<br>
 * �Ȃ��ASpring��OracleLobHandler�Ƃ�������NativeJdbcExtractor�����N���X��񋟂��Ă���B<br>
 * </p>
 * 
 * <p>
 * OracleLobHandler�́A
 * Oracle��JDBC�h���C�o�̃R�l�N�V����(oracle.jdbc.OracleConnection�C���^�t�F�[�X�����N���X)��API���g�p���āA
 * Lob�̃n���h�����O���s���B<br>
 * ���̂��߂ɁA�R�l�N�V�����v�[������擾�����R�l�N�V����
 * (close����ƃR�l�N�V�������v�[���ɕԂ邵���݂���������Ă���A�R�l�N�V�����̃��b�p)����A
 * JDBC�h���C�o�̃R�l�N�V�������擾����������ʂ����̂��ANativeJdbcExtractor(Spring�̃C���^�t�F�[�X)�ł���B<br>
 * JDBC�h���C�o�̃R�l�N�V�����̎擾���@��
 * �g�p���Ă���R�l�N�V�����v�[���̎����ɂ���ĈقȂ邽�߁ASpring���l�X��NativeJdbcExtractor�����N���X��񋟂��Ă���B
 * </p>
 * 
 * <p>
 * Spring���񋟂��Ă�������N���X�̈��SimpleNativeJdbcExtractor������B<br>
 * SimpleNativeJdbcExtractor�́A�ȒP���A����̃R�l�N�V�����v�[�����ӎ����Ȃ�����������Ă���B<br>
 * �������ASimpleNativeJdbcExtractor�𗘗p���Ă��A
 * commons-dbcp-1.3�ȍ~�ȂǁA�R�l�N�V�����v�[���̎����ɂ���ẮAJDBC�R�l�N�V�������擾�ł��Ȃ��ꍇ������B<br>
 * ���̏ꍇ�ASpring���񋟂��Ă��鑼��NativeJdbcExtractor�����N���X���g�p���邩�A
 * �eAP�T�[�o�p�ɐV����NativeJdbcExtractor�����N���X���쐬����K�v������B<br>
 * </p>
 * 
 * <p>
 * �Ⴆ�΁A
 * commons-dbcp-1.3�ȍ~�A���邢�͂�����g�p���Ă���o�[�W������Tomcat�̏ꍇ�́ACommonsDbcpNativeJdbcExtractor���g�p����B
 * AP�T�[�o��WebLogic�̏ꍇ�́AWebLogicNativeJdbcExtractor���g�p����B
 * ��L��NativeJdbcExtractor�����N���X�́A�ǂ����Spring���񋟂��Ă���B
 * </p>
 *
 * <p>
 *  �y<code>Bean��`�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 *   &lt;!-- LOB�t�B�[���h���������߂̃n���h�� --&gt;
 *   &lt;bean id="oracleLobHandler"
 *            class="org.springframework.jdbc.support.lob.OracleLobHandler"&gt;
 *     &lt;property name="nativeJdbcExtractor" ref="simpleExtractor"/&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;!-- iBATIS �f�[�^�x�[�X�w�̂��߂�SQlMap�̐ݒ� --&gt;
 *   &lt;bean id="sqlMapClient"
 *       class="org.springframework.orm.ibatis.SqlMapClientFactoryBean"&gt;
 *     &lt;property name="configLocation" value="WEB-INF/sql-map-config.xml"/&gt;
 *     &lt;property name="dataSource" ref="dataSource"/&gt;
 *     &lt;property name="lobHandler" ref="oracleLobHandler"/&gt;
 *   &lt;/bean&gt;
 *   
 *  &lt;!-- simpleExtractor��Bean��`�ݒ� --&gt;
 *  &lt;!-- �� OC4J��JNDI����f�[�^�\�[�X���擾����ꍇ�́A�v���p�e�B�͂��ׂ�true�ɂ��Ă������ƁB --&gt;
 *  &lt;bean id="simpleExtractor"
 *        class="org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor"&gt;
 *    &lt;property name="nativeConnectionNecessaryForNativeStatements" value="true"/&gt;
 *    &lt;property name="nativeConnectionNecessaryForNativePreparedStatements" value="true"/&gt;
 *    &lt;property name="nativeConnectionNecessaryForNativeCallableStatements" value="true"/&gt;
 *  &lt;/bean&gt;
 * </pre></code>
 * </p>
 *
 * <p>
 * �{�N���X�𗘗p����iBATIS�ݒ�t�@�C���̋L�q���@���ȉ��Ɏ����B
 * </p>
 *
 * <p>
 *  �yCLOB_TEST�e�[�u����`�z<br>
 *   <table border="1" CELLPADDING="8">
 *     <th>��</th>
 *     <th>�^</th>
 *     <th>����</th>
 *
 *     <tr>
 *       <td align=center>PK</td>
 *       <td>INTEGER</td>
 *       <td>NOT NULL</td>
 *     </tr>
 *
 *     <tr>
 *       <td align=center>MAP</td>
 *       <td>CLOB</td>
 *       <td>NOT NULL</td>
 *     </tr>
 *  </table>
 * </p>
 *
 * <p>
 *  �y<code>iBATIS�ݒ�t�@�C��</code>�̐ݒ��z<br>
 * <code><pre>
 * &lt;!? update���̐ݒ� --&gt;
 * &lt;parameterMap id="clobParam" class="java.util.Map"&gt;
 *   &lt;parameter property="pk"/&gt;
 *   &lt;parameter property="map"
 *       typeHandler="jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler"/&gt;
 * &lt;/parameterMap&gt;
 *
 * &lt;insert id="insertCLobTest" parameterMap="clobParam"&gt;
 *   INSERT INTO CLOB_TEST (PK, MAP) VALUES (?, ?)
 * &lt;/insert&gt;
 *
 * &lt;!? select���̐ݒ� --&gt;
 * &lt;resultMap id="clobResult" class="java.util.HashMap"&gt;
 *   &lt;result property="pk"/&gt;
 *   &lt;result property="map"
 *       typeHandler="jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler"/&gt;
 * &lt;/resultMap&gt;
 *
 * &lt;select id="selectCLobTest" resultMap="clobResult"&gt;
 *   SELECT PK, MAP FROM CLOB_TEST
 * &lt;/select&gt;
 * </pre></code>
 * </p>
 *
 */
public class ClobReaderTypeHandler extends AbstractLobTypeHandler {

    /**
     * �R���X�g���N�^�B
     */
    public ClobReaderTypeHandler() {
        super();
    }

    /**
     * �R���X�g���N�^�B
     * @param lobHandler LobHandler
     */
    protected ClobReaderTypeHandler(LobHandler lobHandler) {
        super(lobHandler);
    }

    /**
     * �p�����[�^��ݒ肷��B
     *
     * @param ps �Z�b�g���PreparedStatement
     * @param index �p�����[�^�̃C���f�b�N�X
     * @param value �Z�b�g����p�����[�^
     * @param jdbcType �p�����[�^��JDBC�^
     * @param lobCreator ���p����LobCreator
     * @throws SQLException SQL��O
     */
    @Override
    protected void setParameterInternal(PreparedStatement ps,
                                        int index,
                                        Object value,
                                        String jdbcType,
                                        LobCreator lobCreator)
            throws SQLException {
        lobCreator.setClobAsCharacterStream(ps, index, (Reader) value, 0);
    }

    /**
     * ���ʂ��擾����B
     * @param rs �擾����ResultSet
     * @param index ResultSet�̃C���f�b�N�X
     * @param lobHandler ���p����LobHandler
     * @return �擾����
     * @throws SQLException SQL��O
     */
    @Override
    protected Object getResultInternal(ResultSet rs,
                                       int index,
                                       LobHandler lobHandler)
            throws SQLException {
        return lobHandler.getClobAsCharacterStream(rs, index);
    }

    /**
     * �������{Handler�������^�ɕϊ�����B
     * @param s ������
     * @return Handler�������^�̃C���X�^���X
     */
    public Object valueOf(String s) {
        if (s == null) {
            return null;
        }
        return new StringReader(s);
    }

}
