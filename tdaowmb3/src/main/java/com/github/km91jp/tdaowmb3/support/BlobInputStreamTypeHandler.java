package com.github.km91jp.tdaowmb3.support;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * MyBatis3用のBLOBをInputStreanとマッピングするためのタイプハンドラ
 */
public class BlobInputStreamTypeHandler extends BaseTypeHandler<InputStream> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, InputStream parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setBlob(i, parameter);
    }

    @Override
    public InputStream getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toInputStream(rs.getBlob(columnName));
    }

    @Override
    public InputStream getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toInputStream(rs.getBlob(columnIndex));
    }

    @Override
    public InputStream getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toInputStream(cs.getBlob(columnIndex));
    }

    /**
     * BlobからInputStremを取得する
     * 
     * @param blob
     *            Blob
     * @return InputStream
     * @throws SQLException
     *             BLOBオブジェクトへのアクセス時に例外が発生した場合
     */
    private InputStream toInputStream(Blob blob) throws SQLException {
        if (blob == null) {
            return null;
        }
        return blob.getBinaryStream();
    }

}
