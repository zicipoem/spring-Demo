package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 导航栏实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nav implements Serializable {
    private String url;      // 导航URL
    private String name;     // 导航名称
    private boolean active;  // 是否激活

    // 准备导航列表内容
    public static List<Nav> all(String name) {
        List<Nav> list = new ArrayList<>();
        list.add(new Nav("/customer/list", "客户", name.equals("customer")));
        list.add(new Nav("/article/list", "文章", name.equals("article")));
        list.add(new Nav("/comment/list", "评论", name.equals("comment")));
        list.add(new Nav("/message/list", "留言", name.equals("message")));
        return list;
    }
}
