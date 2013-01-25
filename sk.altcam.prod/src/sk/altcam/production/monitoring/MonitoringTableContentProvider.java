package sk.altcam.production.monitoring;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class MonitoringTableContentProvider implements IStructuredContentProvider{
  private MonitoringTableModel monitoringTableModel = null;
  
  public MonitoringTableContentProvider (MonitoringTableModel monitoringTableModel){
    this.monitoringTableModel = monitoringTableModel;
  }
  
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }
  
  public Object[] getElements(Object parent) {
    return monitoringTableModel.getData().toArray();
  }
  
  public void dispose() {
    // not implemented yet
  }
}
