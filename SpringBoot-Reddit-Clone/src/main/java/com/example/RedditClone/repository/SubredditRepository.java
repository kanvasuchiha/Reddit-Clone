package com.example.RedditClone.repository;

import com.example.RedditClone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
