import java.awt.*;
import java.util.*;
import java.lang.*;

public class JmjController extends Frame {
  
  private final int LOAD_FILE =1;
  private final String QUIT = "quit";

  Jmj jmj;
  
  List list;
  Button juggle_button;
  Button pause_button;
  Button cancel_button;

  Label primarymessage;
  Label secondarymessage;
  Label motion_label;
  Label motion_value;
  Label ballno_label;
  Label ballno_value;
  Label pattern_label;
  Label pattern_value;
  
  Label speed_label;
  Scrollbar speed_gauge;
  Label speed_value;
  Label height_label;
  Scrollbar height_gauge;
  Label height_value;
  Label dwell_label;
  Scrollbar dwell_gauge;
  Label dwell_value;
  Label redrawrate_label;
  Scrollbar redrawrate_gauge;
  Label redrawrate_value;
  
  MenuBar mb;
  Menu menu_option;
  Menu menu_quit;

  JmjDialog jd;

  Label dialog_label = new Label();
  TextField dialog_text = new TextField();
  Button dialog_cancel = new Button("cancel");
  List dialog_motionlist = new List();
  Button dialog_ok = new Button("OK");

  Checkbox mirror_box, ss_box, body_box;
  int dialog_status;
  boolean cancel_flag = false;
  int prevIndex;
  
  public JmjController(Jmj jmj, String quitflag){

    super("Juggle Master java - Control Panel"); 
    this.jmj = jmj;;

    resize(480, 470);
    setLayout(null);
    setBackground(Color.white);
    setResizable(false);
    
    list = new List();
    list.reshape(270, 190, 200, 180);
    add(list);
    
    primarymessage = new Label();
    primarymessage.reshape(10, 400, 460, 20);
    add(primarymessage);
    
    secondarymessage = new Label();
    secondarymessage.reshape(10, 420, 460, 20);
    add(secondarymessage);
    
    juggle_button = new Button("juggle");
    juggle_button.reshape(50, 140, 100, 30);
    add(juggle_button);
    
    pause_button = new Button("pause");
    pause_button.reshape(190, 140, 100, 30);
    add(pause_button);
    
    cancel_button = new Button("stop/cancel");
    cancel_button.reshape(330, 140, 100, 30);
    add(cancel_button);
    
    pattern_label = new Label("pattern");
    pattern_label.reshape(10, 60, 100, 20);
    add(pattern_label);
    
    pattern_value = new Label("");
    pattern_value.reshape(120, 60, 300, 20);
    add(pattern_value);
    
    motion_label = new Label("arm motion");
    motion_label.reshape(10, 80, 100, 20);
    add(motion_label);
    
    motion_value = new Label("");
    motion_value.reshape(120, 80, 300, 20);
    add(motion_value);
    
    ballno_label = new Label("ball #");
    ballno_label.reshape(10, 100, 100, 20);
    add(ballno_label);
    
    ballno_value = new Label("");
    ballno_value.reshape(120, 100, 300, 20);
    add(ballno_value);
    
    speed_label = new Label("speed");
    speed_label.reshape(10, 190, 100, 20);
    add(speed_label);
    
    speed_gauge = new Scrollbar(Scrollbar.HORIZONTAL, 10, 3, 1, 20 + 3);
    speed_gauge.reshape(110, 190, 100, 20);
    add(speed_gauge);
    
    redrawrate_label = new Label("smoothness");
    redrawrate_label.reshape(10, 210, 100, 20);
    add(redrawrate_label);
    
    redrawrate_gauge =  new Scrollbar(Scrollbar.HORIZONTAL, 50, 10, 20, 100 + 10);
    redrawrate_gauge.reshape(110, 210, 100, 20);
    add(redrawrate_gauge);
    
    redrawrate_value = new Label("");
    redrawrate_value.reshape(220, 210, 40, 20);
    add(redrawrate_value);
    
    speed_value = new Label("");
    speed_value.reshape(220, 190, 40, 20);
    add(speed_value);
    
    height_label = new Label("height");
    height_label.reshape(10, 230, 100, 20);
    add(height_label);
    
    height_gauge = new Scrollbar(Scrollbar.HORIZONTAL, 20, 15, 1, 100 + 15);
    height_gauge.reshape(110, 230, 100, 20);
    add(height_gauge);
    
    height_value = new Label("");
    height_value.reshape(220, 230, 40, 20);
    add(height_value);
    
    dwell_label = new Label("dwell ratio");
    dwell_label.reshape(10, 250, 100, 20);
    add(dwell_label);
    
    dwell_gauge = new Scrollbar(Scrollbar.HORIZONTAL, 20, 10, 10, 90 + 10);
    dwell_gauge.reshape(110, 250, 100, 20);
    add(dwell_gauge);
    
    dwell_value = new Label("");
    dwell_value.reshape(220, 250, 40, 20);
    add(dwell_value);
    
    mirror_box = new Checkbox("mirror image");
    mirror_box.reshape(50, 280, 150, 20);
    add(mirror_box);

    ss_box = new Checkbox("show siteswap");
    ss_box.reshape(50, 300, 180, 20);
    add(ss_box);

    body_box = new Checkbox("show juggler");
    body_box.reshape(50, 320, 180, 20);
    add(body_box);

    mb = new MenuBar();
    setMenuBar(mb);
    menu_quit = new Menu(QUIT);
    menu_quit.add(new MenuItem(QUIT));
    if(! (quitflag != null && quitflag.equalsIgnoreCase("true"))){
      mb.add(menu_quit);
    }
    menu_option = new Menu("option");
    menu_option.add(new MenuItem(JmjDialog.STRING_LOAD));
    menu_option.add(new MenuItem(JmjDialog.STRING_SITESWAP));
    menu_option.add(new MenuItem(JmjDialog.STRING_MOTION));

    mb.add(menu_option);
    validate();
    
    dialog_text.setFont(new Font("Dialog", Font.PLAIN, 15));
    dialog_cancel.resize(70, 20);
    dialog_ok.resize(70, 20);
    dialog_motionlist.resize(189, 190);
  }
  
