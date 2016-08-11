package com.gome.pricemonitor.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gome.pricemonitor.common.util.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.domain.ManagerUser;

public interface ManagerUserService {

    
    /**
     * 添加后台管理登录用户
     * @param user
     * @param operatorName
     * @return
     */
    public int save(ManagerUser user, String operatorName);
    
    /**
     * 编辑后台端管理系统账号
     * @param user
     * @param operatorName
     */
    public ManagerUser edit(ManagerUser user, String operatorName);
    
    public void del(Long userId, String operatorName);
    
    public Page<ManagerUser> query(String condition,Long roleId, int pageNo, int pageSize);
    
    public ManagerUser selectByUserName(String userName);
    
    public ManagerUser selectById(Long userId);
    
    public ResponsesDTO updatePasswd(String content, Long userId);
    
    public ResponsesDTO login(String userName, String passwd);
    
    public void exportExcel(HttpServletResponse response, String condition,Long roleId, int pageNo, int pageSize);
    
    /**
     * 记住密码登录
     * @return
     */
    public Map<String, Object> rememberLogin(String token);
    
    //登录
    public Map<String, Object> login(String content, boolean checked);
}
