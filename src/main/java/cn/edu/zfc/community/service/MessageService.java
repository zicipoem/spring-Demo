package cn.edu.zfc.community.service;

import cn.edu.zfc.community.dao.MessageDao;
import cn.edu.zfc.community.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public Message addMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageDao.save(message);
    }

    public List<Message> listMessages() {
        return messageDao.findAllByOrderByCreatedAtDesc();
    }

    public Message getMessageById(Long id) {
        return messageDao.findById(id).orElse(null);
    }

    public boolean deleteMessage(Long id) {
        Message msg = messageDao.findById(id).orElse(null);
        if (msg != null) {
            messageDao.delete(msg);
            return true;
        }
        return false;
    }
}
