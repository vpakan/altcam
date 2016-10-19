package sk.altcam.assembly.report;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import sk.altcam.assembly.MessageBox;
import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;
import sk.altcam.assembly.entity.Order;
import sk.altcam.assembly.entity.User;
import sk.altcam.assembly.monitoring.MonitoringTableModel;
import sk.altcam.assembly.order.OrderTableModel;
import sk.altcam.users.AssemblyUsersModel;

public class ReportModel {

  private ReportControl reportControl = null;
  private OrderTableModel orderTableModel = null;
  private MonitoringTableModel monitoringTableModel = null;
  private AssemblyUsersModel assemblyUserModel = null;

  private class ItemReportData{
    public ItemReportData(int pieces, int processed,
        int loaded) {

      this.pieces = pieces;
      this.loaded = loaded;
      this.processed = processed;
      
    }
    @Override
    public String toString() {
      return "ItemReportData [pieces=" + pieces 
          + ", processed=" + processed + ", loaded=" + loaded + "]";
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + pieces;
      result = prime * result + processed;
      result = prime * result + loaded;
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
      if (pieces != other.pieces)
        return false;
      if (processed != other.processed)
        return false;
      if (loaded != other.loaded)
        return false;
      return true;
    }
    
    public int pieces = 0;
    public int loaded = 0;
    public int processed = 0;
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

  
  private class AssemblyMonitoringOperatorData {
    @Override
    public String toString() {
      return "AssemblyMonitoringOperatorData [user=" + user + ", monitorings="
          + monitorings + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result
          + ((monitorings == null) ? 0 : monitorings.hashCode());
      result = prime * result + ((user == null) ? 0 : user.hashCode());
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
      AssemblyMonitoringOperatorData other = (AssemblyMonitoringOperatorData) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (monitorings == null) {
        if (other.monitorings != null)
          return false;
      } else if (!monitorings.equals(other.monitorings))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
      return true;
    }

    public User user = null;
    public LinkedList<Monitoring> monitorings = new LinkedList<Monitoring>();

    private ReportModel getOuterType() {
      return ReportModel.this;
    }
  }

  private class AssemblyMonitoringItemData {
    @Override
    public String toString() {
      return "AssemblyMonitoringItemData [item=" + item + ", monitorings="
          + monitorings + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result
          + ((monitorings == null) ? 0 : monitorings.hashCode());
      result = prime * result + ((item == null) ? 0 : item.hashCode());
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
      AssemblyMonitoringItemData other = (AssemblyMonitoringItemData) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (monitorings == null) {
        if (other.monitorings != null)
          return false;
      } else if (!monitorings.equals(other.monitorings))
        return false;
      if (item == null) {
        if (other.item != null)
          return false;
      } else if (!item.equals(other.item))
        return false;
      return true;
    }

    public String item = null;
    public LinkedList<Monitoring> monitorings = new LinkedList<Monitoring>();

    private ReportModel getOuterType() {
      return ReportModel.this;
    }
  }

  public ReportModel(ReportControl reportControl,
      OrderTableModel orderTableModel,
      MonitoringTableModel monitoringTableModel,
      AssemblyUsersModel assemblyUserModel) {
    this.reportControl = reportControl;
    this.orderTableModel = orderTableModel;
    this.monitoringTableModel = monitoringTableModel;
    this.assemblyUserModel = assemblyUserModel;
    initializeReports();
  }

