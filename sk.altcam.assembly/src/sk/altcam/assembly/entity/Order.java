package sk.altcam.assembly.entity;

import java.util.Date;
import java.util.List;
import java.lang.Comparable;

import sk.altcam.assembly.AssemblyMonitoring;

public class Order implements Comparable<Order> {
  private String itemNumber;
  private String orderNumber;
  private int numItemsPlanned;
  private int cycle;
  private int numActiveCavities;
  private Date date;
  private Date sysDate;
  private int shift;
  private String machineId;
  private List<Monitoring> monitoring;

  public List<Monitoring> getMonitoring() {
    return monitoring;
  }

  public void setMonitoring(List<Monitoring> monitoring) {
    this.monitoring = monitoring;
  }

  public String getItemNumber() {
    return itemNumber;
  }

  public void setItemNumber(String itemNumber) {
    this.itemNumber = itemNumber;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public int getNumItemsPlanned() {
    return numItemsPlanned;
  }

  public void setNumItemsPlanned(int numItemsPlanned) {
    this.numItemsPlanned = numItemsPlanned;
  }

  public int getCycle() {
    return cycle;
  }

  public void setCycle(int cycle) {
    this.cycle = cycle;
  }

  public int getNumActiveCavities() {
    return numActiveCavities;
  }

  public void setNumActiveCavities(int numActiveCavities) {
    this.numActiveCavities = numActiveCavities;
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

  public String getMachineId() {
    return machineId;
  }

  public void setMachineId(String machineId) {
    this.machineId = machineId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((orderNumber == null) ? 0 : orderNumber.hashCode());
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
    Order other = (Order) obj;
    if (orderNumber == null) {
      if (other.orderNumber != null)
        return false;
    } else if (!orderNumber.equals(other.orderNumber))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("");
    if (monitoring != null) {
      for (Monitoring monitoringEntry : monitoring) {
        if (sb.length() > 0) {
          sb.append(";");
        }
        sb.append(monitoringEntry);
      }
    } else {
      sb.append("<null>");
    }

    return "Order [cycle=" + cycle + ", date=" + date + ", itemNumber="
        + itemNumber + ", numActiveCavities=" + numActiveCavities
        + ", numItemsPlanned=" + numItemsPlanned + ", orderNumber="
        + orderNumber + ", shift=" + shift + ", sysDate=" + sysDate
        + ", machine=" + machineId + "monitoring = [" + sb.toString() + "]]";
  }

  public Order(String orderNumber, String itemNumber, String machineId , Date date) {
    super();
    this.orderNumber = orderNumber;
    this.date = AssemblyMonitoring.resetTimeOfDate(date);
    this.itemNumber = itemNumber;
    this.machineId = machineId;
  }

  public void synchonizeFrom(Order order) {
    this.itemNumber = order.getItemNumber();
    this.orderNumber = order.getOrderNumber();
    this.numItemsPlanned = order.getNumItemsPlanned();
    this.cycle = order.getCycle();
    this.numActiveCavities = order.getNumActiveCavities();
    this.date = order.getDate();
    this.sysDate = order.getSysDate();
    this.shift = order.getShift();
    this.machineId = order.getMachineId();
  }

  public int compareTo(Order order) {
    return AssemblyMonitoring.sortString(this.getOrderNumber(),
        order.getOrderNumber());
  }
}
