package com.gome.pricemonitor.service;

import com.gome.pricemonitor.domain.ManagerUserRole;

public interface ManagerUserRoleService {
	
	public void save(Long userId, Long roleId);
	
    public void delByUser(Long userId);
    
    public void delByRole(Long userId);
    
    public ManagerUserRole getByUserId(Long userId);

}
