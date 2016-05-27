package sk.altcam.assembly.monitoring;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;
/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class MonitoringTableLabelProvider extends LabelProvider implements ITableLabelProvider{
  // Set the table column property names
  private static final String DATE_COLUMN = "Datum";
  private static final String SHIFT_COLUMN = "Zmena";
  private static final String ORDER_NUMBER_COLUMN     = "Interna Objednavka";
  private static final String ITEM_NUMBER_COLUMN   = "Cislo vyrobku";
  private static final String NORM_COLUMN = "Norma";
  private static final String PIECES_COLUMN = "OK ks";
  private static final String NON_OK_PIECES_COLUMN = "NOK ks";
  private static final String SUM_PIECES_COLUMN = "Spolu ks";
  private static final String EFFICIENCY_COLUMN = "Vykon %";
  private static final String SIGN_COLUMN = "+/-";
  private static final String EFFECTIVITY_COLUMN = "Efektivita %";
  private static final String NOK_PERCENT_COLUMN = "NOK %";
  private static final String TIME_FROM_COLUMN = "Cas od";
  private static final String TIME_TO_COLUMN = "Cas do";
  private static final String BREAK_TIME = "Prestavka";
  private static final String PAUSE_TIME = "Prestoj";
  private static final String EFFECTIVE_TIME_COLUMN = "Cisty cas";
  private static final String COMMENT_COLUMN = "Poznamka";
  public static final int ORDER_NUMBER_COLUMN_IND = 0;
  public static final int ITEM_NUMBER_COLUMN_IND  = 1;
  public static final int DATE_COLUMN_IND = 2;
  public static final int SHIFT_NUMBER_COLUMN_IND = 3;
  public static final int NORM_COLUMN_IND = 4;
  public static final int PIECES_COLUMN_IND = 5;
  public static final int NON_OK_PIECES_COLUMN_IND = 6; 
  public static final int SUM_PIECES_COLUMN_IND = 7; 
  public static final int NOK_PERCENT_COLUMN_IND = 8;
  public static final int TIME_FROM_COLUMN_IND = 9;
  public static final int TIME_TO_COLUMN_IND = 10; 
  public static final int BREAK_TIME_IND = 11;
  public static final int PAUSE_TIME_IND = 12; 
  public static final int EFFICIENT_TIME_COLUMN_IND = 13;
  public static final int EFFICIENCY_COLUMN_IND = 14;
  public static final int SIGN_COLUMN_IND = 15;
  public static final int EFFECTIVITY_COLUMN_IND = 16;
  public static final int COMMENT_COLUMN_IND = 17;

  // Set column names
  public static final String[] COLUMN_NAMES = new String[] { 
    ORDER_NUMBER_COLUMN,
    ITEM_NUMBER_COLUMN,
    DATE_COLUMN,
    SHIFT_COLUMN,
    NORM_COLUMN,
    PIECES_COLUMN,
    NON_OK_PIECES_COLUMN,
    SUM_PIECES_COLUMN,
    NOK_PERCENT_COLUMN,
    TIME_FROM_COLUMN,
    TIME_TO_COLUMN,
    BREAK_TIME,
    PAUSE_TIME,
    EFFECTIVE_TIME_COLUMN,
    EFFICIENCY_COLUMN,
    SIGN_COLUMN,
    EFFECTIVITY_COLUMN,
    COMMENT_COLUMN};
  
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
    case DATE_COLUMN_IND:
      result = monitoring.getDate() == null ? "?" : AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(monitoring.getDate());
      break;
    case SHIFT_NUMBER_COLUMN_IND:
      result = String.valueOf(monitoring.getShift());
      break;
    case ORDER_NUMBER_COLUMN_IND:
      result = monitoring.getOrderNumber() == null ? "?" : monitoring.getOrderNumber();
      break;
    case ITEM_NUMBER_COLUMN_IND:
      result = monitoring.getOrderEntity() == null || monitoring.getOrderEntity().getItemNumber() == null ? "?" : monitoring.getOrderEntity().getItemNumber();
      break;
    case NORM_COLUMN_IND:
      result = AssemblyMonitoring.DECIMAL_FORMAT.format(monitoringTableModel.calcNorm(monitoring));
      break;
    case PIECES_COLUMN_IND:
      result = String.valueOf(monitoring.getPieces());
      break;
    case NON_OK_PIECES_COLUMN_IND:
      result = String.valueOf(monitoring.getNonOkPieces());
      break; 
    case SUM_PIECES_COLUMN_IND:
      result = String.valueOf(monitoringTableModel.calcSumPieces(monitoring));
      break;
    case NOK_PERCENT_COLUMN_IND:
      result = AssemblyMonitoring.DECIMAL_FORMAT.format(monitoringTableModel.calcPercentNonOkPieces(monitoring));
      break;
    case EFFICIENCY_COLUMN_IND:
      result = AssemblyMonitoring.DECIMAL_FORMAT.format(monitoringTableModel.calcEfficiency(monitoring));
      break;
    case EFFECTIVITY_COLUMN_IND:
      result = AssemblyMonitoring.DECIMAL_FORMAT.format(monitoringTableModel.calcEffectivity(monitoring));
      break;
    case TIME_FROM_COLUMN_IND:
      result = monitoring.getFromTime() == null ? "?" : AssemblyMonitoring.DISPLAY_TIME_FORMAT.format(monitoring.getFromTime());
      break;
    case TIME_TO_COLUMN_IND:
      result = monitoring.getToTime() == null ? "?" : AssemblyMonitoring.DISPLAY_TIME_FORMAT.format(monitoring.getToTime());
      break;
    case BREAK_TIME_IND:
      result = String.valueOf(monitoring.getBreakTime());
      break; 
    case PAUSE_TIME_IND:
      result = String.valueOf(monitoring.getPauseTime());
      break; 
    case EFFICIENT_TIME_COLUMN_IND:
      Date effectiveDate = monitoringTableModel.calcEfficientTime(monitoring);
      result = effectiveDate == null ? "?" : AssemblyMonitoring.DISPLAY_TIME_FORMAT.format(effectiveDate);
      break;
    case SIGN_COLUMN_IND:
      result = monitoringTableModel.calcSign(monitoring);
      break;
    case COMMENT_COLUMN_IND:
      result = monitoring.getComment();
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
