package cn.edu.zfc.community;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import cn.edu.zfc.community.service.CrawlerService;

public class CrawlerMain {
    public static void main(String[] args) {
        // 启动Spring Boot应用上下文
        ConfigurableApplicationContext context = SpringApplication.run(CommunityApplication.class, args);
        
        // 获取CrawlerService bean
        CrawlerService crawlerService = context.getBean(CrawlerService.class);
        
        try {
            // 设置爬取页数范围，可以根据需要修改
            int startPage = 1;
            int endPage = 5;
            
            System.out.println("开始爬取新闻，页数范围：" + startPage + " - " + endPage);
            System.out.println("==========================================");
            
            // 调用爬虫方法
            int[] result = crawlerService.crawlNews(startPage, endPage);
            
            System.out.println("==========================================");
            System.out.println("爬取完成！");
            System.out.println("总共爬取新新闻：" + result[0] + " 条");
            System.out.println("跳过已存在新闻：" + result[1] + " 条");
            
        } catch (Exception e) {
            System.err.println("爬取过程中出现异常：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭Spring应用上下文
            context.close();
        }
    }
}