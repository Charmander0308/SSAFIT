package com.ssafit.member.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssafit.member.type.MemberRank;

public class MemberAuthDto implements UserDetails {
	private static final long serialVersionUID = 8615676485262096223L;
	
	private Long id;
	private String userId;
	private String userPw;
	private String name;
	private String nickname;
	private String email;
	private Integer grade;
	
//	public MemberAuthDto() {
//	}
	
	public MemberAuthDto(Long id, String userId, String userPw, String name, String nickname, String email, Integer grade) {
		this.id = id;
		this.userId = userId;
		this.userPw = userPw;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.grade = grade;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(this.grade == MemberRank.ADMIN.getCode()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Integer getGrade() {
		return grade;
	}

	@Override
	public String getPassword() {
		if(this.userPw != null && !this.userPw.isEmpty()) {
			return this.userPw;
		}
		//oauth
		return "";
	}

	@Override
	public String getUsername() {
		if(this.userId != null && !this.userId.isEmpty()) {
			return this.userId;
		}
		//oauth
		return String.valueOf(this.id);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

}
