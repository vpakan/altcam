package sk.altcam.production.report;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import sk.altcam.production.MessageBox;
import sk.altcam.production.ProductionMonitoring;
import sk.altcam.production.entity.Monitoring;
import sk.altcam.production.entity.Order;
import sk.altcam.production.monitoring.MonitoringTable;
import sk.altcam.production.order.OrderTableModel;
import sk.altcam.properties.MonitoringProductionProperties;

public class ReportModel {
  private ReportControl reportControl = null;
  private OrderTableModel orderTableModel = null;
  private ProductionMonitoring productionMonitoring = null;
  
  private class ItemReportData{
    public ItemReportData(int produced, int nabehove,
        int prestreky, int nedostreky, int zhorene, int vadaMaterialu,
        int deformacie, int poskodenyZastrekZalis,
        int chybajuciZastrek, int bodVstreku,
        int pritomnostPruznychElementov, int vyhadzane, int prestrekyVOtvoroch,
        int farba, int zastavenieStroja, int ine,
        int nedolisovaneStroj, int nedolisovaneForma, int nedolisovanePeriferie) {

      this.produced = produced;
      this.nabehove = nabehove;
      this.prestreky = prestreky;
      this.nedostreky = nedostreky;
      this.zhorene = zhorene;
      this.vadaMaterialu = vadaMaterialu;
      this.deformacie = deformacie;
      this.poskodenyZastrekZalis = poskodenyZastrekZalis;
      this.chybajuciZastrek = chybajuciZastrek;
      this.bodVstreku = bodVstreku;
      this.pritomnostPruznychElementov = pritomnostPruznychElementov;
      this.vyhadzane = vyhadzane;
      this.prestrekyVOtvoroch = prestrekyVOtvoroch;
      this.farba = farba;
      this.zastavenieStroja = zastavenieStroja;
      this.ine = ine;
      this.nedolisovaneStroj = nedolisovaneStroj;
      this.nedolisovaneForma = nedolisovaneForma;
      this.nedolisovanePeriferie = nedolisovanePeriferie;
      
    }
    @Override
    public String toString() {
      return "ItemReportData [bodVstreku=" + bodVstreku 
          + ", farba=" + farba + ", ine=" + ine + ", nabehove="
          + nabehove + ", nedolisovanePoJadrach=" + chybajuciZastrek
          + ", nedostreknuteOkoloZastreku=" + deformacie
          + ", otrepy=" + nedostreky + ", poskodenyZastrekZalis="
          + poskodenyZastrekZalis + ", prestreky=" + prestreky
          + ", prestrekyVOtvoroch=" + prestrekyVOtvoroch
          + ", pritomnostPruznychElementov=" + pritomnostPruznychElementov
          + ", produced=" + produced + ", vadaMaterialu=" + vadaMaterialu
          + ", vyhadzane=" + vyhadzane + ", zastavenieStroja="
          + zastavenieStroja + ", zhorene=" + zhorene
          + ", nedolisovaneStroj" + nedolisovaneStroj 
          + ", nedolisovaneForma" + nedolisovaneForma
          + ", nedolisovanePeriferie" + nedolisovanePeriferie+ "]";
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + bodVstreku;
      result = prime * result + farba;
      result = prime * result + ine;
      result = prime * result + nabehove;
      result = prime * result + chybajuciZastrek;
      result = prime * result + deformacie;
      result = prime * result + nedostreky;
      result = prime * result + poskodenyZastrekZalis;
      result = prime * result + prestreky;
      result = prime * result + prestrekyVOtvoroch;
      result = prime * result + pritomnostPruznychElementov;
      result = prime * result + produced;
      result = prime * result + vadaMaterialu;
      result = prime * result + vyhadzane;
      result = prime * result + zastavenieStroja;
      result = prime * result + zhorene;
      result = prime * result + nedolisovaneStroj;
      result = prime * result + nedolisovaneForma;
      result = prime * result + nedolisovanePeriferie;
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
      ItemReportData other = (ItemReportData) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (bodVstreku != other.bodVstreku)
        return false;
      if (farba != other.farba)
        return false;
      if (ine != other.ine)
        return false;
      if (nabehove != other.nabehove)
        return false;
      if (chybajuciZastrek != other.chybajuciZastrek)
        return false;
      if (deformacie != other.deformacie)
        return false;
      if (nedostreky != other.nedostreky)
        return false;
      if (poskodenyZastrekZalis != other.poskodenyZastrekZalis)
        return false;
      if (prestreky != other.prestreky)
        return false;
      if (prestrekyVOtvoroch != other.prestrekyVOtvoroch)
        return false;
      if (pritomnostPruznychElementov != other.pritomnostPruznychElementov)
        return false;
      if (produced != other.produced)
        return false;
      if (vadaMaterialu != other.vadaMaterialu)
        return false;
      if (vyhadzane != other.vyhadzane)
        return false;
      if (zastavenieStroja != other.zastavenieStroja)
        return false;
      if (zhorene != other.zhorene)
        return false;
      if (zhorene != other.nedolisovaneStroj)
        return false;
      if (zhorene != other.nedolisovaneForma)
        return false;
      if (zhorene != other.nedolisovanePeriferie)
        return false;
      return true;
    }
    
