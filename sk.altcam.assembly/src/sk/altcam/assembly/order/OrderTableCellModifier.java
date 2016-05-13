package sk.altcam.assembly.order;
import java.text.ParseException;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Order;

/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifies a cell in the 
 * tableViewer
 */

public class OrderTableCellModifier implements ICellModifier {
	private OrderTableModel orderTableModel;
	/**
	 * Constructor 
	 * @param ProductionMonitoringShell an instance of a TableViewerExample 
	 */
	public OrderTableCellModifier(OrderTableModel orderTableModel) {
		super();
		this.orderTableModel = orderTableModel;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
	  int columnIndex = OrderTableLabelProvider.getColumnNames().indexOf(property);
	  Order order = (Order) element;
    if (columnIndex == OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND){
      return order.getOrderNumber().trim().equals("?");
    } else if(columnIndex == OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND){
      return order.getItemNumber().trim().equals("?");
    }
    else{
      return true;
    }  
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		int columnIndex = OrderTableLabelProvider.getColumnNames().indexOf(property);

		Object result = null;
		Order order = (Order) element;
		switch (columnIndex) {
			case OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND :
				result = order.getOrderNumber();
				break;
			case OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND:
			  result = order.getItemNumber();
				break;
			case OrderTableLabelProvider.MACHINE_ID_COLUMN_IND:
        result = order.getMachineId();
        break;
			case OrderTableLabelProvider.DATE_COLUMN_IND: 
				result = order.getDate() == null ? "?" : AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(order.getDate());
				break;
      case OrderTableLabelProvider.NUM_PLANED_ITEMS_COLUMN_IND: 
        result = order.getNumItemsPlanned() + "";
        break;
      case OrderTableLabelProvider.CYCLE_COLUMN_IND: 
        result = AssemblyMonitoring.DECIMAL_FORMAT.format(order.getCycle());
        break;
      case OrderTableLabelProvider.NUM_ACTIVE_CAVITIES_COLUMN_IND: 
        result = order.getNumActiveCavities() + "";
        break;
      case OrderTableLabelProvider.SHIFT_COLUMN_IND:
        result = order.getShift() + "";
        break;
			default :
				result = "";
		}
		return result;	
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {	
		// Find the index of the column 
    int columnIndex = OrderTableLabelProvider.getColumnNames().indexOf(property);
		TableItem item = (TableItem) element;
		Order order = (Order)item.getData();
		String valueString;
    switch (columnIndex) {
    case OrderTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      if (value == null){
        value = "?";
      }
      valueString = ((String)value).trim();
      order.setOrderNumber(valueString);
      break;
    case OrderTableLabelProvider.ITEM_NUMBER_COLUMN_IND:
      if (value == null){
        value = "?";
      }
      valueString = ((String)value).trim();
      order.setItemNumber(valueString);
      break;
    case OrderTableLabelProvider.MACHINE_ID_COLUMN_IND:
      if (value == null){
        value = "?";
      }
      valueString = ((String)value).trim();
      order.setMachineId(valueString);
      break;
    case OrderTableLabelProvider.DATE_COLUMN_IND:
      valueString = ((String)value).trim();
      try {
        order.setDate(AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(valueString));
      } catch (ParseException e) {
        order.setDate(null);
      }
      break;
    case OrderTableLabelProvider.NUM_PLANED_ITEMS_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      order.setNumItemsPlanned(Integer.parseInt(valueString));
      break;
    case OrderTableLabelProvider.CYCLE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = AssemblyMonitoring.DECIMAL_FORMAT.format(0.0);
      }  
      order.setCycle(Double.parseDouble(valueString));
      break;
    case OrderTableLabelProvider.NUM_ACTIVE_CAVITIES_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      order.setNumActiveCavities(Integer.parseInt(valueString));
      break;
    case OrderTableLabelProvider.SHIFT_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      order.setShift(Integer.parseInt(valueString));
      break;
    default :
    }
    orderTableModel.orderChanged (order);
	}
}
