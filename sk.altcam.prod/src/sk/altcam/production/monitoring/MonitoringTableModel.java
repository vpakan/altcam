package sk.altcam.production.monitoring;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import sk.altcam.production.DateVerifyListener;
import sk.altcam.production.EditorNavigationStrategy;
import sk.altcam.production.IntegerVerifyListener;
import sk.altcam.production.MessageBox;
import sk.altcam.production.ProductionMonitoring;
import sk.altcam.production.entity.Monitoring;
import sk.altcam.production.entity.Order;
import sk.altcam.production.order.OrderTableModel;

public class MonitoringTableModel {
  private TableViewer tableViewer = null;
  private LinkedList<Monitoring> data = null;
  private final int[] sorting = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
  private Date filterFromDate = null;
  private Date filterToDate = null;
  private Table table = null;
  private MonitoringTable monitoringTable = null;
  private OrderTableModel orderTableModel = null;
  
  public List<Monitoring> getData() {
    return data;
  }

  public TableViewer getTableViewer() {
    return tableViewer;
  }

  public MonitoringTableModel(MonitoringTable monitoringTable , OrderTableModel orderTableModel){
    this.monitoringTable = monitoringTable;
    this.orderTableModel = orderTableModel;
    initializeFilter();
    initializeTableViewer();
    initializeButtons();
    tableViewer.setInput(data);
    for (int index = 0 ; index < table.getColumnCount() ; index++){
      table.getColumn(index).pack();
    }
    table.pack();
  }
  
  public LinkedList<Monitoring> loadData(){
    LinkedList<Monitoring> result = new LinkedList<Monitoring>();
    result = orderTableModel.getFilteredMonitoringRecords(filterFromDate, filterToDate);
    return result;
  }
  
  public int calcSumTotal(Monitoring monitoring) {
    return monitoring.getProduced() + calcSumBroken(monitoring);
  }
  
  public int calcSumPpmBroken(Monitoring monitoring) {
    return monitoring.getPrestreky() + monitoring.getNedostreky() +
      monitoring.getZhorene() + monitoring.getVadaMaterialu() + monitoring.getDeformacie() +
      monitoring.getPoskodenyZastrekZalis() + monitoring.getChybajuciZastrek() + monitoring.getBodVstreku() +
      monitoring.getPritomnostPruznychElementov() + monitoring.getVyhadzane() + monitoring.getPrestrekyVOtvoroch() +
      monitoring.getFarba() + monitoring.getZastavenieStroja() + monitoring.getIne() +
      monitoring.getNedolisovaneForma() + monitoring.getNedolisovanePeriferie() + monitoring.getNedolisovaneStroj();
  }

  public int calcSumBroken(Monitoring monitoring) {
    return monitoring.getNabehove() + calcSumPpmBroken(monitoring);
  }
  
  private void initializeFilter(){
    Date today = new Date();
    filterFromDate = ProductionMonitoring.getFirstDayOfMonth(today);
    filterToDate = ProductionMonitoring.getLastDayOfMonth(today);
    monitoringTable.getFromDate().setText(
      ProductionMonitoring.DISPLAY_DATE_FORMAT.format(filterFromDate));
    monitoringTable.getToDate().setText(
        ProductionMonitoring.DISPLAY_DATE_FORMAT.format(filterToDate));

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
    // Order
    editors[MonitoringTableLabelProvider.ORDER_COLUMN_IND] = null;
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
    editors[MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND] = orderNumberCellEditor;
    TextCellEditor textEditor = new TextCellEditor(table);
    editors[MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND] = null;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new DateVerifyListener());
    editors[MonitoringTableLabelProvider.DATE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PLANNED_IDLE_TIME_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.FORM_SWITCH_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PRODUCED_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NABEHOVE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PRESTREKY_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NEDOSTREKY_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.ZHORENE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.VADA_MATERIALU_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.DEFORMACIE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.POSKODENY_ZASTREK_ZALIS_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.CHYBAJUCI_ZASTREK_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.BOD_VSTREKU_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.VYHADZANE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.PRESTREKY_V_OTVOROCH_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.FARBA_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.ZASTAVENIE_STROJA_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.INE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NEDOLISOVANE_STROJ_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NEDOLISOVANE_FORMA_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    ((Text) textEditor.getControl())
        .addVerifyListener(new IntegerVerifyListener());
    editors[MonitoringTableLabelProvider.NEDOLISOVANE_PERIFERIE_COLUMN_IND] = textEditor;
    textEditor = new TextCellEditor(table);
    editors[MonitoringTableLabelProvider.SUM_BROKEN_COLUMN_IND] = null;
    textEditor = new TextCellEditor(table);
    editors[MonitoringTableLabelProvider.SUM_TOTAL_COLUMN_IND] = null;
    tableViewer.setCellEditors(editors);
    tableViewer.setCellModifier(new MonitoringTableCellModifier(this));
  }
  
