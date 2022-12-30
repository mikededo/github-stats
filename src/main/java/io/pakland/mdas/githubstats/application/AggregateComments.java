package io.pakland.mdas.githubstats.application;

import io.pakland.mdas.githubstats.domain.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AggregateComments {

    public AggregateComments() {
    }

    private static Map<Team, Map<User, List<Comment>>> groupUserCommentsByTeam(
        List<Comment> comments) {
        return comments
            .stream()
            .collect(
                Collectors.groupingBy(comment -> comment.getUser().getTeam(),
                    Collectors.groupingBy(Comment::getUser)));
    }

    private static Map<User, CommentAggregation> groupCommentAggregationsByUser(
        Map<User, List<Comment>> userReviewMap) {
        Map<User, CommentAggregation> aggCommentByUser = new HashMap<>();
        userReviewMap.forEach((user, commentList) ->
            aggCommentByUser.put(user, CommentAggregation.aggregate(commentList))
        );
        return aggCommentByUser;
    }

    public Map<Team, Map<User, CommentAggregation>> execute(List<Comment> comments) {
        Map<Team, Map<User, List<Comment>>> groupedComments = groupUserCommentsByTeam(comments);

        Map<Team, Map<User, CommentAggregation>> aggComments = new HashMap<>();
        groupedComments.forEach((team, userCommentMap) ->
            aggComments.put(team, groupCommentAggregationsByUser(userCommentMap)));

        return aggComments;
    }
}
