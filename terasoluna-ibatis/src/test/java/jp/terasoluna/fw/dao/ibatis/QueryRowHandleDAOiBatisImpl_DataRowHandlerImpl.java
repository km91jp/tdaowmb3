package jp.terasoluna.fw.dao.ibatis;

import jp.terasoluna.fw.dao.event.DataRowHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * {@link QueryRowHandleDAOiBatisImpl}�̎����̂��߂Ɏg�p�����B
 * 
 * {@link QueryRowHandleDAOiBatisImpl}����g�p�����B
 * 
 */
public class QueryRowHandleDAOiBatisImpl_DataRowHandlerImpl implements DataRowHandler {

    /**
     * ���O�C���X�^���X 
     */
    private static Log log = LogFactory.
            getLog(QueryRowHandleDAOiBatisImpl_DataRowHandlerImpl.class);
    
    public void handleRow(Object param) {
        // �����m�F�p
        if (param != null) {
            log.info("param=" + param);
        } else {
            log.info("param is null");
        }
    }

}
