package sk.altcam.assembly.monitoring;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import sk.altcam.assembly.DateVerifyListener;
import sk.altcam.assembly.EditorNavigationStrategy;
import sk.altcam.assembly.IntegerVerifyListener;
import sk.altcam.assembly.MessageBox;
import sk.altcam.assembly.TimeVerifyListener;
import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;
import sk.altcam.assembly.entity.Order;
import sk.altcam.assembly.order.OrderTableModel;
import sk.altcam.users.AssemblyUsersModel;

public class MonitoringTableModel {
  private TableViewer tableViewer = null;
  private LinkedList<Monitoring> data = null;
  private final int[] sorting = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
      1, 1, 1, 1, 1, 1 };
  private Date filterFromDate = null;
  private Date filterToDate = null;
  private Table table = null;
  private MonitoringTable monitoringTable = null;
  private OrderTableModel orderTableModel = null;
  private AssemblyUsersModel assemblyUsersModel = null;

  public List<Monitoring> getData() {
    return data;
  }

  public TableViewer getTableViewer() {
    return tableViewer;
  }

  public MonitoringTableModel(MonitoringTable monitoringTable,
      OrderTableModel orderTableModel, AssemblyUsersModel assemblyUsersModel) {
    this.monitoringTable = monitoringTable;
    this.orderTableModel = orderTableModel;
    this.assemblyUsersModel = assemblyUsersModel;
    initializeFilter();
    initializeTableViewer();
    initializeButtons();
    tableViewer.setInput(data);
    for (int index = 0; index < table.getColumnCount(); index++) {
      table.getColumn(index).pack();
    }
    table.pack();
    for (int index = 0; index < table.getColumnCount(); index++) {
      Label dummyLabel = new Label(table.getParent(), SWT.BORDER);
      dummyLabel.setText(table.getColumn(index).getText());
      int labelWidth = dummyLabel.computeSize(0, 0).x + 8;
      dummyLabel.dispose();
      if (table.getColumn(index).getWidth() < labelWidth) {
        table.getColumn(index).setWidth(labelWidth);
      }
    }
  }

  public LinkedList<Monitoring> loadData() {
    LinkedList<Monitoring> result = new LinkedList<Monitoring>();
    result = orderTableModel.getFilteredMonitoringRecords(filterFromDate,
        filterToDate, getFilterUserId());
    return result;
  }

  public double calcEfficiency(Monitoring monitoring) {
    double efficiency = -1.0;
    double norm = calcNorm(monitoring);
    double effectiveTimeDecimal = AssemblyMonitoring
        .getDecimalHHMMTime(calcEffectiveTime(monitoring));
    int sumPieces = calcSumPieces(monitoring);
    if (norm > 0 && sumPieces > 0 && effectiveTimeDecimal > 0) {
      double piecesPerHour = sumPieces / effectiveTimeDecimal;
      efficiency = (piecesPerHour / norm) * 100;
    }
    return efficiency;
  }


  public double calcEffectivity(Monitoring monitoring) {
    double effectivty = -1.0;
    double norm = calcNorm(monitoring);
    double effectiveTimeDecimal = AssemblyMonitoring
        .getDecimalHHMMTime(calcEffectiveTime(monitoring));
    if (norm > 0 && monitoring.getPieces() > 0 && effectiveTimeDecimal > 0) {
      double piecesPerHour = monitoring.getPieces() / effectiveTimeDecimal;
      effectivty = (piecesPerHour / norm) * 100;
    }
    return effectivty;
  }
  
  public double calcPercentNonOkPieces(Monitoring monitoring) {
    double result = -1.0;

    int sumPieces = calcSumPieces(monitoring);

    if (sumPieces > 0) {
      result = (monitoring.getNonOkPieces() / (sumPieces * 1.0)) * 100;
    }

    return result;
  }

  public int calcSumPieces(Monitoring monitoring) {
    return monitoring.getPieces() + monitoring.getNonOkPieces();
  }

  public Date calcEffectiveTime(Monitoring monitoring) {
    Date effectiveTime = null;
    if (monitoring.getFromTime() != null && monitoring.getToTime() != null) {
      if (monitoring.getFromTime().after(monitoring.getToTime())) {
        effectiveTime = AssemblyMonitoring.getTimeDiffHHMM(
            AssemblyMonitoring.addOneDay(monitoring.getToTime()),
            monitoring.getFromTime());
      } else {
        effectiveTime = AssemblyMonitoring
            .getTimeDiffHHMM(monitoring.getToTime(), monitoring.getFromTime());
      }
    }
    return AssemblyMonitoring.addMinutes(effectiveTime, -1 * (monitoring.getBreakTime() + monitoring.getPauseTime()));
  }

  public double calcNorm(Monitoring monitoring) {
    double norm = 0.0;
    if (monitoring.getOrderEntity() != null
        && monitoring.getOrderEntity().getCycle() > 0
        && monitoring.getOrderEntity().getNumActiveCavities() > 0) {
      norm = (3600.0 / monitoring.getOrderEntity().getCycle())
          * monitoring.getOrderEntity().getNumActiveCavities();
    }
    return norm;
  }

  public String calcSign(Monitoring monitoring) {
    String sign = "?";
    double efficiency = calcEfficiency(monitoring);
    if (efficiency >= 0) {
      if (efficiency == 100.0) {
        sign = "";
      } else {
        sign = efficiency > 100.0 ? "++" : "--";
      }
    }
    return sign;
  }

  private void initializeFilter() {
    Date today = new Date();
    filterFromDate = AssemblyMonitoring.getFirstDayOfMonth(today);
    filterToDate = AssemblyMonitoring.getLastDayOfMonth(today);
    monitoringTable.getFromDate()
        .setText(AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(filterFromDate));
    monitoringTable.getToDate()
        .setText(AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(filterToDate));
    if (!assemblyUsersModel.getLoggedUser().isAdmin()) {
      monitoringTable.getUserId().setEnabled(false);
      monitoringTable.getUserId().add(assemblyUsersModel
          .formatUserlabel(assemblyUsersModel.getLoggedUser()));
    } else {
      monitoringTable.getUserId().setItems(
          assemblyUsersModel.getUserLabels(assemblyUsersModel.getUsers()));
    }
    monitoringTable.getUserId().select(0);
  }

  private void initializeTableViewer() {
    table = this.monitoringTable.getTable();
    tableViewer = new TableViewer(table);
    EditorNavigationStrategy.setUpTableViewerNavigationStrategy(tableViewer);
    tableViewer.setColumnProperties(MonitoringTableLabelProvider.COLUMN_NAMES);
    tableViewer.setContentProvider(new MonitoringTableContentProvider(this));
    tableViewer.setLabelProvider(new MonitoringTableLabelProvider(this));
    data = loadData();
    for (int index = 0; index < table.getColumnCount(); index++) {
      final int fIndex = index;
      final MonitoringTableModel monitoringTableModel = this;
      table.getColumn(index).addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          tableViewer.setSorter(new MonitoringTableSorter(monitoringTableModel,
              fIndex, sorting[fIndex]));
          sorting[fIndex] = sorting[fIndex] * (-1);
        }
      });
    }
    CellEditor[] editors = new CellEditor[MonitoringTableLabelProvider.COLUMN_NAMES.length];
    TextCellEditor textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new DateVerifyListener());
    editors[MonitoringTableLabelProvider.DATE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.SHIFT_NUMBER_COLUMN_IND] = textEditor;
    // Order
    final TextCellEditor orderNumberCellEditor = new TextCellEditor(table);
    orderNumberCellEditor.addListener(new ICellEditorListener() {
      @Override
      public void editorValueChanged(boolean arg0, boolean arg1) {
        // do nothing
      }

      @Override
      public void cancelEditor() {
        // do nothing
      }

      @Override
      public void applyEditorValue() {
        checkOrderNumber((Text) orderNumberCellEditor.getControl());
      }
    });
    editors[MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND] = orderNumberCellEditor;
    editors[MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.NORM_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.EFFECTIVITY_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.SUM_PIECES_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.NOK_PERCENT_COLUMN_IND] = null;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl()) .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NON_OK_PIECES_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl()) .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.BREAK_TIME_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl()) .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PAUSE_TIME_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl()) .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PIECES_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new TimeVerifyListener());
    editors[MonitoringTableLabelProvider.TIME_FROM_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new TimeVerifyListener());
    editors[MonitoringTableLabelProvider.TIME_TO_COLUMN_IND] = textEditor;
    editors[MonitoringTableLabelProvider.EFFECTIVE_TIME_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.EFFICIENCY_COLUMN_IND] = null;
    editors[MonitoringTableLabelProvider.SIGN_COLUMN_IND] = null;
    textEditor = new TextCellEditor(table);
    editors[MonitoringTableLabelProvider.COMMENT_COLUMN_IND] = textEditor;
    tableViewer.setCellEditors(editors);
    tableViewer.setCellModifier(new MonitoringTableCellModifier(this));
  }

  private void initializeButtons() {
    monitoringTable.getClose().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        e.display.dispose();
      }
    });
    monitoringTable.getAdd().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        addMonitoring();
      }
    });
    monitoringTable.getDelete().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        deleteMonitoring();
      }
    });
    monitoringTable.getFilter().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        filterMonitoring();
      }
    });
  }

  public void monitoringChanged(Monitoring monitoring) {
    // set order entity
    if (monitoring.getOrderEntity() == null) {
      int index = orderTableModel.getData()
          .indexOf(new Order(monitoring.getOrderNumber(), null, null, null));
      if (index >= 0) {
        Order order = orderTableModel.getData().get(index);
        monitoring.setOrderEntity(order);
        getData().get(getData().indexOf(monitoring)).setOrderEntity(order);
        if (order.getMonitoring() == null) {
          order.setMonitoring(new LinkedList<Monitoring>());
        }
        order.getMonitoring().add(order.getMonitoring().size(), monitoring);
        // orderTableModel.getTableViewer().update(order,null);
      }
    } else {
      if (!monitoring.getOrderEntity().getMonitoring().contains(monitoring)) {
        Order order = monitoring.getOrderEntity();
        order.getMonitoring().add(order.getMonitoring().size(), monitoring);
        // orderTableModel.getTableViewer().update(order,null);
      }
    }
    monitoring.setSysDate(new Date());
    tableViewer.update(monitoring, null);
    if (monitoring.getOrderEntity() != null) {
      orderTableModel.orderChanged(monitoring.getOrderEntity());
    }
  }

  private void addMonitoring() {
    if (isAddPossible()) {
      Monitoring monitoring = new Monitoring(null, "?", new Date(),
          getFilterUserId());
      tableViewer.add(monitoring);
      data.addLast(monitoring);
      tableViewer.setSelection(new StructuredSelection(monitoring));
      tableViewer.editElement(monitoring,
          MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND);

    } else {
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
          "Nemozno pridat zaznam do monitoringu."
              + "\nExistuje zaznam ktory nema vyplnene cislo objednavky."
              + "\nOpravte najskor chybny zaznam.");
    }
  }

  private void deleteMonitoring() {
    Monitoring monitoring = (Monitoring) ((IStructuredSelection) tableViewer
        .getSelection()).getFirstElement();
    if (monitoring != null) {
      if (monitoring.getOrderEntity() != null) {
        monitoring.getOrderEntity().getMonitoring().remove(monitoring);
        orderTableModel.orderChanged(monitoring.getOrderEntity());
      }
      tableViewer.remove(monitoring);
      int index = data.indexOf(monitoring);
      data.remove(monitoring);
      if (data.size() == index) {
        index--;
      }
      if (data.size() > index && index >= 0) {
        tableViewer.setSelection(new StructuredSelection(data.get(index)));
        table.setFocus();
      }
    }
  }

  private boolean isAddPossible() {
    boolean addPossible = true;
    if (data != null) {
      Iterator<Monitoring> iterator = data.iterator();
      while (iterator.hasNext() && addPossible) {
        Monitoring monitoring = iterator.next();
        if (monitoring.getOrderNumber().equals("?")) {
          addPossible = false;
        }
      }
    }
    return addPossible;
  }

  private void checkOrderNumber(Text orderNumberText) {
    String errorMessage = null;
    String orderNumber = orderNumberText.getText();
    if (orderNumber == null || !orderNumber.equals("?")) {
      if (orderNumber == null || orderNumber.length() == 0) {
        errorMessage = "Cislo objednavky nemoze byt prazdne!";
      } else if (!orderTableModel.getData()
          .contains(new Order(orderNumber, null, null, null))) {
        errorMessage = "Objednavka s cislom " + orderNumber + " neexistuje.";
      }
      if (errorMessage != null) {
        orderNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
            errorMessage);
      }
    }
  }

  private void filterMonitoring() {
    String fromDate = monitoringTable.getFromDate().getText();
    String toDate = monitoringTable.getToDate().getText();
    try {
      filterFromDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
    } catch (ParseException e) {
      filterFromDate = null;
      monitoringTable.getFromDate().setText("");
    }
    try {
      filterToDate = AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
    } catch (ParseException e) {
      filterToDate = null;
      monitoringTable.getToDate().setText("");
    }
    data = orderTableModel.getFilteredMonitoringRecords(filterFromDate,
        filterToDate, getFilterUserId());
    tableViewer.setInput(data);
  }

  private String getFilterUserId() {
    return monitoringTable.getUserId().getText().split("-")[0].trim();
  }
}
