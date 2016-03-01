package com.github.km91jp.tdaowmb3;

import static org.junit.Assert.assertEquals;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.inject.Inject;

import jp.terasoluna.fw.dao.QueryRowHandleDAO;
import jp.terasoluna.fw.dao.event.DataRowHandler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.km91jp.tdaowmb3.dto.Tdaowmb3test;

@ContextConfiguration(locations = "classpath:beansDef/tdaowmb3.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryRowHandleDAOMyBatis3ImplTest {
    @Inject
    QueryRowHandleDAO queryRowHandleDAO;

    Queue<Tdaowmb3test> queue = new LinkedBlockingDeque<Tdaowmb3test>();

    DataRowHandler handler = new DataRowHandler() {

        ResultHandler<Tdaowmb3test> resultHandler = new ResultHandler<Tdaowmb3test>() {

            public void handleResult(ResultContext<? extends Tdaowmb3test> resultContext) {
                queue.add(resultContext.getResultObject());
            }

        };

        @SuppressWarnings({"rawtypes", "unchecked"})
        public void handleRow(Object arg0) {
            // MyBatis3.3.0よりResultHandlerをタイプセーフに作ることができるようになったが、
            // terasoluna-daoのシグネチャを変えないようにしている
            this.resultHandler.handleResult((ResultContext) arg0);
        }
    };

    @Test
    public void executeForObjectの結果が正しいこと() {
        queryRowHandleDAO.executeWithRowHandler("QueryRowHandleDAOMyBatis3ImplTest.selectMultipleDataForObject", null,
                handler);
        assertEquals(5, queue.size());
        Tdaowmb3test actualObj;
        actualObj = queue.poll();
        assertEquals(1, actualObj.getId());
        assertEquals("first", actualObj.getBody());
        actualObj = queue.poll();
        assertEquals(2, actualObj.getId());
        assertEquals("second", actualObj.getBody());
        actualObj = queue.poll();
        assertEquals(3, actualObj.getId());
        assertEquals("third", actualObj.getBody());
        actualObj = queue.poll();
        assertEquals(4, actualObj.getId());
        assertEquals("fourth", actualObj.getBody());
        actualObj = queue.poll();
        assertEquals(5, actualObj.getId());
        assertEquals("fifth", actualObj.getBody());
    }

}
