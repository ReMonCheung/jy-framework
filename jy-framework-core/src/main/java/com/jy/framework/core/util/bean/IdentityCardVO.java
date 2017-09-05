package com.jy.framework.core.util.bean;

import java.io.Serializable;

/**
 * 身份证
 * 
 * @author Administrator
 *
 */
public class IdentityCardVO implements Serializable {
	private static final long serialVersionUID = 3466934482470853349L;
	
	private String id;
	// 发证地
	private String location;
	// 出生年月日
	private String birthdayStr;
	// 出生年月
	private Long birthday;
	// 性别
	private Integer gender;
	// 年龄
	private Integer age;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "IdentityCardVO [id=" + id + ", location=" + location
				+ ", birthdayStr=" + birthdayStr + ", birthday=" + birthday
				+ ", gender=" + gender + ", age=" + age + "]";
	}

}
