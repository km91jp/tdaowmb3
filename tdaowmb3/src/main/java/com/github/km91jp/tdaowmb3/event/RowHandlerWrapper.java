package com.github.km91jp.tdaowmb3.event;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import jp.terasoluna.fw.dao.event.DataRowHandler;

/**
 * ResultHandlerをDataRowHandlerでラップするクラス
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class RowHandlerWrapper implements ResultHandler {

    /**
     * DataRowHandler
     */
    protected DataRowHandler dataRowHandler = null;

    /**
     * コンストラクタ
     * 
     * @param dataRowHandler
     *            DataRowHandler
     */
    public RowHandlerWrapper(DataRowHandler dataRowHandler) {
        super();
        this.dataRowHandler = dataRowHandler;
    }

    /**
     * DataRowHandlerのメソッドを呼び出す
     */
    public void handleResult(ResultContext valueObject) {
        this.dataRowHandler.handleRow(valueObject);

    }

}
