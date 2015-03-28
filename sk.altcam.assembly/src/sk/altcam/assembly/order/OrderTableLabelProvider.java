package sk.altcam.assembly.order;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import sk.altcam.assembly.ProductionMonitoring;
import sk.altcam.assembly.entity.Order;
/**
 * Label provider for the TableViewerExample
 * 
 * @see org.eclipse.jface.viewers.LabelProvider
 */
public class OrderTableLabelProvider extends LabelProvider implements ITableLabelProvider{
  // Set the table column property names
  private static final String ORDER_NUMBER_COLUMN     = "Interna Objednavka";
  private static final String ITEM_NUMBER_COLUMN   = "Cislo vyrobku";
  private static final String MACHINE_ID_COLUMN   = "ID stroja";
  private static final String DATE_COLUMN = "Datum";
  private static final String NUM_PLANED_ITEMS_COLUMN = "Planovany pocet kusov";
  private static final String CYCLE_COLUMN = "Cyklus";
  private static final String NUM_ACTIVE_CAVITIES_COLUMN = "Pocet aktivnych kavit";
  private static final String SHIFT_COLUMN = "Smennost";
  public static final int ORDER_NUMBER_COLUMN_IND = 0;
  public static final int ITEM_NUMBER_COLUMN_IND = 1;
  public static final int MACHINE_ID_COLUMN_IND = 2;
  public static final int DATE_COLUMN_IND = 3;
  public static final int NUM_PLANED_ITEMS_COLUMN_IND = 4;
  public static final int CYCLE_COLUMN_IND = 5;
  public static final int NUM_ACTIVE_CAVITIES_COLUMN_IND = 6;
  public static final int SHIFT_COLUMN_IND = 7;
  // Set column names
  public static final String[] COLUMN_NAMES = new String[] { 
    ORDER_NUMBER_COLUMN, 
    ITEM_NUMBER_COLUMN,
    MACHINE_ID_COLUMN,
    DATE_COLUMN,
    NUM_PLANED_ITEMS_COLUMN,
    CYCLE_COLUMN,
    NUM_ACTIVE_CAVITIES_COLUMN,
    SHIFT_COLUMN};
  private static final List<String> COLUMN_NAMES_LIST = Arrays.asList(
    OrderTableLabelProvider.COLUMN_NAMES);
  @SuppressWarnings("unused")
  private OrderTableModel orderTableModel = null;
  
  public OrderTableLabelProvider(OrderTableModel orderTableModel){
    super();
    this.orderTableModel = orderTableModel;
  }
  /**
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object,
   *      int)
   */
  public String getColumnText(Object element, int columnIndex) {
    String result = "";
    Order order = (Order) element;
    switch (columnIndex) {
    case ORDER_NUMBER_COLUMN_IND:
      result = order.getOrderNumber() == null ? "?" : order.getOrderNumber();
      break;
    case ITEM_NUMBER_COLUMN_IND:
      result = order.getItemNumber() == null ? "?" : order.getItemNumber();
      break;
    case MACHINE_ID_COLUMN_IND:
      result = order.getMachineId() == null ? "?" : order.getMachineId();
      break;
    case DATE_COLUMN_IND:
      result = order.getDate() == null ? "?" : ProductionMonitoring.DISPLAY_DATE_FORMAT.format(order.getDate());
      break;
    case NUM_PLANED_ITEMS_COLUMN_IND:
      result = String.valueOf(order.getNumItemsPlanned());
      break;
    case CYCLE_COLUMN_IND:
      result = String.valueOf(order.getCycle());
      break;
    case NUM_ACTIVE_CAVITIES_COLUMN_IND:
      result = String.valueOf(order.getNumActiveCavities());
      break;
    case SHIFT_COLUMN_IND:
      result = String.valueOf(order.getShift());
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
    return OrderTableLabelProvider.COLUMN_NAMES_LIST;
  }
}
