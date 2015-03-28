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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import sk.altcam.assembly.DateVerifyListener;
import sk.altcam.assembly.EditorNavigationStrategy;
import sk.altcam.assembly.IntegerVerifyListener;
import sk.altcam.assembly.MessageBox;
import sk.altcam.assembly.ProductionMonitoring;
import sk.altcam.assembly.entity.Monitoring;
import sk.altcam.assembly.entity.Order;
import sk.altcam.assembly.monitoring.MonitoringTable;

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
  private static final String MONITORING_PRODUCED_NODE = "produced";
  private static final String MONITORING_ORDER_NODE = "order";
  private static final String MACHINE_ID_NODE = "machineId";
  private static final String PLANNED_IDLE_TIME_NODE = "plannedIdleTime";
  private static final String FORM_EXCHANGE_NODE = "formExchange";
  private static final String NABEHOVE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[0].replaceAll(" ", "");
  private static final String PRESTREKY_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[1].replaceAll(" ", ""); 
  private static final String NEDOSTREKY_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[2].replaceAll(" ", "");
  private static final String ZHORENE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[3].replaceAll(" ", "");
  private static final String VADA_MATERIALU_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[4].replaceAll(" ", "");
  private static final String DEFORMACIE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[5].replaceAll(" ", "");
  private static final String POSKODENY_ZASTREK_ZALIS_NODE  = MonitoringTable.BROKEN_COLUMN_LABELS[6].replaceAll(" ", "");
  private static final String CHYBAJUCI_ZASTREK_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[7].replaceAll(" ", "");
  private static final String BOD_VSTREKU_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[8].replaceAll(" ", "");
  private static final String PRITOMNOST_PRUZNYCH_ELEMENTOV_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[9].replaceAll(" ", "");
  private static final String VYHADZANE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[10].replaceAll(" ", "");
  private static final String PRESTREKY_V_OTVOROCH_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[11].replaceAll(" ", "");
  private static final String FARBA_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[12].replaceAll(" ", "");
  private static final String ZASTAVENIE_STROJA_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[13].replaceAll(" ", "");
  private static final String INE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[14].replaceAll(" ", "");
  private static final String NEDOLISOVANE_STROJ_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[15].replaceAll(" ", "");
  private static final String NEDOLISOVANE_FORMA_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[16].replaceAll(" ", "");
  private static final String NEDOLISOVANE_PERIFERIE_NODE = MonitoringTable.BROKEN_COLUMN_LABELS[17].replaceAll(" ", "");
  
  private static final String ORDER_NODE = "order";
  
  private TableViewer tableViewer = null;
  private LinkedList<Order> data = null;
  private final int[] sorting = new int[]{1,1,1,1,1,1,1,1};
  private Table table = null;

  private class CompareMonitoringByDate implements Comparator<Monitoring> {
    @Override
    public int compare(Monitoring monitoring1, Monitoring monitoring2) {
      int result = ProductionMonitoring.sortString(monitoring1.getOrderNumber(), monitoring2.getOrderNumber());
      if (result == 0){
        result = ProductionMonitoring.sortDate(monitoring1.getDate(), monitoring2.getDate());
      }
      if (result == 0){
        result = ProductionMonitoring.sortDate(monitoring1.getSysDate(), monitoring2.getSysDate());
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
  
  public OrderTableModel(OrderTable monitoringTable){
    this.orderTable = monitoringTable;
    initializeTableViewer();
    initializeButtons();
    tableViewer.setInput(data);
    for (int index = 0 ; index < table.getColumnCount() ; index++){
      table.getColumn(index).pack();
    }
    table.pack();
  }
  
  public LinkedList<Order> loadData(){
    LinkedList<Order> result = new LinkedList<Order>();
    File dataDir = new File(ProductionMonitoring.DATA_LOCATION);
    FilenameFilter filenameFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(".xml");
      }
    };
    for (File file : dataDir.listFiles(filenameFilter)){
      Order order = loadOrder(file);
      if (order != null){
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
        checkOrderNumber((Text)orderNumberCellEditor.getControl());
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
        checkItemNumber((Text)itemNumberCellEditor.getControl());
      }
    });
    editors[OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND] = itemNumberCellEditor;
    TextCellEditor textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new DateVerifyListener());
    editors[OrderTableLabelProvider.MACHINE_ID_COLUMN_IND] = new TextCellEditor(table);
    editors[OrderTableLabelProvider.DATE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[OrderTableLabelProvider.NUM_PLANED_ITEMS_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
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
  
  private void initializeButtons(){
    orderTable.getClose().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        e.display.dispose();
      }
    });
    orderTable.getAdd().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        addOrder();
      }
    });
    orderTable.getDelete().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        deleteOrder();
      }
    });
  }
  public void orderChanged(Order order) {
    order.setSysDate(new Date());
    tableViewer.update(order, null);
    if (order.getOrderNumber() != null && !order.getOrderNumber().equals("?")){
      saveOrder(order);  
    }
  }
  private void addOrder(){
    if (isAddPossible()){
      Order order = new Order("?","?", "?" ,new Date());
      tableViewer.add(order);
      data.addLast(order);
      tableViewer.setSelection(new StructuredSelection(order));
      tableViewer.editElement(order, OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND);
    }
    else{
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
        "Nemozno pridat objednavku." +
        "\nExistuje objednavka ktora nema vyplnene cislo objednavky alebo cislo vyrobku" +
        "\nOpravte najskor chybnu objednavku.");
    }
  }
  private void deleteOrder(){
    Order order = (Order) ((IStructuredSelection) 
        tableViewer.getSelection()).getFirstElement();
    if (order != null) {
      if (order.getMonitoring() == null || order.getMonitoring().size() == 0){
        tableViewer.remove(order);
        int index = data.indexOf(order);
        data.remove(order);
        new File(ProductionMonitoring.DATA_LOCATION + 
              File.separator + 
              (order.getOrderNumber() == null || order.getOrderNumber().equals("?") ? "unknown" : order.getOrderNumber()) +
              ".xml").delete();
        if (data.size() == index){
          index--;
        }
        if (data.size() > index && index >= 0){
          tableViewer.setSelection(new StructuredSelection(data.get(index)));
          table.setFocus();
        }        
      }
      else{
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
          "Objednavka cislo " + order.getOrderNumber() + " nemoze byt zmazana." +
          "\nK tejto objednavke existuju zaznamy v monitoringu." +
          "\nZmazte najskor zaznamy v monitoringu patriace k tejto objednavke.");
      }
    }  
  }
  
  private void checkOrderNumber (Text orderNumberText){
    String errorMessage = null;
    String orderNumber = orderNumberText.getText();
    if (orderNumber == null || !orderNumber.equals("?")){
      if (orderNumber == null || orderNumber.length() == 0){
        errorMessage = "Cislo objednavky nemoze byt prazdne!";
      } else if (data.contains(new Order(orderNumber, null, null, null))){
        errorMessage = "Objednavka s cislom " + orderNumber + " uz existuje!";
      }
      
      if (errorMessage != null){
        orderNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !", 
          errorMessage);
      }
    }
  }
  
  private void checkItemNumber (Text itemNumberText) {
    String errorMessage = null;
    String itemNumber = itemNumberText.getText();
    if (itemNumber == null || !itemNumber.equals("?")){
      if (itemNumber == null || itemNumber.length() == 0){
        errorMessage = "Cislo vyrobku nemoze byt prazdne!";
      }
      if (errorMessage != null){
        itemNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !", 
          errorMessage);
      }
    }
  }
  private boolean isAddPossible(){
    boolean addPossible = true;
    if (data != null){
      Iterator<Order> iterator = data.iterator();
      while (iterator.hasNext() && addPossible){
        Order order = iterator.next();
        if (order.getOrderNumber().equals("?") || order.getItemNumber().equals("?")){
          addPossible = false;
        }
      }
    }
    return addPossible;
  }
  
  private Order loadOrder(File orderFile){

    Order result = null;
    
    String errorDescription = null;
    SAXReader reader = new SAXReader();
    Document doc;
    try {
      doc = reader.read(orderFile);
      doc.normalize();
      Element rootNode = doc.getRootElement();
      if (rootNode.getName().equals(OrderTableModel.ORDER_NODE)){
        String orderNumber = rootNode.element(OrderTableModel.ORDER_NUMBER_NODE).getText();
        String itemNumber = rootNode.element(OrderTableModel.ITEM_NUMBER_NODE).getText();
        int numItemsPlanned = Integer.parseInt(rootNode.element(OrderTableModel.NUM_ITEMS_PLANNED_NODE).getText());
        int cycle = Integer.parseInt(rootNode.element(OrderTableModel.CYCLE_NODE).getText());
        int numActiveCavities = Integer.parseInt(rootNode.element(OrderTableModel.NUM_ACTIVE_CAVITIES_NODE).getText());
        int shift = Integer.parseInt(rootNode.element(OrderTableModel.SHIFT_NODE).getText());
        String machineId = rootNode.element(OrderTableModel.MACHINE_ID_NODE).getText();
        Date date = ProductionMonitoring.STORAGE_DATE_FORMAT.parse(rootNode.element(OrderTableModel.DATE_NODE).getText());
        Date sysDate = ProductionMonitoring.STORAGE_DATE_FORMAT.parse(rootNode.element(OrderTableModel.SYS_DATE_NODE).getText());
        result = new Order(orderNumber, itemNumber, machineId ,date);
        result.setNumActiveCavities(numActiveCavities);
        result.setNumItemsPlanned(numItemsPlanned);
        result.setCycle(cycle);
        result.setShift(shift);
        result.setSysDate(sysDate);
        Element monitoringsNode =  rootNode.element(OrderTableModel.MONITORINGS_NODE);
        if (monitoringsNode != null){
          List monitoringElements = monitoringsNode.elements(OrderTableModel.MONITORING_NODE);
          LinkedList<Monitoring> monitorings = new LinkedList<Monitoring>();
          for (Object object : monitoringElements){
            Element monitoring = (Element)object;
            int monitoringOrder = Integer.parseInt(monitoring.element(OrderTableModel.MONITORING_ORDER_NODE).getText());
            int monitoringProduced = Integer.parseInt(monitoring.element(OrderTableModel.MONITORING_PRODUCED_NODE).getText());
            int plannedIdleTime = Integer.parseInt(monitoring.element(OrderTableModel.PLANNED_IDLE_TIME_NODE).getText());
            int formExchange = Integer.parseInt(monitoring.element(OrderTableModel.FORM_EXCHANGE_NODE).getText());
            int nabehove = Integer.parseInt(monitoring.element(OrderTableModel.NABEHOVE_NODE).getText());
            int prestreky = Integer.parseInt(monitoring.element(OrderTableModel.PRESTREKY_NODE).getText());
            int nedostreky = Integer.parseInt(getValueForRenamedColumn(monitoring,OrderTableModel.NEDOSTREKY_NODE,"Otrepy"));
            int zhorene = Integer.parseInt(monitoring.element(OrderTableModel.ZHORENE_NODE).getText());
            int vadaMaterialu = Integer.parseInt(monitoring.element(OrderTableModel.VADA_MATERIALU_NODE).getText());
            int deformacie = Integer.parseInt(getValueForRenamedColumn(monitoring,OrderTableModel.DEFORMACIE_NODE,"N.o.za."));
            int poskodenyZastrekZalis = Integer.parseInt(monitoring.element(OrderTableModel.POSKODENY_ZASTREK_ZALIS_NODE).getText());
            int chybajuciZastrek = Integer.parseInt(getValueForRenamedColumn(monitoring,OrderTableModel.CHYBAJUCI_ZASTREK_NODE,"N.poj."));
            int bodVstreku = Integer.parseInt(monitoring.element(OrderTableModel.BOD_VSTREKU_NODE).getText());
            int pritomnostPruznychElementov = Integer.parseInt(monitoring.element(OrderTableModel.PRITOMNOST_PRUZNYCH_ELEMENTOV_NODE).getText());
            int vyhadzane = Integer.parseInt(monitoring.element(OrderTableModel.VYHADZANE_NODE).getText());
            int prestrekyVOtvoroch = Integer.parseInt(monitoring.element(OrderTableModel.PRESTREKY_V_OTVOROCH_NODE).getText());
            int farba = Integer.parseInt(monitoring.element(OrderTableModel.FARBA_NODE).getText());
            int zastavenieStroja = Integer.parseInt(monitoring.element(OrderTableModel.ZASTAVENIE_STROJA_NODE).getText());
            int ine = Integer.parseInt(monitoring.element(OrderTableModel.INE_NODE).getText());
            int nedolisovaneStroj = Integer.parseInt(monitoring.element(OrderTableModel.NEDOLISOVANE_STROJ_NODE).getText());
            int nedolisovaneForma = Integer.parseInt(monitoring.element(OrderTableModel.NEDOLISOVANE_FORMA_NODE).getText());
            int nedolisovanePeriferie = Integer.parseInt(monitoring.element(OrderTableModel.NEDOLISOVANE_PERIFERIE_NODE).getText());
            Date monitoringDate = ProductionMonitoring.STORAGE_DATE_FORMAT.parse(monitoring.element(OrderTableModel.MONITORING_DATE_NODE).getText());
            Date monitoringSysDate = ProductionMonitoring.STORAGE_DATE_FORMAT.parse(monitoring.element(OrderTableModel.MONITORING_SYS_DATE_NODE).getText());
            Monitoring monitoringEntity = new Monitoring(result, result.getOrderNumber(), monitoringDate);
            monitoringEntity.setNabehove(nabehove);
            monitoringEntity.setPrestreky(prestreky);
            monitoringEntity.setNedostreky(nedostreky);
            monitoringEntity.setZhorene(zhorene);
            monitoringEntity.setVadaMaterialu(vadaMaterialu);
            monitoringEntity.setDeformacie(deformacie);
            monitoringEntity.setPoskodenyZastrekZalis(poskodenyZastrekZalis);
            monitoringEntity.setChybajuciZastrek(chybajuciZastrek);
            monitoringEntity.setBodVstreku(bodVstreku);
            monitoringEntity.setPritomnostPruznychElementov(pritomnostPruznychElementov);
            monitoringEntity.setVyhadzane(vyhadzane);
            monitoringEntity.setPrestrekyVOtvoroch(prestrekyVOtvoroch);
            monitoringEntity.setFarba(farba);
            monitoringEntity.setZastavenieStroja(zastavenieStroja);
            monitoringEntity.setIne(ine);         
            monitoringEntity.setOrder(monitoringOrder);
            monitoringEntity.setProduced(monitoringProduced);
            monitoringEntity.setSysDate(monitoringSysDate);
            monitoringEntity.setNedolisovaneStroj(nedolisovaneStroj);
            monitoringEntity.setNedolisovaneForma(nedolisovaneForma);
            monitoringEntity.setNedolisovanePeriferie(nedolisovanePeriferie);
            monitoringEntity.setPlannedIdleTime(plannedIdleTime);
            monitoringEntity.setFormExchange(formExchange);
            monitorings.addLast(monitoringEntity);
          }
          result.setMonitoring(monitorings);
        }
      }
      else{
        errorDescription = "Hlavny element suboru musi byt " + OrderTableModel.ORDER_NODE;
      }
    } catch (DocumentException e) {
      errorDescription = e.toString();
    } catch (NullPointerException jlnpe){
      errorDescription = jlnpe.toString();
    } catch (ParseException pe){
      errorDescription = pe.toString();
    }

    if (errorDescription != null){
      errorDescription = "Subor " + orderFile.getAbsoluteFile() + " nebol uspesne nacitany.\n" +
        "Detaily chyby:\n" +
        errorDescription;
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba!",
         errorDescription);
      result = null;
    }
    return result;
  }
  
  private void saveOrder(Order order){
    Document document = DocumentHelper.createDocument();
    Element orderElement = document.addElement(OrderTableModel.ORDER_NODE);
    orderElement.addElement(OrderTableModel.ORDER_NUMBER_NODE)
      .addText(order.getOrderNumber());
    orderElement.addElement(OrderTableModel.ITEM_NUMBER_NODE)
      .addText(order.getItemNumber());
    orderElement.addElement(OrderTableModel.MACHINE_ID_NODE)
      .addText(order.getMachineId());
    orderElement.addElement(OrderTableModel.DATE_NODE)
      .addText(ProductionMonitoring.STORAGE_DATE_FORMAT.format(order.getDate()));
    orderElement.addElement(OrderTableModel.SYS_DATE_NODE)
      .addText(ProductionMonitoring.STORAGE_DATE_FORMAT.format(order.getSysDate()));
    orderElement.addElement(OrderTableModel.CYCLE_NODE)
      .addText(String.valueOf(order.getCycle()));
    orderElement.addElement(OrderTableModel.SHIFT_NODE)
      .addText(String.valueOf(order.getShift()));
    orderElement.addElement(OrderTableModel.NUM_ACTIVE_CAVITIES_NODE)
      .addText(String.valueOf(order.getNumActiveCavities()));
    orderElement.addElement(OrderTableModel.NUM_ITEMS_PLANNED_NODE)
      .addText(String.valueOf(order.getNumItemsPlanned()));
    List<Monitoring> monitorings = order.getMonitoring();
    if (monitorings != null && monitorings.size() > 0){
      Collections.sort(monitorings, new CompareMonitoringByDate());
      Element monitoringsElement = orderElement.addElement(OrderTableModel.MONITORINGS_NODE);
      int newOrder = 1;
      for (Monitoring monitoring : monitorings){
        if (monitoring.getOrderNumber() != "?" && 
            !monitoring.getOrderNumber().equals(order.getOrderNumber())){
          MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
            "Data objednavky c.: " + order.getOrderNumber() +
            " obsahuju zaznamy monitoringu ktore maju cislo objednavky: " + monitoring.getOrderNumber() +
            ".\nZapiste si obidve cisla objednavky a opravte data rucne");
        }
        Element monitoringElement = monitoringsElement.addElement(OrderTableModel.MONITORING_NODE); 
        monitoringElement.addElement(OrderTableModel.MONITORING_DATE_NODE)
          .addText(ProductionMonitoring.STORAGE_DATE_FORMAT.format(monitoring.getDate()));
        monitoringElement.addElement(OrderTableModel.MONITORING_SYS_DATE_NODE)
          .addText(ProductionMonitoring.STORAGE_DATE_FORMAT.format(monitoring.getSysDate()));
        monitoringElement.addElement(OrderTableModel.PLANNED_IDLE_TIME_NODE)
            .addText(String.valueOf(monitoring.getPlannedIdleTime()));
        monitoringElement.addElement(OrderTableModel.FORM_EXCHANGE_NODE)
          .addText(String.valueOf(monitoring.getFormExchange()));
        monitoringElement.addElement(OrderTableModel.MONITORING_PRODUCED_NODE)
            .addText(String.valueOf(monitoring.getProduced()));
        monitoringElement.addElement(OrderTableModel.NABEHOVE_NODE).addText(
            String.valueOf(monitoring.getNabehove()));
        monitoringElement.addElement(OrderTableModel.PRESTREKY_NODE).addText(
            String.valueOf(monitoring.getPrestreky()));
        monitoringElement.addElement(OrderTableModel.NEDOSTREKY_NODE).addText(
            String.valueOf(monitoring.getNedostreky()));
        monitoringElement.addElement(OrderTableModel.ZHORENE_NODE).addText(
            String.valueOf(monitoring.getZhorene()));
        monitoringElement.addElement(OrderTableModel.VADA_MATERIALU_NODE)
            .addText(String.valueOf(monitoring.getVadaMaterialu()));
        monitoringElement.addElement(
            OrderTableModel.DEFORMACIE_NODE).addText(
            String.valueOf(monitoring.getDeformacie()));
        monitoringElement.addElement(
            OrderTableModel.POSKODENY_ZASTREK_ZALIS_NODE).addText(
            String.valueOf(monitoring.getPoskodenyZastrekZalis()));
        monitoringElement.addElement(
            OrderTableModel.CHYBAJUCI_ZASTREK_NODE).addText(
            String.valueOf(monitoring.getChybajuciZastrek()));
        monitoringElement.addElement(OrderTableModel.BOD_VSTREKU_NODE).addText(
            String.valueOf(monitoring.getBodVstreku()));
        monitoringElement.addElement(
            OrderTableModel.PRITOMNOST_PRUZNYCH_ELEMENTOV_NODE).addText(
            String.valueOf(monitoring.getPritomnostPruznychElementov()));
        monitoringElement.addElement(OrderTableModel.VYHADZANE_NODE).addText(
            String.valueOf(monitoring.getVyhadzane()));
        monitoringElement.addElement(OrderTableModel.PRESTREKY_V_OTVOROCH_NODE)
            .addText(String.valueOf(monitoring.getPrestrekyVOtvoroch()));
        monitoringElement.addElement(OrderTableModel.FARBA_NODE).addText(
            String.valueOf(monitoring.getFarba()));
        monitoringElement.addElement(OrderTableModel.ZASTAVENIE_STROJA_NODE)
            .addText(String.valueOf(monitoring.getZastavenieStroja()));
        monitoringElement.addElement(OrderTableModel.INE_NODE).addText(
            String.valueOf(monitoring.getIne()));
        monitoringElement.addElement(OrderTableModel.NEDOLISOVANE_STROJ_NODE).addText(
            String.valueOf(monitoring.getNedolisovaneStroj()));
        monitoringElement.addElement(OrderTableModel.NEDOLISOVANE_FORMA_NODE).addText(
            String.valueOf(monitoring.getNedolisovaneForma()));
        monitoringElement.addElement(OrderTableModel.NEDOLISOVANE_PERIFERIE_NODE).addText(
            String.valueOf(monitoring.getNedolisovanePeriferie()));
        monitoringElement.addElement(OrderTableModel.MONITORING_ORDER_NODE)
          .addText(String.valueOf(newOrder++));
      }
    }
    // Pretty print the document to System.out
    OutputFormat format = OutputFormat.createPrettyPrint();
    XMLWriter writer;
    try {
      writer = new XMLWriter( new FileWriter(ProductionMonitoring.DATA_LOCATION + 
          File.separator + 
          (order.getOrderNumber() == null || order.getOrderNumber().equals("?") ? "unknown" : order.getOrderNumber()) +
          ".xml")
       , format );
      writer.write( document );
      writer.close();
    } catch (IOException e) {
      MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !",
        "Chyba pri zapise dat!\n" +
        "Data neboli spravne ulozene!\n"+ e);
    }
  }
  
  public LinkedList<Monitoring> getFilteredMonitoringRecords(Date fromDate, Date toDate){
    LinkedList<Monitoring> result = new LinkedList<Monitoring>();
    if (data != null){
      for (Order order : data){
        List<Monitoring> monitorings = order.getMonitoring();
        if (monitorings != null){
          for (Monitoring monitoring : monitorings){
            if (ProductionMonitoring.isDateWithinPeriod(monitoring.getDate(), fromDate, toDate)){
              result.add(monitoring);
            }
          }
        }
      }
    }
    Collections.sort(result);
    return result;
  }
  
  public String getValueForRenamedColumn (Element parentNode, String newNodeName, String oldNodeName){
    
    Element valueNode = parentNode.element(newNodeName);
    if (valueNode != null){
      return valueNode.getText(); 
    }
    else{
      valueNode = parentNode.element(oldNodeName);
      if (valueNode != null){
        return "0";
      }
      else{
        throw new NullPointerException("Wrong input file format"); 
      }
    }
  }
}
