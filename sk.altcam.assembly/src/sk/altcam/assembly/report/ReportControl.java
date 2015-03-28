package sk.altcam.assembly.report;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import sk.altcam.assembly.ProductionMonitoring;
import sk.altcam.assembly.monitoring.MonitoringTable;
import sk.altcam.properties.MonitoringAssemblyProperties;

public class ReportControl extends Composite{
  private static int MIN_TEXT_WIDTH = 120;
  private static int MIN_GROUP_WIDTH = 500;
  
  private Text fromMonitoringDate = null;
  private Text toMonitoringDate = null;
  private Text fromMachineDate = null;
  private Text toMachineDate = null;
  private Text itemNumber = null;
  private Button orderReport = null;
  private Button monitoringReport = null;
  private Button machineReport = null;
  private Button[] monitoringReportVisibleColumns = null;

  private ProductionMonitoring productionMonitoring;
  
  public ReportControl(Composite parent, int style , ProductionMonitoring productionMonitoring){
    super(parent,style);
    this.productionMonitoring = productionMonitoring;
    this.setLayout(new GridLayout(2,false));
    Group grMonitoringReport = new Group (this, SWT.BORDER);
    GridData gdGrMonitoringReport = new GridData(GridData.FILL_BOTH);
    gdGrMonitoringReport.widthHint = ReportControl.MIN_GROUP_WIDTH;
    gdGrMonitoringReport.verticalSpan = 2;
    grMonitoringReport.setText(" Kazovost vyrobku: ");
    grMonitoringReport.setLayout(new GridLayout(2,false));
    grMonitoringReport.setLayoutData(gdGrMonitoringReport);
    Label label = new Label(grMonitoringReport, SWT.NONE);
    label.setText("Cislo vyrobku:");
    itemNumber = new Text(grMonitoringReport, SWT.BORDER);
    GridData gdItemNumber = new GridData();
    gdItemNumber.widthHint = ReportControl.MIN_TEXT_WIDTH;
    itemNumber.setLayoutData(gdItemNumber);
    label = new Label(grMonitoringReport, SWT.NONE);
    label.setText("Od:");
    fromMonitoringDate = new Text(grMonitoringReport, SWT.BORDER);
    label = new Label(grMonitoringReport, SWT.NONE);
    label.setText("Do:");
    toMonitoringDate = new Text(grMonitoringReport, SWT.BORDER);
    Group grMonitoringReportVisibleColumns = new Group (grMonitoringReport, SWT.BORDER);
    GridData gdGrMonitoringReportVisibleColumns = new GridData(GridData.FILL_BOTH);
    gdGrMonitoringReportVisibleColumns.horizontalSpan = 2;
    grMonitoringReportVisibleColumns.setText(" Zobrazit stlpce ");
    grMonitoringReportVisibleColumns.setLayout(new GridLayout(2,false));
    grMonitoringReportVisibleColumns.setLayoutData(gdGrMonitoringReportVisibleColumns);
    monitoringReportVisibleColumns = new Button[MonitoringTable.BROKEN_COLUMN_LABELS.length];
    for (int index = 0 ; index < MonitoringTable.BROKEN_COLUMN_LABELS.length ; index ++){
      monitoringReportVisibleColumns[index] = new Button (grMonitoringReportVisibleColumns , SWT.CHECK);
      monitoringReportVisibleColumns[index].setText(MonitoringTable.BROKEN_COLUMN_LABELS[index] +
          " - " +
          MonitoringTable.BROKEN_COLUMN_DESCS[index]);
      monitoringReportVisibleColumns[index].setData(MonitoringTable.BROKEN_COLUMN_LABELS[index]);
      
    }
    monitoringReport = new Button(grMonitoringReport, SWT.PUSH | SWT.CENTER);
    monitoringReport.setText(" Generuj report ");
    Group grOrderReport = new Group (this, SWT.BORDER);
    GridData gdGrOrderReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    grOrderReport.setText(" Priebezne vyrobene kusy: ");
    grOrderReport.setLayout(new GridLayout(2,false));
    grOrderReport.setLayoutData(gdGrOrderReport);
    GridData gdMonitoringReportButton = new GridData(GridData.FILL_HORIZONTAL);
    gdMonitoringReportButton.widthHint = ReportControl.MIN_TEXT_WIDTH;
    gdMonitoringReportButton.horizontalSpan = 2;
    orderReport = new Button(grOrderReport, SWT.PUSH | SWT.CENTER);
    orderReport.setText(" Generuj report ");
    orderReport.setLayoutData(gdMonitoringReportButton);
    Group grMachineReport = new Group (this, SWT.BORDER);
    GridData gdGrMachineReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    grMachineReport.setText(" Efektivita strojov: ");
    grMachineReport.setLayout(new GridLayout(2,false));
    grMachineReport.setLayoutData(gdGrMachineReport);
    label = new Label(grMachineReport, SWT.NONE);
    label.setText("Od:");
    fromMachineDate = new Text(grMachineReport, SWT.BORDER);
    label = new Label(grMachineReport, SWT.NONE);
    label.setText("Do:");
    toMachineDate = new Text(grMachineReport, SWT.BORDER);
    machineReport = new Button(grMachineReport, SWT.PUSH | SWT.CENTER);
    machineReport.setText(" Generuj report ");
    machineReport.setLayoutData(gdMonitoringReportButton);
    monitoringReport.setLayoutData(gdMonitoringReportButton);

  }
  public Text getFromMonitoringDate() {
    return fromMonitoringDate;
  }
  public void setFromMonitoringDate(Text fromMonitoringDate) {
    this.fromMonitoringDate = fromMonitoringDate;
  }
  public Text getToMonitoringDate() {
    return toMonitoringDate;
  }
  public void setToMonitoringDate(Text toMonitoringDate) {
    this.toMonitoringDate = toMonitoringDate;
  }
  public Text getItemNumber() {
    return itemNumber;
  }
  public void setItemNumber(Text itemNumber) {
    this.itemNumber = itemNumber;
  }
  public Button getOrderReport() {
    return orderReport;
  }
  public void setOrderReport(Button orderReport) {
    this.orderReport = orderReport;
  }
  public Button getMonitoringReport() {
    return monitoringReport;
  }
  public void setMonitoringReport(Button monitoringReport) {
    this.monitoringReport = monitoringReport;
  }
  
  public Button getMachineReport() {
    return machineReport;
  }
  public void setMachineReport(Button machineReport) {
    this.machineReport = machineReport;
  }
  public Text getFromMachineDate() {
    return fromMachineDate;
  }
  public void setFromMachineDate(Text fromMachineDate) {
    this.fromMachineDate = fromMachineDate;
  }
  public Text getToMachineDate() {
    return toMachineDate;
  }
  public void setToMachineDate(Text toMachineDate) {
    this.toMachineDate = toMachineDate;
  }
  public void initialize(){
    
    for (int index = 0 ; index < monitoringReportVisibleColumns.length ; index++){
      if (productionMonitoring.getMonitoringProductionProperties()
          .getProperty(MonitoringAssemblyProperties.VISIBLE_COLUMNS_PROPERTY_PREFIX 
             + monitoringReportVisibleColumns[index].getData())
          .equals("false")){
       monitoringReportVisibleColumns[index].setSelection(false);
     }
     else{
       monitoringReportVisibleColumns[index].setSelection(true);  
     }
    }
    
  }
  
  public Button[] getMonitoringReportVisibleColumns() {
    return monitoringReportVisibleColumns;
  }
  
}