  public boolean handleEvent(Event e){
    if (e.target == speed_gauge){
      setSpeedLabel();
      return true;
    }
    if (e.target == height_gauge){
      setHeightLabel();
      return true;
    }
    if (e.target == dwell_gauge){
      setDwellLabel();
      return true;
    }
    if (e.target == redrawrate_gauge){
      setRedrawrateLabel();
      return true;
    }
    if (e.target == list && e.id == Event.LIST_SELECT){
      chooseValidIndex();
      return true;
    }
    return super.handleEvent(e);
  }
  public boolean action(Event e, Object obj){
    if (e.target == list && e.id == Event.ACTION_EVENT){
      chooseValidIndex();
      juggle_pressed();
      return true;
    }
    if (e.target == juggle_button){
      juggle_pressed();
      return true;
    }
    if (e.target == pause_button){
      jmj.status = (jmj.status == Jmj.IDLE ? Jmj.IDLE :
		    jmj.status == Jmj.PAUSE ? Jmj.JUGGLING : Jmj.PAUSE);
      return true;
    }
    if (e.target == cancel_button){
      if(jmj.status != Jmj.IDLE){
	jmj.stopJuggling();
	return true;
      }
      cancel_flag = true;
      return true;
    }
    if (e.target instanceof MenuItem){
      if ("quit".equals((String) obj)){
	jmj.quit();
      }
      if (JmjDialog.STRING_LOAD.equals((String) obj) ){
	disposeDialog();
	jd = new JmjDialog(this);
	jd.popup(JmjDialog.LOAD_FILE);
      }
      if (JmjDialog.STRING_SITESWAP.equals((String) obj)){
	disposeDialog();
	jd = new JmjDialog(this);
	jd.popup(JmjDialog.TRY_SITESWAP);
      }
      if (JmjDialog.STRING_MOTION.equals((String) obj)){
	try{
	  disposeDialog();
	  jd = new JmjDialog(this);
	  jd.popup(JmjDialog.CHOOSE_MOTION);
	}
	catch(ArrayIndexOutOfBoundsException aioobe){}
      }
      return true;
    }
    return super.action(e, obj);
  }
  void juggle_pressed(){
    synchronized(jmj){
      jmj.startJuggling(list.getSelectedIndex());
    }
  }
  boolean isCanceled(){
    try{
      return cancel_flag;
    }
    finally{
      cancel_flag = false;
    }
  }
  void setSpeedLabel(){
    speed_value.setText(String.valueOf(getSpeed()));
  }
  void setHeightLabel(){
    height_value.setText(String.valueOf(getHeight()));
  }
  void setDwellLabel(){
    dwell_value.setText(String.valueOf(getDwell()));
  }
  void setRedrawrateLabel(){
    redrawrate_value.setText(String.valueOf(getRedrawrate()));
  }
  
