package sk.altcam.assembly.entity;

import java.util.Date;

import sk.altcam.assembly.ProductionMonitoring;

public class Monitoring  implements Comparable<Monitoring>{
  private String orderNumber;
  private Date date;
  private Date sysDate;
  private int produced;
  private int nabehove;
  private int prestreky;
  private int nedostreky;
  private int zhorene;
  private int vadaMaterialu;
  private int deformacie;
  private int poskodenyZastrekZalis;
  private int chybajuciZastrek;
  private int bodVstreku;
  private int pritomnostPruznychElementov;
  private int vyhadzane;
  private int prestrekyVOtvoroch;
  private int farba;
  private int zastavenieStroja;
  private int ine;
  private int nedolisovaneStroj;
  private int nedolisovaneForma;
  private int nedolisovanePeriferie;
  private int order;
  private int plannedIdleTime;
  private int formExchange;
  private Order orderEntity;
  public Order getOrderEntity() {
    return orderEntity;
  }
  public void setOrderEntity(Order orderEntity) {
    this.orderEntity = orderEntity;
  }
  public int getOrder() {
    return order;
  }
  public void setOrder(int order) {
    this.order = order;
  }
  public int getProduced() {
    return produced;
  }
  public void setProduced(int produced) {
    this.produced = produced;
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
    this.date = ProductionMonitoring.resetTimeOfDate(date);
  }
  public Date getSysDate() {
    return sysDate;
  }
  public void setSysDate(Date sysDate) {
    this.sysDate = sysDate;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result
        + ((orderNumber == null) ? 0 : orderNumber.hashCode());
    return result;
  }
  public int getNabehove() {
    return nabehove;
  }
  public void setNabehove(int nabehove) {
    this.nabehove = nabehove;
  }
  public int getPrestreky() {
    return prestreky;
  }
  public void setPrestreky(int prestreky) {
    this.prestreky = prestreky;
  }
  public int getNedostreky() {
    return nedostreky;
  }
  public void setNedostreky(int nedostreky) {
    this.nedostreky = nedostreky;
  }
  public int getZhorene() {
    return zhorene;
  }
  public void setZhorene(int zhorene) {
    this.zhorene = zhorene;
  }
  public int getVadaMaterialu() {
    return vadaMaterialu;
  }
  public void setVadaMaterialu(int vadaMaterialu) {
    this.vadaMaterialu = vadaMaterialu;
  }
  public int getDeformacie() {
    return deformacie;
  }
  public void setDeformacie(int deformacie) {
    this.deformacie = deformacie;
  }
  public int getPoskodenyZastrekZalis() {
    return poskodenyZastrekZalis;
  }
  public void setPoskodenyZastrekZalis(int poskodenyZastrekZalis) {
    this.poskodenyZastrekZalis = poskodenyZastrekZalis;
  }
  public int getChybajuciZastrek() {
    return chybajuciZastrek;
  }
  public void setChybajuciZastrek(int chybajuciZastrek) {
    this.chybajuciZastrek = chybajuciZastrek;
  }
  public int getBodVstreku() {
    return bodVstreku;
  }
  public void setBodVstreku(int bodVstreku) {
    this.bodVstreku = bodVstreku;
  }
  public int getPritomnostPruznychElementov() {
    return pritomnostPruznychElementov;
  }
  public void setPritomnostPruznychElementov(int pritomnostPruznychElementov) {
    this.pritomnostPruznychElementov = pritomnostPruznychElementov;
  }
  public int getVyhadzane() {
    return vyhadzane;
  }
  public void setVyhadzane(int vyhadzane) {
    this.vyhadzane = vyhadzane;
  }
  public int getPrestrekyVOtvoroch() {
    return prestrekyVOtvoroch;
  }
  public void setPrestrekyVOtvoroch(int prestrekyVOtvoroch) {
    this.prestrekyVOtvoroch = prestrekyVOtvoroch;
  }
  public int getFarba() {
    return farba;
  }
  public void setFarba(int farba) {
    this.farba = farba;
  }
  public int getZastavenieStroja() {
    return zastavenieStroja;
  }
  public void setZastavenieStroja(int zastavenieStroja) {
    this.zastavenieStroja = zastavenieStroja;
  }
  public int getIne() {
    return ine;
  }
  public void setIne(int ine) {
    this.ine = ine;
  }
  public int getPlannedIdleTime() {
    return plannedIdleTime;
  }
  public void setPlannedIdleTime(int plannedIdleTime) {
    this.plannedIdleTime = plannedIdleTime;
  }
  public int getFormExchange() {
    return formExchange;
  }
  public void setFormExchange(int formExchange) {
    this.formExchange = formExchange;
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
    if (orderNumber == null) {
      if (other.orderNumber != null)
        return false;
    } else if (!orderNumber.equals(other.orderNumber))
      return false;
    if (order != other.order){
      return false;
    }
    return true;
  }
  @Override
  public String toString() {
    return "Monitoring [date=" + date + ", produced=" + produced 
        + ", order=" + order + ", nabehove=" + nabehove 
        + ", plannedIdleTime=" + plannedIdleTime + ", formExchange=" + formExchange 
        + ", prestreky=" + prestreky + ", orderNumber=" + orderNumber
        + ", otrepy=" + nedostreky + ", zhorene=" + zhorene
        + ", vadaMaterialu=" + vadaMaterialu + ", nedostreknuteOkoloZastreku=" + deformacie
        + ", poskodenyZastrekZalis=" + poskodenyZastrekZalis + ", nedolisovanePoJadrach=" + chybajuciZastrek
        + ", bodVstreku=" + bodVstreku + ", pritomnostPruznychElementov=" + pritomnostPruznychElementov
        + ", vyhadzane=" + vyhadzane + ", prestrekyVOtvoroch=" + prestrekyVOtvoroch
        + ", farba=" + farba + ", zastavenieStroja=" + zastavenieStroja
        + ", ine=" + ine + ", sysDate=" + sysDate + "]";
  }
  public Monitoring(Order order, String orderNumber, Date date) {
    super();
    this.orderNumber = orderNumber;
    this.date = ProductionMonitoring.resetTimeOfDate(date);
    this.orderEntity = order;
  }
  
  public int compareTo(Monitoring monitoring) {
    int result = ProductionMonitoring.sortString(this.getOrderNumber(), monitoring.getOrderNumber());
    if (result == 0){
      result = ProductionMonitoring.sortInt(this.getOrder(), monitoring.getOrder());
    }
    return result; 
  }
  public int getNedolisovaneStroj() {
    return nedolisovaneStroj;
  }
  public void setNedolisovaneStroj(int nedolisovaneStroj) {
    this.nedolisovaneStroj = nedolisovaneStroj;
  }
  public int getNedolisovaneForma() {
    return nedolisovaneForma;
  }
  public void setNedolisovaneForma(int nedolisovaneForma) {
    this.nedolisovaneForma = nedolisovaneForma;
  }
  public int getNedolisovanePeriferie() {
    return nedolisovanePeriferie;
  }
  public void setNedolisovanePeriferie(int nedolisovanePeriferie) {
    this.nedolisovanePeriferie = nedolisovanePeriferie;
  }
    
}
