package User;

import Team.Role;

public class Profile {
    private Long id;
    private User user;
    private Role role;
    private String content;
    private String photo;
    private String portforlio;
    private String fileName;
    private String originalFileName;
    
    public String getOriginalFileName() {
    	return originalFileName;
    }
    
    public void setOriginalFileName(String originaFileName) {
    	this.originalFileName = originaFileName;	
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setId(Long id) {
    	this.id = id;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    public void setRole(Role role) {
    	this.role = role;
    }
    
    public void setContent(String content) {
    	this.content = content;
    }
    
    public void setPhoto(String photo) {
    	this.photo = photo;
    }
    
    public void setPortforlio(String portforlio) {
    	this.portforlio = portforlio;
    }
    
    public Long getId() {
    	return id;
    }
    
    public User getUser() {
    	return user;
    }
    
    public Role getRole() {
    	return role;
    }
    
    public String getContent() {
    	return content;
    }
    
    public String getPhoto() {
    	return photo;
    }
    
    public String getPortforlio() {
    	return portforlio;
    }
    
    public Profile() {
    	
    }
    
    public static class Builder {
        private Long id;
        private User user;
        private Role role;
        private String content;
        private String photo;
        private String portforlio;
        private String fileName;
        private String originalFileName;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        public Builder originalFileName(String originalFileName) {
        	this.originalFileName = originalFileName;
        	return this;
        }
        
        public Builder user(User user) {
        	this.user = user;
        	return this;
        	
        }
        
        public Builder role(Role role) {
        	this.role = role;
        	return this;
        }
        
        public Builder content(String content) {
        	this.content = content;
        	return this;
        }
        
        public Builder photo(String photo) {
        	this.photo = photo;
        	return this;
        }
        
        public Builder portforlio(String portforlio) {
        	this.portforlio = portforlio;
        	return this;
        }

        public Profile build() {
            return new Profile(this);
        }


    }
    
    public Profile(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.role = builder.role;
        this.content = builder.content;
        this.photo = builder.photo;
        this.portforlio = builder.portforlio;
        this.fileName = builder.fileName;
        this.originalFileName = builder.originalFileName;
    }
}
