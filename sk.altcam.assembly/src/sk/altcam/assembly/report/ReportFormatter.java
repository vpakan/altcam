package sk.altcam.assembly.report;

import java.text.DecimalFormat;
import java.util.Arrays;

public class ReportFormatter {
  public static final int ALLIGN_LEFT = 0;
  public static final int ALLIGN_RIGHT = 1;
  public static final int ALLIGN_CENTER = 2;
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
  private int[] columnsWidth = null;
  private int[] columnsAllign = null;
  private boolean[] columnsVisible = null;
  private int pageWidth = 0;
  private StringBuffer sb = new StringBuffer("");
  private int columnIndex = 0;
  public ReportFormatter (int[] columnsWidth , int[] columnsAllign, boolean[] columnsVisible){
    this.columnsWidth = columnsWidth;
    this.columnsAllign = columnsAllign;
    this.columnsVisible = columnsVisible;
    pageWidth = 0;
    for (int index = 0; index < columnsWidth.length; index++){
      if (this.columnsVisible[index])
      this.pageWidth += this.columnsWidth[index] + 1; 
    }
    pageWidth--;
  }
  static {
    ReportFormatter.DECIMAL_FORMAT.setMinimumFractionDigits(2);
    ReportFormatter.DECIMAL_FORMAT.setMaximumFractionDigits(2);
  }
  /**
   * @param args
   */
  public static void main(String[] args) {
    ReportFormatter rf = new ReportFormatter(new int[]{4,6,8,10}, 
      new int[]{ReportFormatter.ALLIGN_LEFT,
        ReportFormatter.ALLIGN_CENTER,
        ReportFormatter.ALLIGN_RIGHT,
        ReportFormatter.ALLIGN_RIGHT},
      new boolean[] {true,true,true,true});
    rf.addText("1234567890123456789012345678901234567890");
    rf.addNewLine();
    rf.addCenteredLine("center");
    rf.addColumnText("a");
    rf.addColumnText("a");
    rf.addColumnText("a");
    rf.addColumnText("a");
    rf.addNewLine();
    rf.addColumnText("aa");
    rf.addColumnText("aa");
    rf.addColumnText("aa");
    rf.addColumnText("aa");
    rf.addNewLine();
    rf.addColumnText("aaaaaa");
    rf.addColumnText("aaaaaa");
    rf.addColumnText("aaaaaa");
    rf.addColumnText("aaaaaa");
    rf.addNewLine();
    rf.addColumnText("aaaaaaaa");
    rf.addColumnText("aaaaaaaa");
    rf.addColumnText("aaaaaaaa");
    rf.addColumnText("aaaaaaaa");
    rf.addNewLine();
    System.out.println(rf.getReportText());
    System.out.println(ReportFormatter.DECIMAL_FORMAT.format(10));
  }
  public void addText (String text){
    sb.append(text);
  }
  public void addColumnText (String text){
    if (columnsVisible == null || columnsVisible[columnIndex]){
      if (columnIndex != 0){
        sb.append(" ");
      }
      sb.append(ReportFormatter.allign(text, columnsWidth[columnIndex], columnsAllign[columnIndex]));
    }
    columnIndex++;
  }
  public void addNewLine(){
    columnIndex = 0;
    sb.append("\n");
  }
  public void addCenteredLine(String text){
    sb.append(ReportFormatter.allign(text, pageWidth, ReportFormatter.ALLIGN_CENTER));
    sb.append("\n");
  }
  public String getReportText(){
    return sb.toString();
  }
  public static String allign (String text, int width, int allign){
    String result = null;
    if (text != null){
      if (text.length() < width){
        if (allign == ReportFormatter.ALLIGN_RIGHT){
          result = fill(' ',width - text.length()) + text;
        }else if(allign == ReportFormatter.ALLIGN_CENTER){
          result = fill(' ', (width - text.length()) / 2) + text;
          result = result + fill(' ',width - result.length());
        }else{
          result = text + fill(' ',width - text.length());
        } 
      }
      else{
        result = text.substring(0, width);
      }
    }
    else {
      result = ReportFormatter.fill(' ',width); 
    }
    return result;
  }
  private static String fill (char c,int num){
    char[] fill = new char[num];
    Arrays.fill(fill, c);
    return new String(fill);   
  }
}
