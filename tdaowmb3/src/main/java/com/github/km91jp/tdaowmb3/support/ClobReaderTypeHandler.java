package com.github.km91jp.tdaowmb3.support;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * MyBatis3用のCLOBをReaderにマッピングするためのタイプハンドラ
 */
public class ClobReaderTypeHandler extends BaseTypeHandler<Reader> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Reader parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setClob(i, parameter);
    }

    @Override
    public Reader getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toReader(rs.getClob(columnName));
    }

    @Override
    public Reader getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toReader(rs.getClob(columnIndex));
    }

    @Override
    public Reader getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toReader(cs.getClob(columnIndex));
    }

    /**
     * ClobからReaderを取得する
     * 
     * @param clob
     *            Clob
     * @return Reader
     * @throws SQLException
     *             CLOBオブジェクトへのアクセス時に例外が発生した場合
     */
    private Reader toReader(Clob clob) throws SQLException {
        if (clob == null) {
            return null;
        }
        return clob.getCharacterStream();
    }

}
