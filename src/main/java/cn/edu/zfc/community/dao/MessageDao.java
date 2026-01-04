package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 留言数据访问接口
@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    // 按创建时间倒序查询所有留言
    List<Message> findAllByOrderByCreatedAtDesc();
}
