package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 文章数据访问层
 */
@Repository
public interface ArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    /**
     * 根据标题查询文章
     */
    Article findByTitle(String title);
}
