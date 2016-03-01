package com.github.km91jp.tdaowmb3;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.terasoluna.fw.dao.QueryDAO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.km91jp.tdaowmb3.dto.Tdaowmb3test;

@ContextConfiguration(locations = "classpath:beansDef/tdaowmb3.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryDAOMyBatis3ImplTest {

    @Inject
    QueryDAO queryDAO;

    @Test
    public void executeForObjectの結果が正しいこと() {
        Tdaowmb3test dto = queryDAO.executeForObject("QueryDAOMyBatis3ImplTest.selectOneDataForObject", 1,
                Tdaowmb3test.class);
        assertEquals(1, dto.getId());
        assertEquals("first", dto.getBody());
    }

    @Test
    public void executeForObjectが複数件結果を返す場合は例外がスローされること() {
        try {
            queryDAO.executeForObject("QueryDAOMyBatis3ImplTest.selectMultipleDataForObject", null, Tdaowmb3test.class);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeForObjectの結果の型が違う場合は例外がスローされること() {
        try {
            Tdaowmb3test rObj = queryDAO.executeForObject("QueryDAOMyBatis3ImplTest.selectOneDataForObject", null,
                    Map.class);
            fail(rObj.getClass().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeForObjectArrayの結果が正しいこと() {
        Tdaowmb3test[] dtos = queryDAO.executeForObjectArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForObject",
                null, Tdaowmb3test.class);
        assertEquals(5, dtos.length);
        assertEquals(1, dtos[0].getId());
        assertEquals("first", dtos[0].getBody());
        assertEquals(2, dtos[1].getId());
        assertEquals("second", dtos[1].getBody());
        assertEquals(3, dtos[2].getId());
        assertEquals("third", dtos[2].getBody());
        assertEquals(4, dtos[3].getId());
        assertEquals("fourth", dtos[3].getBody());
        assertEquals(5, dtos[4].getId());
        assertEquals("fifth", dtos[4].getBody());
    }

    @Test
    public void executeForObjectArrayの件数調整が正しいこと() {
        Tdaowmb3test[] dtos = queryDAO.executeForObjectArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForObject",
                null, Tdaowmb3test.class, 0, 2);
        assertEquals(2, dtos.length);
        assertEquals(1, dtos[0].getId());
        assertEquals("first", dtos[0].getBody());
        assertEquals(2, dtos[1].getId());
        assertEquals("second", dtos[1].getBody());
    }

    @Test
    public void executeForObjectArrayの結果の型が違う場合は例外がスローされること() {
        try {
            queryDAO.executeForObjectArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForObject", null, Map.class);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            queryDAO.executeForObjectArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForObject", null, Map.class, 0,
                    1);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void executeForObjectListの結果が正しいこと() {
        List<Tdaowmb3test> dtoList = queryDAO.executeForObjectList(
                "QueryDAOMyBatis3ImplTest.selectMultipleDataForObject", null);
        assertEquals(5, dtoList.size());
        assertEquals(1, dtoList.get(0).getId());
        assertEquals("first", dtoList.get(0).getBody());
        assertEquals(2, dtoList.get(1).getId());
        assertEquals("second", dtoList.get(1).getBody());
        assertEquals(3, dtoList.get(2).getId());
        assertEquals("third", dtoList.get(2).getBody());
        assertEquals(4, dtoList.get(3).getId());
        assertEquals("fourth", dtoList.get(3).getBody());
        assertEquals(5, dtoList.get(4).getId());
        assertEquals("fifth", dtoList.get(4).getBody());
    }

    @Test
    public void executeForObjectListの件数調整が正しいこと() {
        List<Tdaowmb3test> dtoList = queryDAO.executeForObjectList(
                "QueryDAOMyBatis3ImplTest.selectMultipleDataForObject", null, 4, 1);
        assertEquals(1, dtoList.size());
        assertEquals(5, dtoList.get(0).getId());
        assertEquals("fifth", dtoList.get(0).getBody());
    }

    @Test
    public void executeForMapの結果が正しいこと() {
        Map<String, Object> map = queryDAO.executeForMap("QueryDAOMyBatis3ImplTest.selectOneDataForMap", 2);
        assertEquals(2, map.size());
        assertEquals(2, map.get("id"));
        assertEquals("second", map.get("body"));
    }

    @Test
    public void executeForMapArrayの結果が正しいこと() {
        Map<String, Object>[] maps = queryDAO.executeForMapArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForMap",
                null);
        assertEquals(5, maps.length);
        assertEquals(1, maps[0].get("id"));
        assertEquals("first", maps[0].get("body"));
        assertEquals(2, maps[1].get("id"));
        assertEquals("second", maps[1].get("body"));
        assertEquals(3, maps[2].get("id"));
        assertEquals("third", maps[2].get("body"));
        assertEquals(4, maps[3].get("id"));
        assertEquals("fourth", maps[3].get("body"));
        assertEquals(5, maps[4].get("id"));
        assertEquals("fifth", maps[4].get("body"));
    }

    @Test
    public void executeForMapArrayの件数調整が正しいこと() {
        Map<String, Object>[] maps = queryDAO.executeForMapArray("QueryDAOMyBatis3ImplTest.selectMultipleDataForMap",
                null, 2, 2);
        assertEquals(2, maps.length);
        assertEquals(3, maps[0].get("id"));
        assertEquals("third", maps[0].get("body"));
        assertEquals(4, maps[1].get("id"));
        assertEquals("fourth", maps[1].get("body"));
    }

    @Test
    public void executeForMapListの結果が正しいこと() {
        List<Map<String, Object>> mapList = queryDAO.executeForMapList(
                "QueryDAOMyBatis3ImplTest.selectMultipleDataForMap", null);
        assertEquals(5, mapList.size());
        assertEquals(1, mapList.get(0).get("id"));
        assertEquals("first", mapList.get(0).get("body"));
        assertEquals(2, mapList.get(1).get("id"));
        assertEquals("second", mapList.get(1).get("body"));
        assertEquals(3, mapList.get(2).get("id"));
        assertEquals("third", mapList.get(2).get("body"));
        assertEquals(4, mapList.get(3).get("id"));
        assertEquals("fourth", mapList.get(3).get("body"));
        assertEquals(5, mapList.get(4).get("id"));
        assertEquals("fifth", mapList.get(4).get("body"));
    }

    @Test
    public void executeForMapListの件数調整が正しいこと() {
        List<Map<String, Object>> mapList = queryDAO.executeForMapList(
                "QueryDAOMyBatis3ImplTest.selectMultipleDataForMap", null, 0, 3);
        assertEquals(3, mapList.size());
        assertEquals(1, mapList.get(0).get("id"));
        assertEquals("first", mapList.get(0).get("body"));
        assertEquals(2, mapList.get(1).get("id"));
        assertEquals("second", mapList.get(1).get("body"));
        assertEquals(3, mapList.get(2).get("id"));
        assertEquals("third", mapList.get(2).get("body"));
    }

}
