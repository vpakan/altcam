package sk.altcam.assembly;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import sk.altcam.assembly.monitoring.MonitoringTable;
import sk.altcam.assembly.monitoring.MonitoringTableModel;
import sk.altcam.assembly.order.OrderTable;
import sk.altcam.assembly.order.OrderTableModel;
import sk.altcam.assembly.report.ReportControl;
import sk.altcam.assembly.report.ReportModel;
import sk.altcam.users.AssemblyUsersModel;
import sk.altcam.users.AdminControl;

public class AssemblyMonitoring {
  public static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  public static final SimpleDateFormat DISPLAY_TIME_FORMAT = new SimpleDateFormat("HH:mm");
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
  private AdminControl adminControl;
  private MonitoringTableModel monitoringTableModel;
  private AssemblyUsersModel assemblyUsersModel;
  private TabItem tiAdmin;
  private TabItem tiReports;
    
  static {
    AssemblyMonitoring.DECIMAL_FORMAT.setMinimumFractionDigits(2);
    AssemblyMonitoring.DECIMAL_FORMAT.setMaximumFractionDigits(2);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    initializeDataLocation();
    new AssemblyMonitoring().run();
  }

  private void run(){
    Shell shell = new Shell();
    shell.setText("Monitoring montaze");
    // Set layout for shell
    GridLayout layout = new GridLayout(1,false);
    shell.setLayout(layout);
    shell.setLayoutData(new GridData(GridData.FILL_BOTH));
    Composite composite = new Composite(shell, SWT.NONE);
    createContent(composite);
    assemblyUsersModel = new AssemblyUsersModel(adminControl);
    shell.addDisposeListener(new DisposeListener() {
      public void widgetDisposed(DisposeEvent e) {
        dispose();     
      }
    });
    LoginScreen.logIn(Display.getCurrent(), assemblyUsersModel);
    if (assemblyUsersModel.getLoggedUser() != null){
      initialize();
      // Ask the shell to display its content
      shell.open();
      if (assemblyUsersModel.getLoggedUser() != null){
        if (!assemblyUsersModel.getLoggedUser().isAdmin()){
          tiReports.dispose();
          tiAdmin.dispose();
        }
        Display display = shell.getDisplay();
        while (!shell.isDisposed()) {
          if (!display.readAndDispatch())
            display.sleep();
        }
      }
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
    GridLayout layout = new GridLayout(1,false);
    parent.setLayout(layout);
    parent.setLayoutData(new GridData(GridData.FILL_BOTH));
    TabFolder tabFolderMain = new TabFolder (parent, SWT.BORDER);
    tabFolderMain.setLayoutData(new GridData(GridData.FILL_BOTH));
    TabItem monitoring = new TabItem (tabFolderMain, SWT.NONE);
    monitoring.setText ("Monitoring");
    monitoringTable = new MonitoringTable(tabFolderMain, SWT.NONE);
    monitoring.setControl(monitoringTable);
    TabItem orders = new TabItem (tabFolderMain, SWT.NONE);
    orders.setText ("Objednavky");
    orderTable = new OrderTable(tabFolderMain, SWT.NONE);
    orders.setControl(orderTable);
    tiReports = new TabItem (tabFolderMain, SWT.NONE);
    tiReports.setText ("Reporty");
    reportControl = new ReportControl(tabFolderMain, SWT.NONE);
    tiReports.setControl(reportControl);
    tiAdmin = new TabItem(tabFolderMain, SWT.NONE);
    tiAdmin.setText("Admin");
    adminControl = new AdminControl(tabFolderMain, SWT.NONE, this);
    tiAdmin.setControl(adminControl);
  }
  
  private void initialize(){
	  // Initialize Orders
    orderTableModel = new OrderTableModel(orderTable);
    // Initialize Monitoring
    monitoringTableModel = new MonitoringTableModel(monitoringTable,orderTableModel,assemblyUsersModel);
    // Initialize Reports
    new ReportModel(reportControl,orderTableModel, monitoringTableModel, assemblyUsersModel);
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
  
  public static int sortDouble(double input1 , double input2){
    double diff = input1 - input2;
    int result = diff < 0 ? -1 : (diff > 0) ? 1 : 0;  
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
  
  public static Date addOneDay(Date date){
    
    Date result = null;
    
    if (date != null){
      GC.setTime(date);
      GC.add(Calendar.DAY_OF_MONTH, 1);
      result = GC.getTime();
    }
        
    return result;
    
  }
  
  public static Date getTimeDiffHHMM(Date higherDate, Date lowerDate){
    
    GC.setTimeInMillis(higherDate.getTime() - lowerDate.getTime());
    GC.set(Calendar.MILLISECOND, 0);
    GC.set(Calendar.SECOND, 0);
    GC.set(Calendar.DAY_OF_MONTH, 1);
    GC.set(Calendar.MONTH, 0);
    GC.add(Calendar.HOUR_OF_DAY, -1 * (GC.get(Calendar.ZONE_OFFSET) / 3600000));
    return GC.getTime();
    
  }
  
  public static Date addMinutes(Date date, int minutes){
    
    GC.setTimeInMillis(date.getTime());
    GC.add(Calendar.MINUTE,minutes);
    return GC.getTime();
    
  }
  
  public static double getDecimalHHMMTime(Date date){
    
    GC.setTime(date);
    GC.set(Calendar.MILLISECOND, 0);
    GC.set(Calendar.SECOND, 0);
    GC.set(Calendar.DAY_OF_MONTH, 1);
    GC.set(Calendar.MONTH, 0);
       
    return GC.get(Calendar.HOUR) + (GC.get(Calendar.MINUTE) / 60.0);
    
  }
  
  private static void initializeDataLocation(){
    DATA_LOCATION = new File(AssemblyMonitoring.class
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
