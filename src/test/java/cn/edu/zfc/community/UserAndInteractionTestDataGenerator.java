package cn.edu.zfc.community;

import cn.edu.zfc.community.dao.*;
import cn.edu.zfc.community.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class UserAndInteractionTestDataGenerator {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private ArticleDao articleDao;

    @Test
    public void generateTestData() {
        // 生成20个用户
        List<User> users = generateUsers(20);
        System.out.println("成功生成20个用户！");

        // 获取所有文章
        List<Article> articles = articleDao.findAll();
        if (articles.isEmpty()) {
            System.out.println("警告：没有找到文章数据，请先生成文章数据！");
            return;
        }

        // 为每个用户生成留言
        List<Message> messages = generateMessages(users, 20);
        System.out.println("成功生成20条留言！");

        // 为每个用户生成评论（每用户随机1-3条评论）
        List<Comment> comments = generateComments(users, articles, 20);
        System.out.println("成功生成20条评论！");

        // 为每篇文章生成点赞记录（每篇文章随机5-15个点赞）
        List<Like> likes = generateLikes(users, articles, 100);
        System.out.println("成功生成100条点赞记录！");

        // 为每篇文章生成收藏记录（每篇文章随机3-10个收藏）
        List<Favorite> favorites = generateFavorites(users, articles, 60);
        System.out.println("成功生成60条收藏记录！");

        // 更新文章的点赞和收藏数量
        updateArticleCounts(articles);
        System.out.println("成功更新文章的点赞和收藏数量！");

        System.out.println("=== 测试数据生成完成 ===");
        System.out.println("用户数: " + users.size());
        System.out.println("留言数: " + messages.size());
        System.out.println("评论数: " + comments.size());
        System.out.println("点赞数: " + likes.size());
        System.out.println("收藏数: " + favorites.size());
    }

    private List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        String[] roles = {"user", "user", "user", "user", "admin"}; // 大部分是普通用户，少部分管理员
        String[] avatars = {
            "/assets/img/avatar1.png",
            "/assets/img/avatar2.png",
            "/assets/img/avatar3.png",
            "/assets/img/avatar4.png",
            "/assets/img/avatar5.png"
        };

        for (int i = 1; i <= count; i++) {
            User user = new User();
            user.setName("用户" + String.format("%03d", i));
            user.setPassword("123456"); // 统一密码
            user.setAvatar(avatars[(i - 1) % avatars.length]);
            user.setRole(roles[(i - 1) % roles.length]);
            users.add(user);
        }

        return userDao.saveAll(users);
    }

    private List<Message> generateMessages(List<User> users, int count) {
        List<Message> messages = new ArrayList<>();
        Random random = new Random();
        String[] messageContents = {
            "这个网站的内容非常丰富，更新也很及时！",
            "希望能增加更多的分类，方便查找。",
            "界面设计很美观，用户体验很好。",
            "文章质量很高，学到了很多知识。",
            "希望能添加评论互动功能，增加用户参与度。",
            "希望可以推送个性化内容。",
            "网站加载速度很快，点赞！",
            "希望能增加夜间模式，保护眼睛。",
            "内容覆盖面广，满足不同用户需求。",
            "建议增加搜索功能，方便查找历史文章。",
            "非常喜欢这个网站的内容，每天都会来看。",
            "希望能增加用户分享功能。",
            "文章排版清晰，阅读体验很好。",
            "希望能增加更多互动活动。",
            "网站内容专业性强，值得信赖。",
            "希望能增加文章收藏功能。",
            "界面简洁大方，操作方便。",
            "希望能增加用户等级系统。",
            "内容更新频繁，总能看到新鲜事。",
            "希望能增加私信功能，方便用户交流。"
        };

        for (int i = 0; i < count; i++) {
            Message message = new Message();
            message.setName(users.get(i % users.size()).getName());
            message.setEmail("user" + (i + 1) + "@example.com");
            message.setContent(messageContents[i % messageContents.length]);
            messages.add(message);
        }

        return messageDao.saveAll(messages);
    }

    private List<Comment> generateComments(List<User> users, List<Article> articles, int count) {
        List<Comment> comments = new ArrayList<>();
        Random random = new Random();
        String[] commentContents = {
            "这篇文章写得太好了，非常有启发性！",
            "观点很新颖，值得深思。",
            "支持作者，继续加油！",
            "内容很实用，学到了很多。",
            "希望能有更多这样的文章。",
            "分析得很透彻，赞一个！",
            "这篇文章解决了我很多困惑。",
            "写得真好，通俗易懂。",
            "期待作者的后续作品。",
            "很有价值的分享，感谢！"
        };

        for (int i = 0; i < count; i++) {
            Comment comment = new Comment();
            comment.setArticleId(articles.get(i % articles.size()).getId());
            comment.setUserId(users.get(i % users.size()).getId());
            comment.setAvatar(users.get(i % users.size()).getAvatar());
            comment.setContent(commentContents[i % commentContents.length]);
            comments.add(comment);
        }

        return commentDao.saveAll(comments);
    }

    private List<Like> generateLikes(List<User> users, List<Article> articles, int count) {
        List<Like> likes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // 随机选择用户和文章
            User user = users.get(random.nextInt(users.size()));
            Article article = articles.get(random.nextInt(articles.size()));

            // 检查是否已经点赞过
            Like existingLike = likeDao.findByArticleIdAndUserId(article.getId(), user.getId());
            if (existingLike == null) {
                Like like = new Like();
                like.setArticleId(article.getId());
                like.setUserId(user.getId());
                likes.add(like);
            }
        }

        return likeDao.saveAll(likes);
    }

    private List<Favorite> generateFavorites(List<User> users, List<Article> articles, int count) {
        List<Favorite> favorites = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // 随机选择用户和文章
            User user = users.get(random.nextInt(users.size()));
            Article article = articles.get(random.nextInt(articles.size()));

            // 检查是否已经收藏过
            java.util.Optional<Favorite> existingFavorite = favoriteDao.findByArticleIdAndUserId(article.getId(), user.getId());
            if (!existingFavorite.isPresent()) {
                Favorite favorite = new Favorite();
                favorite.setArticleId(article.getId());
                favorite.setUserId(user.getId());
                favorites.add(favorite);
            }
        }

        return favoriteDao.saveAll(favorites);
    }

    private void updateArticleCounts(List<Article> articles) {
        for (Article article : articles) {
            Long likeCount = likeDao.countByArticleId(article.getId());
            Long favoriteCount = favoriteDao.countByArticleId(article.getId());
            article.setLikeCount(likeCount.intValue());
            article.setFavoriteCount(favoriteCount.intValue());
        }
        articleDao.saveAll(articles);
    }
}
