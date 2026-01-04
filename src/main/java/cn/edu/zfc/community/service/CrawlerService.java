package cn.edu.zfc.community.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zfc.community.dao.ArticleDao;
import cn.edu.zfc.community.pojo.Article;

import java.io.IOException;

// 爬虫服务类
@Service
public class CrawlerService {

    // 文章数据访问对象
    @Autowired
    ArticleDao articleDao;

    // 梦域动漫地址
    public static final String BASE_URL = "https://www.moelove.cn/";

    // 爬取新闻
    public int[] crawlNews(int startPage, int endPage) throws IOException {
        int[] newsCount = new int[2];
        newsCount[0] = 0;
        newsCount[1] = 0;
        for (int i = startPage; i <= endPage; i++) {
            int[] count = crawlSinglePage(i);
            System.out.println("第 " + i + " 页爬取完成，共爬取 " + count[0] + " 条新闻，" + count[1] + " 条新闻已存在");
            newsCount[0] += count[0];
            newsCount[1] += count[1];
            try {
                Thread.sleep(1000); // 慢点爬，爬一页停一秒，防止进黑名单
            } catch (InterruptedException e) {
                System.err.println("异常: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return newsCount;
    }

    // 单页新闻解析方法
    public int[] crawlSinglePage(int page) throws IOException {
        int[] result = new int[2];
        result[0] = 0;
        result[1] = 0;

        // 连接网站并获取文档
        Document doc = Jsoup.connect(BASE_URL + "page_" + page + ".html")
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36 Edg/143.0.0.0")
                .timeout(10000)
                .get();

        Elements excerptEl = doc.getElementsByClass("excerpt");

        // 遍历每个新闻元素并解析内容
        for (Element excerpt : excerptEl) {
            Article article = new Article();

            // 解析标题
            String title = excerpt.select(".focus a").attr("title");
            article.setTitle(title);

            // 跳过已存在的新闻
            if (articleDao.findByTitle(title) != null) {
                result[1]++;
                continue;
            }

            // 解析摘要
            String summary = excerpt.select(".note").text();
            article.setSummary(summary);

            // 解析标签
            String tag = excerpt.select("header .public-icon-first").text();
            if (tag.isEmpty()) {
                tag = excerpt.select(".meta a").text();
            }
            article.setTag(tag);

            // 解析封面图片
            String coverUrl = excerpt.select(".focus a img").attr("src");
            coverUrl = BASE_URL + coverUrl;
            article.setCoverImage(coverUrl);

            // 解析新闻链接
            String newsUrl = excerpt.select(".focus a").attr("href");
            article.setUrl(newsUrl);

            // 解析新闻内容
            crawlNewsContent(newsUrl, article);

            // 保存新闻到数据库
            articleDao.save(article);
            result[0]++;
        }

        return result;
    }

    // 解析新闻内容方法
    public void crawlNewsContent(String newsUrl, Article article) throws IOException {
        // 连接网站并获取文档
        Document doc = Jsoup.connect(newsUrl)
                .userAgent(
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36 Edg/143.0.0.0")
                .timeout(10000)
                .get();

        Element contentEl = doc.selectFirst(".content .article-content");

        // 相对路径转换为绝对路径
        contentEl.select("img").forEach(img -> {
            img.attr("src", BASE_URL + img.attr("src"));
        });

        // 图片添加圆角类
        contentEl.select("img").forEach(img -> {
            img.addClass("rounded-3");
        });

        // 保存新闻内容
        article.setContent(contentEl.html());
    }
}