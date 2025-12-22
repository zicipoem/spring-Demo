package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 使用@Repository标识为Dao层Bean
 * 继承JpaRepository实现基础的增删改查功能，支持findBy方法，支持@Query
 * 继承JpaSpecificationExecutor实现范式查询
 */
@Repository
public interface ArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
}