    public int produced = 0;
    public int nabehove;
    public int prestreky;
    public int nedostreky;
    public int zhorene;
    public int vadaMaterialu;
    public int deformacie;
    public int poskodenyZastrekZalis;
    public int chybajuciZastrek;
    public int bodVstreku;
    public int pritomnostPruznychElementov;
    public int vyhadzane;
    public int prestrekyVOtvoroch;
    public int farba;
    public int zastavenieStroja;
    public int ine;
    public int nedolisovaneStroj;
    public int nedolisovaneForma;
    public int nedolisovanePeriferie;
    private ReportModel getOuterType() {
      return ReportModel.this;
    }
  }
  private class MachineReportData{
    public MachineReportData() {
      this.accumulatedTheoreticalProduced = 0;
      this.accumulatedProduced = 0;
    }
    @Override
    public String toString() {
      return "MachineReportData [accumulatedTheoreticalProduced=" + accumulatedTheoreticalProduced 
          + ", accumulatedProduced=" + accumulatedProduced + "]";
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + new Double(accumulatedTheoreticalProduced).hashCode();
      result = prime * result + accumulatedProduced;
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
      MachineReportData other = (MachineReportData) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (accumulatedTheoreticalProduced != other.accumulatedTheoreticalProduced)
        return false;
      if (accumulatedProduced != other.accumulatedProduced)
        return false;
      return true;
    }
    
