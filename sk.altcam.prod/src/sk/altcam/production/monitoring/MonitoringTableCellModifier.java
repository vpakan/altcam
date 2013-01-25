package sk.altcam.production.monitoring;
import java.text.ParseException;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import sk.altcam.production.ProductionMonitoring;
import sk.altcam.production.entity.Monitoring;

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
	  int columnIndex = MonitoringTableLabelProvider.getColumnNames().indexOf(property);
	  Monitoring monitoring = (Monitoring) element;
	  if (columnIndex == MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND ){
	    return monitoring.getOrderNumber().trim().equals("?");
	  }
	  else{
	    return true;
	  }  
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		int columnIndex = MonitoringTableLabelProvider.getColumnNames().indexOf(property);

		Object result = null;
		Monitoring monitoring = (Monitoring) element;
		switch (columnIndex) {
			case MonitoringTableLabelProvider.ORDER_COLUMN_IND: 
				result = new Integer(monitoring.getOrder());
				break;
			case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND :
				result = monitoring.getOrderNumber();
				break;
			case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND: 
				break;
			case MonitoringTableLabelProvider.DATE_COLUMN_IND: 
				result = monitoring.getDate() == null ? "?" : ProductionMonitoring.DISPLAY_DATE_FORMAT.format(monitoring.getDate());
				break;
			case MonitoringTableLabelProvider.PLANNED_IDLE_TIME_COLUMN_IND: 
        result = monitoring.getPlannedIdleTime() + "";
        break;
			case MonitoringTableLabelProvider.FORM_SWITCH_COLUMN_IND: 
        result = monitoring.getFormExchange() + "";
        break;  
      case MonitoringTableLabelProvider.PRODUCED_COLUMN_IND: 
        result = monitoring.getProduced() + "";
        break;
      case MonitoringTableLabelProvider.NABEHOVE_COLUMN_IND: 
        result = monitoring.getNabehove() + "";
        break;
      case MonitoringTableLabelProvider.PRESTREKY_COLUMN_IND: 
        result = monitoring.getPrestreky() + "";
        break;
      case MonitoringTableLabelProvider.NEDOSTREKY_COLUMN_IND: 
        result = monitoring.getNedostreky() + "";
        break;
      case MonitoringTableLabelProvider.ZHORENE_COLUMN_IND: 
        result = monitoring.getZhorene() + "";
        break;
      case MonitoringTableLabelProvider.VADA_MATERIALU_COLUMN_IND: 
        result = monitoring.getVadaMaterialu() + "";
        break;
      case MonitoringTableLabelProvider.DEFORMACIE_COLUMN_IND: 
        result = monitoring.getDeformacie() + "";
        break;
      case MonitoringTableLabelProvider.POSKODENY_ZASTREK_ZALIS_COLUMN_IND: 
        result = monitoring.getPoskodenyZastrekZalis() + "";
        break;
      case MonitoringTableLabelProvider.CHYBAJUCI_ZASTREK_COLUMN_IND: 
        result = monitoring.getChybajuciZastrek() + "";
        break;
      case MonitoringTableLabelProvider.BOD_VSTREKU_COLUMN_IND: 
        result = monitoring.getBodVstreku() + "";
        break;
      case MonitoringTableLabelProvider.PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND: 
        result = monitoring.getPritomnostPruznychElementov() + "";
        break;
      case MonitoringTableLabelProvider.VYHADZANE_COLUMN_IND: 
        result = monitoring.getVyhadzane() + "";
        break;
      case MonitoringTableLabelProvider.PRESTREKY_V_OTVOROCH_COLUMN_IND: 
        result = monitoring.getPrestrekyVOtvoroch() + "";
        break;
      case MonitoringTableLabelProvider.FARBA_COLUMN_IND: 
        result = monitoring.getFarba() + "";
        break;
      case MonitoringTableLabelProvider.ZASTAVENIE_STROJA_COLUMN_IND: 
        result = monitoring.getZastavenieStroja() + "";
        break;
      case MonitoringTableLabelProvider.INE_COLUMN_IND: 
        result = monitoring.getIne() + "";
        break;
      case MonitoringTableLabelProvider.NEDOLISOVANE_STROJ_COLUMN_IND: 
        result = monitoring.getNedolisovaneStroj() + "";
        break;
      case MonitoringTableLabelProvider.NEDOLISOVANE_FORMA_COLUMN_IND: 
        result = monitoring.getNedolisovaneForma() + "";
        break;
      case MonitoringTableLabelProvider.NEDOLISOVANE_PERIFERIE_COLUMN_IND: 
        result = monitoring.getNedolisovanePeriferie() + "";
        break;
      case MonitoringTableLabelProvider.SUM_BROKEN_COLUMN_IND: 
        break;
      case MonitoringTableLabelProvider.SUM_TOTAL_COLUMN_IND: 
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
    case MonitoringTableLabelProvider.ORDER_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.ORDER_NUMBER_COLUMN_IND:
      valueString = ((String)value).trim();
      monitoring.setOrderNumber(valueString);
      break;
    case MonitoringTableLabelProvider.ITEM_NUMBER_COLUMN_IND: 
      break;
    case MonitoringTableLabelProvider.DATE_COLUMN_IND:
      valueString = ((String)value).trim();
      try {
        monitoring.setDate(ProductionMonitoring.DISPLAY_DATE_FORMAT.parse(valueString));
      } catch (ParseException e) {
        monitoring.setDate(null);
      }
      break;
    case MonitoringTableLabelProvider.PLANNED_IDLE_TIME_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPlannedIdleTime(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.FORM_SWITCH_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setFormExchange(Integer.parseInt(valueString));
      break;  
    case MonitoringTableLabelProvider.PRODUCED_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setProduced(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.NABEHOVE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setNabehove(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.PRESTREKY_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPrestreky(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.NEDOSTREKY_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setNedostreky(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.ZHORENE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setZhorene(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.VADA_MATERIALU_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setVadaMaterialu(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.DEFORMACIE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setDeformacie(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.POSKODENY_ZASTREK_ZALIS_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPoskodenyZastrekZalis(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.CHYBAJUCI_ZASTREK_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setChybajuciZastrek(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.BOD_VSTREKU_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setBodVstreku(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.PRITOMNOST_PRUZNYCH_ELEMENTOV_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPritomnostPruznychElementov(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.VYHADZANE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setVyhadzane(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.PRESTREKY_V_OTVOROCH_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setPrestrekyVOtvoroch(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.FARBA_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setFarba(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.ZASTAVENIE_STROJA_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setZastavenieStroja(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.INE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0){
        valueString = "0";
      }  
      monitoring.setIne(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_STROJ_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0) {
        valueString = "0";
      }
      monitoring.setNedolisovaneStroj(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_FORMA_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0) {
        valueString = "0";
      }
      monitoring.setNedolisovaneForma(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.NEDOLISOVANE_PERIFERIE_COLUMN_IND:
      valueString = ((String) value).trim();
      if (valueString.length() == 0) {
        valueString = "0";
      }
      monitoring.setNedolisovanePeriferie(Integer.parseInt(valueString));
      break;
    case MonitoringTableLabelProvider.SUM_PPM_BROKEN_COLUMN_IND:
      break;
   case MonitoringTableLabelProvider.SUM_BROKEN_COLUMN_IND:
      break;
    case MonitoringTableLabelProvider.SUM_TOTAL_COLUMN_IND:
      break;
    default :
    }
    monitoringTableModel.monitoringChanged (monitoring);
	}
}
