package sk.altcam.assembly.monitoring;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import sk.altcam.assembly.AssemblyMonitoring;

public class MonitoringTable extends Composite{
  private Table table = null;
  private Combo userId = null;
  private Text fromDate = null;
  private Text toDate = null;
  private Button add = null;
  private Button delete = null;
  private Button close = null;
  private Button filter = null;
  //Set column names
  public static final int[] COLUMN_ALLIGN = new int[] { 
   SWT.LEFT,
   SWT.LEFT,
   SWT.LEFT,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.CENTER,
   SWT.CENTER,
   SWT.RIGHT,
   SWT.RIGHT,
   SWT.CENTER,
   SWT.RIGHT,
   SWT.CENTER,
   SWT.RIGHT,
   SWT.LEFT};
    
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
  public Combo getUserId() {
    return userId;
  }
  public Table getTable() {
    return table;
  }
  public MonitoringTable(Composite parent, int style){
    super(parent,style);
    this.setLayout(new GridLayout(1,false));
    createFilterComposite (this);
    createTable(this);
    createButtonsComposite(this);
  }
  private void createFilterComposite (Composite parent){
    Composite filterComposite = new Composite (parent,SWT.NONE);
    filterComposite.setLayout(new GridLayout(5,false));
    Label label = new Label(filterComposite,SWT.NONE);
    label.setText("ID:");
    userId = new Combo(filterComposite, SWT.BORDER | SWT.READ_ONLY);
    GridData gdUserId = new GridData(GridData.FILL_HORIZONTAL);
    gdUserId.horizontalSpan = 4;
    userId.setLayoutData(gdUserId);
    label = new Label(filterComposite,SWT.NONE);
    label.setText("Od:");
    fromDate = new Text(filterComposite,SWT.BORDER);
    label = new Label(filterComposite,SWT.NONE);
    label.setText("Do:");
    toDate = new Text(filterComposite,SWT.BORDER);
    filter = new Button (filterComposite,SWT.NONE);
    filter.setText(" Filter ");
    GridData gdFilter = new GridData ();
    gdFilter.widthHint = AssemblyMonitoring.BUTTONS_MIN_WIDTH;
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
        TableColumn column = new TableColumn(table, MonitoringTable.COLUMN_ALLIGN[index]);   
        column.setText(MonitoringTableLabelProvider.COLUMN_NAMES[index]);
      }    
  }

  private void createButtonsComposite (Composite parent){
    Composite buttons = new Composite (parent,SWT.NONE);
    buttons.setLayout(new GridLayout(4,false));
    buttons.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    add = new Button(buttons,SWT.PUSH | SWT.CENTER);
    add.setText(" Pridaj ");
    GridData gdAdd = new GridData ();
    gdAdd.widthHint = AssemblyMonitoring.BUTTONS_MIN_WIDTH;
    add.setLayoutData(gdAdd);
    delete = new Button(buttons,SWT.PUSH | SWT.CENTER);
    delete.setText(" Zmaz ");
    GridData gdDelete = new GridData ();
    gdDelete.widthHint = AssemblyMonitoring.BUTTONS_MIN_WIDTH;
    delete.setLayoutData(gdDelete);
    Label dummyLabel = new Label(buttons,SWT.NONE);
    GridData gridDataDummy = new GridData(GridData.FILL_HORIZONTAL);
    dummyLabel.setLayoutData(gridDataDummy);
    close = new Button(buttons,SWT.PUSH | SWT.CENTER);
    GridData gdClose = new GridData ();
    gdClose.widthHint = AssemblyMonitoring.BUTTONS_MIN_WIDTH;
    close.setLayoutData(gdClose);
    close.setText(" Zavri ");
  }
}
