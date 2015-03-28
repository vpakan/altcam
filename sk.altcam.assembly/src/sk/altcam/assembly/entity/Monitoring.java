package sk.altcam.assembly.entity;

import java.util.Date;

import sk.altcam.assembly.AssemblyMonitoring;

public class Monitoring  implements Comparable<Monitoring>{
  private String orderNumber;
  private Date date;
  private Date sysDate;
  private Order orderEntity;
  private int shift;
  private int pieces;
  private Date fromTime;
  private Date toTime;
  private String comment;
  private String userId;
  private int breakTime;
  private int pauseTime;
  private int nonOkPieces;
  
  public Order getOrderEntity() {
    return orderEntity;
  }
  public void setOrderEntity(Order orderEntity) {
    this.orderEntity = orderEntity;
  }
  
  public int getBreakTime() {
    return breakTime;
  }
  public void setBreakTime(int breakTime) {
    this.breakTime = breakTime;
  }
  public int getPauseTime() {
    return pauseTime;
  }
  public void setPauseTime(int pauseTime) {
    this.pauseTime = pauseTime;
  }
  public int getNonOkPieces() {
    return nonOkPieces;
  }
  public void setNonOkPieces(int nonOkPieces) {
    this.nonOkPieces = nonOkPieces;
  }
  public String getOrderNumber() {
    return orderNumber;
  }
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = AssemblyMonitoring.resetTimeOfDate(date);
  }
  public Date getSysDate() {
    return sysDate;
  }
  public void setSysDate(Date sysDate) {
    this.sysDate = sysDate;
  }
  public int getShift() {
    return shift;
  }
  public void setShift(int shift) {
    this.shift = shift;
  }
  public int getPieces() {
    return pieces;
  }
  public void setPieces(int pieces) {
    this.pieces = pieces;
  }
  public Date getFromTime() {
    return fromTime;
  }
  public void setFromTime(Date fromTime) {
    this.fromTime = fromTime;
  }
  public Date getToTime() {
    return toTime;
  }
  public void setToTime(Date toTime) {
    this.toTime = toTime;
  }
  public String getComment() {
    return comment;
  }
  public void setComment(String comment) {
    this.comment = comment;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public Monitoring(Order order, String orderNumber, Date date , String userId) {
    super();
    this.orderNumber = orderNumber;
    this.date = AssemblyMonitoring.resetTimeOfDate(date);
    this.orderEntity = order;
    this.userId = userId;
    this.fromTime = AssemblyMonitoring.resetTimeOfDate(new Date(0));
    this.toTime = AssemblyMonitoring.resetTimeOfDate(new Date(0));
    this.comment = "";
    this.pieces = 0;
    this.nonOkPieces = 0;
    this.shift = 1;
    this.breakTime = 0;
    this.pauseTime = 0;
  }
  
  public int compareTo(Monitoring monitoring) {
    int result = AssemblyMonitoring.sortDate(this.getDate(), monitoring.getDate());
    if (result == 0){
      result = AssemblyMonitoring.sortDate(this.getFromTime(), monitoring.getFromTime());
      if (result == 0){
        result = AssemblyMonitoring.sortString(this.getUserId(), monitoring.getUserId());
      }
    }
    return result; 
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((fromTime == null) ? 0 : fromTime.hashCode());
    result = prime * result
        + ((orderNumber == null) ? 0 : orderNumber.hashCode());
    result = prime * result + ((toTime == null) ? 0 : toTime.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
    Monitoring other = (Monitoring) obj;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (fromTime == null) {
      if (other.fromTime != null)
        return false;
    } else if (!fromTime.equals(other.fromTime))
      return false;
    if (orderNumber == null) {
      if (other.orderNumber != null)
        return false;
    } else if (!orderNumber.equals(other.orderNumber))
      return false;
    if (toTime == null) {
      if (other.toTime != null)
        return false;
    } else if (!toTime.equals(other.toTime))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }
  @Override
  public String toString() {
    return "Monitoring [orderNumber=" + orderNumber + ", date=" + date
        + ", sysDate=" + sysDate + ", orderEntity=" + orderEntity + ", shift="
        + shift + ", pieces=" + pieces + ", fromTime=" + fromTime + ", toTime="
        + toTime + ", comment=" + comment + ", userId=" + userId
        + ", breakTime=" + breakTime + ", pauseTime=" + pauseTime
        + ", nonOkPieces=" + nonOkPieces + "]";
  }   
  
  
}
