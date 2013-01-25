package sk.altcam.production.monitoring;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import sk.altcam.production.ProductionMonitoring;

public class MonitoringTable extends Composite{
  private Table table = null;
  private Text fromDate = null;
  private Text toDate = null;
  private Button add = null;
  private Button delete = null;
  private Button close = null;
  private Button filter = null;
  
  public static final String[] BROKEN_COLUMN_LABELS = new String[]{
    "Nabehove",
    "Prelis.",  // was before Prestr.
    "Nedolis.",  // was before Nedost, was before "Otrepy"
    "Zhorene",
    "Spi. Fl.",  // was Vada ma.
    "Deform.",  // was before "N. o. za."
    "NOK zal.",  // was before P. z. z.
    "Chyb. z.",  // was before "N. po j."
    "Zla p.z.",  // was Bod. vst.
    "Bubliny",  // was P. p. el.
    "P. forma",  // was vyhadzo.
    "P. stroj",  // was P. v otv.
    "P. peri.",  // was Farba
    "S.p.p.p.",  // Zast. s.
    "NOK roz.",  // was Ine
    "N. stroj",
    "N. forma",
    "N. peri." 
  };
  
  public static final String[] BROKEN_COLUMN_DESCS = new String[]{
    "Nabehove (spustenie, udrzba)",
    "Prelisovane v procese",
    "Nedolisovane v procese",
    "Zhorene",
    "Spinave + flaky",
    "Deformacie",
    "NOK zalisok",
    "Chybajuci zalisok",
    "Zla poloha zalisku",
    "Bubliny",
    "Prelisovane - forma",
    "Prelisovane - stroj",
    "Prelisovane - periferie",
    "Stabilizacia procesu po probleme",
    "NOK rozmer",
    "Nedolisovane - stroj",
    "Nedolisovane - forma",
    "Nedolisovane - periferie"
  };
  
  public Text getFromDate() {
    return fromDate;
  }
  public Text getToDate() {
    return toDate;
  }
  public Button getAdd() {
    return add;
  }
  public Button getDelete() {
    return delete;
  }
  public Button getClose() {
    return close;
  }
  public Button getFilter() {
    return filter;
  }
  
  public Table getTable() {
    return table;
  }
  public MonitoringTable(Composite parent, int style){
    super(parent,style);
    this.setLayout(new GridLayout(1,false));
    createFilterComposite (this);
    createTable(this);
    createLegend(this);
    createButtonsComposite(this);
  }
  private void createFilterComposite (Composite parent){
    Composite filterComposite = new Composite (parent,SWT.NONE);
    filterComposite.setLayout(new GridLayout(3,false));
    filterComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    Label label = new Label(filterComposite,SWT.NONE);
    label.setText("Od:");
    fromDate = new Text(filterComposite,SWT.BORDER);
    new Label(filterComposite,SWT.NONE);
    label = new Label(filterComposite,SWT.NONE);
    label.setText("Do:");
    toDate = new Text(filterComposite,SWT.BORDER);
    filter = new Button (filterComposite,SWT.NONE);
    filter.setText(" Filter ");
    GridData gdFilter = new GridData ();
    gdFilter.widthHint = ProductionMonitoring.BUTTONS_MIN_WIDTH;
    filter.setLayoutData(gdFilter);

  }
  private void createTable (Composite parent){
    table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
        SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
      GridData gridData = new GridData(GridData.FILL_BOTH);
      gridData.grabExcessVerticalSpace = true;
      gridData.horizontalSpan = 4;
      table.setLayoutData(gridData);   
      table.setLinesVisible(true);
      table.setHeaderVisible(true);
      for (int index = 0 ; index < MonitoringTableLabelProvider.COLUMN_NAMES.length ; index ++){
        TableColumn column = new TableColumn(table, 
          ((index == 0 || index < 2) ? SWT.LEFT : SWT.RIGHT),
          index);   
        column.setText(MonitoringTableLabelProvider.COLUMN_NAMES[index]);
      }    
  }
  private void createLegend (Composite parent){
    Label legend = new Label(parent, SWT.SINGLE | SWT.BORDER | SWT.WRAP);
      GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
      gridData.horizontalSpan = 4;
      legend.setLayoutData(gridData);  
      StringBuffer sb = new StringBuffer("");
      for (int index = 0 ; index < MonitoringTable.BROKEN_COLUMN_LABELS.length ; index++){
        sb.append("[");
        sb.append(MonitoringTable.BROKEN_COLUMN_LABELS[index]);
        sb.append("]:");
        sb.append(MonitoringTable.BROKEN_COLUMN_DESCS[index]);
        sb.append(" ");
      }
      sb.append("[Zmatky pre ppm]: suma zmatkov pre vypocet ppm bez nabehovyk kusov ");
      sb.append("[Zmatky]:suma vsetkych zmatkov ");
      
      sb.append("[Spolu]: suma vyrobenych nekazovych kusov a vsetkych zmatkov");
      legend.setText(sb.toString());
  }
  private void createButtonsComposite (Composite parent){
    Composite buttons = new Composite (parent,SWT.NONE);
    buttons.setLayout(new GridLayout(4,false));
    buttons.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    add = new Button(buttons,SWT.PUSH | SWT.CENTER);
    add.setText(" Pridaj ");
    GridData gdAdd = new GridData ();
    gdAdd.widthHint = ProductionMonitoring.BUTTONS_MIN_WIDTH;
    add.setLayoutData(gdAdd);
    delete = new Button(buttons,SWT.PUSH | SWT.CENTER);
    delete.setText(" Zmaz ");
    GridData gdDelete = new GridData ();
    gdDelete.widthHint = ProductionMonitoring.BUTTONS_MIN_WIDTH;
    delete.setLayoutData(gdDelete);
    Label dummyLabel = new Label(buttons,SWT.NONE);
    GridData gridDataDummy = new GridData(GridData.FILL_HORIZONTAL);
    dummyLabel.setLayoutData(gridDataDummy);
    close = new Button(buttons,SWT.PUSH | SWT.CENTER);
    GridData gdClose = new GridData ();
    gdClose.widthHint = ProductionMonitoring.BUTTONS_MIN_WIDTH;
    close.setLayoutData(gdClose);
    close.setText(" Zavri ");
  }
}
