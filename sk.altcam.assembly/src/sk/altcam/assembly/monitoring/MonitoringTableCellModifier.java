package sk.altcam.assembly.monitoring;
import java.text.ParseException;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;

/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifies a cell in the 
 * tableViewer
 */

public class MonitoringTableCellModifier implements ICellModifier {
	private MonitoringTableModel monitoringTableModel;
	/**
	 * Constructor 
	 * @param ProductionMonitoringShell an instance of a TableViewerExample 
	 */
	public MonitoringTableCellModifier(MonitoringTableModel monitoringTableModel) {
		super();
		this.monitoringTableModel = monitoringTableModel;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
	  return true;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		int columnIndex = MonitoringTableLabelProvider.getColumnNames().indexOf(property);

		Object result = null;
		Monitoring monitoring = (Monitoring) element;
		switch (columnIndex) {
			case MonitoringTableLabelProvider.DATE_COLUMN_IND: 
				result = monitoring.getDate() == null ? "?" : AssemblyMonitoring.DISPLAY_DATE_FORMAT.format(monitoring.getDate());
				break;
			case MonitoringTableLabelProvider.SHIFT_NUMBER_COLUMN_IND:
        result = monitoring.getShift() + "";
        break;
      case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND :
				result = monitoring.getOrderNumber();
				break;
			case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND: 
				break;
			case MonitoringTableLabelProvider.NORM_COLUMN_IND: 
        break;
			case MonitoringTableLabelProvider.PIECES_COLUMN_IND: 
        result = monitoring.getPieces() + "";
        break;  
			case MonitoringTableLabelProvider.NON_OK_PIECES_COLUMN_IND: 
        result = monitoring.getNonOkPieces() + "";
        break;
			case MonitoringTableLabelProvider.SUM_PIECES_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.EFFICIENCY_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.EFFECTIVITY_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.NOK_PERCENT_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.TIME_FROM_COLUMN_IND: 
        result = monitoring.getFromTime() == null ? "?" : AssemblyMonitoring.DISPLAY_TIME_FORMAT.format(monitoring.getFromTime());
        break;
      case MonitoringTableLabelProvider.TIME_TO_COLUMN_IND: 
        result = monitoring.getToTime() == null ? "?" : AssemblyMonitoring.DISPLAY_TIME_FORMAT.format(monitoring.getToTime());
        break;
      case MonitoringTableLabelProvider.EFFICIENT_TIME_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.SIGN_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.COMMENT_COLUMN_IND: 
        result = monitoring.getComment();
        break;
      case MonitoringTableLabelProvider.BREAK_TIME_IND: 
        result = monitoring.getBreakTime() + "";
        break;  
      case MonitoringTableLabelProvider.PAUSE_TIME_IND: 
        result = monitoring.getPauseTime() + "";
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
    int columnIndex = MonitoringTableLabelProvider.getColumnNames().indexOf(property);
		TableItem item = (TableItem) element;
		Monitoring monitoring = (Monitoring)item.getData();
		String valueString;
    switch (columnIndex) {
    case MonitoringTableLabelProvider.DATE_COLUMN_IND:
      valueString = ((String)value).trim();
      try {
        monitoring.setDate(AssemblyMonitoring.DISPLAY_DATE_FORMAT.parse(valueString));
      } catch (ParseException e) {
        monitoring.setDate(null);
      }
      break;
    case MonitoringTableLabelProvider.SHIFT_NUMBER_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setShift(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      valueString = ((String)value).trim();
      monitoring.setOrderNumber(valueString);
      break;
    case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND: 
      break;  
    case MonitoringTableLabelProvider.NORM_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.PIECES_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPieces(Integer.parseInt(valueString));
      break;  
    case MonitoringTableLabelProvider.NON_OK_PIECES_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setNonOkPieces(Integer.parseInt(valueString));
      break;  
    case MonitoringTableLabelProvider.TIME_FROM_COLUMN_IND:
      valueString = ((String)value).trim();
      try {
        monitoring.setFromTime(AssemblyMonitoring.DISPLAY_TIME_FORMAT.parse(valueString));
      } catch (ParseException e) {
        monitoring.setFromTime(null);
      }
      break;
    case MonitoringTableLabelProvider.TIME_TO_COLUMN_IND:
      valueString = ((String)value).trim();
      try {
        monitoring.setToTime(AssemblyMonitoring.DISPLAY_TIME_FORMAT.parse(valueString));
      } catch (ParseException e) {
        monitoring.setToTime(null);
      }
      break;
    case MonitoringTableLabelProvider.BREAK_TIME_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setBreakTime(Integer.parseInt(valueString));
      break;  
    case MonitoringTableLabelProvider.PAUSE_TIME_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPauseTime(Integer.parseInt(valueString));
      break;    
    case MonitoringTableLabelProvider.EFFICIENCY_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.SIGN_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.EFFICIENT_TIME_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.EFFECTIVITY_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.SUM_PIECES_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.NOK_PERCENT_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.COMMENT_COLUMN_IND:
      valueString = ((String) value).trim();
      monitoring.setComment(valueString);
      break;
    default :
    }
    monitoringTableModel.monitoringChanged (monitoring);
	}
}
