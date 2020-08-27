package com.example.logproducer.repositories;

import com.example.logproducer.models.domain.Log;
import com.example.logproducer.models.domain.LogKey;
import com.example.logproducer.models.statistics.Behavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LogRepository extends JpaRepository<Log, LogKey> {

    @Query(value = "SELECT " +
            "new com.com.example.logproducer.models.statistics.Behavior(event_name, COUNT(event_name), date) " +
            "FROM logs " +
            "WHERE ((event_name = 'click') or (event_name = 'impression')) AND (time >= subtractDays(today(), 7)) " +
            "GROUP BY toDate(time) as date, event_name " +
            "ORDER BY event_name, date")
    List<Behavior> getBehaviorsInLastDays(Integer days);
}
