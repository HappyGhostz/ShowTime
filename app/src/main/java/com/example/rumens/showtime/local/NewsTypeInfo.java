package com.example.rumens.showtime.local;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhaochen Ping
 * @create 2017/4/21
 * @description
 */
@Entity
public class NewsTypeInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String typeId;
    public String getTypeId() {
        return this.typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1707873593)
    public NewsTypeInfo(Long id, String name, String typeId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
    }
    @Generated(hash = 215923915)
    public NewsTypeInfo() {
    }
}
