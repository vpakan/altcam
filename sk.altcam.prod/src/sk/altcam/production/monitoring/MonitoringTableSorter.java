package sk.altcam.production.monitoring;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import sk.altcam.production.ProductionMonitoring;
import sk.altcam.production.entity.Monitoring;


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
    case MonitoringTableLabelProvider.ORDER_COLUMN_IND:
      result = ProductionMonitoring.sortString(monitoring1.getOrderNumber(),
          monitoring2.getOrderNumber());
      if (result == 0){
        result = ProductionMonitoring.sortInt(monitoring1.getOrder(),
            monitoring2.getOrder());
      }
      break;
    case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      result = ProductionMonitoring.sortString(monitoring1.getOrderNumber(),
          monitoring2.getOrderNumber());
      break;
    case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND:
      result = ProductionMonitoring.sortString(monitoring1.getOrderEntity() == null ? "" : monitoring1.getOrderEntity().getItemNumber(),
          monitoring2.getOrderEntity() == null ? "" : monitoring2.getOrderEntity().getItemNumber());
      break;
    case MonitoringTableLabelProvider.DATE_COLUMN_IND:
      result = ProductionMonitoring.sortDate(monitoring1.getDate(), monitoring2
          .getDate());
      break;
    case MonitoringTableLabelProvider.PLANNED_IDLE_TIME_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getPlannedIdleTime(),
          monitoring2.getPlannedIdleTime());
      break;
    case MonitoringTableLabelProvider.FORM_SWITCH_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getFormExchange(),
          monitoring2.getFormExchange());
      break;
    case MonitoringTableLabelProvider.PRODUCED_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getProduced(),
          monitoring2.getProduced());
      break;
    case MonitoringTableLabelProvider.NABEHOVE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getNabehove(),
          monitoring2.getNabehove());
      break;
    case MonitoringTableLabelProvider.PRESTREKY_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getPrestreky(),
          monitoring2.getPrestreky());
      break;
    case MonitoringTableLabelProvider.NEDOSTREKY_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getNedostreky(),
          monitoring2.getNedostreky());
      break;
    case MonitoringTableLabelProvider.ZHORENE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getZhorene(),
          monitoring2.getZhorene());
      break;
    case MonitoringTableLabelProvider.VADA_MATERIALU_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getVadaMaterialu(),
          monitoring2.getVadaMaterialu());
      break;
    case MonitoringTableLabelProvider.DEFORMACIE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getDeformacie(),
          monitoring2.getDeformacie());
      break;
    case MonitoringTableLabelProvider.POSKODENY_ZASTREK_ZALIS_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getPoskodenyZastrekZalis(),
          monitoring2.getPoskodenyZastrekZalis());
      break;
    case MonitoringTableLabelProvider.CHYBAJUCI_ZASTREK_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getChybajuciZastrek(),
          monitoring2.getChybajuciZastrek());
      break;
    case MonitoringTableLabelProvider.BOD_VSTREKU_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getBodVstreku(),
          monitoring2.getBodVstreku());
      break;
    case MonitoringTableLabelProvider.PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getPritomnostPruznychElementov(),
          monitoring2.getPritomnostPruznychElementov());
      break;
    case MonitoringTableLabelProvider.VYHADZANE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getVyhadzane(),
          monitoring2.getVyhadzane());
      break;
    case MonitoringTableLabelProvider.PRESTREKY_V_OTVOROCH_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getPrestrekyVOtvoroch(),
          monitoring2.getPrestrekyVOtvoroch());
      break;
    case MonitoringTableLabelProvider.FARBA_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getFarba(),
          monitoring2.getFarba());
      break;
    case MonitoringTableLabelProvider.ZASTAVENIE_STROJA_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getZastavenieStroja(),
          monitoring2.getZastavenieStroja());
      break;
    case MonitoringTableLabelProvider.INE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getIne(),
          monitoring2.getIne());
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_STROJ_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getNedolisovaneStroj(),
          monitoring2.getNedolisovaneStroj());
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_FORMA_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getNedolisovaneForma(),
          monitoring2.getNedolisovaneForma());
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_PERIFERIE_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoring1.getNedolisovanePeriferie(),
          monitoring2.getNedolisovanePeriferie());
      break;
    case MonitoringTableLabelProvider.SUM_PPM_BROKEN_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoringTableModel.calcSumPpmBroken(monitoring1),
          monitoringTableModel.calcSumBroken(monitoring2));
      break;
    case MonitoringTableLabelProvider.SUM_BROKEN_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoringTableModel.calcSumBroken(monitoring1),
          monitoringTableModel.calcSumBroken(monitoring2));
      break;
    case MonitoringTableLabelProvider.SUM_TOTAL_COLUMN_IND:
      result = ProductionMonitoring.sortInt(monitoringTableModel.calcSumTotal(monitoring1),
          monitoringTableModel.calcSumTotal(monitoring2));
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
