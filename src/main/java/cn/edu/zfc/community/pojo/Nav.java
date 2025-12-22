package cn.edu.zfc.community.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nav implements Serializable {
    private String url;
    private String name;
    private boolean active;

    /**
     * 准备导航列表内容
     */
    public static List<Nav> all(String name) {
        List<Nav> list = new ArrayList<>();
        list.add(new Nav("/customer/list", "客户", name.equals("customer")));
        list.add(new Nav("/article/list", "文章", name.equals("article")));
        return list;
    }
}