  void setLabels(){
    pattern_value.setText(jmj.pattern);
    ballno_value.setText(String.valueOf(jmj.ballno));
    motion_value.setText(jmj.motion);
  }
  void setSpeed(float speed){
    speed_gauge.setValue((int)(speed * 10));
    setSpeedLabel();
  }
  void setHeight(float height){
    height_gauge.setValue((int)(height * 100));
    setHeightLabel();
  }
  void setDwell(float dwell){
    dwell_gauge.setValue((int)(dwell * 100));
    setDwellLabel();
  }
  void setRedrawrate(float redrawrate){
    redrawrate_gauge.setValue((int)redrawrate);
    setRedrawrateLabel();
  }
  float getSpeed(){
    return speed_gauge.getValue() / 10f;
  }
  float getHeight(){
    return height_gauge.getValue() / 100f;
  }
  float getDwell(){
    return dwell_gauge.getValue() / 100f;
  }
  float getRedrawrate(){
    return (float)redrawrate_gauge.getValue();
  }
  boolean ifShowBody(){
    return body_box.getState();
  }
  boolean ifShowSiteSwap(){
    return ss_box.getState();
  }
  boolean ifMirror(){
    return mirror_box.getState();
  }
  void setIfShowBody(boolean f){
    body_box.setState(f);
  }
  void setIfShowSiteSwap(boolean f){
    ss_box.setState(f);
  }
  void setIfMirror(boolean f){
    mirror_box.setState(f);
  }

  boolean isNewChoice(){
    boolean b;
    b = (prevIndex != list.getSelectedIndex());
    prevIndex = list.getSelectedIndex();
    return b;
  }
  void enableSwitches(){
    juggle_button.enable();
    pause_button.enable();
    list.enable();
  }
  void disableSwitches(){
    juggle_button.disable();
    pause_button.disable();
    list.disable();
  }
  void putMessage(String s1, String s2){
    putPrimaryMessage(s1);
    putSecondaryMessage(s2);
  }
  void putPrimaryMessage(String s){
    primarymessage.setText(s);
  }
  void putSecondaryMessage(String s){
    secondarymessage.setText(s);
  }
  void chooseValidIndex(){
    int i ;
    for (i = list.getSelectedIndex(); i < list.countItems() ; i++){
      if (jmj.holder.isPattern(i)){
	list.select(i);
	return;
      }
    }
    for (i = list.getSelectedIndex(); i > -1 ; i--){
      if (jmj.holder.isPattern(i)){
	list.select(i);
	return;
      }
    }
    list.deselect(list.getSelectedIndex());
  }
  void addPatternList(String name){
    list.addItem(name);
  }
  void clearList(){
    list.clear();
  }
  void validatePatternList(){
    list.validate();
  }
  void enableMenuBar(){
    menu_option.enable();
    menu_quit.enable();
  }
  void disableMenuBar(){
    menu_option.disable();
    menu_quit.enable();
  }
  void disposeDialog(){
    if(jd != null){
      jd.hide();
      jd.dispose();
      jd = null;
    }
  }

  static class JmjDialog extends Dialog{
    public static final String STRING_LOAD = "load a pattern file";
    public static final String STRING_SITESWAP = "try a new siteswap";
    public static final String STRING_MOTION = "choose the motion";

    public static final int LOAD_FILE = 1;
    public static final int TRY_SITESWAP = 2;
    public static final int CHOOSE_MOTION = 3;
    private int status;
    JmjController jc;

    private List motionlist;
    private Label label;
    private Button ok, cancel;
    private TextField text;

