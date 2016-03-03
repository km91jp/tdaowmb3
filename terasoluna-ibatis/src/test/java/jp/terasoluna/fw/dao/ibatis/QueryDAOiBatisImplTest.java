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

import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.dao.IllegalClassTypeException;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.dao.ibatis.QueryDAOiBatisImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * QueryDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.dao.ibatis.QueryDAOiBatisImpl
 */
public class QueryDAOiBatisImplTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private QueryDAOiBatisImpl dao = new QueryDAOiBatisImpl();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ� GUI �A�v���P�[�V�������N������B
     * 
     * @param args
     *            java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(QueryDAOiBatisImplTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new QueryDAOiBatisImpl();
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dao = null;
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name
     *            ���̃e�X�g�P�[�X�̖��O�B
     */
    public QueryDAOiBatisImplTest(String name) {
        super(name);
    }

    /**
     * testExecuteForObject01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (���) queryForObject�̖߂��Object:String("abc")<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) E:String("abc")<br>
     * queryForObject�̖߂��Object������clazz�̃N���X��
     * �ϊ�����ĕԋp����邱�Ƃ��m�F�B<br>
     * (��ԕω�) queryForObject�̌ďo�m�F:������sqlId�A
     * bindParams�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForObject�̖߂�l���A����clazz�̕��ɃL���X�g�ł��A�ԋp�����ꍇ
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObject01() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        String str = dao.executeForObject("sqlId", "1", String.class);

        // �߂�l�̊m�F
        assertEquals("abc", str);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForObject02() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:null<br>
     * (���) queryForObject�̖߂��Object:String("abc")<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) queryForObject�̌ďo�m�F:������sqlId�A
     * bindParams�ŌĂяo����Ă��鎖���m�F<br>
     * (��ԕω�) ��O:IllegalClassTypeException<br>
     * (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * ����clazz��null�̏ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObject02() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        String str = dao.executeForObject("sqlId", "1", null);

        // �߂�l�̊m�F
        assertNull(str);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForObject03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.Integer"<br>
     * (���) queryForObject�̖߂��Object:String("abc")<br>
     * 
     * <br>
     * ���Ғl�F (��ԕω�) queryForObject�̌ďo�m�F:������sqlId�A
     * bindParams�ŌĂяo����Ă��鎖���m�F<br>
     * (��ԕω�) ��O:IllegalClassTypeException<br>
     * ���b�v���ꂽ��O�FClassCastException (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * ����clazz��queryForObject�̖߂�ɕ����Ⴄ�ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObject03() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        try {
            dao.executeForObject("sqlId", "1", Integer.class);
            fail();
        } catch (IllegalClassTypeException e) {
            assertEquals(ClassCastException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError(
                    IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE));
        }

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForObject04() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (���) queryForObject�̖߂��Object:null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) E:null<br>
     * (��ԕω�) queryForObject�̌ďo�m�F:������sqlId�A
     * bindParams�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForObject�̖߂�l���Anull�̏ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObject04() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub02());

        // �e�X�g���{
        String str = dao.executeForObject("sqlId", "1", String.class);

        // �߂�l�̊m�F
        assertNull(str);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub02 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub02) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForMap01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (���) queryForMap�̖߂��Map:not null<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Map:not null<br>
     * (��ԕω�) queryForObject�̌ďo�m�F:������sqlId�AbindParams �ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForObject�̖߂肪not null�ŁA���̖߂肪���̂܂ܕԋp�����ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForMap01() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub03());

        // �e�X�g���{
        Map<String, Object> str = dao.executeForMap("sqlId", "1");

        // �߂�l�̊m�F
        assertNotNull(str);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub03 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub03) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForObjectArrayStringObjectClass01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (���) queryForList�̖߂��List:not null<br>
     * [String("a"),String("b"), String("c")]<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) E[]:String("a")<br>
     * String("b")<br>
     * String("c")<br>
     * (��ԕω�) queryForList�̌ďo�m�F:������sqlId�AbindParams��
     * �Ăяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForList�̖߂肪not null�ŁA����clazz�̌^�̔z��ɕϊ��ł����ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClass01() 
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        String[] str = dao.executeForObjectArray("sqlId", "1", String.class);

        // �߂�l�̊m�F
        assertEquals("a", str[0]);
        assertEquals("b", str[1]);
        assertEquals("c", str[2]);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForObjectArrayStringObjectClass02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:null<br>
     * (���) queryForList�̖߂��List:not null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:llegalClassTypeException<br>
     * (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * ����clazz��null�̏ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClass02() 
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        try {
            dao.executeForObjectArray("sqlId", "1", null);
            fail();
        } catch (IllegalClassTypeException e) {
            assertNull(e.getCause());
            assertTrue(LogUTUtil.checkError(
                    IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE));
        }

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertFalse(sqlMapTemp.isCalled());
    }

    /**
     * testExecuteForObjectArrayStringObjectClass03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.Integer"<br>
     * (���) queryForList�̖߂��List:"not null<br>
     * [String(""a""),String(""b""), String(""c"")]"<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalClassTypeException<br>
     * ���b�v���ꂽ��O�F<br>
     * ArrayStoreException<br>
     * (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * queryForList�̖߂�ƈ���clazz�̌^���Ⴄ�ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClass03() 
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        try {
            dao.executeForObjectArray("sqlId", "1", Integer.class);
            fail();
        } catch (IllegalClassTypeException e) {
            assertEquals(ArrayStoreException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError(
                    IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE));
        }

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForMapArrayStringObject01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Map�z��<br>
     * [0]=Map["abc","123"]<br>
     * (��ԕω�) executeForObjectArray�̌ďo�m�F:������sqlId�A bindParams,
     * Map.class�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * �����ł́AexecuteForObjectArray���Ăяo���Ă��邽�߁A
     * �e�X�g��executeForObjectArray�̃e�X�g�P�[�X�ɕ�܂���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForMapArrayStringObject01() throws Exception {
        // �O����
        dao = new QueryDAOiBatisImpl_QueryDAOiBatisImplStub01();

        // �e�X�g���{
        Map<String, Object>[] map = dao.executeForMapArray("sqlId", "1");

        // ����
        assertTrue(map[0].containsKey("abc"));
        assertEquals("123", map[0].get("abc"));
        assertTrue(((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .isCalled());
        assertEquals("sqlId",
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getSqlID());
        assertEquals("1", ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBindParams());
        assertEquals(Map.class.getName(),
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getClazz()
                        .getName());
    }

    /**
     * testExecuteForObjectArrayStringObjectClassintint01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (����) beginIndex:10<br>
     * (����) maxCount:100<br>
     * (���) queryForList�̖߂��List:not null<br>
     * [String("a"),String("b"), String("c")]<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) E[]:String("a")<br>
     * String("b")<br>
     * String("c")<br>
     * (��ԕω�) queryForList�̌ďo�m�F:������sqlID, bindParams, beginIndex,
     * maxCount�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForList�̖߂肪not null�ŁA����clazz�̌^�̔z��ɕϊ��ł����ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClassintint01()
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        String[] str = dao.executeForObjectArray("sqlId", "1", String.class,
                10, 100);

        // �߂�l�̊m�F
        assertEquals("a", str[0]);
        assertEquals("b", str[1]);
        assertEquals("c", str[2]);

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
        assertEquals(10, sqlMapTemp.getSkipResults());
        assertEquals(100, sqlMapTemp.getMaxResults());
    }

    /**
     * testExecuteForObjectArrayStringObjectClassintint02() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:null<br>
     * (���) queryForList�̖߂��List:not null<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:llegalClassTypeException<br>
     * (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * ����clazz��null�̏ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClassintint02()
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        try {
            dao.executeForObjectArray("sqlId", "1", null, 10, 100);
            fail();
        } catch (IllegalClassTypeException e) {
            assertNull(e.getCause());
            assertTrue(LogUTUtil.checkError(
                    IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE));
        }

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertFalse(sqlMapTemp.isCalled());
    }

    /**
     * testExecuteForObjectArrayStringObjectClassintint03() <br>
     * <br>
     * 
     * (�ُ�n) <br>
     * �ϓ_�FG <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (���) queryForList�̖߂��List:not null<br>
     * [Integer(1)]<br>
     * 
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalClassTypeException<br>
     * ���b�v���ꂽ��O�F<br>
     * ArrayStoreException<br>
     * (��ԕω�) ���O:�����b�Z�[�W��<br>
     * "The mistake Class Type of the argument."<br>
     * 
     * <br>
     * queryForList�̖߂�ƈ���clazz�̌^���Ⴄ�ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectArrayStringObjectClassintint03()
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        try {
            dao.executeForObjectArray("sqlId", "1", Integer.class, 10, 100);
            fail();
        } catch (IllegalClassTypeException e) {
            assertEquals(ArrayStoreException.class.getName(), e.getCause()
                    .getClass().getName());
            assertTrue(LogUTUtil.checkError(
                    IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE));
        }

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
        assertEquals(10, sqlMapTemp.getSkipResults());
        assertEquals(100, sqlMapTemp.getMaxResults());
    }

    /**
     * testExecuteForMapArrayStringObjectintint01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) beginIndex:10<br>
     * (����) maxCount:100<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Map�z��<br>
     * [0]=Map["abc","123"]<br>
     * (��ԕω�) executeForObjectArray�̌ďo�m�F:������sqlId�A bindParams,
     * Map.class,beginIndex, maxCount�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * �����ł́AexecuteForObjectArray���Ăяo���Ă��邽�߁A
     * �e�X�g��executeForObjectArray�̃e�X�g�P�[�X�ɕ�܂���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForMapArrayStringObjectintint01() throws Exception {
        // �O����
        dao = new QueryDAOiBatisImpl_QueryDAOiBatisImplStub01();

        // �e�X�g���{
        Map<String, Object>[] map = dao.executeForMapArray("sqlId", "1", 10,
                100);

        // ����
        assertTrue(map[0].containsKey("abc"));
        assertEquals("123", map[0].get("abc"));
        assertTrue(((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .isCalled());
        assertEquals("sqlId",
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getSqlID());
        assertEquals("1", ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBindParams());
        assertEquals(Map.class.getName(),
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getClazz()
                        .getName());
        assertEquals(10, ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBeginIndex());
        assertEquals(100, ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getMaxCount());
    }

    /**
     * testExecuteForObjectListStringObjectClass01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (���) queryForList�̖߂��List:not null<br>
     * [String("a"),String("b"), String("c")]<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) List<E>:String("a")<br>
     * String("b")<br>
     * String("c")<br>
     * (��ԕω�) queryForList�̌ďo�m�F:������sqlId�AbindParams��
     * �Ăяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForList�̖߂肪not null�̏ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectListStringObjectClass01() 
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        List<String> str = dao.executeForObjectList("sqlId", "1");

        // �߂�l�̊m�F
        assertEquals("a", str.get(0));
        assertEquals("b", str.get(1));
        assertEquals("c", str.get(2));

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testExecuteForMapListStringObject01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Map��List<br>
     * List.get(0)=Map["abc","123"]<br>
     * (��ԕω�) executeForObjectList�̌ďo�m�F:������sqlId�A bindParams,
     * Map.class�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * �����ł́AexecuteForObjectList���Ăяo���Ă��邽�߁A
     * �e�X�g��executeForObjectList�̃e�X�g�P�[�X�ɕ�܂���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForMapListStringObject01() throws Exception {
        // �O����
        dao = new QueryDAOiBatisImpl_QueryDAOiBatisImplStub01();

        // �e�X�g���{
        List<Map<String, Object>> map = dao.executeForMapList("sqlId", "1");

        // ����
        assertTrue(map.get(0).containsKey("abc"));
        assertEquals("123", map.get(0).get("abc"));
        assertTrue(((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .isCalled());
        assertEquals("sqlId",
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getSqlID());
        assertEquals("1", ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBindParams());
    }

    /**
     * testExecuteForObjectListStringObjectClassintint01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) clazz:"java.lang.String"<br>
     * (����) beginIndex:10<br>
     * (����) maxCount:100<br>
     * (���) queryForList�̖߂��List:not null<br>
     * [String("a"),String("b"), String("c")]<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) List<E>:String("a")<br>
     * String("b")<br>
     * String("c")<br>
     * (��ԕω�) queryForList�̌ďo�m�F:������sqlID, bindParams, beginIndex,
     * maxCount�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * queryForList�̖߂肪not null�ŁA����clazz�̌^�̔z��ɕϊ��ł����ꍇ <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObjectListStringObjectClassintint01()
            throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new QueryDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        List<String> str = dao.executeForObjectList("sqlId", "1", 
                10, 100);

        // �߂�l�̊m�F
        assertEquals("a", str.get(0));
        assertEquals("b", str.get(1));
        assertEquals("c", str.get(2));

        // �Ăяo���m�F
        QueryDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp =
            (QueryDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
        assertEquals(10, sqlMapTemp.getSkipResults());
        assertEquals(100, sqlMapTemp.getMaxResults());
    }

    /**
     * testExecuteForMapListStringObjectintint01() <br>
     * <br>
     * 
     * (����n) <br>
     * �ϓ_�FC <br>
     * <br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     * (����) bindParams:"1"<br>
     * (����) beginIndex:10<br>
     * (����) maxCount:100<br>
     * 
     * <br>
     * ���Ғl�F(�߂�l) Map��List<br>
     * List.get(0)=Map["abc","123"]<br>
     * (��ԕω�) executeForObjectList�̌ďo�m�F:������sqlId�A bindParams,
     * Map.class,beginIndex, maxCount�ŌĂяo����Ă��鎖���m�F<br>
     * 
     * <br>
     * �����ł́AexecuteForObjectList���Ăяo���Ă��邽�߁A
     * �e�X�g��executeForObjectList�̃e�X�g�P�[�X�ɕ�܂���B <br>
     * 
     * @throws Exception
     *             ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForMapListStringObjectintint01() throws Exception {
        // �O����
        dao = new QueryDAOiBatisImpl_QueryDAOiBatisImplStub01();

        // �e�X�g���{
        List<Map<String, Object>> map = dao.executeForMapList("sqlId", "1", 10,
                100);

        // ����
        assertTrue(map.get(0).containsKey("abc"));
        assertEquals("123", map.get(0).get("abc"));
        assertTrue(((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .isCalled());
        assertEquals("sqlId",
                ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao).getSqlID());
        assertEquals("1", ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBindParams());

        assertEquals(10, ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getBeginIndex());
        assertEquals(100, ((QueryDAOiBatisImpl_QueryDAOiBatisImplStub01) dao)
                .getMaxCount());
    }
    
}
