package sk.altcam.assembly.entity;

import sk.altcam.assembly.AssemblyMonitoring;

public class User implements Comparable<User>{
  private String passwordHash;
  private String userId;
  private String userName;
  private boolean isAdmin;
  
  public User( String userId, String userName,boolean isAdmin,String passwordHash) {
    super();
    this.passwordHash = passwordHash;
    this.userId = userId;
    this.userName = userName;
    this.isAdmin = isAdmin;
  }
  
  public String getPasswordHash() {
    return passwordHash;
  }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public boolean isAdmin() {
    return isAdmin;
  }
  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isAdmin ? 1231 : 1237);
    result = prime * result
        + ((passwordHash == null) ? 0 : passwordHash.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    result = prime * result + ((userName == null) ? 0 : userName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (isAdmin != other.isAdmin)
      return false;
    if (passwordHash == null) {
      if (other.passwordHash != null)
        return false;
    } else if (!passwordHash.equals(other.passwordHash))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    if (userName == null) {
      if (other.userName != null)
        return false;
    } else if (!userName.equals(other.userName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "User [passwordHash=" + passwordHash + ", userId=" + userId
        + ", userName=" + userName + ", isAdmin=" + isAdmin + "]";
  }

  @Override
  public int compareTo(User user) {
      return AssemblyMonitoring.sortString(this.getUserId(), user.getUserId());
  }  
  
}
