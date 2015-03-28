package sk.altcam.assembly.monitoring;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.entity.Monitoring;


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
public class MonitoringTableSorter extends ViewerSorter {
	// Criteria that the instance uses 
	private int criteria = 0;
	private int order = 1;
	private MonitoringTableModel monitoringTableModel = null;
	public MonitoringTableSorter (MonitoringTableModel monitoringTableModel,int criteria, int order){
	  this.order = order;
	  this.criteria = criteria;
	  this.monitoringTableModel = monitoringTableModel;
	}

	/**
	 * Creates a resource sorter that will use the given sort criteria.
	 *
	 * @param criteria the sort criterion to use: one of <code>NAME</code> or 
	 *   <code>TYPE</code>
	 */

  public int compare(Viewer viewer, Object o1, Object o2) {

    Monitoring monitoring1 = (Monitoring) o1;
    Monitoring monitoring2 = (Monitoring) o2;
  
    int result = 0;
    switch (criteria) {
    case MonitoringTableLabelProvider.DATE_COLUMN_IND:
      result = AssemblyMonitoring.sortDate(monitoring1.getDate(), monitoring2
          .getDate());
      break;
    case MonitoringTableLabelProvider.SHIFT_NUMBER_COLUMN_IND:
      result = AssemblyMonitoring.sortInt(monitoring1.getShift(),
          monitoring2.getShift());
      break;
    case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      result = AssemblyMonitoring.sortString(monitoring1.getOrderNumber(),
          monitoring2.getOrderNumber());
      break;
    case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND:
      result = AssemblyMonitoring.sortString(monitoring1.getOrderEntity() == null ? "" : monitoring1.getOrderEntity().getItemNumber(),
          monitoring2.getOrderEntity() == null ? "" : monitoring2.getOrderEntity().getItemNumber());
      break;
    case MonitoringTableLabelProvider.NORM_COLUMN_IND:
      result = AssemblyMonitoring.sortDouble(monitoringTableModel.calcNorm(monitoring1),
          monitoringTableModel.calcNorm(monitoring2));
      break;
    case MonitoringTableLabelProvider.PIECES_COLUMN_IND:
      result = AssemblyMonitoring.sortInt(monitoring1.getPieces(),
          monitoring2.getPieces());
      break;
    case MonitoringTableLabelProvider.NON_OK_PIECES_COLUMN_IND:
      result = AssemblyMonitoring.sortInt(monitoring1.getNonOkPieces(),
          monitoring2.getNonOkPieces());
      break;
    case MonitoringTableLabelProvider.SUM_PIECES_COLUMN_IND:
      result = AssemblyMonitoring.sortInt(monitoringTableModel.calcSumPieces(monitoring1),
          monitoringTableModel.calcSumPieces(monitoring2));
      break;
    case MonitoringTableLabelProvider.BREAK_TIME_IND:
      result = AssemblyMonitoring.sortInt(monitoring1.getBreakTime(),
          monitoring2.getPieces());
      break;
    case MonitoringTableLabelProvider.PAUSE_TIME_IND:
      result = AssemblyMonitoring.sortInt(monitoring1.getPauseTime(),
          monitoring2.getPieces());
      break;
    case MonitoringTableLabelProvider.TIME_FROM_COLUMN_IND:
      result = AssemblyMonitoring.sortDate(monitoring1.getFromTime(),
          monitoring2.getFromTime());
      break;
    case MonitoringTableLabelProvider.TIME_TO_COLUMN_IND:
      result = AssemblyMonitoring.sortDate(monitoring1.getToTime(),
          monitoring2.getToTime());
      break;
    case MonitoringTableLabelProvider.EFFECTIVE_TIME_COLUMN_IND:
      result = AssemblyMonitoring.sortDate(monitoringTableModel.calcEffectiveTime(monitoring1),
          monitoringTableModel.calcEffectiveTime(monitoring2));
      break;
    case MonitoringTableLabelProvider.SIGN_COLUMN_IND:
      result = AssemblyMonitoring.sortString(monitoringTableModel.calcSign(monitoring1),
          monitoringTableModel.calcSign(monitoring2));
      break;
    case MonitoringTableLabelProvider.COMMENT_COLUMN_IND:
      result = AssemblyMonitoring.sortString(monitoring1.getComment(),
          monitoring2.getComment());
      break;
    case MonitoringTableLabelProvider.EFFICIENCY_COLUMN_IND:
      result = AssemblyMonitoring.sortDouble(monitoringTableModel.calcEfficiency(monitoring1),
          monitoringTableModel.calcEfficiency(monitoring2));
      break;
    case MonitoringTableLabelProvider.NOK_PERCENT_COLUMN_IND:
      result = AssemblyMonitoring.sortDouble(monitoringTableModel.calcPercentNonOkPieces(monitoring1),
          monitoringTableModel.calcPercentNonOkPieces(monitoring2));
      break;
    case MonitoringTableLabelProvider.EFFECTIVITY_COLUMN_IND:
      result = AssemblyMonitoring.sortDouble(monitoringTableModel.calcEffectivity(monitoring1),
          monitoringTableModel.calcEffectivity(monitoring2));
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
