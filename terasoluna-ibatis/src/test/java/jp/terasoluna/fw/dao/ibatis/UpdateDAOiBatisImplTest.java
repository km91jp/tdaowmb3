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

import java.util.ArrayList;
import java.util.List;

import jp.terasoluna.fw.dao.SqlHolder;
import jp.terasoluna.utlib.LogUTUtil;
import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.dao.ibatis.UpdateDAOiBatisImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * UpdateDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.dao.ibatis.UpdateDAOiBatisImpl
 */
public class UpdateDAOiBatisImplTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private UpdateDAOiBatisImpl dao = new UpdateDAOiBatisImpl();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(UpdateDAOiBatisImplTest.class);
    }

    /**
     * �������������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = new UpdateDAOiBatisImpl();
        LogUTUtil.flush();
    }

    /**
     * �I���������s���B
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
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
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public UpdateDAOiBatisImplTest(String name) {
        super(name);
    }

    /**
     * testExecute01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     *         (����) bindParams:"1"<br>
     *         (���) update�̖߂�l:1<br>
     *
     * <br>
     * ���Ғl�F(�߂�l) E:1<br>
     *         (��ԕω�) update�̌ďo�m�F:������sqlId�AbindParams�ŌĂяo����Ă��鎖���m�F<br>
     *
     * <br>
     * update�𐳏�ɌĂяo���A���ʂ��ԋp�����ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecute01() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        int i = dao.execute("sqlId", "1");

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp = (UpdateDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

    /**
     * testAddBatch01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlID1"<br>
     *         (����) bindParams:"1"<br>
     *         (���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:List[0] = <br>
     *                        SqlHolder(sqlID="sqlID1",bindParams="1")<br>
     *         
     * <br>
     * sqlHolders��null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({"unchecked","deprecation"})
    public void testAddBatch01() throws Exception {
        // �O����

        // �e�X�g���{
        dao.addBatch("sqlID1", "1");

        // ����
        Object obj = UTUtil.getPrivateField(dao, "batchSqls");
        List<SqlHolder> sqlHolders
            = ((ThreadLocal<List<SqlHolder>>) obj).get();
        SqlHolder sqlHolder = sqlHolders.get(0);
        assertEquals("sqlID1", sqlHolder.getSqlID());
        assertEquals("1", sqlHolder.getBindParams());
    }

    /**
     * testAddBatch02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlID2"<br>
     *         (����) bindParams:"2"<br>
     *         (���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:Not Null<br>
     *                List[0] = <br>
     *                    SqlHolder(sqlID="sqlID1",bindParams="1")<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:List[0] = <br>
     *                        SqlHolder(sqlID="sqlID1",bindParams="1")<br>
     *                    List[1] = <br>
     *                        SqlHolder(sqlID="sqlID2",bindParams="2")<br>
     *         
     * <br>
     * sqlHolders��Not null�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings({"unchecked","deprecation"})
    public void testAddBatch02() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        ThreadLocal<List<SqlHolder>> batchSqls
            = new ThreadLocal<List<SqlHolder>>();
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        sqlHolders.add(new SqlHolder("sqlID1","1"));
        batchSqls.set(sqlHolders);
        UTUtil.setPrivateField(dao, "batchSqls", batchSqls);

        // �e�X�g���{
        dao.addBatch("sqlID2", "2");

        // ����
        Object obj = UTUtil.getPrivateField(dao, "batchSqls");
        sqlHolders = ((ThreadLocal<List<SqlHolder>>) obj).get();
        SqlHolder sqlHolder = sqlHolders.get(0);
        assertEquals("sqlID1", sqlHolder.getSqlID());
        assertEquals("1", sqlHolder.getBindParams());
        sqlHolder = sqlHolders.get(1);
        assertEquals("sqlID2", sqlHolder.getSqlID());
        assertEquals("2", sqlHolder.getBindParams());
    }

    /**
     * testExecuteBatch01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�FNo batch sql. Call #addBatch(String, Object) at least 1 time.<br>
     *         (��ԕω�) ���O:�����b�Z�[�W��<br>
     *                    No batch sql. Call #addBatch(String, Object) at least 1 time.<br>
     *         
     * <br>
     * sqlHolders���Anull�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testExecuteBatch01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            dao.executeBatch();
            fail("�e�X�g���s");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("No SqlMapClient specified", e.getMessage());
        }
    }

    /**
     * testExecuteBatch02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:size = 0<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *                    ���b�Z�[�W�FNo batch sql. Call #addBatch(String, Object) at least 1 time.<br>
     *         (��ԕω�) ���O:�����b�Z�[�W��<br>
     *                    No batch sql. Call #addBatch(String, Object) at least 1 time.<br>
     *         
     * <br>
     * sqlHolders���Asize=0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testExecuteBatch02() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        ThreadLocal<List<SqlHolder>> batchSqls
            = new ThreadLocal<List<SqlHolder>>();
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        batchSqls.set(sqlHolders);
        UTUtil.setPrivateField(dao, "batchSqls", batchSqls);

        // �e�X�g���{
        int i = dao.executeBatch();

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertFalse(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
    }

    /**
     * testExecuteBatch03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:size = 1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) startBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) addBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) executeBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * sqlHolders���Asize=1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testExecuteBatch03() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        ThreadLocal<List<SqlHolder>> batchSqls = new ThreadLocal<List<SqlHolder>>();
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        sqlHolders.add(new SqlHolder("sqlID1","1"));
        batchSqls.set(sqlHolders);
        UTUtil.setPrivateField(dao, "batchSqls", batchSqls);

        // �e�X�g���{
        int i = dao.executeBatch();

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertTrue(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
        List<String> id = sqlMap.getId();
        List<Object> param = sqlMap.getParam();
        assertEquals("sqlID1", id.get(0));
        assertEquals("1", param.get(0));
    }

    /**
     * testExecuteBatch04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(���) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:size = 3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:3<br>
     *         (��ԕω�) startBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) addBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) executeBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * sqlHolders���Asize=3�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testExecuteBatch04() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        ThreadLocal<List<SqlHolder>> batchSqls = new ThreadLocal<List<SqlHolder>>();
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        sqlHolders.add(new SqlHolder("sqlID1","1"));
        sqlHolders.add(new SqlHolder("sqlID2","2"));
        sqlHolders.add(new SqlHolder("sqlID3","3"));
        batchSqls.set(sqlHolders);
        UTUtil.setPrivateField(dao, "batchSqls", batchSqls);

        // �e�X�g���{
        int i = dao.executeBatch();

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertTrue(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
        List<String> id = sqlMap.getId();
        List<Object> param = sqlMap.getParam();
        assertEquals("sqlID1", id.get(0));
        assertEquals("sqlID2", id.get(1));
        assertEquals("sqlID3", id.get(2));
        assertEquals("1", param.get(0));
        assertEquals("2", param.get(1));
        assertEquals("3", param.get(2));
    }

    /**
     * testExecuteBatchList01()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) sqlHolders:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalArgumentException<br>
     *                    ���b�Z�[�W�FNo SqlMapClient specified<br>
     *         
     * <br>
     * sqlHolders���Anull�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteBatchList01() throws Exception {
        // �O����

        // �e�X�g���{
        try {
            dao.executeBatch(null);
            fail("�e�X�g���s");
        } catch (IllegalArgumentException e) {
            // ����
            assertEquals("No SqlMapClient specified", e.getMessage());
        }
    }

    /**
     * testExecuteBatchList02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sqlHolders:size=0<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) startBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) update�̌ďo�m�F:�Ăяo����Ȃ�<br>
     *         (��ԕω�) executeBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         
     * <br>
     * sqlHolders���Asize=0�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteBatchList02() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(
                new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();

        // �e�X�g���{
        int i = dao.executeBatch(sqlHolders);

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertFalse(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
    }

    /**
     * testExecuteBatchList03()
     * <br><br>
     * 
     * (����n) 
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sqlHolders:size=1<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:1<br>
     *         (��ԕω�) startBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) update�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) executeBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * sqlHolders���Asize=1�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteBatchList03() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        sqlHolders.add(new SqlHolder("sqlID1","1"));

        // �e�X�g���{
        int i = dao.executeBatch(sqlHolders);

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertTrue(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
        List<String> id = sqlMap.getId();
        List<Object> param = sqlMap.getParam();
        assertEquals("sqlID1", id.get(0));
        assertEquals("1", param.get(0));
    }

    /**
     * testExecuteBatchList04()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sqlHolders:size=3<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:3<br>
     *         (��ԕω�) startBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) update�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) executeBatch�̌ďo�m�F:�Ăяo����邱�Ƃ��m�F����B<br>
     *         (��ԕω�) batchSqls.get()�ɂĎ擾����List�usqlHolders�v:null<br>
     *         
     * <br>
     * sqlHolders���Asize=3�̏ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteBatchList04() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new UpdateDAOiBatisImpl_SqlMapClientTemplateStub01());
        List<SqlHolder> sqlHolders = new ArrayList<SqlHolder>();
        sqlHolders.add(new SqlHolder("sqlID1","1"));
        sqlHolders.add(new SqlHolder("sqlID2","2"));
        sqlHolders.add(new SqlHolder("sqlID3","3"));

        // �e�X�g���{
        int i = dao.executeBatch(sqlHolders);

        // ����
        assertEquals(1, i);
        UpdateDAOiBatisImple_SqlMapSessionImpl sqlMap
            = (UpdateDAOiBatisImple_SqlMapSessionImpl) dao
                .getSqlMapClientTemplate().getSqlMapClient().openSession();
        assertTrue(sqlMap.isStartBatchCalled());
        assertTrue(sqlMap.isUpdateCalled());
        assertTrue(sqlMap.isExecuteBatchCalled());
        List<String> id = sqlMap.getId();
        List<Object> param = sqlMap.getParam();
        assertEquals("sqlID1", id.get(0));
        assertEquals("sqlID2", id.get(1));
        assertEquals("sqlID3", id.get(2));
        assertEquals("1", param.get(0));
        assertEquals("2", param.get(1));
        assertEquals("3", param.get(2));
    }

}
