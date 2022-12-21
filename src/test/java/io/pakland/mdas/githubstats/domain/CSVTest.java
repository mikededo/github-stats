package io.pakland.mdas.githubstats.domain;

import java.util.List;

abstract public class CSVTest {

    protected boolean isValidCSV(String candidate) {
        List<String> lines = List.of(candidate.split("\n"));
        if (lines.size() < 1) return false;

        String header = lines.get(0);
        if (lines.size() == 1) return true;
        else if (header.isBlank()) return false;

        List<String> fields = List.of(header.split(","));
        long invalidLines = lines.stream().filter(line -> line.split(",").length != fields.size()).count();
        return invalidLines == 0;
    }

}
