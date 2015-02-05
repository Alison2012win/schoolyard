package com.schoolyard.school.console.service.impl;



import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schoolyard.common.util.UserInfo;
import com.schoolyard.service.sysuser.po.SysUserPO;
import com.schoolyard.service.sysuser.service.SysUserService;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 */
@Transactional(readOnly = true)
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Resource(name = "sysUserServiceImpl")
	private SysUserService sysUserService;
	
	 /** 
     * 获得用户所有联系人群组的权限集合. 
     */  
	private Set<SimpleGrantedAuthority> obtainGrantedAuthorities(String roleCode) {  
        Set<SimpleGrantedAuthority> authSet = new HashSet<SimpleGrantedAuthority>();  
//        for (Role role : user.getRoleList()) {  
//            for (Privilege privilege : role.getPrivilegeList()) {  
//                authSet.add(new GrantedAuthorityImpl(privilege.getPrefixedName()));  
//            }  
//        }  
        //获取当前登录用户的信息
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//        	    .getAuthentication()
//        	    .getPrincipal();

        authSet.add(new SimpleGrantedAuthority(roleCode));
        return authSet;  
    } 

	@Override
	public UserInfo loadUserByUsername(String username)
			throws UsernameNotFoundException {
		SysUserPO user = sysUserService.findUserByLogin(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}
		Set<SimpleGrantedAuthority> grantedAuths = obtainGrantedAuthorities("USER");
		//mini-web示例中无以下属性, 暂时全部设为true
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = (user.getStatus()==1);
//		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
//		String password = md5.encodePassword("admin", null);
//		System.out.println("#####################################################################");
//		System.out.println(md5.encodePassword("1", "admin"));
//		System.err.println(md5.encodePassword("admin", null));
//		user.setPassword(md5.encodePassword("admin", null));
		UserInfo userdetails = new UserInfo(
				user.getLogin(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				grantedAuths);
//		TeacherPhotoPO photo = teacherService.findTeacherPhotoByTid(user.getTid());
//		userdetails.setPhoto(photo != null ? photo.getImgUrl() : "");
		userdetails.setUid(user.getSysuid());
//		TeacherPO taecher = teacherService.findTeacherById(user.getTid());
		userdetails.setName(user.getName());
		
		return userdetails;
	}
}
