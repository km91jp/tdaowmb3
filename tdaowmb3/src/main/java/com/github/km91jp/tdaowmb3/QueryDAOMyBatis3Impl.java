package com.github.km91jp.tdaowmb3;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import jp.terasoluna.fw.dao.IllegalClassTypeException;
import jp.terasoluna.fw.dao.QueryDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * QueryDAOのMyBatis3実装
 * 
 * @see jp.terasoluna.fw.dao.QueryDAO
 */
public class QueryDAOMyBatis3Impl extends SqlSessionDaoSupport implements QueryDAO {

    /**
     * ロガー
     */
    private static Log log = LogFactory.getLog(QueryDAOMyBatis3Impl.class);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public <E> E executeForObject(String sqlID, Object bindParams, @SuppressWarnings("rawtypes") Class clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObject Start.");
        }

        Object obj = getSqlSession().selectOne(sqlID, bindParams);
        if (log.isDebugEnabled() && obj != null) {
            log.debug("Return type:" + obj.getClass().getName());
        }

        E rObj = null;
        try {
            if (clazz != null && obj != null) {
                rObj = (E) clazz.cast(obj);
            }
        } catch (ClassCastException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObject End.");
        }

        return rObj;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object> executeForMap(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMap Start.");
        }

        Map<String, Object> rObj = this.executeForObject(sqlID, bindParams, Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMap End.");
        }

        return rObj;
    }
    /**
     * {@inheritDoc}
     */
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams, @SuppressWarnings("rawtypes") Class clazz) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException();
        }

        List<E> list = getSqlSession().selectList(sqlID, bindParams);

        @SuppressWarnings("unchecked")
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object>[] executeForMapArray(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams, Map.class);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * {@inheritDoc}
     */
    public <E> E[] executeForObjectArray(String sqlID, Object bindParams, @SuppressWarnings("rawtypes") Class clazz,
            int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray Start.");
        }

        if (clazz == null) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException();
        }

        RowBounds rowBounds = new RowBounds(beginIndex, maxCount);
        List<E> list = getSqlSession().selectList(sqlID, bindParams, rowBounds);

        @SuppressWarnings("unchecked")
        E[] retArray = (E[]) Array.newInstance(clazz, list.size());
        try {
            list.toArray(retArray);
        } catch (ArrayStoreException e) {
            log.error(IllegalClassTypeException.ERROR_ILLEGAL_CLASS_TYPE);
            throw new IllegalClassTypeException(e);
        }

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectArray End.");
        }

        return retArray;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object>[] executeForMapArray(String sqlID, Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray Start.");
        }

        Map<String, Object>[] map = executeForObjectArray(sqlID, bindParams, Map.class, beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapArray End.");
        }

        return map;
    }

    /**
     * {@inheritDoc}
     */
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        List<E> list = getSqlSession().selectList(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }

    /**
     * {@inheritDoc}
     */
    public <E> List<E> executeForObjectList(String sqlID, Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList Start.");
        }

        RowBounds rowBounds = new RowBounds(beginIndex, maxCount);
        List<E> list = getSqlSession().selectList(sqlID, bindParams, rowBounds);

        if (log.isDebugEnabled()) {
            log.debug("executeForObjectList End.");
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public List<Map<String, Object>> executeForMapList(String sqlID, Object bindParams, int beginIndex, int maxCount) {

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList Start.");
        }

        List<Map<String, Object>> mapList = executeForObjectList(sqlID, bindParams, beginIndex, maxCount);

        if (log.isDebugEnabled()) {
            log.debug("executeForMapList End.");
        }

        return mapList;
    }

}