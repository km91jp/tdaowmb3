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

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.jdbc.support.lob.LobHandler;

/**
 * {@link jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * iBATIS���痘�p�����Clob�ƃX�g���[�����}�b�s���O���������iBATIS�̃^�C�v�n���h���B
 * <p>
 * 
 * @see jp.terasoluna.fw.orm.ibatis.support.ClobReaderTypeHandler
 */
@SuppressWarnings("unused")
public class ClobReaderTypeHandlerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(ClobReaderTypeHandlerTest.class);
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
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public ClobReaderTypeHandlerTest(String name) {
        super(name);
    }

    /**
     * testClobReaderTypeHandler01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) lobHandler:not null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) lobHandler:������lobHandler�Ɠ����lobHandler<br>
     *         
     * <br>
     * ��������not null�̏ꍇ�A�����ɐݒ肷�邱�Ƃ̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClobReaderTypeHandler01() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();

        // �e�X�g���{
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        // ����
        assertSame(lob, UTUtil.getPrivateField(handler, "lobHandler"));
    }

    /**
     * testClobReaderTypeHandler02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) lobHandler:null<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:IllegalStateException<br>
     *         
     * <br>
     * ������null�̏ꍇ�AIllegalStateException���X���[�����邱�Ƃ̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testClobReaderTypeHandler02() throws Exception {
        // �O����
        LobHandler lob = null;

        // �e�X�g���{
        try {
            ClobReaderTypeHandler handler =
                new ClobReaderTypeHandler(lob);
            fail();
        } catch (IllegalStateException e) {
            // ����            
        }
    }

    /**
     * testSetParameterInternal01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) value:Reader�C���X�^���X<br>
     *         (���) lobCreator.setClobAsCharacterStream():����<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:����<br>
     *         (��ԕω�) lobCreator.setClobAsCharacterStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
     *         
     * <br>
     * lobCreator.setClobAsCharacterStream()�����s����Ă��邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetParameterInternal01() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : Reader
        StringReader value = new StringReader("");
        String jdbcType = null;
        // LobCreator�����N���X : �Ăяo���m�F
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        // �e�X�g���{
        handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);

        // ����
        boolean b = ((Boolean) lobCreator.isSetClobAsCharacterStream)
        .booleanValue();
        assertTrue(b);
        
        value.close();
    }

    /**
     * testSetParameterInternal02()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(����) value:Reader�ȊO�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:ClassCastExceptinon<br>
     *         
     * <br>
     * ClassCastException���X���[����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetParameterInternal02() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : Reader�ȊO
        String value = "";
        String jdbcType = null;
        // LobCreator�����N���X
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        try {
            // �e�X�g���{
            handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);
            fail();
        } catch (ClassCastException e) {
            // ����
        }
    }

    /**
     * testSetParameterInternal03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) lobCreator.setClobAsCharacterStream():SQLException<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) �Ȃ�:SQLException<br>
     *         
     * <br>
     * SQLException���X���[����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetParameterInternal03() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : -
        StringReader value = null;
        String jdbcType = null;
        // LobCreator�����N���X : SQLException
        LobCreatorImpl02 lobCreator = new LobCreatorImpl02();
        
        ClobReaderTypeHandler handler = new ClobReaderTypeHandler(lob);

        try {
            // �e�X�g���{
            handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);
            fail();
        } catch (SQLException e) {
            // ����
        }
    }

    /**
     * testGetResultInternal01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) lobHandler.getClobAsCharacterStream():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:lobHandler.getClobAsCharacterStream()�̖߂�l�Ɠ���C���X�^���X<br>
     *         (��ԕω�) lobHandler.getClobAsCharacterStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
     *         
     * <br>
     * lobHandler.getClobAsCharacterStream()�̖߂�l�Ɠ���C���X�^���X��ԋp���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultInternal01() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ResultSet ps = null;
        int index = 0;
        
        // LobHandler�����N���X��getClobAsCharacterStream�̃��^�[���l : 
        // StringReader�C���X�^���X
        StringReader sr = new StringReader("");
        UTUtil.setPrivateField(lob, "r", sr);
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        // �e�X�g���{
        StringReader reader =
            (StringReader) handler.getResultInternal(ps, index, lob);

        // ����
        boolean b = ((Boolean) lob.isGetClobAsCharacterStream).booleanValue();
        assertTrue(b);
        assertSame(sr, reader);
        
        sr.close();
    }

    /**
     * testGetResultInternal02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) lobHandler.getClobAsCharacterStream():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) lobHandler.getClobAsCharacterStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
     *         
     * <br>
     * null��ԋp���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultInternal02() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ResultSet ps = null;
        int index = 0;
        
        // LobHandler�����N���X��getClobAsCharacterStream�̃��^�[���l : null
        StringReader sr = null;
        UTUtil.setPrivateField(lob, "r", sr);
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        // �e�X�g���{
        StringReader reader =
            (StringReader) handler.getResultInternal(ps, index, lob);

        // ����
        boolean b = ((Boolean) lob.isGetClobAsCharacterStream).booleanValue();
        assertTrue(b);
        assertNull(reader);
    }

    /**
     * testGetResultInternal03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) lobHandler.getClobAsCharacterStream():SQLException<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:SQLException<br>
     *         
     * <br>
     * SQLException���X���[����邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultInternal03() throws Exception {
        // �O����
        // getClobAsCharacterStream : SQLException
        LobHandlerImpl02 lob = new LobHandlerImpl02();
        
        ResultSet ps = null;
        int index = 0;
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);

        try {
            // �e�X�g���{
            StringReader reader =
                (StringReader) handler.getResultInternal(ps, index, lob);
            fail();
        } catch (SQLException e) {
            // ����
        }
    }

    /**
     * testValueOf01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) s:null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         
     * <br>
     * null��ԋp���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValueOf01() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = null;

        // �e�X�g���{
        Object obj = handler.valueOf(s);

        // ����
        assertNull(obj);
    }

    /**
     * testValueOf02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) s:""<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:new StringReader("".getBytes())<br>
     *         
     * <br>
     * ""�𕶎��X�g���[�����������̂�ԋp���邱�Ƃ̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValueOf02() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = "";

        // �e�X�g���{
        Object obj = handler.valueOf(s);

        // ����
        assertEquals(StringReader.class.getName(),
                obj.getClass().getName());
        String str = (String) UTUtil.getPrivateField(obj, "str");
        assertEquals("", str);
    }

    /**
     * testValueOf03()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(����) s:"ABC"<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:new StringReader("ABC".getBytes())<br>
     *         
     * <br>
     * "ABC"�𕶎��X�g���[�����������̂�ԋp����e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValueOf03() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ClobReaderTypeHandler handler =
            new ClobReaderTypeHandler(lob);
        
        String s = "ABC";

        // �e�X�g���{
        Object obj = handler.valueOf(s);

        // ����
        assertEquals(StringReader.class.getName(),
                obj.getClass().getName());
        String str = (String) UTUtil.getPrivateField(obj, "str");
        assertEquals("ABC", str);
    }
}