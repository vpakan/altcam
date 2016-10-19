package sk.altcam.assembly.report;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ReportControl extends Composite{
  
  private static int MIN_TEXT_WIDTH = 120;
  private static int MIN_GROUP_WIDTH = 500;
    
  private Text fromAssemblyDate = null;
  private Text toAssemblyDate = null;
  private Text orderNumber = null;
  private Button assemblyOperatorReport = null;
  private Button assemblyItemReport = null;
  private Button assemblyAllReport = null;
  private Text fromMonitoringDate = null;
  private Text toMonitoringDate = null;
  private Text fromMachineDate = null;
  private Text toMachineDate = null;
  private Text itemNumber = null;
  private Button orderReport = null;
  private Button monitoringReport = null;
  private Button machineReport = null;
  
  public ReportControl(Composite parent, int style){
    super(parent,style);
    this.setLayout(new GridLayout(2,false));
    // Broken pieces
    Group grMonitoringReport = new Group (this, SWT.BORDER);
    GridData gdGrMonitoringReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
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
    GridData gdMonitoringReportButton = new GridData(GridData.FILL_HORIZONTAL);
    gdMonitoringReportButton.horizontalSpan = 2;
    gdMonitoringReportButton.widthHint = 200;
    monitoringReport = new Button(grMonitoringReport, SWT.PUSH | SWT.CENTER);
    monitoringReport.setText(" Generuj report ");
    monitoringReport.setLayoutData(gdMonitoringReportButton);
    // Operators effectivity
    Group grAssemblyReport = new Group (this, SWT.BORDER);
    GridData gdGrAssemblyReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    grAssemblyReport.setText(" Efektivita operatorov: ");
    grAssemblyReport.setLayout(new GridLayout(2,false));
    grAssemblyReport.setLayoutData(gdGrAssemblyReport);
    label = new Label(grAssemblyReport, SWT.NONE);
    label.setText("Od:");
    fromAssemblyDate = new Text(grAssemblyReport, SWT.BORDER);
    label = new Label(grAssemblyReport, SWT.NONE);
    label.setText("Do:");
    toAssemblyDate = new Text(grAssemblyReport, SWT.BORDER);
    assemblyOperatorReport = new Button(grAssemblyReport, SWT.PUSH | SWT.CENTER);
    assemblyOperatorReport.setText(" Report za operatora ");
    assemblyItemReport = new Button(grAssemblyReport, SWT.PUSH | SWT.CENTER);
    assemblyItemReport.setText(" Report za vyrobok ");
    assemblyAllReport = new Button(grAssemblyReport, SWT.PUSH | SWT.CENTER);
    assemblyAllReport.setText(" Sumarny report ");
    assemblyOperatorReport.setLayoutData(gdMonitoringReportButton);
    assemblyItemReport.setLayoutData(gdMonitoringReportButton);
    assemblyAllReport.setLayoutData(gdMonitoringReportButton);
    // Currently processed pieces
    Group grOrderReport = new Group (this, SWT.BORDER);
    GridData gdGrOrderReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    grOrderReport.setText(" Priebezne vyrobene kusy: ");
    grOrderReport.setLayout(new GridLayout(2,false));
    grOrderReport.setLayoutData(gdGrOrderReport);
    orderReport = new Button(grOrderReport, SWT.PUSH | SWT.CENTER);
    orderReport.setText(" Generuj report ");
    orderReport.setLayoutData(gdMonitoringReportButton);
    new Label(this, SWT.NONE);
    // Machines effectivity
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
  public Text getOrderNumber() {
    return orderNumber;
  }
  public void setOrderNumber(Text itemNumber) {
    this.orderNumber = itemNumber;
  }
  public Button getAssemblyOperatorReport() {
    return assemblyOperatorReport;
  }
  public Button getAssemblyItemReport() {
    return assemblyItemReport;
  }
  public Button getAssemblyAllReport() {
    return assemblyAllReport;
  }
  public Text getFromAssemblyDate() {
    return fromAssemblyDate;
  }
  public void setFromAssemblyDate(Text fromMachineDate) {
    this.fromAssemblyDate = fromMachineDate;
  }
  public Text getToAssemblyDate() {
    return toAssemblyDate;
  }
  public void setToAssemblyDate(Text toMachineDate) {
    this.toAssemblyDate = toMachineDate;
  }
  
  public Text getItemNumber() {
    return itemNumber;
  }
  public void setItemNumber(Text itemNumber) {
    this.itemNumber = itemNumber;
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

}
