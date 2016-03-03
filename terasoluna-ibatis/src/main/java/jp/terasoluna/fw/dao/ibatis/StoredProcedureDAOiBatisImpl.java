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

import jp.terasoluna.fw.dao.StoredProcedureDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * StoredProcedureDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 *<p/>
 * ���̃N���X�́ABean��`�t�@�C����Bean��`���s���T�[�r�X�w��
 * �C���W�F�N�V�������Ďg�p����B�ȉ��ɐݒ�Ⴈ��ю�����������B<br/>
 *
 * <br/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * Bean��`�t�@�C���̗�
 * </legend>
 * <p/>
 * <code>
 * &lt;bean id="listBLogic"
 * class="jp.strutspring.blogic.ListBLogic"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="dao"&gt;&lt;ref
 * local="<b>spDAO</b>"/&gt;&lt;/property&gt;<br/>
 * &lt;/bean&gt;<br/>
 * <br/>
 * &lt;bean id="<b>spDAO</b>"<br/>
 * &nbsp;&nbsp;class="<b>jp.terasoluna.
 * fw.dao.ibatis.StoredProcedureDAOiBatisImpl</b>"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="sqlMapClient"&gt;&lt;ref
 * local="sqlMapClient"/&gt;&lt;/property&gt;<br/>
 * &lt;/bean&gt;<br/>
 * <br/>
 * &lt;bean id="sqlMapClient"<br/>
 * &nbsp;&nbsp;&nbsp;
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
 * �T�[�r�X�w�ł̎g�p���@
 * </legend>
 * <p/>
 * <code>
 * public class UserGetBLogic{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;private StoredProcedureDAO dao = null;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public StoredProcedureDAO getDao() {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public void setDao(StoredProcedureDAO dao) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;this.dao = dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public String execute(ActionForm form) {<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * UserBean<String, String> userBean = new UserBean<String, String>();<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * userBean.setInputId("1");<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * <b>dao.executeForObject("getUserName", userBean);</b><br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * String userName = userBean.getName();<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return "success";<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * }
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend>
 * iBATIS�̐ݒ��
 * </legend>
 * <p/>
 * <code>
 * &lt;parameterMap id="UserBean"
 * class="jp.strutspring.blogic.UserBean"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&lt;parameter property="inputId"
 * javaType="java.lang.String" mode="IN"/&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&lt;parameter property="name"
 * javaType="java.lang.String" mode="OUT"/&gt;<br/>
 * &lt;/parameterMap&gt;<br/>
 * &lt;procedure id="getUserName" parameterMap="UserBean"&gt;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;{call TESTPROCEDURE(?,?)}<br/>
 * &lt;/procedure&gt;
 * </code>
 * </fieldset>
 * <p/>
 */
public class StoredProcedureDAOiBatisImpl extends SqlMapClientDaoSupport
        implements StoredProcedureDAO {

    /**
     * ���O�C���X�^���X
     */
    private static Log log = LogFactory
            .getLog(StoredProcedureDAOiBatisImpl.class);

    /**
     * �w�肳�ꂽSQLID�̃X�g�A�h�v���V�[�W���[�����s����B
     * �X�g�A�h�v���V�[�W���[�̌��ʂł���A�E�g�p�����[�^�́A
     * ������bindParams�ɔ��f�����B
     *
     * @param sqlID ���s����SQL��ID
     * @param bindParams SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     */
    public void executeForObject(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }

        //SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        //SQL�̎��s�F�l�̎擾
        sqlMapTemp.queryForObject(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
        }

    }

}