  private void initializeButtons(){
    monitoringTable.getClose().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        e.display.dispose();
      }
    });
    monitoringTable.getAdd().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        addMonitoring();
      }
    });
    monitoringTable.getDelete().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        deleteMonitoring();
      }
    });
    monitoringTable.getFilter().addSelectionListener(new SelectionAdapter() {
      public void widgetSelected( SelectionEvent e ){
        filterMonitoring();
      }
    });
  }
  public void monitoringChanged(Monitoring monitoring) {
    // set order entity
    if (monitoring.getOrderEntity() == null){
      int index = orderTableModel.getData().indexOf(new Order(monitoring.getOrderNumber(), null , null, null));
      if (index >= 0){
        Order order = orderTableModel.getData().get(index);
        monitoring.setOrderEntity(order);
        getData().get(getData().indexOf(monitoring)).setOrderEntity(order);
        if (order.getMonitoring() == null){
          order.setMonitoring(new LinkedList<Monitoring>());
        }
        order.getMonitoring().add(order.getMonitoring().size(),monitoring);
        //orderTableModel.getTableViewer().update(order,null);
        int orderIndex = getNextOrder(monitoring.getOrderNumber());
        monitoring.setOrder(orderIndex);
        Monitoring monitoringData = getData().get(getData().indexOf(monitoring));
        monitoringData.setOrder(orderIndex);
      }
    }
    else{
      if (!monitoring.getOrderEntity().getMonitoring().contains(monitoring)){
        Order order = monitoring.getOrderEntity();
        order.getMonitoring().add(order.getMonitoring().size(),monitoring);
        //orderTableModel.getTableViewer().update(order,null);
      }
    }
    monitoring.setSysDate(new Date());
    tableViewer.update(monitoring, null);
    if (monitoring.getOrderEntity() != null){
      orderTableModel.orderChanged(monitoring.getOrderEntity());  
    }
  }
  private void addMonitoring(){
    if (isAddPossible()) {
      Monitoring monitoring = new Monitoring(null, "?", new Date());
      monitoring.setOrder(-1);
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
  private void deleteMonitoring(){
    Monitoring monitoring = (Monitoring) ((IStructuredSelection) 
        tableViewer.getSelection()).getFirstElement();
    if (monitoring != null) {
      if(monitoring.getOrderEntity() != null){
        monitoring.getOrderEntity().getMonitoring().remove(monitoring);
        orderTableModel.orderChanged(monitoring.getOrderEntity());
      }
      tableViewer.remove(monitoring);
      int index = data.indexOf(monitoring);
      data.remove(monitoring);
      if (data.size() == index){
        index--;
      }
      if (data.size() > index && index >= 0){
        tableViewer.setSelection(new StructuredSelection(data.get(index)));
        table.setFocus();
      }
    }  
  }
  private boolean isAddPossible(){
    boolean addPossible = true;
    if (data != null){
      Iterator<Monitoring> iterator = data.iterator();
      while (iterator.hasNext() && addPossible){
        Monitoring monitoring = iterator.next();
        if (monitoring.getOrderNumber().equals("?")){
          addPossible = false;
        }
      }
    }
    return addPossible;
  }
  
  private void checkOrderNumber (Text orderNumberText) {
    String errorMessage = null;
    String orderNumber = orderNumberText.getText();
    if (orderNumber == null || !orderNumber.equals("?")){
      if (orderNumber == null || orderNumber.length() == 0){
        errorMessage = "Cislo objednavky nemoze byt prazdne!";
      } else if (!orderTableModel.getData().contains(new Order(orderNumber, null, null, null))){
        errorMessage = "Objednavka s cislom " + orderNumber + " neexistuje.";
      }
      if (errorMessage != null){
        orderNumberText.setText("?");
        MessageBox.displayMessageBox(table.getShell(), SWT.ERROR, "Chyba !", 
          errorMessage);
      }
    }
  }
  private int getNextOrder(String orderNumber){
    int order = 1;
    for (Monitoring monitoring : data){
      if (orderNumber.equals(monitoring.getOrderNumber())){
        if (monitoring.getOrder() >= order){
          order = monitoring.getOrder() + 1; 
        }
      }
    }
    
    return order;
  }
  
  private void filterMonitoring(){
    String fromDate = monitoringTable.getFromDate().getText();
    String toDate = monitoringTable.getToDate().getText();
    try {
      filterFromDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(fromDate);
    } catch (ParseException e) {
      filterFromDate = null;
      monitoringTable.getFromDate().setText("");
    }
    try {
      filterToDate = ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(toDate);
    } catch (ParseException e) {
      filterToDate = null;
      monitoringTable.getToDate().setText("");
    }
    data = orderTableModel.getFilteredMonitoringRecords(filterFromDate, filterToDate);
    tableViewer.setInput(data);
  }
  
}
