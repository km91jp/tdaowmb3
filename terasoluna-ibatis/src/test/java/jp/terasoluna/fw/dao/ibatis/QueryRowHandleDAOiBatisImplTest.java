/**
 * 
 */
package jp.terasoluna.fw.dao.ibatis;

import jp.terasoluna.fw.dao.event.DataRowHandler;
import jp.terasoluna.utlib.LogUTUtil;
import junit.framework.TestCase;

/**
 * {@link jp.co.nttdata.illigra.lucy.illigralib.dao.ibatis.RowHandledQueryDAOiBatisImpl}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * RowHandledQueryDAO�C���^�t�F�[�X��iBATIS�p�����N���X�B
 * <p>
 * 
 * @see jp.terasoluna.fw.dao.ibatis.QueryDAOiBatisImpl
 */
public class QueryRowHandleDAOiBatisImplTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private QueryRowHandleDAOiBatisImpl dao = new QueryRowHandleDAOiBatisImpl();

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
        dao = new QueryRowHandleDAOiBatisImpl();
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
     * testExecuteWithRowHandler01()
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     *         (����) bindParams:"hoge"<br>
     *         (����) rowHandler:not null<br>
     *         (�O�����) sqlMapClientTemplate:SqlMapClientTemplateStub01<br>
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) SqlMapClientTemplate�̌ďo�m�F:������sqlID�AbindParams��
     *                    �Ăяo����Ă��鎖���m�F<br>
     *         (��ԕω�) DataRowHandler�̌ďo�m�F:�yINFO���O�z"param=hoge"<br>
     *
     * <br>
     * �o�C���h�p�����[�^��null�łȂ��ꍇ�A�����DataRowHandler��
     * ���s����邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteWithRowHandler01() throws Exception {
        // �O����
        QueryRowHandleDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapClTemp = 
            new QueryRowHandleDAOiBatisImpl_SqlMapClientTemplateStub01();
        dao.setSqlMapClientTemplate(sqlMapClTemp);

        DataRowHandler rowHandler = 
                    new QueryRowHandleDAOiBatisImpl_DataRowHandlerImpl();

        // �e�X�g���{
        dao.executeWithRowHandler("sqlId", "hoge", rowHandler);

        // ����
        assertTrue(sqlMapClTemp.isCalled());
        assertEquals("sqlId", sqlMapClTemp.getStatementName());
        assertEquals("hoge", sqlMapClTemp.getParameterObject());
        // RowHandlerWrappere�o�R��DataRowHandler�����s���ꂽ���Ƃ̊m�F
        assertTrue(LogUTUtil.checkInfo("param=hoge"));
    }

    /**
     * testExecuteWithRowHandler02()
     * <br><br>
     *
     * ����n
     * <br>
     * �ϓ_�FA
     * <br><br>
     * ���͒l�F(����) sqlID:"sqlId"<br>
     *         (����) bindParams:null<br>
     *         (����) rowHandler:not null<br>
     *         (�O�����) sqlMapClientTemplate:SqlMapClientTemplateStub01<br>
     * <br>
     * ���Ғl�F(�߂�l) �Ȃ�<br>
     *         (��ԕω�) SqlMapClientTemplate�̌ďo�m�F:������sqlID�AbindParams��
     *                    �Ăяo����Ă��鎖���m�F<br>
     *         (��ԕω�) DataRowHandler�̌ďo�m�F:�yINFO���O�z"param=hoge"<br>
     *
     * <br>
     * �o�C���h�p�����[�^��null�̏ꍇ�A�����DataRowHandler��
     * ���s����邱�Ƃ��m�F
     * <br>
     *
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testExecuteWithRowHandler02() throws Exception {
        // �O����
        QueryRowHandleDAOiBatisImpl_SqlMapClientTemplateStub01 sqlMapClTemp = new QueryRowHandleDAOiBatisImpl_SqlMapClientTemplateStub01();
        dao.setSqlMapClientTemplate(sqlMapClTemp);

        DataRowHandler rowHandler = new QueryRowHandleDAOiBatisImpl_DataRowHandlerImpl();

        // �e�X�g���{
        dao.executeWithRowHandler("sqlId", null, rowHandler);

        // ����
        assertTrue(sqlMapClTemp.isCalled());
        assertEquals("sqlId", sqlMapClTemp.getStatementName());
        assertNull(sqlMapClTemp.getParameterObject());
        // RowHandlerWrappere�o�R��DataRowHandler�����s���ꂽ���Ƃ̊m�F
        assertTrue(LogUTUtil.checkInfo("param is null"));
    }

}
