package com.xy.po;

import java.sql.Date;

// user类的数据访问对象---对应user表
public class User {
    private int id;
    private String username;
    private String password;
    private String telephone;
    private String email;
    //private Date bir;
    private String birthday;
    private String sex;


    public User(String username,  String password, String email) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
    
    

	public User(String username, String telephone, String email, String birthday, String sex) {
		super();
		this.username = username;
		this.telephone = telephone;
		this.email = email;
		this.birthday = birthday;
		this.sex = sex;
	}

	public User(String username) {
		super();
		this.username = username;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public User() {
		
	}

	

	public User(int id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}



	public User(String username, String password, String telephone, String email, String birthday, String sex) {
		super();
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.birthday = birthday;
		this.sex = sex;
	}

	public User(int id, String username, String email, String password, String telephone, String sex) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	

	public User(int id, String username, String email, String password, String telephone, String birthday, String sex) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.birthday = birthday;
		this.sex = sex;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", telephone=" + telephone + ", birthday=" + birthday + ", sex=" + sex + "]";
	}

 
}