    public double accumulatedTheoreticalProduced = 0.0D;
    public int accumulatedProduced = 0;
    private ReportModel getOuterType() {
      return ReportModel.this;
    }
  }
  public ReportModel (ReportControl reportControl,OrderTableModel orderTableModel, ProductionMonitoring productionMonitoring){
    this.reportControl = reportControl;
    this.orderTableModel = orderTableModel;
    this.productionMonitoring = productionMonitoring;
    initializeReports();
  }
  private void initializeReports(){
    Date today = new Date();
    String fromDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.format(ProductionMonitoring.getFirstDayOfMonth(today));
    String toDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.format(ProductionMonitoring.getLastDayOfMonth(today));
    
    reportControl.getFromMonitoringDate().setText(fromDate);
    reportControl.getToMonitoringDate().setText(toDate);
    reportControl.getFromMachineDate().setText(fromDate);
    reportControl.getToMachineDate().setText(toDate);
    
    reportControl.getMonitoringReport().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        runMonitoringReport();
      }
    });

    reportControl.getOrderReport().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        runOrderReport();
      }
    });    
    
    reportControl.getMachineReport().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        runMachineReport();
      }
    });    
    
    reportControl.initialize();
    
  }
  
  private void runOrderReport (){
    String reportFileLocation = getReportFileLocation("orderrep.txt");
    if (reportFileLocation != null){
      ReportFormatter rf = new ReportFormatter(new int[]{4,16,10,8,8,8,8}, 
          new int[]{ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT},
          new boolean[]{true,true,true,true,true,true,true});
      rf.addText("Datum vytvorenia reportu: " + ProductionMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
      rf.addNewLine();
      rf.addNewLine();
      rf.addCenteredLine("Rozpracovane objednavky");
      rf.addNewLine();
      rf.addColumnText("C.o.");
      rf.addColumnText("Cislo vyr.");
      rf.addColumnText("Datum o.");
      rf.addColumnText("Planov.");
      rf.addColumnText("Hotove");
      rf.addColumnText("Dorobit");
      rf.addColumnText("Hotove %");
      rf.addNewLine();
      for (Order order : orderTableModel.getData()){
        List<Monitoring> monitorings = order.getMonitoring();
        int produced = 0;
        if (monitorings != null){
          for (Monitoring monitoring : monitorings){
            produced += monitoring.getProduced();
          }
        }  
        if (produced < order.getNumItemsPlanned()) {
          rf.addNewLine();
          rf.addColumnText(order.getOrderNumber());
          rf.addColumnText(order.getItemNumber());
          rf.addColumnText(order.getDate() == null ? ""
              : ProductionMonitoring.DISPLAY_DATE_FORMAT
                  .format(order.getDate()));
          rf.addColumnText(String.valueOf(order.getNumItemsPlanned()));
          rf.addColumnText(String.valueOf(produced));
          int missing = order.getNumItemsPlanned() - produced;
          rf.addColumnText(String.valueOf(missing));
          rf.addColumnText(ProductionMonitoring.DECIMAL_FORMAT
              .format(((float) produced / order.getNumItemsPlanned()) * 100));
        }
      }
      writeToFile(reportFileLocation, rf.getReportText());
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ICON_INFORMATION, "Info",
        "Report bol vygenerovany do suboru: " + reportFileLocation);
    }
  }
  
  private void runMonitoringReport (){
    String reportFileLocation = getReportFileLocation("prodrep.txt");
    if (reportFileLocation != null){
      saveProductionMonitoringReportProps();
      String fromDate = reportControl.getFromMonitoringDate().getText();
      String toDate = reportControl.getToMonitoringDate().getText();
      String reportItemNumber = reportControl.getItemNumber().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }
      ReportFormatter rf = new ReportFormatter(new int[]{16,8,8,
          8,8,8,8,8,8,8,8,8,8,
          8,8,8,8,8,8,8,8,8,8,
          8,8,8,8,8,8,8,8,8,8,
          8,8,8,8,8,8,8,8,8,8}, 
          new int[]{ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT},
          getMonitoringReportVisibleColumns(43));
      rf.addText("Od: " + ProductionMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate) +
        " Do: " + ProductionMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate) +
        " Datum vytvorenia reportu: " + ProductionMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
      rf.addNewLine();
      rf.addNewLine();
      rf.addCenteredLine("Kazovost vyrobku za obdobie");
      rf.addNewLine();
      rf.addColumnText("Cislo vyr.");
      rf.addColumnText("Vyrobene");
      for (int index = 0 ; index < MonitoringTable.BROKEN_COLUMN_LABELS.length ; index++){
        rf.addColumnText(MonitoringTable.BROKEN_COLUMN_LABELS[index]);
        if (index > 0) {
          rf.addColumnText("ppm");
        }
      }
      rf.addColumnText("Zma.-ppm");
      rf.addColumnText("ppm");
      rf.addColumnText("Zmatky");
      rf.addColumnText("ppm");
      rf.addColumnText("Sp.-ppm");
      rf.addColumnText("Spolu");
      rf.addNewLine();
      HashMap<String, ItemReportData> itemsData = new HashMap<String, ItemReportData>(); 
      for (Order order : orderTableModel.getData()){
        if (reportItemNumber.length() == 0 || 
            reportItemNumber.equals("*") ||
            order.getItemNumber().equals(reportItemNumber)){
          ItemReportData itemReportData = null;
          if (itemsData.containsKey(order.getItemNumber())){
            itemReportData = itemsData.get(order.getItemNumber());
          }
          else{
            itemReportData = new ItemReportData(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
            itemsData.put(order.getItemNumber(), itemReportData);
          }
          List<Monitoring> monitorings = order.getMonitoring();
          if (monitorings != null){
            for (Monitoring monitoring : monitorings){
              if (ProductionMonitoring.isDateWithinPeriod(monitoring.getDate(),
                  reportFromDate, reportToDate)) {
                itemReportData.produced += monitoring.getProduced();
                itemReportData.nabehove += monitoring.getNabehove();
                itemReportData.prestreky += monitoring.getPrestreky();
                itemReportData.nedostreky += monitoring.getNedostreky();
                itemReportData.zhorene += monitoring.getZhorene();
                itemReportData.vadaMaterialu += monitoring.getVadaMaterialu();
                itemReportData.deformacie += monitoring.getDeformacie();
                itemReportData.poskodenyZastrekZalis += monitoring.getPoskodenyZastrekZalis();
                itemReportData.chybajuciZastrek += monitoring.getChybajuciZastrek();
                itemReportData.bodVstreku += monitoring.getBodVstreku();
                itemReportData.pritomnostPruznychElementov += monitoring.getPritomnostPruznychElementov();
                itemReportData.vyhadzane += monitoring.getVyhadzane();
                itemReportData.prestrekyVOtvoroch += monitoring.getPrestrekyVOtvoroch();
                itemReportData.farba += monitoring.getFarba();
                itemReportData.zastavenieStroja += monitoring.getZastavenieStroja();
                itemReportData.ine += monitoring.getIne();
                itemReportData.nedolisovaneStroj += monitoring.getNedolisovaneStroj();
                itemReportData.nedolisovaneForma += monitoring.getNedolisovaneForma();
                itemReportData.nedolisovanePeriferie += monitoring.getNedolisovanePeriferie();
           
              }
            }
          }
        }
      }
      int produced = 0;
      int nabehove = 0;
      int prestreky = 0;
      int otrepy = 0;
      int zhorene = 0;
      int vadaMaterialu = 0;
      int nedostreknuteOkoloZastreku = 0;
      int poskodenyZastrekZalis = 0;
      int nedolisovanePoJadrach = 0;
      int bodVstreku = 0;
      int pritomnostPruznychElementov = 0;
      int vyhadzane = 0;
      int prestrekyVOtvoroch = 0;
      int farba = 0;
      int zastavenieStroja = 0;
      int ine = 0;     
      int nedolisovaneStroj = 0;
      int nedolisovaneForma = 0;
      int nedolisovanePeriferie = 0;
      for (String itemNumber : itemsData.keySet()){
        ItemReportData itemReportData = itemsData.get(itemNumber);
        produced += itemReportData.produced;
        nabehove += itemReportData.nabehove;
        prestreky += itemReportData.prestreky;
        otrepy += itemReportData.nedostreky;
        zhorene += itemReportData.zhorene;
        vadaMaterialu += itemReportData.vadaMaterialu;
        nedostreknuteOkoloZastreku += itemReportData.deformacie;
        poskodenyZastrekZalis += itemReportData.poskodenyZastrekZalis;
        nedolisovanePoJadrach += itemReportData.chybajuciZastrek;
        bodVstreku += itemReportData.bodVstreku;
        pritomnostPruznychElementov += itemReportData.pritomnostPruznychElementov;
        vyhadzane += itemReportData.vyhadzane;
        prestrekyVOtvoroch += itemReportData.prestrekyVOtvoroch;
        farba += itemReportData.farba;
        zastavenieStroja += itemReportData.zastavenieStroja;
        ine += itemReportData.ine;
        nedolisovaneStroj += itemReportData.nedolisovaneStroj;
        nedolisovaneForma += itemReportData.nedolisovaneForma;
        nedolisovanePeriferie += itemReportData.nedolisovanePeriferie;
 
        int sumPpmBroken = itemReportData.prestreky + itemReportData.nedostreky +
          itemReportData.zhorene + itemReportData.vadaMaterialu +
          itemReportData.deformacie + itemReportData.poskodenyZastrekZalis +
          itemReportData.chybajuciZastrek + itemReportData.bodVstreku +
          itemReportData.pritomnostPruznychElementov + itemReportData.vyhadzane +
          itemReportData.prestrekyVOtvoroch + itemReportData.farba +
          itemReportData.zastavenieStroja + itemReportData.ine +
          itemReportData.nedolisovaneStroj + itemReportData.nedolisovaneForma + 
          itemReportData.nedolisovanePeriferie;
        int sumBroken = itemReportData.nabehove + sumPpmBroken;
        int totalForPpm = sumPpmBroken + itemReportData.produced;
        int total = itemReportData.produced + sumBroken;
        rf.addNewLine();
        rf.addColumnText(itemNumber);
        rf.addColumnText(String.valueOf(itemReportData.produced));
        rf.addColumnText(String.valueOf(itemReportData.nabehove));
        rf.addColumnText(String.valueOf(itemReportData.prestreky));
        long value = getPpm(itemReportData.prestreky,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.nedostreky));
        value = getPpm(itemReportData.nedostreky,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.zhorene));
        value = getPpm(itemReportData.zhorene,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.vadaMaterialu));
        value = getPpm(itemReportData.vadaMaterialu,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.deformacie));
        value = getPpm(itemReportData.deformacie,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.poskodenyZastrekZalis));
        value = getPpm(itemReportData.poskodenyZastrekZalis,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.chybajuciZastrek));
        value = getPpm(itemReportData.chybajuciZastrek,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.bodVstreku));
        value = getPpm(itemReportData.bodVstreku,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.pritomnostPruznychElementov));
        value = getPpm(itemReportData.pritomnostPruznychElementov,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.vyhadzane));
        value = getPpm(itemReportData.vyhadzane,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.prestrekyVOtvoroch));
        value = getPpm(itemReportData.prestrekyVOtvoroch,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.farba));
        value = getPpm(itemReportData.farba,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.zastavenieStroja));
        value = getPpm(itemReportData.zastavenieStroja,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.ine));
        value = getPpm(itemReportData.ine,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.nedolisovaneStroj));
        value = getPpm(itemReportData.nedolisovaneStroj,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.nedolisovaneForma));
        value = getPpm(itemReportData.nedolisovaneForma,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.nedolisovanePeriferie));
        value = getPpm(itemReportData.nedolisovanePeriferie,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(sumPpmBroken));
        value = getPpm(sumPpmBroken,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(sumBroken));
        value = getPpm(sumBroken,total);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(totalForPpm));
        rf.addColumnText(String.valueOf(total));
      }
      int sumPpmBroken = prestreky + otrepy +
        zhorene + vadaMaterialu +
        nedostreknuteOkoloZastreku + poskodenyZastrekZalis +
        nedolisovanePoJadrach + bodVstreku +
        pritomnostPruznychElementov + vyhadzane +
        prestrekyVOtvoroch + farba +
        zastavenieStroja + ine +
        nedolisovaneStroj + nedolisovaneForma + nedolisovanePeriferie;
      int sumBroken = sumPpmBroken + nabehove;
      int totalForPpm = produced + sumPpmBroken;
      int total = produced + sumBroken;
      rf.addNewLine();
      rf.addNewLine();
      rf.addColumnText("Spolu");
      rf.addColumnText(String.valueOf(produced));
      rf.addColumnText(String.valueOf(nabehove));
      rf.addColumnText(String.valueOf(prestreky));
      long value = getPpm(prestreky,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(otrepy));
      value = getPpm(otrepy,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(zhorene));
      value = getPpm(zhorene,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(vadaMaterialu));
      value = getPpm(vadaMaterialu,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(nedostreknuteOkoloZastreku));
      value = getPpm(nedostreknuteOkoloZastreku,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(poskodenyZastrekZalis));
      value = getPpm(poskodenyZastrekZalis,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(nedolisovanePoJadrach));
      value = getPpm(nedolisovanePoJadrach,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(bodVstreku));
      value = getPpm(bodVstreku,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(pritomnostPruznychElementov));
      value = getPpm(pritomnostPruznychElementov,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(vyhadzane));
      value = getPpm(vyhadzane,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(prestrekyVOtvoroch));
      value = getPpm(prestrekyVOtvoroch,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(farba));
      value = getPpm(farba,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(zastavenieStroja));
      value = getPpm(zastavenieStroja,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(ine));
      value = getPpm(ine,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(nedolisovaneStroj));
      value = getPpm(nedolisovaneStroj,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(nedolisovaneForma));
      value = getPpm(nedolisovaneForma,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(nedolisovanePeriferie));
      value = getPpm(nedolisovanePeriferie,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(sumPpmBroken));
      value = getPpm(sumPpmBroken,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(sumBroken));
      value = getPpm(sumBroken,total);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(totalForPpm));
      rf.addColumnText(String.valueOf(total));
      // legend
      rf.addNewLine();
      rf.addNewLine();
      rf.addText("Legenda:");
      rf.addNewLine();
      rf.addNewLine();
      rf.addText("Cislo vyr. - Cislo vyrobku");
      rf.addNewLine();
      rf.addText("Vyrobene - pocet vyrobenych nekazovych kusov");
      rf.addNewLine();
      
      for (int index = 0 ; index < MonitoringTable.BROKEN_COLUMN_LABELS.length ; index++){
        if (!productionMonitoring.getMonitoringProductionProperties()
            .getProperty(MonitoringProductionProperties.VISIBLE_COLUMNS_PROPERTY_PREFIX 
              + MonitoringTable.BROKEN_COLUMN_LABELS[index])
            .equals("false")){
          rf.addText(MonitoringTable.BROKEN_COLUMN_LABELS[index] + " - " +
              MonitoringTable.BROKEN_COLUMN_DESCS[index]);
          rf.addNewLine();
        }
      }
      rf.addText("Zma.-ppm - suma zmatkov pre vypocet ppm");
      rf.addNewLine();
      rf.addText("Zmatky - suma vsetkych zmatkov aj s nabehovymi kusmi");
      rf.addNewLine();
      rf.addText("Sp.-ppm - suma zmatkov pre vypocet ppm a vyrobenych nekazovych kusov");
      rf.addNewLine();
      rf.addText("Spolu - suma vyrobenych nekazovych kusov a vsetkych zmatkov");
      writeToFile(reportFileLocation, rf.getReportText());
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ICON_INFORMATION, "Info",
        "Report bol vygenerovany do suboru: " + reportFileLocation);
    }
  }
  
  private String getReportFileLocation(String fileName){
    FileDialog fileDialog = new FileDialog(reportControl.getShell(), SWT.SAVE);   
    fileDialog.setText("Zadajte umiestnenie vystupneho suboru");
    fileDialog.setFileName(ProductionMonitoring.REPORT_LOCATION +
      File.separator +
      fileName);
    String[] filterExt = { "*.txt" };
    fileDialog.setFilterExtensions(filterExt);
    String selected = fileDialog.open();
    if (selected != null && !selected.endsWith(".txt")){
      selected = selected + ".txt";
    }
    return selected;
  }
  
  private void writeToFile(String fileName, String text) {
    try {
      FileOutputStream output = new FileOutputStream(fileName);
      PrintStream file = new PrintStream(output);
      file.print(text);
      file.flush();
      file.close();
      output.flush();
      output.close();
    } catch (Exception e) {
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ERROR, "Chyba!",
        "Nepodarilo sa zapisat vystupny subor.\n" +
        "Popis chyby: " + e);
    }
  }
  
  private long getPpm (int numItem , int total){
    return Math.round(((double)numItem/total)*1000000);
  }
  
  private boolean[] getMonitoringReportVisibleColumns( int numOfColumns){
    boolean[] result = new boolean[numOfColumns];
    for (int index = 0 ; index < result.length ; index++){
      result[index] = true;
    }
    int brokenColumnIndex = 0;
    int index = 2;
    while (index < result.length - 6){
      if (productionMonitoring.getMonitoringProductionProperties()
            .getProperty(MonitoringProductionProperties.VISIBLE_COLUMNS_PROPERTY_PREFIX 
              + MonitoringTable.BROKEN_COLUMN_LABELS[brokenColumnIndex])
            .equals("false")){
        result[index] = false;
        // nabehove doesn't have ppm
        if (index > 2){
          result[index + 1] = false;
        }
      }
      if (index > 2) {
        index+=2;
      }
      else{
        index++;
      }
      brokenColumnIndex++;
    }
    return result;
  }
  
  private void saveProductionMonitoringReportProps(){
    Button[] monitoringReportColumns = reportControl.getMonitoringReportVisibleColumns();
    for (Button button : monitoringReportColumns){
      productionMonitoring.getMonitoringProductionProperties()
        .setProperty(MonitoringProductionProperties.VISIBLE_COLUMNS_PROPERTY_PREFIX 
          + button.getData(),
        button.getSelection() ? "true" : "false");
    }
    productionMonitoring.getMonitoringProductionProperties().saveProperties();  
  }
  
  private void runMachineReport(){

    String reportFileLocation = getReportFileLocation("machinerep.txt");
    if (reportFileLocation != null){
      String fromDate = reportControl.getFromMachineDate().getText();
      String toDate = reportControl.getToMachineDate().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }
      ReportFormatter rf = new ReportFormatter(new int[]{20,20}, 
          new int[]{ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_RIGHT},
          new boolean[]{true,true});
      rf.addText("Od: " + ProductionMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate) +
        " Do: " + ProductionMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate) +
        " Datum vytvorenia reportu: " + ProductionMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
      rf.addNewLine();
      rf.addNewLine();
      rf.addCenteredLine("Efektivnost stroja za obdobie");
      rf.addNewLine();
      rf.addColumnText("ID stroja.");
      rf.addColumnText("Efektivita stroja %");
      rf.addNewLine();
      HashMap<String, MachineReportData> machinesData = new HashMap<String, MachineReportData>(); 
      for (Order order : orderTableModel.getData()){
          MachineReportData machineReportData = null;
          if (machinesData.containsKey(order.getMachineId())){
            machineReportData = machinesData.get(order.getMachineId());
          }
          else{
            machineReportData = new MachineReportData();
            machinesData.put(order.getMachineId(), machineReportData);
          }
          List<Monitoring> monitorings = order.getMonitoring();
          if (monitorings != null){
            for (Monitoring monitoring : monitorings){
              if (ProductionMonitoring.isDateWithinPeriod(monitoring.getDate(),
                  reportFromDate, reportToDate)) {
                machineReportData.accumulatedTheoreticalProduced += 
                    (60.0D * order.getNumActiveCavities() * (1440 + monitoring.getFormExchange() - monitoring.getPlannedIdleTime()))/order.getCycle();
                machineReportData.accumulatedProduced += monitoring.getProduced();
              }
            }
          }
      }
      
      LinkedList<String> machineIds = new LinkedList(machinesData.keySet());
      Collections.sort(machineIds, new Comparator<String>() {
        @Override
        public int compare(String machineId1, String machineId2) {
          return machineId1.compareTo(machineId2);
        }
      });
      for (String machineId : machineIds){
        rf.addNewLine();
        rf.addColumnText(machineId);
        MachineReportData machineRD = machinesData.get(machineId);
        rf.addColumnText(ProductionMonitoring.DECIMAL_FORMAT
          .format((100.0D*machineRD.accumulatedProduced)/machineRD.accumulatedTheoreticalProduced));
      }
      
      writeToFile(reportFileLocation, rf.getReportText());
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ICON_INFORMATION, "Info",
        "Report bol vygenerovany do suboru: " + reportFileLocation);
    }  
  }
}
