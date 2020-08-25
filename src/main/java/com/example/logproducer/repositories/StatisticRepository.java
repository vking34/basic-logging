package com.example.logproducer.repositories;

import com.example.logproducer.models.Statistic;
import com.example.logproducer.models.StatisticKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, StatisticKey> {

}
