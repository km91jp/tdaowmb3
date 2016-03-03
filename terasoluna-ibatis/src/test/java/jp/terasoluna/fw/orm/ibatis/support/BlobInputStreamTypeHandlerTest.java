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

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.terasoluna.utlib.UTUtil;
import junit.framework.TestCase;

import org.springframework.jdbc.support.lob.LobHandler;

/**
 * {@link jp.terasoluna.fw.orm.ibatis.support.BlobInputStreamTypeHandler}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * iBATIS���痘�p�����BLOB�ƃX�g���[�����}�b�s���O���������iBATIS�̃^�C�v�n���h���B
 * <p>
 * 
 * @see jp.terasoluna.fw.orm.ibatis.support.BlobInputStreamTypeHandler
 */
@SuppressWarnings("unused")
public class BlobInputStreamTypeHandlerTest extends TestCase {

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(BlobInputStreamTypeHandlerTest.class);
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
    public BlobInputStreamTypeHandlerTest(String name) {
        super(name);
    }

    /**
     * testBlobInputStreamTypeHandler01()
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
    public void testBlobInputStreamTypeHandler01() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();

        // �e�X�g���{
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // ����
        assertSame(lob, UTUtil.getPrivateField(handler, "lobHandler"));
    }

    /**
     * testBlobInputStreamTypeHandler02()
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
    public void testBlobInputStreamTypeHandler02() throws Exception {
        // �O����
        LobHandler lob = null;

        // �e�X�g���{
        try {
            BlobInputStreamTypeHandler handler =
                new BlobInputStreamTypeHandler(lob);
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
     * ���͒l�F(����) value:InputStream�C���X�^���X<br>
     *         (���) lobCreator.setBlobAsBinaryStream():����<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) void:����<br>
     *         (��ԕω�) lobCreator.setBlobAsBinaryStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
     *         
     * <br>
     * lobCreator.setBlobAsBinaryStream()�����s����Ă��邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testSetParameterInternal01() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : InputStream
        ByteArrayInputStream value = new ByteArrayInputStream("".getBytes());
        String jdbcType = null;
        // LobCreator�����N���X : �Ăяo���m�F
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        handler.setParameterInternal(ps, index, value, jdbcType, lobCreator);

        // ����
        boolean b = ((Boolean) lobCreator.isSetBlobAsBinaryStream).booleanValue();
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
     * ���͒l�F(����) value:InputStream�ȊO�̃C���X�^���X<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:ClassCastExceptinon<br>
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
        // value : InputStream�ȊO
        String value = "";
        String jdbcType = null;
        // LobCreator�����N���X
        LobCreatorImpl01 lobCreator = new LobCreatorImpl01();
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        try {
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
     * ���͒l�F(���) lobCreator.setBlobAsBinaryStream():SQLException<br>
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
    public void testSetParameterInternal03() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        PreparedStatement ps = null;
        int index = 0;
        // value : -
        Object value = null;
        String jdbcType = null;
        // LobCreator�����N���X : SQLException
        LobCreatorImpl02 lobCreator = new LobCreatorImpl02();
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        try {
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
     * ���͒l�F(���) lobHandler.getBlobAsBinaryStream():not null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:lobHandler.getBlobAsBinaryStream()�̖߂�l�Ɠ���C���X�^���X<br>
     *         (��ԕω�) lobHandler.getBlobAsBinaryStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
     *         
     * <br>
     * lobHandler.getBlobAsBinaryStream()�̖߂�l�Ɠ���C���X�^���X��ԋp���邱�Ƃ̃e�X�g�B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testGetResultInternal01() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        ResultSet ps = null;
        int index = 0;
        
        // LobHandler�����N���X��getBlobAsBinaryStream�̃��^�[���l : 
        // ByteArrayInputStream�C���X�^���X
        ByteArrayInputStream bais = new ByteArrayInputStream("".getBytes());
        UTUtil.setPrivateField(lob, "is", bais);
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        ByteArrayInputStream input =
            (ByteArrayInputStream) handler.getResultInternal(ps, index, lob);

        // ����
        boolean b = ((Boolean) lob.isGetBlobAsBinaryStream).booleanValue();
        assertTrue(b);
        assertSame(bais, input);
        
        bais.close();
    }

    /**
     * testGetResultInternal02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) lobHandler.getBlobAsBinaryStream():null<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) Object:null<br>
     *         (��ԕω�) lobHandler.getBlobAsBinaryStream():�Ăяo����Ă��邱�Ƃ��m�F<br>
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
        
        // LobHandler�����N���X��getBlobAsBinaryStream�̃��^�[���l : null
        ByteArrayInputStream bais = null;
        UTUtil.setPrivateField(lob, "is", bais);
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        ByteArrayInputStream input =
            (ByteArrayInputStream) handler.getResultInternal(ps, index, lob);

        // ����
        boolean b = ((Boolean) lob.isGetBlobAsBinaryStream).booleanValue();
        assertTrue(b);
        assertNull(input);
    }

    /**
     * testGetResultInternal03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) lobHandler.getBlobAsBinaryStream():SQLException<br>
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
        // getBlobAsBinaryStream : SQLException
        LobHandlerImpl02 lob = new LobHandlerImpl02();
        
        ResultSet ps = null;
        int index = 0;
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);

        // �e�X�g���{
        try {
            ByteArrayInputStream input =
                (ByteArrayInputStream) handler.getResultInternal(ps, index, lob);
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
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);
        
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
     * ���Ғl�F(�߂�l) Object:new ByteArrayInputStream("".getBytes())<br>
     *         
     * <br>
     * ""���X�g���[�����������̂�ԋp���邱�Ƃ̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValueOf02() throws Exception {
        // �O����
        LobHandlerImpl01 lob = new LobHandlerImpl01();
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);
        
        String s = "";

        // �e�X�g���{
        Object obj = handler.valueOf(s);

        // ����
        assertEquals(ByteArrayInputStream.class.getName(),
                obj.getClass().getName());
        
        byte[] b1 = (byte[]) UTUtil.getPrivateField(obj, "buf");
        byte[] b2 = "".getBytes();
        for(int i=0; i<b1.length; i++) {
            assertEquals(b2[i], b1[i]);
        }
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
     * ���Ғl�F(�߂�l) Object:new ByteArrayInputStream("ABC".getBytes())<br>
     *         
     * <br>
     * "ABC"���X�g���[�����������̂�ԋp����bs���Ƃ̃e�X�g
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testValueOf03() throws Exception {
        // �O����
        LobHandler lob = new LobHandlerImpl01();
        
        BlobInputStreamTypeHandler handler =
            new BlobInputStreamTypeHandler(lob);
        
        String s = "ABC";

        // �e�X�g���{
        Object obj = handler.valueOf(s);

        // ����
        assertEquals(ByteArrayInputStream.class.getName(),
                obj.getClass().getName());
        
        byte[] b1 = (byte[]) UTUtil.getPrivateField(obj, "buf");
        byte[] b2 = "ABC".getBytes();
        for(int i=0; i<b1.length; i++) {
            assertEquals(b2[i], b1[i]);
        }
    }
}