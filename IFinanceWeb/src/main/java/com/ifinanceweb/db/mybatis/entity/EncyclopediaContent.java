package com.ifinanceweb.db.mybatis.entity;

import java.util.Date;

public class EncyclopediaContent {
    private Integer id;

    private String catalogTypecode;

    private Long createdby;

    private Date createdon;

    private Long updateby;

    private Date updatedon;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatalogTypecode() {
        return catalogTypecode;
    }

    public void setCatalogTypecode(String catalogTypecode) {
        this.catalogTypecode = catalogTypecode == null ? null : catalogTypecode.trim();
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}