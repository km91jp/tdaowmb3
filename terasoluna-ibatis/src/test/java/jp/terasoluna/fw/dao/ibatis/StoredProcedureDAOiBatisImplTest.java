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

import junit.framework.TestCase;

/**
 * {@link jp.terasoluna.fw.dao.ibatis.StoredProcedureDAOiBatisImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 *
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * StoredProcedureDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p>
 *
 * @see jp.terasoluna.fw.dao.ibatis.StoredProcedureDAOiBatisImpl
 */
public class StoredProcedureDAOiBatisImplTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private StoredProcedureDAOiBatisImpl dao = new StoredProcedureDAOiBatisImpl();

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     *
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(StoredProcedureDAOiBatisImplTest.class);
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
        dao = new StoredProcedureDAOiBatisImpl();
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
    public StoredProcedureDAOiBatisImplTest(String name) {
        super(name);
    }

    /**
     * testExecuteForObject01()
     * <br><br>
     *
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     *         (����) bindParams:"1"<br>
     *
     * <br>
     * ���Ғl�F(��ԕω�) queryForObject�̌ďo�m�F:������sqlId�AbindParams�ŌĂяo����Ă��鎖���m�F<br>
     *
     * <br>
     * defineConnection�AqueryForObject�𐳏�ɌĂяo���ꍇ
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteForObject01() throws Exception {
        // �O����
        dao.setSqlMapClientTemplate(new StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01());

        // �e�X�g���{
        dao.executeForObject("sqlId", "1");

        // ����
        StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapTemp = (StoredProcedureDAOiBatisImpl_SqlMapClientTemplateStub01) dao
                .getSqlMapClientTemplate();
        assertTrue(sqlMapTemp.isCalled());
        assertEquals("sqlId", sqlMapTemp.getStatementName());
        assertEquals("1", sqlMapTemp.getParameterObject());
    }

}
