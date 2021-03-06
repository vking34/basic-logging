package com.example.logproducer.services;

import com.example.logproducer.models.domain.Log;
import com.example.logproducer.models.statistics.Behavior;
import com.example.logproducer.repositories.LogRepository;
import com.example.logproducer.repositories.StatisticRepository;
import com.example.logproducer.models.statistics.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private LogRepository logRepository;

    @Resource
    @Qualifier("clickhouse_connection")
    private ClickHouseConnectionImpl connection;


    public List<Behavior> getBehaviorsInLastDays(Integer integer){
        return logRepository.getBehaviorsInLastDays(integer);
    }


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

//    public List<Statistic> exeStatement(String sql) {
//        ClickHousePreparedStatement statement = null;
//        try {
//            statement = connection.createClickHousePreparedStatement(sql, 1);
//
//            ResultSet resultSet = statement.executeQuery();
//            ResultSetMetaData rsmd = resultSet.getMetaData();
//            List<Statistic> result = List<Statistic> rsmd
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return null;
//    }

    public List<Map> getStatistics(){
        return this.exeSQL("SELECT * FROM periodic_summary");
    }

    public List<Statistic> getStatistic(){
        return statisticRepository.findAll();
    }

    public Page<Log> getLogs(Pageable pageable){
        return logRepository.findAll(pageable);
    }
}
