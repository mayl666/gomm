package com.gome.pricemonitor.domain;

public class ManagerRoleFunc {
    private Long id;

    private Long roleId;

    private Long funcId;

    private Long funcParentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }

    public Long getFuncParentId() {
        return funcParentId;
    }

    public void setFuncParentId(Long funcParentId) {
        this.funcParentId = funcParentId;
    }
}