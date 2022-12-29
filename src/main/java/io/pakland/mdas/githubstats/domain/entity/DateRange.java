package io.pakland.mdas.githubstats.domain.entity;

import java.time.Instant;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DateRange {

    private Instant from;
    private Instant to;

    public boolean isBetweenRange(Instant instant) {
        return instant.isAfter(from) && instant.isBefore(to);
    }

    public boolean isFollowingToRange(Instant instant) {
        return instant.isAfter(to);
    }

    public boolean isPreviousToRange(Instant instant) {
        return instant.isBefore(from);
    }
}
