package cn.edu.zfc.community.dao;

import cn.edu.zfc.community.pojo.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    List<Message> findAllByOrderByCreatedAtDesc();
}
