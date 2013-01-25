package sk.altcam.production;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import sk.altcam.production.monitoring.MonitoringTable;
import sk.altcam.production.monitoring.MonitoringTableModel;
import sk.altcam.production.order.OrderTable;
import sk.altcam.production.order.OrderTableModel;
import sk.altcam.production.report.ReportControl;
import sk.altcam.production.report.ReportModel;
import sk.altcam.properties.MonitoringProductionProperties;

public class ProductionMonitoring {
  public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final SimpleDateFormat STORAGE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss.S");
  public static final GregorianCalendar GC = new GregorianCalendar();
  public static final int BUTTONS_MIN_WIDTH = 80;
  public static String DATA_LOCATION = "/home/vpakan/tmp";
  public static String REPORT_LOCATION = null;
  public static String PROPERTIES_LOCATION = null;
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
  private MonitoringTable monitoringTable = null;
  private ReportControl reportControl;
  private OrderTable orderTable;
  private OrderTableModel orderTableModel;
  private MonitoringProductionProperties monitoringProductionProperties;
  
  public MonitoringProductionProperties getMonitoringProductionProperties() {
    return monitoringProductionProperties;
  }

  static {
    ProductionMonitoring.DECIMAL_FORMAT.setMinimumFractionDigits(6);
    ProductionMonitoring.DECIMAL_FORMAT.setMaximumFractionDigits(6);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    initializeDataLocation();
    new ProductionMonitoring().run();
  }

  private void run(){
    Shell shell = new Shell();
    shell.setText("Production Monitoring");

    // Set layout for shell
    FillLayout layout = new FillLayout();
    shell.setLayout(layout);
    Composite composite = new Composite(shell, SWT.NONE);
    createContent(composite);
    initialize();
    shell.addDisposeListener(new DisposeListener() {

      public void widgetDisposed(DisposeEvent e) {
        dispose();     
      }
      
    });

    // Ask the shell to display its content
    shell.open();
    Display display = shell.getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }
  
  private void dispose(){
    /*
    System.out.println("dispose");
    for (Monitoring monitoring : monitoringTableModel.getData()){
      System.out.println(monitoring);
    }
    for (Order order : orderTableModel.getData()){
      System.out.println(order);
    }
    */
  }
  
  private void createContent(Composite parent){
    parent.setLayout(new FillLayout());
    final TabFolder tabFolder = new TabFolder (parent, SWT.BORDER);
    TabItem monitoring = new TabItem (tabFolder, SWT.NONE);
    monitoring.setText ("Monitoring");
    monitoringTable = new MonitoringTable(tabFolder, SWT.NONE);
    monitoring.setControl(monitoringTable);
    TabItem orders = new TabItem (tabFolder, SWT.NONE);
    orders.setText ("Objednavky");
    orderTable = new OrderTable(tabFolder, SWT.NONE);
    orders.setControl(orderTable);
    TabItem reports = new TabItem (tabFolder, SWT.NONE);
    reports.setText ("Reporty");
    reportControl = new ReportControl(tabFolder, SWT.NONE , this);
    reports.setControl(reportControl);
  }
  
  private void initialize(){
    monitoringProductionProperties = new MonitoringProductionProperties();
    monitoringProductionProperties.loadProperties();
    // Initialize Orders
    orderTableModel = new OrderTableModel(orderTable);
    // Initialize Monitoring
    new MonitoringTableModel(monitoringTable,orderTableModel);
    // Initialize Reports
    new ReportModel(reportControl,orderTableModel, this);
  }
  
  public static int sortString(String input1 , String input2){
    if (input1 == null && input2 == null){
      return 0;
    } else if (input1 == null){
      return 1;
    } else if (input2 == null){
      return -1;
    } else{
      return input1.compareTo(input2);      
    }

  }
  
  public static int sortDate(Date input1 , Date input2){
    int result = 0;
    if (input1 == null && input2 == null){
      result =  0;
    } else if (input1 == null){
      result =  1;
    } else if (input2 == null){
      result =  -1;
    } else{
      if (input1.before(input2)) {
        result = -1;
      } else if (input1.after(input2)) {
        result = 1;
      }
    }
    return result;
  }
  
  public static int sortInt(int input1 , int input2){
    int result = input1 - input2;
    result = result < 0 ? -1 : (result > 0) ? 1 : 0;  
    return result;
  }
  public static Date getFirstDayOfMonth(Date date){
    GC.setTime(date);
    GC.set(Calendar.MILLISECOND, 0);
    GC.set(Calendar.SECOND, 0);
    GC.set(Calendar.MINUTE, 0);
    GC.set(Calendar.HOUR_OF_DAY, 0);
    GC.set(Calendar.DAY_OF_MONTH, 1);
    
    return GC.getTime();
    
  }
  
  public static Date getLastDayOfMonth(Date date){
    GC.setTime(date);
    GC.set(Calendar.MILLISECOND, 0);
    GC.set(Calendar.SECOND, 0);
    GC.set(Calendar.MINUTE, 0);
    GC.set(Calendar.HOUR_OF_DAY, 0);
    GC.set(Calendar.DAY_OF_MONTH, 1);
    GC.add(Calendar.MONTH, 1);
    GC.add(Calendar.MILLISECOND, -1);
    
    return GC.getTime();
    
  }
  
  public static Date resetTimeOfDate(Date date){
    
    Date result = null;
    
    if (date != null){
      GC.setTime(date);
      GC.set(Calendar.MILLISECOND, 0);
      GC.set(Calendar.SECOND, 0);
      GC.set(Calendar.MINUTE, 0);
      GC.set(Calendar.HOUR_OF_DAY, 0);
      result = GC.getTime();
    }
        
    return result;
    
  }
  
  private static void initializeDataLocation(){
    DATA_LOCATION = new File(ProductionMonitoring.class
        .getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .getPath())
      .getParent();
    // Add data subdirectory
    REPORT_LOCATION = DATA_LOCATION + File.separator + "report";
    PROPERTIES_LOCATION = DATA_LOCATION + File.separator + "properties";
    DATA_LOCATION = DATA_LOCATION + File.separator + "data";
    File dataDir = new File(DATA_LOCATION);
    if (!dataDir.exists()){
      dataDir.mkdir();
    }
    File reportDir = new File(REPORT_LOCATION);
    if (!reportDir.exists()){
      reportDir.mkdir();
    }
    File propsDir = new File(PROPERTIES_LOCATION);
    if (!propsDir.exists()){
      propsDir.mkdir();
    }
  }
  
  public static boolean isDateWithinPeriod (Date date, Date fromDate, Date toDate){
    return date == null
      ||((fromDate == null
          || fromDate.equals(date)
          || fromDate.before(date))
        && (toDate == null
            || toDate.equals(date)
            || toDate.after(date)));

  }
  
}
