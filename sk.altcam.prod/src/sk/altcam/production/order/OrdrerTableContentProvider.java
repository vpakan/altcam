package sk.altcam.production.order;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class OrdrerTableContentProvider implements IStructuredContentProvider{
  private OrderTableModel orderTableModel = null;
  
  public OrdrerTableContentProvider (OrderTableModel orderTableModel){
    this.orderTableModel = orderTableModel;
  }
  
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }
  
  public Object[] getElements(Object parent) {
    return orderTableModel.getData().toArray();
  }
  
  public void dispose() {
    // not implemented yet
  }
}
