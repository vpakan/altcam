package sk.altcam.assembly.order;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import sk.altcam.assembly.ProductionMonitoring;
import sk.altcam.assembly.entity.Order;


/**
 * Sorter for the TableViewerExample that displays items of type 
 * <code>ExampleTask</code>.
 * The sorter supports three sort criteria:
 * <p>
 * <code>DESCRIPTION</code>: Task description (String)
 * </p>
 * <p>
 * <code>OWNER</code>: Task Owner (String)
 * </p>
 * <p>
 * <code>PERCENT_COMPLETE</code>: Task percent completed (int).
 * </p>
 */
public class OrderTableSorter extends ViewerSorter {
	// Criteria that the instance uses 
	private int criteria = 0;
	private int order = 1;
	@SuppressWarnings("unused")
	private OrderTableModel orderTableModel = null;
	public OrderTableSorter (OrderTableModel orderTableModel,int criteria, int order){
	  this.order = order;
	  this.criteria = criteria;
	  this.orderTableModel = orderTableModel;
	}

	/**
	 * Creates a resource sorter that will use the given sort criteria.
	 *
	 * @param criteria the sort criterion to use: one of <code>NAME</code> or 
	 *   <code>TYPE</code>
	 */

  public int compare(Viewer viewer, Object o1, Object o2) {

    Order order1 = (Order) o1;
    Order order2 = (Order) o2;
  
    int result = 0;
    switch (criteria) {
    case OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      result = ProductionMonitoring.sortString(order1.getOrderNumber(),
          order2.getOrderNumber());
      break;
    case OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND:
      result = ProductionMonitoring.sortString(order1.getItemNumber(),
          order2.getItemNumber());
      break;
    case OrderTableLabelProvider.MACHINE_ID_COLUMN_IND:
      result = ProductionMonitoring.sortString(order1.getMachineId(),
          order2.getMachineId());
      break;
    case OrderTableLabelProvider.DATE_COLUMN_IND:
      result = ProductionMonitoring.sortDate(order1.getDate(), order2
          .getDate());
      break;
    case OrderTableLabelProvider.NUM_PLANED_ITEMS_COLUMN_IND:
      result = ProductionMonitoring.sortInt(order1.getNumItemsPlanned(),
          order2.getNumItemsPlanned());
      break;
    case OrderTableLabelProvider.CYCLE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(order1.getCycle(),
          order2.getCycle());
      break;
    case OrderTableLabelProvider.NUM_ACTIVE_CAVITIES_COLUMN_IND:
      result = ProductionMonitoring.sortInt(order1.getNumActiveCavities(),
          order2.getNumActiveCavities());
      break;
    case OrderTableLabelProvider.SHIFT_COLUMN_IND:
      result = ProductionMonitoring.sortInt(order1.getShift(),
          order2.getShift());
      break;
    default:
      break;
    }

    result = this.order * result;
    
    return result;
  }

	/**
	 * Returns the sort criteria of this this sorter.
	 *
	 * @return the sort criterion
	 */
	public int getCriteria() {
		return criteria;
	}
}
