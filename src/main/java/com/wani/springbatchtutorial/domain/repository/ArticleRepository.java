package com.wani.springbatchtutorial.domain.repository;

import com.wani.springbatchtutorial.domain.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
