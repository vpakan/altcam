package sk.altcam.assembly;

import java.text.ParseException;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class TimeVerifyListener implements VerifyListener{
  public void verifyText(VerifyEvent e) {
    if (e.text.length() > 1){
      try{
        AssemblyMonitoring.DISPLAY_TIME_FORMAT.parse(e.text);
        e.doit = true;
      } catch (ParseException pe){
        e.doit = false;
      }
    }
    else{
      e.doit = "0123456789:".indexOf(e.text) >= 0 ;  
    }
  }
}
