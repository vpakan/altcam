package sk.altcam.assembly.order;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Comparator;

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
import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;
import sk.altcam.assembly.entity.Order;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class OrderTableModel {
  private static final String ITEM_NUMBER_NODE = "itemNumber";
  private static final String ORDER_NUMBER_NODE = "orderNumber";
  private static final String NUM_ITEMS_PLANNED_NODE = "numItemsPlanned";
  private static final String CYCLE_NODE = "cycle";
  private static final String NUM_ACTIVE_CAVITIES_NODE = "numActiveCavities";
  private static final String DATE_NODE = "date";
  private static final String SYS_DATE_NODE = "sysDate";
  private static final String SHIFT_NODE = "shift";
  private static final String MONITORINGS_NODE = "monitorings";
  private static final String MONITORING_NODE = "monitoring";
  private static final String MONITORING_DATE_NODE = "date";
  private static final String MONITORING_SYS_DATE_NODE = "sysDate";
  private static final String MONITORING_SHIFT_NODE = "shift";
  private static final String MONITORING_PIECES_NODE = "pieces";
  private static final String MONITORING_FROM_TIME_NODE = "fromTime";
  private static final String MONITORING_TO_TIME_NODE = "toTime";
  private static final String MONITORING_COMMENT_NODE = "comment";
  private static final String MONITORING_USER_ID_NODE = "userId";
  private static final String MACHINE_ID_NODE = "machineId";
  private static final String MONITORING_NOK_PIECES_PROCESSED_NODE = "nokProcessed";
  private static final String MONITORING_NOK_PIECES_LOADED_NODE = "nokLoaded";
  //TODO: remove latter - stays here only for backwards compatibility
  private static final String MONITORING_NOK_PIECES_NODE = "nokPieces";
  private static final String MONITORING_BREAK_NODE = "break";
  private static final String MONITORING_PAUSE_NODE = "pause";

  private static final String ORDER_NODE = "order";

  private TableViewer tableViewer = null;
  private LinkedList<Order> data = null;
  private final int[] sorting = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
  private Table table = null;

  private class CompareMonitoringByDate implements Comparator<Monitoring> {
    @Override
    public int compare(Monitoring monitoring1, Monitoring monitoring2) {
      int result = AssemblyMonitoring.sortString(monitoring1.getOrderNumber(),
          monitoring2.getOrderNumber());
      if (result == 0) {
        result = AssemblyMonitoring.sortDate(monitoring1.getDate(),
            monitoring2.getDate());
      }
      if (result == 0) {
        result = AssemblyMonitoring.sortDate(monitoring1.getSysDate(),
            monitoring2.getSysDate());
      }
      return result;
    }
  }

  public List<Order> getData() {
    return data;
  }

  public TableViewer getTableViewer() {
    return tableViewer;
  }

  private OrderTable orderTable = null;

  public OrderTableModel(OrderTable orderTable) {
    this.orderTable = orderTable;
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

  public LinkedList<Order> loadData() {
    LinkedList<Order> result = new LinkedList<Order>();
    File dataDir = new File(AssemblyMonitoring.DATA_LOCATION);
    FilenameFilter filenameFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".xml");
      }
    };
    for (File file : dataDir.listFiles(filenameFilter)) {
      Order order = loadOrder(file);
      if (order != null) {
        result.add(order);
      }
    }
    Collections.sort(result);
    return result;
  }

  private void initializeTableViewer() {
    table = this.orderTable.getTable();
    tableViewer = new TableViewer(table);
    EditorNavigationStrategy.setUpTableViewerNavigationStrategy(tableViewer);
    tableViewer.setColumnProperties(OrderTableLabelProvider.COLUMN_NAMES);
    tableViewer.setContentProvider(new OrdrerTableContentProvider(this));
    tableViewer.setLabelProvider(new OrderTableLabelProvider(this));
    data = loadData();
    for (int index = 0; index < table.getColumnCount(); index++) {
      final int fIndex = index;
      final OrderTableModel monitoringTableModel = this;
      table.getColumn(index).addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          tableViewer.setSorter(new OrderTableSorter(monitoringTableModel,
              fIndex, sorting[fIndex]));
          sorting[fIndex] = sorting[fIndex] * (-1);
        }
      });
    }
    CellEditor[] editors = new CellEditor[OrderTableLabelProvider.COLUMN_NAMES.length];
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
    editors[OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND] = orderNumberCellEditor;
    final TextCellEditor itemNumberCellEditor = new TextCellEditor(table);
    itemNumberCellEditor.addListener(new ICellEditorListener() {
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
        checkItemNumber((Text) itemNumberCellEditor.getControl());
      }
    });
    editors[OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND] = itemNumberCellEditor;
    TextCellEditor textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new DateVerifyListener());
    editors[OrderTableLabelProvider.MACHINE_ID_COLUMN_IND] = new TextCellEditor(
        table);
    editors[OrderTableLabelProvider.DATE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[OrderTableLabelProvider.NUM_PLANED_ITEMS_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    editors[OrderTableLabelProvider.CYCLE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[OrderTableLabelProvider.NUM_ACTIVE_CAVITIES_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[OrderTableLabelProvider.SHIFT_COLUMN_IND] = textEditor;
    tableViewer.setCellEditors(editors);
    tableViewer.setCellModifier(new OrderTableCellModifier(this));
  }

  private void initializeButtons() {
    orderTable.getClose().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        e.display.dispose();
      }
    });
    orderTable.getAdd().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        addOrder();
      }
    });
    orderTable.getDelete().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        deleteOrder();
      }
    });
  }

  public void orderChanged(Order order) {
    order.setSysDate(new Date());
    tableViewer.update(order, null);
    if (order.getOrderNumber() != null && !order.getOrderNumber().equals("?")) {
      saveOrder(order);
    }
  }

  private void addOrder() {
    if (isAddPossible()) {
      Order order = new Order("?", "?", "?", new Date());
      tableViewer.add(order);
      data.addLast(order);
      tableViewer.setSelection(new StructuredSelection(order));
      tableViewer.editElement(order,
          OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND);
    } else {
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
          "Nemozno pridat objednavku."
              + "\nExistuje objednavka ktora nema vyplnene cislo objednavky alebo cislo vyrobku"
              + "\nOpravte najskor chybnu objednavku.");
    }
  }

  private void deleteOrder() {
    Order order = (Order) ((IStructuredSelection) tableViewer.getSelection())
        .getFirstElement();
    if (order != null) {
      if (order.getMonitoring() == null || order.getMonitoring().size() == 0) {
        tableViewer.remove(order);
        int index = data.indexOf(order);
        data.remove(order);
        new File(AssemblyMonitoring.DATA_LOCATION + File.separator
            + (order.getOrderNumber() == null
                || order.getOrderNumber().equals("?") ? "unknown"
                    : order.getOrderNumber())
            + ".xml").delete();
        if (data.size() == index) {
          index--;
        }
        if (data.size() > index && index >= 0) {
          tableViewer.setSelection(new StructuredSelection(data.get(index)));
          table.setFocus();
        }
      } else {
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
            "Objednavka cislo " + order.getOrderNumber()
                + " nemoze byt zmazana."
                + "\nK tejto objednavke existuju zaznamy v monitoringu."
                + "\nZmazte najskor zaznamy v monitoringu patriace k tejto objednavke.");
      }
    }
  }

  private void checkOrderNumber(Text orderNumberText) {
    String errorMessage = null;
    String orderNumber = orderNumberText.getText();
    if (orderNumber == null || !orderNumber.equals("?")) {
      if (orderNumber == null || orderNumber.length() == 0) {
        errorMessage = "Cislo objednavky nemoze byt prazdne!";
      } else if (data.contains(new Order(orderNumber, null, null, null))) {
        errorMessage = "Objednavka s cislom " + orderNumber + " uz existuje!";
      }

      if (errorMessage != null) {
        orderNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
            errorMessage);
      }
    }
  }

  private void checkItemNumber(Text itemNumberText) {
    String errorMessage = null;
    String itemNumber = itemNumberText.getText();
    if (itemNumber == null || !itemNumber.equals("?")) {
      if (itemNumber == null || itemNumber.length() == 0) {
        errorMessage = "Cislo vyrobku nemoze byt prazdne!";
      }
      if (errorMessage != null) {
        itemNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
            errorMessage);
      }
    }
  }

  private boolean isAddPossible() {
    boolean addPossible = true;
    if (data != null) {
      Iterator<Order> iterator = data.iterator();
      while (iterator.hasNext() && addPossible) {
        Order order = iterator.next();
        if (order.getOrderNumber().equals("?")
            || order.getItemNumber().equals("?")) {
          addPossible = false;
        }
      }
    }
    return addPossible;
  }

  private Order loadOrder(File orderFile) {

    Order result = null;

    String errorDescription = null;
    SAXReader reader = new SAXReader();
    Document doc;
    try {
      doc = reader.read(orderFile);
      doc.normalize();
      Element rootNode = doc.getRootElement();
      if (rootNode.getName().equals(OrderTableModel.ORDER_NODE)) {
        String orderNumber = rootNode.element(OrderTableModel.ORDER_NUMBER_NODE)
            .getText();
        String itemNumber = rootNode.element(OrderTableModel.ITEM_NUMBER_NODE)
            .getText();
        int numItemsPlanned = Integer.parseInt(
            rootNode.element(OrderTableModel.NUM_ITEMS_PLANNED_NODE).getText());
        double cycle = Double
            .parseDouble(rootNode.element(OrderTableModel.CYCLE_NODE).getText());
        int numActiveCavities = Integer.parseInt(rootNode
            .element(OrderTableModel.NUM_ACTIVE_CAVITIES_NODE).getText());
        int shift = Integer
            .parseInt(rootNode.element(OrderTableModel.SHIFT_NODE).getText());
        String machineId = rootNode.element(OrderTableModel.MACHINE_ID_NODE)
            .getText();
        Date date = AssemblyMonitoring.STORAGE_DATE_FORMAT
            .parse(rootNode.element(OrderTableModel.DATE_NODE).getText());
        Date sysDate = AssemblyMonitoring.STORAGE_DATE_FORMAT
            .parse(rootNode.element(OrderTableModel.SYS_DATE_NODE).getText());
        result = new Order(orderNumber, itemNumber, machineId, date);
        result.setNumActiveCavities(numActiveCavities);
        result.setNumItemsPlanned(numItemsPlanned);
        result.setCycle(cycle);
        result.setShift(shift);
        result.setSysDate(sysDate);
        Element monitoringsNode = rootNode
            .element(OrderTableModel.MONITORINGS_NODE);
        if (monitoringsNode != null) {
          List monitoringElements = monitoringsNode
              .elements(OrderTableModel.MONITORING_NODE);
          LinkedList<Monitoring> monitorings = new LinkedList<Monitoring>();
          for (Object object : monitoringElements) {
            Element monitoring = (Element) object;
            int monitoringPieces = Integer.parseInt(monitoring
                .element(OrderTableModel.MONITORING_PIECES_NODE).getText());
            //TODO: remove latter - stays here only for backwards compatibility
            Element nonOkPieces = monitoring
                .element(OrderTableModel.MONITORING_NOK_PIECES_NODE);
            int monitoringNokProcessed = 0;
            int monitoringNokLoaded = 0;
            if (nonOkPieces != null){
              monitoringNokProcessed = Integer.parseInt(nonOkPieces.getText());  
            }
            else{
              monitoringNokProcessed = Integer.parseInt(monitoring
                  .element(OrderTableModel.MONITORING_NOK_PIECES_PROCESSED_NODE).getText());;
              monitoringNokLoaded = Integer.parseInt(monitoring
                  .element(OrderTableModel.MONITORING_NOK_PIECES_LOADED_NODE).getText());
            }            
            int monitoringBreak = Integer.parseInt(monitoring
                .element(OrderTableModel.MONITORING_BREAK_NODE).getText());
            int monitoringPause = Integer.parseInt(monitoring
                .element(OrderTableModel.MONITORING_PAUSE_NODE).getText());
            int monitoringShift = Integer.parseInt(monitoring
                .element(OrderTableModel.MONITORING_SHIFT_NODE).getText());
            String monitoringUserId = monitoring
                .element(OrderTableModel.MONITORING_USER_ID_NODE).getText();
            String monitoringComment = monitoring
                .element(OrderTableModel.MONITORING_COMMENT_NODE).getText();
            Date monitoringDate = AssemblyMonitoring.STORAGE_DATE_FORMAT
                .parse(monitoring.element(OrderTableModel.MONITORING_DATE_NODE)
                    .getText());
            Date monitoringSysDate = AssemblyMonitoring.STORAGE_DATE_FORMAT
                .parse(
                    monitoring.element(OrderTableModel.MONITORING_SYS_DATE_NODE)
                        .getText());
            Date monitoringFromTime = AssemblyMonitoring.STORAGE_DATE_FORMAT
                .parse(monitoring
                    .element(OrderTableModel.MONITORING_FROM_TIME_NODE)
                    .getText());
            Date monitoringToTime = AssemblyMonitoring.STORAGE_DATE_FORMAT
                .parse(
                    monitoring.element(OrderTableModel.MONITORING_TO_TIME_NODE)
                        .getText());
            Monitoring monitoringEntity = new Monitoring(result,
                result.getOrderNumber(), monitoringDate, monitoringUserId);
            monitoringEntity.setSysDate(monitoringSysDate);
            monitoringEntity.setFromTime(monitoringFromTime);
            monitoringEntity.setToTime(monitoringToTime);
            monitoringEntity.setShift(monitoringShift);
            monitoringEntity.setPieces(monitoringPieces);
            monitoringEntity.setComment(monitoringComment);
            monitoringEntity.setNonOkProcessed(monitoringNokProcessed);
            monitoringEntity.setNonOkLoaded(monitoringNokLoaded);
            monitoringEntity.setBreakTime(monitoringBreak);
            monitoringEntity.setPauseTime(monitoringPause);
            monitorings.addLast(monitoringEntity);
          }
          result.setMonitoring(monitorings);
        }
      } else {
        errorDescription = "Hlavny element suboru musi byt "
            + OrderTableModel.ORDER_NODE;
      }
    } catch (DocumentException e) {
      errorDescription = e.toString();
    } catch (NullPointerException jlnpe) {
      errorDescription = jlnpe.toString();
    } catch (ParseException pe) {
      errorDescription = pe.toString();
    }

    if (errorDescription != null) {
      errorDescription = "Subor " + orderFile.getAbsoluteFile()
          + " nebol uspesne nacitany.\n" + "Detaily chyby:\n"
          + errorDescription;
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba!",
          errorDescription);
      result = null;
    }
    return result;
  }

  private void saveOrder(Order order) {
    Document document = DocumentHelper.createDocument();
    Element orderElement = document.addElement(OrderTableModel.ORDER_NODE);
    orderElement.addElement(OrderTableModel.ORDER_NUMBER_NODE)
        .addText(order.getOrderNumber());
    orderElement.addElement(OrderTableModel.ITEM_NUMBER_NODE)
        .addText(order.getItemNumber());
    orderElement.addElement(OrderTableModel.MACHINE_ID_NODE)
        .addText(order.getMachineId());
    orderElement.addElement(OrderTableModel.DATE_NODE).addText(
        AssemblyMonitoring.STORAGE_DATE_FORMAT.format(order.getDate()));
    orderElement.addElement(OrderTableModel.SYS_DATE_NODE).addText(
        AssemblyMonitoring.STORAGE_DATE_FORMAT.format(order.getSysDate()));
    orderElement.addElement(OrderTableModel.CYCLE_NODE)
        .addText(String.valueOf(order.getCycle()));
    orderElement.addElement(OrderTableModel.SHIFT_NODE)
        .addText(String.valueOf(order.getShift()));
    orderElement.addElement(OrderTableModel.NUM_ACTIVE_CAVITIES_NODE)
        .addText(String.valueOf(order.getNumActiveCavities()));
    orderElement.addElement(OrderTableModel.NUM_ITEMS_PLANNED_NODE)
        .addText(String.valueOf(order.getNumItemsPlanned()));
    List<Monitoring> monitorings = order.getMonitoring();
    if (monitorings != null && monitorings.size() > 0) {
      Collections.sort(monitorings, new CompareMonitoringByDate());
      Element monitoringsElement = orderElement
          .addElement(OrderTableModel.MONITORINGS_NODE);
      for (Monitoring monitoring : monitorings) {
        if (monitoring.getOrderNumber() != "?"
            && !monitoring.getOrderNumber().equals(order.getOrderNumber())) {
          MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
              "Data objednavky c.: " + order.getOrderNumber()
                  + " obsahuju zaznamy monitoringu ktore maju cislo objednavky: "
                  + monitoring.getOrderNumber()
                  + ".\nZapiste si obidve cisla objednavky a opravte data rucne");
        }
        Element monitoringElement = monitoringsElement
            .addElement(OrderTableModel.MONITORING_NODE);
        monitoringElement.addElement(OrderTableModel.MONITORING_DATE_NODE)
            .addText(AssemblyMonitoring.STORAGE_DATE_FORMAT
                .format(monitoring.getDate()));
        monitoringElement.addElement(OrderTableModel.MONITORING_SYS_DATE_NODE)
            .addText(AssemblyMonitoring.STORAGE_DATE_FORMAT
                .format(monitoring.getSysDate()));
        monitoringElement.addElement(OrderTableModel.MONITORING_SHIFT_NODE)
            .addText(String.valueOf(monitoring.getShift()));
        monitoringElement.addElement(OrderTableModel.MONITORING_PIECES_NODE)
            .addText(String.valueOf(monitoring.getPieces()));
        monitoringElement.addElement(OrderTableModel.MONITORING_NOK_PIECES_PROCESSED_NODE)
            .addText(String.valueOf(monitoring.getNonOkProcessed()));
        monitoringElement.addElement(OrderTableModel.MONITORING_NOK_PIECES_LOADED_NODE)
            .addText(String.valueOf(monitoring.getNonOkLoaded()));
        monitoringElement.addElement(OrderTableModel.MONITORING_BREAK_NODE)
            .addText(String.valueOf(monitoring.getBreakTime()));
        monitoringElement.addElement(OrderTableModel.MONITORING_PAUSE_NODE)
            .addText(String.valueOf(monitoring.getPauseTime()));
        monitoringElement.addElement(OrderTableModel.MONITORING_FROM_TIME_NODE)
            .addText(AssemblyMonitoring.STORAGE_DATE_FORMAT
                .format(monitoring.getFromTime()));
        monitoringElement.addElement(OrderTableModel.MONITORING_TO_TIME_NODE)
            .addText(AssemblyMonitoring.STORAGE_DATE_FORMAT
                .format(monitoring.getToTime()));
        monitoringElement.addElement(OrderTableModel.MONITORING_COMMENT_NODE)
            .addText(monitoring.getComment());
        monitoringElement.addElement(OrderTableModel.MONITORING_USER_ID_NODE)
            .addText(monitoring.getUserId());

      }
    }
    // Pretty print the document to System.out
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter writer;
    try {
      writer = new XMLWriter(
          new FileWriter(AssemblyMonitoring.DATA_LOCATION + File.separator
              + (order.getOrderNumber() == null
                  || order.getOrderNumber().equals("?") ? "unknown"
                      : order.getOrderNumber())
              + ".xml"),
          format);
      writer.write(document);
      writer.close();
    } catch (IOException e) {
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
          "Chyba pri zapise dat!\n" + "Data neboli spravne ulozene!\n" + e);
    }
  }

  public LinkedList<Monitoring> getFilteredMonitoringRecords(Date fromDate,
      Date toDate, String userId) {
    LinkedList<Monitoring> result = new LinkedList<Monitoring>();
    if (data != null) {
      for (Order order : data) {
        List<Monitoring> monitorings = order.getMonitoring();
        if (monitorings != null) {
          for (Monitoring monitoring : monitorings) {
            if (monitoring.getUserId().equals(userId) && AssemblyMonitoring
                .isDateWithinPeriod(monitoring.getDate(), fromDate, toDate)) {
              result.add(monitoring);
            }
          }
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  public String getValueForRenamedColumn(Element parentNode, String newNodeName,
      String oldNodeName) {

    Element valueNode = parentNode.element(newNodeName);
    if (valueNode != null) {
      return valueNode.getText();
    } else {
      valueNode = parentNode.element(oldNodeName);
      if (valueNode != null) {
        return "0";
      } else {
        throw new NullPointerException("Wrong input file format");
      }
    }
  }
}
