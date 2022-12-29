package io.pakland.mdas.githubstats.infrastructure.csv;

import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import io.pakland.mdas.githubstats.domain.contract.AggregationFileProcessor;
import io.pakland.mdas.githubstats.domain.entity.Metric;
import liquibase.repackaged.com.opencsv.CSVReader;
import liquibase.repackaged.com.opencsv.bean.ColumnPositionMappingStrategy;
import liquibase.repackaged.com.opencsv.bean.MappingStrategy;
import liquibase.repackaged.com.opencsv.bean.StatefulBeanToCsv;
import liquibase.repackaged.com.opencsv.bean.StatefulBeanToCsvBuilder;
import liquibase.repackaged.com.opencsv.exceptions.*;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

@Service
public class CsvAggregationFileProcessor implements AggregationFileProcessor {

    @Override
    public void write(String path, List<Metric> metrics) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("test333.csv")) ){

            StatefulBeanToCsvBuilder<Metric> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<Metric> beanWriter = builder
                    .withSeparator(';')
                    .withApplyQuotesToAll(false)
                    .build();

            // TODO : add header to csv

            beanWriter.write(metrics);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
