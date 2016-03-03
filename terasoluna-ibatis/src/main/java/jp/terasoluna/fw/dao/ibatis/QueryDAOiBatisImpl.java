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

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.dao.IllegalClassTypeException;
import jp.terasoluna.fw.dao.QueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * QueryDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p/>
 * ���̃N���X�́ABean��`�t�@�C����Bean��`���s���T�[�r�X�w��
 * �C���W�F�N�V�������Ďg�p����B�ȉ��ɐݒ�Ⴈ��ю�����������B<br/>
 * 
 * <br/> <fieldset style="border:1pt solid black;padding:10px;width:100%;">
 * <legend> Bean��`�t�@�C���̗� </legend> <p/> <code>
 * &lt;bean id="listBLogic"
 * class="jp.strutspring.blogic.ListBLogic"&gt;<br/>
 * &nbsp;&nbsp;&lt;property name="dao"&gt;&lt;ref
 * local="<b>queryDAO</b>"/&gt;&lt;/property&gt;<br/>
 * &lt;/bean&gt;<br/>
 * <br/>
 * &lt;bean id="<b>queryDAO</b>"<br/>
 * &nbsp;&nbsp;class="<b>jp.terasoluna.
 * fw.dao.ibatis.QueryDAOiBatisImpl</b>"&gt;<br/>
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
 * </fieldset> <p/> <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;">
 * <legend> �T�[�r�X�w�ł̎g�p���@�F�擾�f�[�^��1���̏ꍇ </legend>
 * <p/> <code>
 * public&nbsp;class&nbsp;ListBLogic{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;private&nbsp;QueryDAO&nbsp;dao&nbsp;=&nbsp;null;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;QueryDAO&nbsp;getDao()&nbsp;{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * &nbsp;&nbsp;&nbsp;
 * public&nbsp;void&nbsp;setDao(QueryDAO&nbsp;dao)&nbsp;{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * this.dao&nbsp;=&nbsp;dao;<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * <br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;String
 * execute(ActionForm&nbsp;form)&nbsp;{<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;UserBean&nbsp;bean<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
 * <b>dao.executeForObject("getUser","10000000",UserBean.class);</b><br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;...<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;"success";<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br/>
 * }
 * </code>
 * </fieldset> <p/> 
 * <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;">
 * <legend> �T�[�r�X�w�ł̎g�p���@�F�擾�f�[�^���������̏ꍇ(�S���擾)List��</legend>
 * <p/>
 * <code>
 * public&nbsp;String&nbsp;execute(ActionForm&nbsp;form)&nbsp;{</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;List<UserBean>&nbsp;bean</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
 * <b>dao.executeForObjectList("getUser","10000000");</b></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;"success";</br>
 * }
 * </code>
 * </fieldset>
 * <p/>
 * <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;">
 * <legend> �T�[�r�X�w�ł̎g�p���@�F�擾�f�[�^���������̏ꍇ(�S���擾)�z���</legend>
 * <p/> 
 * <code>
 * public&nbsp;String&nbsp;execute(ActionForm&nbsp;form)&nbsp;{</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;UserBean[]&nbsp;bean</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
 * <b>dao.executeForObjectArray("getUser","10000000",UserBean.class);</b></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;"success";</br>
 * }
 * </code>
 * </fieldset> <p/> <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;">
 * <legend> �T�[�r�X�w�ł̎g�p���@�F�擾�f�[�^���������̏ꍇ
 * (�w��C���f�b�N�X����̎w�茏�������擾)List��
 * </legend>
 * <br>
 * &nbsp;&nbsp;�����Ɏ擾�J�n�C���f�b�N�X����ь����̎w����s���B
 * �ȉ��̗�ł́A21���ڂ���10�����擾����B <p/> 
 * <code>
 * public&nbsp;String&nbsp;execute(ActionForm&nbsp;form)&nbsp;{</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;List<UserBean>&nbsp;bean</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
 * <b>dao.executeForObjectList("getUser","10000000",
 * 20,&nbsp;10);</b></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;"success";</br>
 * }
 * </code>
 * </fieldset> <p/> <fieldset style="border:1pt solid
 * black;padding:10px;width:100%;">
 * <legend> �T�[�r�X�w�ł̎g�p���@�F�擾�f�[�^���������̏ꍇ
 * (�w��C���f�b�N�X����̎w�茏�������擾)�z���
 * </legend>
 * <br>
 * &nbsp;&nbsp;�����Ɏ擾�J�n�C���f�b�N�X����ь����̎w����s���B
 * �ȉ��̗�ł́A21���ڂ���10�����擾����B <p/>
 * <code>
 * public&nbsp;String&nbsp;execute(ActionForm&nbsp;form)&nbsp;{</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;UserBean[]&nbsp;bean</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;=
 * <b>dao.executeForObjectArray("getUser","10000000",UserBean.class,
 * 20,&nbsp;10);</b></br>
 * &nbsp;&nbsp;&nbsp;&nbsp;...</br>
 * &nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;"success";</br>
 * }
 * </code>
 * </fieldset> <p/>
 * 
 */
public class QueryDAOiBatisImpl extends SqlMapClientDaoSupport implements
        QueryDAO {

    /**
     * ���O�C���X�^���X
     */
    private static Log log = LogFactory.getLog(QueryDAOiBatisImpl.class);

    /**
     * �Q�ƌnSQL�����s���A���ʂ��w�肳�ꂽ�I�u�W�F�N�g�Ƃ��ĕԋp����B
     * SQL�̌��ʃI�u�W�F�N�g�ƁA�w�肳�ꂽ�^��������ꍇ�́A��O�𔭐�������B
     * 
     * @param <E>
     *            �ԋp�l�̌^
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz
     *            �ԋp�l�̃N���X
     * @return SQL�̎��s����
     */
    @SuppressWarnings("unchecked")
    public <E> E executeForObject(String sqlID,
                                   Object bindParams,
                                   Class clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }

        // SqlMapClientTemplate�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�l�̎擾
        Object obj = sqlMapTemp.queryForObject(sqlID, bindParams);
        if (log.isDebugEnabled() && obj != null) {
            log.debug("Return type:" + obj.getClass().getName());
        }

        E rObj = null;
        try {
            if (clazz != null && obj != null) {
                rObj = (E) clazz.cast(obj);
            }
        } catch (ClassCastException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
        }

        return rObj;
    }

    /**
     * �Q�ƌnSQL�����s���AMap�Ƃ��ĕԋp����B
     * 
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> executeForMap(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMap Start.");
        }

        Map<String, Object> rObj = this.executeForObject(sqlID, bindParams,
                Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMap End.");
        }

        return rObj;
    }

    /**
     * �Q�ƌnSQL�����s���A���ʂ��w�肳�ꂽ�I�u�W�F�N�g�̔z��Ƃ��ĕԋp����B
     * SQL�̌��ʃI�u�W�F�N�g�ƁA�w�肳�ꂽ�^��������ꍇ�́A��O�𔭐�������B
     * ����0�����͋�z�񂪕ԋp�����B
     * @param <E>
     *            �ԋp�l�̌^
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz
     *            �ԋp�l�̃N���X
     * @return SQL�̎��s����
     */
    @SuppressWarnings("unchecked")
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException();
        }

        // SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�l�̎擾
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams);

        // �z��ɕϊ�
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * �Q�ƌnSQL�����s���AMap�̔z��Ƃ��ĕԋp����B
     * Map�z��̕ϊ��Ɏ��s�����ꍇ�́A��O�𔭐�������B
     * ����0�����͋�z�񂪕ԋp�����B
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams,
                Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * �Q�ƌnSQL�����s���A���ʂ��w�肳�ꂽ�I�u�W�F�N�g�̔z��Ƃ��ĕԋp����B
     * �ԋp����錋�ʂ́A�����ɂĎw�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�����ł���B
     * SQL�̌��ʃI�u�W�F�N�g�ƁA�w�肳�ꂽ�^��������ꍇ�́A��O�𔭐�������B
     * ����0�����͋�z�񂪕ԋp�����B
     * @param <E>
     *            �ԋp�l�̌^
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param clazz
     *            �ԋp�l�̃N���X
     * @param beginIndex
     *            �擾����J�n�C���f�b�N�X
     * @param maxCount
     *            �擾���錏��
     * @return SQL�̎��s����
     */
    @SuppressWarnings("unchecked")
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams,
            Class clazz, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException();
        }

        // SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�l�̎擾
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams, beginIndex,
                maxCount);

        // �z��ɕϊ�
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * �Q�ƌnSQL�����s���AMap�̔z��Ƃ��ĕԋp����B
     * �ԋp����錋�ʂ́A�����ɂĎw�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�����ł���B
     * Map�z��̕ϊ��Ɏ��s�����ꍇ�́A��O�𔭐�������B
     * ����0�����͋�z�񂪕ԋp�����B
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex
     *            �擾����J�n�C���f�b�N�X
     * @param maxCount
     *            �擾���錏��
     * @return SQL�̎��s����
     */
    public Map<String, Object>[] executeForMapArray(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams,
                Map.class, beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * �Q�ƌnSQL�����s���A���ʂ��w�肳�ꂽ�I�u�W�F�N�g��List�Ƃ��ĕԋp����B
     * ����0�����͋󃊃X�g���ԋp�����B
     * @param <E>
     *            �ԋp�l�̌^
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s���ʃ��X�g
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        // SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�l�̎擾
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams);
        
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }
    
    /**
     * �Q�ƌnSQL�����s���AMap��List�Ƃ��ĕԋp����B
     * ����0�����͋󃊃X�g���ԋp�����B
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @return SQL�̎��s����
     */
    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }    

    /**
     * �Q�ƌnSQL�����s���A���ʂ��w�肳�ꂽ�I�u�W�F�N�g��List�Ƃ��ĕԋp����B
     * �ԋp����錋�ʂ́A�����ɂĎw�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�����ł���B
     * ����0�����͋󃊃X�g���ԋp�����B
     * @param <E>
     *            �ԋp�l�̌^
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex
     *            �擾����J�n�C���f�b�N�X
     * @param maxCount
     *            �擾���錏��
     * @return SQL�̎��s����
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams,
            int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        // SqlMapClient�̎擾
        SqlMapClientTemplate sqlMapTemp = getSqlMapClientTemplate();

        // SQL�̎��s�F�l�̎擾
        List<E> list = sqlMapTemp.queryForList(sqlID, bindParams, beginIndex,
                maxCount);
        
        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }    
    
    /**
     * �Q�ƌnSQL�����s���AMap��List�Ƃ��ĕԋp����B
     * �ԋp����錋�ʂ́A�����ɂĎw�肳�ꂽ�C���f�b�N�X����w�肳�ꂽ�����ł���B
     * ����0�����͋󃊃X�g���ԋp�����B
     * @param sqlID
     *            ���s����SQL��ID
     * @param bindParams
     *            SQL�Ƀo�C���h����l���i�[�����I�u�W�F�N�g
     * @param beginIndex
     *            �擾����J�n�C���f�b�N�X
     * @param maxCount
     *            �擾���錏��
     * @return SQL�̎��s����
     */
    public List<Map<String, Object>> executeForMapList(String sqlID,
            Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams,
                beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }
    
}