  private void initializeReports() {
    Date today = new Date();
    String fromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT
        .format(AssemblyMonitoring.getFirstDayOfMonth(today));
    String toDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT
        .format(AssemblyMonitoring.getLastDayOfMonth(today));
    // assembly reports init
    reportControl.getFromAssemblyDate().setText(fromDate);
    reportControl.getToAssemblyDate().setText(toDate);

    reportControl.getAssemblyOperatorReport()
        .addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            runAssemblyOperatorReport();
          }
        });

    reportControl.getAssemblyItemReport()
        .addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            runAssemblyItemReport();
          }
        });

    reportControl.getAssemblyAllReport()
        .addSelectionListener(new SelectionAdapter() {
          public void widgetSelected(SelectionEvent e) {
            runAssemblyAllReport();
          }
        });
    // production reports init
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

  }

  private String getReportDirectoryLocation() {
    DirectoryDialog directoryDialog = new DirectoryDialog(
        reportControl.getShell(), SWT.SAVE);
    directoryDialog.setText("Zadajte adresar pre vystupne subory");
    directoryDialog.setFilterPath(AssemblyMonitoring.REPORT_LOCATION);
    String selected = directoryDialog.open();
    return selected;
  }

  private String getReportFileLocation(String fileName) {
    FileDialog fileDialog = new FileDialog(reportControl.getShell(), SWT.SAVE);
    fileDialog.setText("Zadajte umiestnenie vystupneho suboru");
    fileDialog.setFileName(
        AssemblyMonitoring.REPORT_LOCATION + File.separator + fileName);
    String[] filterExt = { "*.txt" };
    fileDialog.setFilterExtensions(filterExt);
    String selected = fileDialog.open();
    if (selected != null && !selected.endsWith(".txt")) {
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
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ERROR,
          "Chyba!",
          "Nepodarilo sa zapisat vystupny subor.\n" + "Popis chyby: " + e);
    }
  }

  private void runAssemblyOperatorReport() {

    String reportFileLocation = getReportDirectoryLocation();
    if (reportFileLocation != null) {
      String fromDate = reportControl.getFromAssemblyDate().getText();
      String toDate = reportControl.getToAssemblyDate().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }

      HashMap<String, AssemblyMonitoringOperatorData> userData = new HashMap<String, AssemblyMonitoringOperatorData>();
      for (Order order : orderTableModel.getData()) {
        List<Monitoring> monitorings = order.getMonitoring();
        if (monitorings != null) {
          for (Monitoring monitoring : monitorings) {
            if (AssemblyMonitoring.isDateWithinPeriod(monitoring.getDate(),
                reportFromDate, reportToDate)) {
              AssemblyMonitoringOperatorData assemblyMonitoringData = null;
              if (userData.containsKey(monitoring.getUserId())) {
                assemblyMonitoringData = userData.get(monitoring.getUserId());
              } else {
                assemblyMonitoringData = new AssemblyMonitoringOperatorData();
                assemblyMonitoringData.user = assemblyUserModel
                    .getUser(monitoring.getUserId());
                userData.put(monitoring.getUserId(), assemblyMonitoringData);
              }
              assemblyMonitoringData.monitorings.add(monitoring);
            }
          }
        }
      }

      int numReports = 0;
      for (String userId : userData.keySet()) {
        AssemblyMonitoringOperatorData assemblyMonitoringData = userData
            .get(userId);
        ReportFormatter rf = new ReportFormatter(
            new int[] { 10, 5, 10, 14, 10, 9, 12, 12, 9, 9, 9, 9, 2, 14, 6, 6, 9, 7, 9,
                30 },
            new int[] { ReportFormatter.ALLIGN_LEFT,
                ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_LEFT,
                ReportFormatter.ALLIGN_LEFT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_CENTER,
                ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_CENTER,
                ReportFormatter.ALLIGN_LEFT },
            new boolean[] { true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true,
                true, true, true });

        rf.addText("Od: "
            + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate)
            + " Do: "
            + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate)
            + " Datum vytvorenia reportu: "
            + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
        rf.addNewLine();
        rf.addNewLine();
        rf.addCenteredLine("Vykon zamestnanca za obdobie");
        rf.addNewLine();
        rf.addText("Id a meno zamestnanca: " + userId + " - "
            + (assemblyUserModel.getUser(userId).getUserName()));
        rf.addNewLine();
        rf.addNewLine();
        rf.addColumnText("Datum");
        rf.addColumnText("Zmena");
        rf.addColumnText("Cislo_IO");
        rf.addColumnText("Cislo_vyrobku");
        rf.addColumnText("Norma/hod.");
        rf.addColumnText("OK_ks");
        rf.addColumnText("NOK_procesne_ks");
        rf.addColumnText("NOK_nabehove_ks");
        rf.addColumnText("NOK_ks");
        rf.addColumnText("Spolu_ks");
        rf.addColumnText("NOK_%");
        rf.addColumnText("Vykon_(%)");
        rf.addColumnText("+-");
        rf.addColumnText("Efektivita_(%)");
        rf.addColumnText("Cas_od");
        rf.addColumnText("Cas_do");
        rf.addColumnText("Prestavka");
        rf.addColumnText("Prestoj");
        rf.addColumnText("Cisty_cas");
        rf.addColumnText("Poznamka");
        rf.addNewLine();

        Collections.sort(assemblyMonitoringData.monitorings,
            new Comparator<Monitoring>() {
              @Override
              public int compare(Monitoring monitoring1,
                  Monitoring monitoring2) {
                return monitoring1.compareTo(monitoring2);
              }
            });
        int numNormPassed = 0;
        int numNormFailed = 0;
        for (Monitoring monitoring : assemblyMonitoringData.monitorings) {
          rf.addNewLine();
          rf.addColumnText(AssemblyMonitoring.DISPLAY_DATE_FORMAT
              .format(monitoring.getDate()));
          rf.addColumnText(String.valueOf(monitoring.getShift()));
          rf.addColumnText(monitoring.getOrderEntity().getOrderNumber());
          rf.addColumnText(monitoring.getOrderEntity().getItemNumber());
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcNorm(monitoring)));
          rf.addColumnText(String.valueOf(monitoring.getPieces()));
          rf.addColumnText(String.valueOf(monitoring.getNonOkProcessed()));
          rf.addColumnText(String.valueOf(monitoring.getNonOkLoaded()));
          rf.addColumnText(String.valueOf(monitoringTableModel.calcSumNonOkPieces(monitoring)));
          rf.addColumnText(
              String.valueOf(monitoringTableModel.calcSumPieces(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcPercentNonOkPieces(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcEfficiency(monitoring)));
          String sign = monitoringTableModel.calcSign(monitoring);
          rf.addColumnText(sign);
          if (sign.startsWith("-")) {
            numNormFailed++;
          } else {
            numNormPassed++;
          }
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcEffectivity(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoring.getFromTime()));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoring.getToTime()));
          rf.addColumnText(String.valueOf(monitoring.getBreakTime()));
          rf.addColumnText(String.valueOf(monitoring.getPauseTime()));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoringTableModel.calcEfficientTime(monitoring)));
          rf.addColumnText(monitoring.getComment());
        }
        rf.addNewLine();
        rf.addNewLine();
        rf.addText("Pocet zaznamov so SPLNENOU normou: " + numNormPassed);
        rf.addNewLine();
        rf.addText("Pocet zaznamov s NESPLNENOU normou: " + numNormFailed);
        rf.addNewLine();
        rf.addText("Pocet zaznamov spolu: " + (numNormFailed + numNormPassed));

        writeToFile(reportFileLocation + File.separator + userId + ".txt",
            rf.getReportText());
        numReports++;

      }

      MessageBox.displayMessageBox(reportControl.getShell(),
          SWT.ICON_INFORMATION, "Info",
          "Reporty boli vygenerovane do adresara: " + reportFileLocation
              + "\nPocet vygenerovanych reportov: " + numReports);
    }
  }

  private void runAssemblyItemReport() {
    String reportFileLocation = getReportDirectoryLocation();
    if (reportFileLocation != null) {
      String fromDate = reportControl.getFromAssemblyDate().getText();
      String toDate = reportControl.getToAssemblyDate().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }

      HashMap<String, AssemblyMonitoringItemData> itemData = new HashMap<String, AssemblyMonitoringItemData>();
      for (Order order : orderTableModel.getData()) {
        List<Monitoring> monitorings = order.getMonitoring();
        if (monitorings != null) {
          for (Monitoring monitoring : monitorings) {
            if (AssemblyMonitoring.isDateWithinPeriod(monitoring.getDate(),
                reportFromDate, reportToDate)) {
              AssemblyMonitoringItemData assemblyMonitoringItemData = null;
              if (itemData
                  .containsKey(monitoring.getOrderEntity().getItemNumber())) {
                assemblyMonitoringItemData = itemData
                    .get(monitoring.getOrderEntity().getItemNumber());
              } else {
                assemblyMonitoringItemData = new AssemblyMonitoringItemData();
                assemblyMonitoringItemData.item = monitoring.getOrderEntity()
                    .getItemNumber();
                itemData.put(assemblyMonitoringItemData.item,
                    assemblyMonitoringItemData);
              }
              assemblyMonitoringItemData.monitorings.add(monitoring);
            }
          }
        }
      }

      int numReports = 0;
      for (String item : itemData.keySet()) {
        AssemblyMonitoringItemData assemblyMonitoringData = itemData.get(item);
        ReportFormatter rf = new ReportFormatter(
            new int[] { 10, 5, 10, 12, 25, 10, 9, 12, 12, 9, 9, 9, 9, 2, 14, 6, 6, 9, 7,
                9, 30 },
            new int[] { ReportFormatter.ALLIGN_LEFT,
                ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_LEFT,
                ReportFormatter.ALLIGN_LEFT, ReportFormatter.ALLIGN_LEFT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_CENTER,
                ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
                ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_LEFT },
            new boolean[] { true, true, true, true, true, true, true, true,
                true, true, true, true, true, true, true, true, true, true,
                true, true, true, true });

        rf.addText("Od: "
            + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate)
            + " Do: "
            + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate)
            + " Datum vytvorenia reportu: "
            + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
        rf.addNewLine();
        rf.addNewLine();
        rf.addCenteredLine("Vykon za vyrobok za obdobie");
        rf.addNewLine();
        rf.addText("Cislo_vyrobku: " + item);
        rf.addNewLine();
        rf.addNewLine();
        rf.addColumnText("Datum");
        rf.addColumnText("Zmena");
        rf.addColumnText("Cislo_IO");
        rf.addColumnText("ID_operatora");
        rf.addColumnText("Meno_operatora");
        rf.addColumnText("Norma/hod.");
        rf.addColumnText("OK_ks");
        rf.addColumnText("NOK_procesne_ks");
        rf.addColumnText("NOK_nabehove_ks");
        rf.addColumnText("NOK_ks");
        rf.addColumnText("Spolu_ks");
        rf.addColumnText("NOK_%");
        rf.addColumnText("Vykon_(%)");
        rf.addColumnText("+-");
        rf.addColumnText("Efektivita_(%)");
        rf.addColumnText("Cas_od");
        rf.addColumnText("Cas_do");
        rf.addColumnText("Prestavka");
        rf.addColumnText("Prestoj");
        rf.addColumnText("Cisty_cas");
        rf.addColumnText("Poznamka");
        rf.addNewLine();

        Collections.sort(assemblyMonitoringData.monitorings,
            new Comparator<Monitoring>() {
              @Override
              public int compare(Monitoring monitoring1,
                  Monitoring monitoring2) {
                return monitoring1.compareTo(monitoring2);
              }
            });
        int numNormPassed = 0;
        int numNormFailed = 0;
        for (Monitoring monitoring : assemblyMonitoringData.monitorings) {
          rf.addNewLine();
          rf.addColumnText(AssemblyMonitoring.DISPLAY_DATE_FORMAT
              .format(monitoring.getDate()));
          rf.addColumnText(String.valueOf(monitoring.getShift()));
          rf.addColumnText(monitoring.getOrderEntity().getOrderNumber());
          rf.addColumnText(monitoring.getUserId());
          rf.addColumnText(
              assemblyUserModel.getUser(monitoring.getUserId()).getUserName().replaceAll(" ", "_"));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcNorm(monitoring)));
          rf.addColumnText(String.valueOf(monitoring.getPieces()));
          rf.addColumnText(String.valueOf(monitoring.getNonOkProcessed()));
          rf.addColumnText(String.valueOf(monitoring.getNonOkLoaded()));
          rf.addColumnText(String.valueOf(monitoringTableModel.calcSumNonOkPieces(monitoring)));
          rf.addColumnText(
              String.valueOf(monitoringTableModel.calcSumPieces(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcPercentNonOkPieces(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcEfficiency(monitoring)));
          String sign = monitoringTableModel.calcSign(monitoring);
          rf.addColumnText(sign);
          if (sign.startsWith("-")) {
            numNormFailed++;
          } else {
            numNormPassed++;
          }
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(monitoringTableModel.calcEffectivity(monitoring)));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoring.getFromTime()));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoring.getToTime()));
          rf.addColumnText(String.valueOf(monitoring.getBreakTime()));
          rf.addColumnText(String.valueOf(monitoring.getPauseTime()));
          rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
              .format(monitoringTableModel.calcEfficientTime(monitoring)));
          rf.addColumnText(monitoring.getComment());
        }
        rf.addNewLine();
        rf.addNewLine();
        rf.addText("Pocet zaznamov so SPLNENOU normou: " + numNormPassed);
        rf.addNewLine();
        rf.addText("Pocet zaznamov s NESPLNENOU normou: " + numNormFailed);
        rf.addNewLine();
        rf.addText("Pocet zaznamov spolu: " + (numNormFailed + numNormPassed));

        writeToFile(reportFileLocation + File.separator + item + ".txt",
            rf.getReportText());
        numReports++;

      }

      MessageBox.displayMessageBox(reportControl.getShell(),
          SWT.ICON_INFORMATION, "Info",
          "Reporty boli vygenerovane do adresara: " + reportFileLocation
              + "\nPocet vygenerovanych reportov: " + numReports);
    }

  }

  private void runAssemblyAllReport() {
    String reportFileLocation = getReportFileLocation("assemblysum.txt");
    if (reportFileLocation != null) {
      String fromDate = reportControl.getFromAssemblyDate().getText();
      String toDate = reportControl.getToAssemblyDate().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }

      LinkedList<Monitoring> monitoringData = new LinkedList<Monitoring>();
      for (Order order : orderTableModel.getData()) {
        List<Monitoring> monitorings = order.getMonitoring();
        if (monitorings != null) {
          for (Monitoring monitoring : monitorings) {
            if (AssemblyMonitoring.isDateWithinPeriod(monitoring.getDate(),
                reportFromDate, reportToDate)) {
              monitoringData.add(monitoring);
            }
          }
        }
      }

      ReportFormatter rf = new ReportFormatter(
          new int[] { 10, 5, 10, 14, 12,25,10, 9, 12, 12, 9, 9, 9, 9, 2, 14, 6, 6, 9, 7, 9,
              30 },
          new int[] { ReportFormatter.ALLIGN_LEFT,
              ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_LEFT,
              ReportFormatter.ALLIGN_LEFT, ReportFormatter.ALLIGN_LEFT,
              ReportFormatter.ALLIGN_LEFT,ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_CENTER,
              ReportFormatter.ALLIGN_CENTER, ReportFormatter.ALLIGN_RIGHT,
              ReportFormatter.ALLIGN_RIGHT, ReportFormatter.ALLIGN_CENTER,
              ReportFormatter.ALLIGN_LEFT },
          new boolean[] { true, true, true, true, true,true, true, true, true, true, true,
              true, true, true, true, true, true, true, true, true, true, true });

      rf.addText(
          "Od: " + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate)
              + " Do: "
              + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate)
              + " Datum vytvorenia reportu: "
              + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
      rf.addNewLine();
      rf.addNewLine();
      rf.addCenteredLine("Monitoring montaze za obdobie");
      rf.addNewLine();
      rf.addNewLine();
      rf.addColumnText("Datum");
      rf.addColumnText("Zmena");
      rf.addColumnText("Cislo_IO");
      rf.addColumnText("Cislo_vyrobku");
      rf.addColumnText("ID_operatora");
      rf.addColumnText("Meno_operatora");
      rf.addColumnText("Norma/hod.");
      rf.addColumnText("OK_ks");
      rf.addColumnText("NOK_procesne_ks");
      rf.addColumnText("NOK_nabehove_ks");
      rf.addColumnText("NOK_ks");
      rf.addColumnText("Spolu_ks");
      rf.addColumnText("NOK_%");
      rf.addColumnText("Vykon_(%)");
      rf.addColumnText("+-");
      rf.addColumnText("Efektivita_(%)");
      rf.addColumnText("Cas_od");
      rf.addColumnText("Cas_do");
      rf.addColumnText("Prestavka");
      rf.addColumnText("Prestoj");
      rf.addColumnText("Cisty_cas");
      rf.addColumnText("Poznamka");
      rf.addNewLine();

      Collections.sort(monitoringData, new Comparator<Monitoring>() {
        @Override
        public int compare(Monitoring monitoring1, Monitoring monitoring2) {
          return monitoring1.compareTo(monitoring2);
        }
      });
      int numNormPassed = 0;
      int numNormFailed = 0;
      for (Monitoring monitoring : monitoringData) {
        rf.addNewLine();
        rf.addColumnText(AssemblyMonitoring.DISPLAY_DATE_FORMAT
            .format(monitoring.getDate()));
        rf.addColumnText(String.valueOf(monitoring.getShift()));
        rf.addColumnText(monitoring.getOrderEntity().getOrderNumber());
        rf.addColumnText(monitoring.getOrderEntity().getItemNumber());
        rf.addColumnText(monitoring.getUserId());
        rf.addColumnText(
            assemblyUserModel.getUser(monitoring.getUserId()).getUserName().replaceAll(" ", "_"));
        rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
            .format(monitoringTableModel.calcNorm(monitoring)));
        rf.addColumnText(String.valueOf(monitoring.getPieces()));
        rf.addColumnText(String.valueOf(monitoring.getNonOkProcessed()));
        rf.addColumnText(String.valueOf(monitoring.getNonOkLoaded()));
        rf.addColumnText(String.valueOf(monitoringTableModel.calcSumNonOkPieces(monitoring)));
        rf.addColumnText(
            String.valueOf(monitoringTableModel.calcSumPieces(monitoring)));
        rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
            .format(monitoringTableModel.calcPercentNonOkPieces(monitoring)));
        rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
            .format(monitoringTableModel.calcEfficiency(monitoring)));
        String sign = monitoringTableModel.calcSign(monitoring);
        rf.addColumnText(sign);
        if (sign.startsWith("-")) {
          numNormFailed++;
        } else {
          numNormPassed++;
        }
        rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
            .format(monitoringTableModel.calcEffectivity(monitoring)));
        rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
            .format(monitoring.getFromTime()));
        rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
            .format(monitoring.getToTime()));
        rf.addColumnText(String.valueOf(monitoring.getBreakTime()));
        rf.addColumnText(String.valueOf(monitoring.getPauseTime()));
        rf.addColumnText(AssemblyMonitoring.DISPLAY_TIME_FORMAT
            .format(monitoringTableModel.calcEfficientTime(monitoring)));
        rf.addColumnText(monitoring.getComment());
      }
      rf.addNewLine();
      rf.addNewLine();
      rf.addText("Pocet zaznamov so SPLNENOU normou: " + numNormPassed);
      rf.addNewLine();
      rf.addText("Pocet zaznamov s NESPLNENOU normou: " + numNormFailed);
      rf.addNewLine();
      rf.addText("Pocet zaznamov spolu: " + (numNormFailed + numNormPassed));

      writeToFile(reportFileLocation,rf.getReportText());

      MessageBox.displayMessageBox(reportControl.getShell(),
          SWT.ICON_INFORMATION, "Info",
          "Report bol vygenerovany do suboru: " + reportFileLocation);
    }

  }
  private void runMachineReport(){

    String reportFileLocation = getReportFileLocation("machinerep.txt");
    if (reportFileLocation != null){
      String fromDate = reportControl.getFromMachineDate().getText();
      String toDate = reportControl.getToMachineDate().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }
      ReportFormatter rf = new ReportFormatter(new int[]{20,20}, 
          new int[]{ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_RIGHT},
          new boolean[]{true,true});
      rf.addText("Od: " + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate) +
        " Do: " + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate) +
        " Datum vytvorenia reportu: " + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
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
        if (monitorings != null) {
          for (Monitoring monitoring : monitorings) {
            if (AssemblyMonitoring.isDateWithinPeriod(monitoring.getDate(),
                reportFromDate, reportToDate)) {
              machineReportData.accumulatedTheoreticalProduced += monitoringTableModel
                  .calcNorm(monitoring)
                  * AssemblyMonitoring.getDecimalHHMMTime(
                      monitoringTableModel.calcEfficientTime(monitoring));
              machineReportData.accumulatedProduced += monitoring.getPieces();
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
        rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
          .format((100.0D*machineRD.accumulatedProduced)/machineRD.accumulatedTheoreticalProduced));
      }
      
      writeToFile(reportFileLocation, rf.getReportText());
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ICON_INFORMATION, "Info",
        "Report bol vygenerovany do suboru: " + reportFileLocation);
    }  
  }
  
  private void runMonitoringReport (){
    String reportFileLocation = getReportFileLocation("prodrep.txt");
    if (reportFileLocation != null){
      String fromDate = reportControl.getFromMonitoringDate().getText();
      String toDate = reportControl.getToMonitoringDate().getText();
      String reportItemNumber = reportControl.getItemNumber().getText();
      Date reportFromDate = null;
      Date reportToDate = null;
      try {
        reportFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
      } catch (ParseException e) {
        reportFromDate = null;
      }
      try {
        reportToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
      } catch (ParseException e) {
        reportToDate = null;
      }
      ReportFormatter rf = new ReportFormatter(new int[]{16,8,12,
          8,12,8,8,8,8,8,8,8}, 
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
            ReportFormatter.ALLIGN_RIGHT},
          new boolean[]{true,true,true,true,true,true,true,true,true,true,true,true});
      rf.addText("Od: " + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportFromDate) +
        " Do: " + AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(reportToDate) +
        " Datum vytvorenia reportu: " + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
      rf.addNewLine();
      rf.addNewLine();
      rf.addCenteredLine("Kazovost vyrobku za obdobie");
      rf.addNewLine();
      rf.addColumnText("Cislo vyr.");
      rf.addColumnText("Vyrobene");
      rf.addColumnText("NOK_procesne");
      rf.addColumnText("ppm");
      rf.addColumnText("NOK_Nabehove");
      rf.addColumnText("ppm");
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
            itemReportData = new ItemReportData(0,0,0);
            itemsData.put(order.getItemNumber(), itemReportData);
          }
          List<Monitoring> monitorings = order.getMonitoring();
          if (monitorings != null){
            for (Monitoring monitoring : monitorings){
              if (AssemblyMonitoring.isDateWithinPeriod(monitoring.getDate(),
                  reportFromDate, reportToDate)) {
                itemReportData.pieces += monitoring.getPieces();
                itemReportData.processed += monitoring.getNonOkProcessed();
                itemReportData.loaded += monitoring.getNonOkLoaded();
              }
            }
          }
        }
      }
      int pieces = 0;
      int loaded = 0;
      int processed = 0;
      for (String itemNumber : itemsData.keySet()){
        ItemReportData itemReportData = itemsData.get(itemNumber);
        pieces += itemReportData.pieces;
        processed += itemReportData.processed;
        loaded += itemReportData.loaded;
 
        int sumPpmBroken = itemReportData.processed + itemReportData.loaded;
        int sumBroken = sumPpmBroken;
        int totalForPpm = sumPpmBroken + itemReportData.pieces;
        int total = itemReportData.pieces + sumBroken;
        rf.addNewLine();
        rf.addColumnText(itemNumber);
        rf.addColumnText(String.valueOf(itemReportData.pieces));
        rf.addColumnText(String.valueOf(itemReportData.processed));
        long value = getPpm(itemReportData.processed,totalForPpm);
        rf.addColumnText(String.valueOf(value));
        rf.addColumnText(String.valueOf(itemReportData.loaded));
        value = getPpm(itemReportData.loaded,totalForPpm);
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
      int sumPpmBroken = processed + loaded;
      int sumBroken = sumPpmBroken;
      int totalForPpm = pieces + sumPpmBroken;
      int total = pieces + sumBroken;
      rf.addNewLine();
      rf.addNewLine();
      rf.addColumnText("Spolu");
      rf.addColumnText(String.valueOf(pieces));
      rf.addColumnText(String.valueOf(processed));
      long value = getPpm(processed,totalForPpm);
      rf.addColumnText(String.valueOf(value));
      rf.addColumnText(String.valueOf(loaded));
      value = getPpm(loaded,totalForPpm);
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
      rf.addText("NOK_Procesne  - pocet pokazenych procesnych ks");
      rf.addNewLine();
      rf.addText("NOK_Nabehove  - pocet pokazenych nabehovych ks");
      rf.addNewLine();
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
  private void runOrderReport (){
    String reportFileLocation = getReportFileLocation("orderrep.txt");
    if (reportFileLocation != null){
      ReportFormatter rf = new ReportFormatter(new int[]{5,16,10,8,8,8,8}, 
          new int[]{ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_LEFT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT,
            ReportFormatter.ALLIGN_RIGHT},
          new boolean[]{true,true,true,true,true,true,true});
      rf.addText("Datum vytvorenia reportu: " + AssemblyMonitoring.STORAGE_DATE_FORMAT.format(new Date()));
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
            produced += monitoring.getPieces();
          }
        }  
        if (produced < order.getNumItemsPlanned()) {
          rf.addNewLine();
          rf.addColumnText(order.getOrderNumber());
          rf.addColumnText(order.getItemNumber());
          rf.addColumnText(order.getDate() == null ? ""
              : AssemblyMonitoring.DISPLAY_DATE_FORMAT
                  .format(order.getDate()));
          rf.addColumnText(String.valueOf(order.getNumItemsPlanned()));
          rf.addColumnText(String.valueOf(produced));
          int missing = order.getNumItemsPlanned() - produced;
          rf.addColumnText(String.valueOf(missing));
          rf.addColumnText(AssemblyMonitoring.DECIMAL_FORMAT
              .format(((float) produced / order.getNumItemsPlanned()) * 100));
        }
      }
      writeToFile(reportFileLocation, rf.getReportText());
      MessageBox.displayMessageBox(reportControl.getShell(), SWT.ICON_INFORMATION, "Info",
        "Report bol vygenerovany do suboru: " + reportFileLocation);
    }
  }
  private long getPpm (int numItem , int total){
    return Math.round(((double)numItem/total)*1000000);
  }
}
