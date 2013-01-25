package sk.altcam.production.monitoring;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import sk.altcam.production.ProductionMonitoring;
import sk.altcam.production.entity.Monitoring;
/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class MonitoringTableLabelProvider extends LabelProvider implements ITableLabelProvider{
  // Set the table column property names
  private static final String ORDER_COLUMN     = "Poradie";
  private static final String ORDER_NUMBER_COLUMN     = "Interna Objednavka";
  private static final String ITEM_NUMBER_COLUMN   = "Cislo vyrobku";
  private static final String DATE_COLUMN = "Datum";
  private static final String PRODUCED_COLUMN = "Vyrobene";
  private static final String PLANNED_IDLE_TIME_COLUMN = "P. prestoj";
  private static final String FORM_SWITCH_COLUMN = "Vymena f.";
  private static final String NABEHOVE_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[0];
  private static final String PRESTREKY_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[1]; 
  private static final String OTREPY_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[2];
  private static final String ZHORENE_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[3];
  private static final String VADA_MATERIALU_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[4];
  private static final String NEDOSTREKNUTE_OKOLO_ZASTREKU_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[5];
  private static final String POSKODENY_ZASTREK_ZALIS_COLUMN  = MonitoringTable.BROKEN_COLUMN_LABELS[6];
  private static final String NEDOLISOVANE_PO_JADRACH_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[7];
  private static final String BOD_VSTREKU_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[8];
  private static final String PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[9];
  private static final String VYHADZANE_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[10];
  private static final String PRESTREKY_V_OTVOROCH_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[11];
  private static final String FARBA_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[12];
  private static final String ZASTAVENIE_STROJA_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[13];
  private static final String INE_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[14];
  private static final String NEDOLISOVANE_STROJ_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[15];
  private static final String NEDOLISOVANE_FORMA_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[16];
  private static final String NEDOLISOVANE_PERIFERIE_COLUMN = MonitoringTable.BROKEN_COLUMN_LABELS[17];
  private static final String SUM_PPM_BROKEN_COLUMN = "Zmatky pre ppm";
  private static final String SUM_BROKEN_COLUMN = "Zmatky spolu";
  private static final String SUM_TOTAL_COLUMN = "Spolu";
  public static final int ORDER_COLUMN_IND = 0;
  public static final int ORDER_NUMBER_COLUMN_IND = 1;
  public static final int ITEM_NUMBER_COLUMN_IND = 2;
  public static final int DATE_COLUMN_IND = 3;
  public static final int PLANNED_IDLE_TIME_COLUMN_IND = 4;
  public static final int FORM_SWITCH_COLUMN_IND = 5;
  public static final int PRODUCED_COLUMN_IND = 6;
  public static final int NABEHOVE_COLUMN_IND = 7;
  public static final int PRESTREKY_COLUMN_IND = 8; 
  public static final int NEDOSTREKY_COLUMN_IND = 9;
  public static final int ZHORENE_COLUMN_IND = 10;
  public static final int VADA_MATERIALU_COLUMN_IND = 11;
  public static final int DEFORMACIE_COLUMN_IND = 12;
  public static final int POSKODENY_ZASTREK_ZALIS_COLUMN_IND  = 13;
  public static final int CHYBAJUCI_ZASTREK_COLUMN_IND = 14;
  public static final int BOD_VSTREKU_COLUMN_IND = 15;
  public static final int PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND = 16;
  public static final int VYHADZANE_COLUMN_IND = 17;
  public static final int PRESTREKY_V_OTVOROCH_COLUMN_IND = 18;
  public static final int FARBA_COLUMN_IND = 19;
  public static final int ZASTAVENIE_STROJA_COLUMN_IND = 20;
  public static final int INE_COLUMN_IND = 21;
  public static final int NEDOLISOVANE_STROJ_COLUMN_IND = 22;
  public static final int NEDOLISOVANE_FORMA_COLUMN_IND = 23;
  public static final int NEDOLISOVANE_PERIFERIE_COLUMN_IND = 24;
  public static final int SUM_PPM_BROKEN_COLUMN_IND = 25;
  public static final int SUM_BROKEN_COLUMN_IND = 26;
  public static final int SUM_TOTAL_COLUMN_IND = 27;
  // Set column names
  public static final String[] COLUMN_NAMES = new String[] { 
    ORDER_COLUMN,
    ORDER_NUMBER_COLUMN, 
    ITEM_NUMBER_COLUMN,
    DATE_COLUMN,
    PLANNED_IDLE_TIME_COLUMN,
    FORM_SWITCH_COLUMN,
    PRODUCED_COLUMN,
    NABEHOVE_COLUMN,
    PRESTREKY_COLUMN, 
    OTREPY_COLUMN,
    ZHORENE_COLUMN,
    VADA_MATERIALU_COLUMN,
    NEDOSTREKNUTE_OKOLO_ZASTREKU_COLUMN,
    POSKODENY_ZASTREK_ZALIS_COLUMN,
    NEDOLISOVANE_PO_JADRACH_COLUMN,
    BOD_VSTREKU_COLUMN,
    PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN,
    VYHADZANE_COLUMN,
    PRESTREKY_V_OTVOROCH_COLUMN,
    FARBA_COLUMN,
    ZASTAVENIE_STROJA_COLUMN,
    INE_COLUMN,
    NEDOLISOVANE_STROJ_COLUMN,
    NEDOLISOVANE_FORMA_COLUMN,
    NEDOLISOVANE_PERIFERIE_COLUMN,
    SUM_PPM_BROKEN_COLUMN,
    SUM_BROKEN_COLUMN,
    SUM_TOTAL_COLUMN};
  private static final List<String> COLUMN_NAMES_LIST = Arrays.asList(
    MonitoringTableLabelProvider.COLUMN_NAMES);
  private MonitoringTableModel monitoringTableModel = null;
  
  public MonitoringTableLabelProvider(MonitoringTableModel monitoringTableModel){
    super();
    this.monitoringTableModel = monitoringTableModel;
  }
  /**
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
   *      int)
   */
  public String getColumnText(Object element, int columnIndex) {
    String result = "";
    Monitoring monitoring = (Monitoring) element;
    switch (columnIndex) {
    case ORDER_COLUMN_IND:
      result = String.valueOf(monitoring.getOrder());
      break;
    case ORDER_NUMBER_COLUMN_IND:
      result = monitoring.getOrderNumber() == null ? "?" : monitoring.getOrderNumber();
      break;
    case ITEM_NUMBER_COLUMN_IND:
      result = monitoring.getOrderEntity() == null || monitoring.getOrderEntity().getItemNumber() == null ? "?" : monitoring.getOrderEntity().getItemNumber();
      break;
    case DATE_COLUMN_IND:
      result = monitoring.getDate() == null ? "?" : ProductionMonitoring.DISPLAY_DATE_FORMAT.format(monitoring.getDate());
      break;
    case PLANNED_IDLE_TIME_COLUMN_IND:
      result = String.valueOf(monitoring.getPlannedIdleTime());
      break;
    case FORM_SWITCH_COLUMN_IND:
      result = String.valueOf(monitoring.getFormExchange());
      break;
    case PRODUCED_COLUMN_IND:
      result = String.valueOf(monitoring.getProduced());
      break;
    case NABEHOVE_COLUMN_IND:
      result = String.valueOf(monitoring.getNabehove());
      break;
    case PRESTREKY_COLUMN_IND:
      result = String.valueOf(monitoring.getPrestreky());
      break;
    case NEDOSTREKY_COLUMN_IND:
      result = String.valueOf(monitoring.getNedostreky());
      break;
    case ZHORENE_COLUMN_IND:
      result = String.valueOf(monitoring.getZhorene());
      break;
    case VADA_MATERIALU_COLUMN_IND:
      result = String.valueOf(monitoring.getVadaMaterialu());
      break;
    case DEFORMACIE_COLUMN_IND:
      result = String.valueOf(monitoring.getDeformacie());
      break;
    case POSKODENY_ZASTREK_ZALIS_COLUMN_IND:
      result = String.valueOf(monitoring.getPoskodenyZastrekZalis());
      break;
    case CHYBAJUCI_ZASTREK_COLUMN_IND:
      result = String.valueOf(monitoring.getChybajuciZastrek());
      break;
    case BOD_VSTREKU_COLUMN_IND:
      result = String.valueOf(monitoring.getBodVstreku());
      break;
    case PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND:
      result = String.valueOf(monitoring.getPritomnostPruznychElementov());
      break;
    case VYHADZANE_COLUMN_IND:
      result = String.valueOf(monitoring.getVyhadzane());
      break;
    case PRESTREKY_V_OTVOROCH_COLUMN_IND:
      result = String.valueOf(monitoring.getPrestrekyVOtvoroch());
      break;
    case FARBA_COLUMN_IND:
      result = String.valueOf(monitoring.getFarba());
      break;
    case ZASTAVENIE_STROJA_COLUMN_IND:
      result = String.valueOf(monitoring.getZastavenieStroja());
      break;
    case NEDOLISOVANE_STROJ_COLUMN_IND:
      result = String.valueOf(monitoring.getNedolisovaneStroj());
      break;
    case NEDOLISOVANE_FORMA_COLUMN_IND:
      result = String.valueOf(monitoring.getNedolisovaneForma());
      break;
    case NEDOLISOVANE_PERIFERIE_COLUMN_IND:
      result = String.valueOf(monitoring.getNedolisovanePeriferie());
      break;
    case INE_COLUMN_IND:
      result = String.valueOf(monitoring.getIne());
      break;
    case SUM_PPM_BROKEN_COLUMN_IND:
      result = String.valueOf(monitoringTableModel.calcSumPpmBroken(monitoring));
      break;
    case SUM_BROKEN_COLUMN_IND:
      result = String.valueOf(monitoringTableModel.calcSumBroken(monitoring));
      break;
    case SUM_TOTAL_COLUMN_IND:
      result = String.valueOf(monitoringTableModel.calcSumTotal(monitoring));
      break;
    default:
      break;
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object,
   *      int)
   */
  public Image getColumnImage(Object element, int columnIndex) {
    return
    /*
     * (columnIndex == 0) ? // COMPLETED_COLUMN? getImage(((ExampleTask)
     * element).isCompleted()) :
     */
    null;
  }
  /**
   * Return the column names in a collection
   * 
   * @return List  containing column names
   */
  public static List<String> getColumnNames() {
    return MonitoringTableLabelProvider.COLUMN_NAMES_LIST;
  }
}
