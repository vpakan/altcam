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
    
  private Text fromAssemblyDate = null;
  private Text toAssemblyDate = null;
  private Text orderNumber = null;
  private Button assemblyOperatorReport = null;
  private Button assemblyItemReport = null;
  private Button assemblyAllReport = null;
  
  public ReportControl(Composite parent, int style){
    super(parent,style);
    this.setLayout(new GridLayout(1,false));
    Group grAssemblyReport = new Group (this, SWT.BORDER);
    GridData gdGrAssemblyReport = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    grAssemblyReport.setText(" Efektivita operatorov: ");
    grAssemblyReport.setLayout(new GridLayout(2,false));
    grAssemblyReport.setLayoutData(gdGrAssemblyReport);
    Label label = new Label(grAssemblyReport, SWT.NONE);
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
    GridData gdMonitoringReportButton = new GridData(GridData.FILL_HORIZONTAL);
    gdMonitoringReportButton.horizontalSpan = 2;
    assemblyOperatorReport.setLayoutData(gdMonitoringReportButton);
    assemblyItemReport.setLayoutData(gdMonitoringReportButton);
    assemblyAllReport.setLayoutData(gdMonitoringReportButton);
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
  
}