    JmjDialog(JmjController j){
      super(j, false);
      setLayout(null);
      jc = j;
      motionlist = jc.dialog_motionlist;
      label = jc.dialog_label;
      ok = jc.dialog_ok;
      cancel = jc.dialog_cancel;
      text = jc.dialog_text;
      add(ok);
      add(cancel);
      add(label);
      hide();
    }
    void popup(int mode){
      switch(status = mode){
      case LOAD_FILE:
	resize(400, 160);
	add(text);
	label.setText("type in URL or filename to load");
	text.setText(String.valueOf(jc.jmj.getCodeBase()));
	label.reshape(10, 30, 380, 20);
	text.reshape(10, 60, 380, 25);
	ok.move(100, 100);
	cancel.move(230, 100);
	validate();
	show();
	return;
      case TRY_SITESWAP:
	resize(600, 260);
	add(text);
	add(motionlist);
	label.setText("Type in a siteswap, and choose the motion if you like");
	text.setText(new String());
	makeMotionlist();
	motionlist.reshape(410, 30, 180, 190);
	label.reshape(10, 30, 380, 20);
	text.reshape(10, 60, 380, 30);
	cancel.move(230, 120);
	ok.move(100, 120);
	validate();
        show();
	return;
      case CHOOSE_MOTION:
	String s;
	resize(200, 310);
	jc.jmj.stopJuggling();
	add(motionlist);
	label.setText("Choose the motion");
	makeMotionlist();
	label.reshape(5, 30, 190, 20);
	motionlist.reshape(5, 60, 190, 180);
	cancel.move(110, 250);
	ok.move(20, 250);
	validate();
	show();
	return;
      }
    }

    public boolean action(Event e, Object obj){
      switch(status){
      case LOAD_FILE:
	if (e.target == text || e.target == ok){
	  hide();
	  if(text.getText().length() != 0 &&
	     !text.getText().equals(String.valueOf(jc.jmj.getCodeBase()))){
	    jc.jmj.openFile(text.getText());
	  }
	  return true;
	}
	break;
      case TRY_SITESWAP:
	if (e.target == ok || e.target == text){
	  hide();
	  if(text.getText().length() != 0){
	    if(motionlist.getSelectedIndex() != -1){
	      jc.jmj.motion = motionlist.getSelectedItem();
	    }
	    else{
	      jc.jmj.motion = Jmj.NORMAL;
	    }
	    int i = jc.list.getSelectedIndex();
	    if(i != -1){
	      jc.list.deselect(i);
	      jc.isNewChoice();
	    }
	    synchronized(jc.jmj){
	      jc.jmj.startJuggling(Jmj.SITESWAP_MODE, text.getText());
	    }
	  }
	  return true;
	}
	break;
      case CHOOSE_MOTION:
	if (e.target == motionlist || e.target == ok){
	  hide();
	  synchronized(jc.jmj){
	    jc.jmj.startJuggling(Jmj.MOTION_MODE, motionlist.getSelectedItem());
	  }
	  return true;
	}
      }
      if (e.target == cancel){
	hide();
	return true;
      }
      return super.action(e, obj);
    }
    void quickSort(int left, int right){
      int i, last;
      if(left >= right) return;
      swap(left, (left + right)/2);
      last = left;
      for (i = left + 1; i <= right; i++)
	if(motionlist.getItem(i).compareTo(motionlist.getItem(left)) < 0)
	  swap(++last, i);
      swap(last,left);
      quickSort(left, last - 1);
      quickSort(last + 1, right);
    }
    void swap(int d1, int d2){
      String tmp = motionlist.getItem(d1);
      motionlist.replaceItem(motionlist.getItem(d2), d1);
      motionlist.replaceItem(tmp, d2);
    }
    void makeMotionlist(){
      if (jc.jmj.holder.countMotions() > motionlist.countItems()){
	String s;
	motionlist.clear();
	while(true){
	  s =jc.jmj.holder.nextMotion();
	  if (s.length() == 0) break;
	  motionlist.addItem(s);
	}
	quickSort(0, motionlist.countItems() - 1);
      }
    }
  }      
}
