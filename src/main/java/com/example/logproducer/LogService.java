package com.example.logproducer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.clickhouse.ClickHouseConnectionImpl;
import ru.yandex.clickhouse.ClickHousePreparedStatement;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LogService {

    @Resource
    @Qualifier("clickhouse_connection")
    private ClickHouseConnectionImpl connection;

    public List<Map> exeSQL(String sql) {
        ClickHousePreparedStatement statement = null;
        try {
            statement = connection.createClickHousePreparedStatement(sql, 1);

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            List<Map> list = new ArrayList();
            while (resultSet.next()) {
                Map row = new HashMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {

                    row.put(rsmd.getColumnName(i), resultSet.getString(rsmd.getColumnName(i)));
//                    System.out.println(rsmd.getColumnName(i));
                }
                list.add(row);
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<Map> getStatistics(){
        return this.exeSQL("SELECT * FROM periodic_summary");
    }
}
