import java.awt.*;
import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.*;

public class Jmj extends java.applet.Applet implements Runnable{
  
  public final static float KW = 0.25f;
  public final static int XR = 1024;
  public final static int DW = 290;
  public final static int BMAX = 35;
  public final static int LMAX = 200;
  public final static int MMAX = 11;
  public final static String NORMAL = "Normal";
  
  final static int IDLE = 0;
  final static int PAUSE = 1;
  final static int JUGGLING = 2;
  final static int SITESWAP_MODE = -3;// should be < -1;
  final static int MOTION_MODE = -2;// should be < -1;

  static int Y_OFFSET = 0;
  //  ImageFrame imf;
  ImageFrame imf = null;
  JmjController controller;
  PatternHolder holder = null;
  Thread kicker = null;
  
  int dpm;
  int hand_x;
  int hand_y;
  int arm_x;
  int arm_y;
  int gx_max, gx_min;
  int tx, c0;
  
  int status;
  Arm ap = new Arm();
  
  float ga = 9.8f;
  float dwell, height, speed = 1.0f, redrawrate = 50.0f;
  int base;
  boolean mirror = false;
  String pattern, siteswap, motion = NORMAL, startpattern;
  String filename;
  byte motionarray[] = {13, 0, 4, 0};
  int startindex = -1;

  long time_count;
  int time_period;
  
  boolean syn;
  boolean hand_on = true;
  boolean show_ss = true;
  int intsyn;
  
  Ball rhand = new Ball(), lhand = new Ball();
  Ball b[] = new Ball[BMAX];
  int tw;
  int aw;
  int ballno;
  int pmax;
  
  int patt[][] = new int[LMAX][MMAX];
  int patts[] = new int[LMAX];
  int pattw;
  int r[] = new int[LMAX*2];
  float high[] = new float[BMAX+1];
  int patt_x;
  int kw0;
  String singless[] = new String[LMAX];
  int vsync_Count1;
  
  // hereafter were in ImageFrame

    Color color[] = {
      new Color(255, 255, 255),
      new Color(190, 190, 190),
      new Color(0, 0, 0),
      new Color(0, 255, 255),
      new Color(160, 32, 240),
      new Color(255, 0, 255),
      new Color(255, 192, 203),
      new Color(176, 48, 96),
      new Color(135, 206, 235),
      new Color(255, 127, 80),
      new Color(160, 82, 45),
      new Color(255, 165, 0),
      new Color(0, 255, 0),
      new Color(255, 255, 0),
      new Color(255, 0, 0),
      new Color(0, 0, 255)
    };

    public final  int PM_W = 32;
    public final  int PM_H = 24;
    final  int NCOLOR = 16;
    final  int IMAGE_WIDTH = 480;
    final  int IMAGE_HEIGHT = 400;
    final  int HOR_CENTER = (IMAGE_WIDTH/2);
    final  int VER_CENTER = (IMAGE_HEIGHT/2);

    // Jmj jmj;
    Graphics gg1 = null ,gg2 = null;
    Image bm[] = new Image[NCOLOR];
    Image r_bm[] = new Image[NCOLOR];
    Image l_bm[] = new Image[NCOLOR];

    Graphics bm_gc[] = new Graphics[NCOLOR];
    Graphics r_bm_gc[] = new Graphics[NCOLOR];
    Graphics l_bm_gc[] = new Graphics[NCOLOR];

    Image image_pixmap;
    Graphics image_gc;

    int bm1;
    int bm2;

  // above were in ImageFrame

  public void init(){
    String s;
    s = getParameter("embed");
    if(s != null && s.equalsIgnoreCase("true")){
      Y_OFFSET = 0;
      setBackground(Color.white);
      resize(IMAGE_WIDTH, IMAGE_HEIGHT + Y_OFFSET + 20);
      validate();
      show();
      image_pixmap = createImage(IMAGE_WIDTH, IMAGE_HEIGHT + 20);
      image_gc = image_pixmap.getGraphics();
    }
    else{
      imf = new ImageFrame(this);
      Y_OFFSET = 20;
      imf.setLayout(null);
      imf.setBackground(Color.white);
      imf.resize(IMAGE_WIDTH, IMAGE_HEIGHT + Y_OFFSET + 20);
      imf.validate();
      Dimension d = getToolkit().getScreenSize();
      imf.move(d.width / 2, d.height / 2);
      d = null;
      imf.show();
      image_pixmap = imf.createImage(IMAGE_WIDTH, IMAGE_HEIGHT + 20);
      image_gc = image_pixmap.getGraphics();
    }
    
    holder = new PatternHolder(this);
    controller = new JmjController(this,
		     getParameter("noquit"));
    controller.move(20, 20);
    controller.show();
    controller.enableMenuBar();
    
    for ( int i = 0; i < BMAX ; i++ )
      b[i] = new Ball();
    Ball.jmj = this;
    
    /*
     * if(System.getProperty("java.vendor".startsWith("Microsoft")){
     *  System.err.println("How awful!\nI found another servant of Bill GATE$!!!\nOh,I can't stand this!");
     *  throw new UnknownError();
     * }
     */

    readParameter();
  }
  public void quit(){
    stop();
    destroy();
  }
  public void destroy(){
    stopJuggling();
    try{
      controller.hide();
      controller.dispose();
    }
    catch(Throwable e){}
    try{
      disposeGraphics();
      if(imf != null){
	imf.dispose();
	imf.hide();
      }
    }
    catch(Throwable e){}
  }
  
  void stopJuggling(){
    if (kicker != null){
      kicker.stop();
      kicker = null;
    }
    status = IDLE;
  }
  void readParameter(){
    String s;
    s = getParameter("file");
    startpattern = getParameter("startwith");
    if (s != null && s.length() != 0){
      openFile(s);
    }
    if (startindex >= 0){
      synchronized(this){
	startJuggling(startindex);
      }
      return;
    }
    if(startpattern != null && startpattern.length() > 0){
      if (!startJuggling(SITESWAP_MODE, startpattern)){
	putError("Error in <PARAM> tag with \'startwith\' term.",
		 "Mail this message to the webmaster");
	return;
      }
    }
  }
  void openFile(String s){
    synchronized(holder){
      BufferedReader fp = null;
      controller.disableSwitches();
      stopJuggling();
      controller.disableMenuBar();
      controller.clearList();
      System.gc();
      try{
	URL u = new URL(s);
	fp = new BufferedReader(new InputStreamReader(u.openStream()));
      }
      catch (MalformedURLException e1){
	try{
	  URL u = new URL(getCodeBase() + s);
	  fp = new BufferedReader(new InputStreamReader(u.openStream()));
	  s = getCodeBase() + s;
	}
	catch (MalformedURLException e2) {
	  try {
	    fp = new BufferedReader(new FileReader(s));
	  }
	  catch (FileNotFoundException e3){
	  }
	  catch (IOException e4){
	  }
	}
	catch (IOException e5){
	}
      }
      catch (IOException e6){
      }
      finally{
	if(fp == null){
	  putError("file not found or inaccesible URL", s);
	  controller.enableMenuBar();
	  return;
	}
      }
      controller.putPrimaryMessage("loading" + s);
      try{
	if(!holder.setHolder(fp))
	  return;
      }
      finally{
	try{
	  fp.close();
	}
	catch(IOException e){
	}
      }
      controller.putMessage("done loading " + s,"preparing patterns table.wait...");
      filename = s;
      for (int i = 0 ;;i++){
	s = holder.nameAt(i);
	if (s.length() == 0 || controller.isCanceled()){
	  break;
	}
	controller.addPatternList(s);
      }
      controller.validatePatternList();
      controller.enableSwitches();
      controller.putMessage(new String(),new String());
      controller.enableMenuBar();
      controller.setSpeed(speed);
      controller.setRedrawrate(redrawrate);
      controller.setIfShowBody(hand_on);
      controller.setIfShowSiteSwap(show_ss);
      controller.setIfMirror(mirror);
      System.gc();
    }
  }
  
  void set_dpm(){
    int i,gy_max,gy_min;
    
    speed = 2;
    base = 0;
    dpm = 400;
    gy_max = 80 - 11;
    gy_min = -200 - 11;
    gx_max = -1000;
    gx_min = 1000;
    
    if (!pattInitialize())
      return;
    for(time_count = 0;
	time_count < tw * (pattw + pmax + (motionarray.length / 4));
	time_count++){
      for(i = 0; i < ballno; i++){
	b[i].juggle();
	gy_max = Math.max(gy_max, b[i].gy);
	gy_min = Math.min(gy_min, b[i].gy);
	gx_max = Math.max(gx_max, b[i].gx);
	gx_min = Math.min(gx_min, b[i].gx);
      }
      
      rhand.juggle();
      lhand.juggle();
      
      gy_max = Math.max(gy_max, rhand.gy);
      gy_min = Math.min(gy_min, rhand.gy);
      gy_max = Math.max(gy_max, lhand.gy);
      gy_min = Math.min(gy_min, lhand.gy);
      gx_max = Math.max(gx_max, rhand.gx);
      gx_min = Math.min(gx_min, rhand.gx);
      gx_max = Math.max(gx_max, lhand.gx);
      gx_min = Math.min(gx_min, lhand.gx);
      
      ap.rx[0] = rhand.gx + 11 + arm_x;
      ap.ry[0] = rhand.gy + 11 + arm_y;
      ap.lx[0] = lhand.gx + 11 - arm_x;
      ap.ly[0] = lhand.gy + 11 + arm_y;
      arm_line();
      for (i = 0; i < 5; i++) {
	gx_max = Math.max(gx_max, ap.rx[i]);
	gx_max = Math.max(gx_max, ap.lx[i]);
	gx_min = Math.min(gx_min, ap.rx[i]);
	gx_min = Math.min(gx_min, ap.lx[i]);
      }
    }
    if(gy_max-gy_min > 0){
      dpm = (int)((float)400 * 340 / (gy_max - gy_min));
      if(dpm > DW){
	dpm = DW;
      }
      base = (int)(370 - (float)gy_max * dpm / 400);
    }
  }
  void arm_line(){
    int mx,my,k;
    int sx,sy;
    
    sx = (int)((long)dpm * XR / kw0);
    sy = base - dpm / 3;
    
    ap.rx[1] = (ap.rx[0] + (HOR_CENTER + sx) * 2) / 3 + dpm / 12;
    ap.lx[1] = (ap.lx[0] + (HOR_CENTER - sx) * 2) / 3 - dpm / 12;
    ap.ry[1] = (ap.ry[0] + sy) / 2 + dpm / 8;
    ap.ly[1] = (ap.ly[0] + sy) / 2 + dpm / 8;
    
    ap.rx[2] = (ap.rx[1] + (HOR_CENTER + sx) * 3) / 4;
    ap.lx[2] = (ap.lx[1] + (HOR_CENTER - sx) * 3) / 4;
    ap.ry[2] = (ap.ry[1] + sy * 2) / 3 - dpm / 25;
    ap.ly[2] = (ap.ly[1] + sy * 2) / 3 - dpm / 25;
    
    ap.rx[3] = (ap.rx[2] + (HOR_CENTER + sx) * 2) / 3 - dpm / 13;
    ap.lx[3] = (ap.lx[2] + (HOR_CENTER - sx) * 2) / 3 + dpm / 13;
    ap.ry[3] = (ap.ry[2] + sy * 2) / 3 - dpm / 40;
    ap.ly[3] = (ap.ly[2] + sy * 2) / 3 - dpm / 40;
    
    mx = (ap.rx[3] + ap.lx[3]) / 2;
    my = (ap.ry[3] + ap.ly[3]) / 2;
    
    ap.rx[4] = (mx * 2 + ap.rx[3]) / 3;
    ap.lx[4] = (mx * 2 + ap.lx[3]) / 3;
    ap.ry[4] = (my * 2 + ap.ry[3]) / 3;
    ap.ly[4] = (my * 2 + ap.ly[3]) / 3;
    
    ap.hx = mx;
    ap.hy = (my * 2 - dpm * 2 / 3 + base) / 3;
    ap.hr = dpm / 11;
    
    ap.rx[5] = ap.hx + dpm / 20;
    ap.lx[5] = ap.hx - dpm / 20;
    ap.ry[5] = ap.hy + dpm / 13;
    ap.ly[5] = ap.ry[5];
  }
  boolean pattInitialize(){
    // return false if wrong siteswap;
    int i, j, k;
    float tw0, aw0;
    ballno = 0;
    pmax = 0;
    
    for (i = 0; i < pattw; i++){
      for (j = 0; j < patts[i]; j++){
	ballno += Math.abs(patt[i][j]);
	pmax = Math.max(pmax, Math.abs(patt[i][j]));
      }
    }
    if (ballno % pattw != 0){
      System.out.println("ballno % pattw != 0");
      return false;
    }
    ballno /= pattw;
    if (ballno == 0){
      System.out.println("no ball");
      return false;
    }
    if (ballno > BMAX){
      System.out.println("too many balls");
      return false;
    }
    for (i = 0; i < LMAX * 2; i++){
      r[i] = 0;
    }
    for (i = 0; i <= ballno; i++){
      j = 0;
      while (r[j] == patts[j % pattw] && j < pattw + pmax){
	j++;
      }
      if (i == ballno){
	if (j == pattw + pmax){
	  break;
	}
	else {
	  System.out.println("mulitplex error");
	  return false;
	}
      }
      b[i].st = 0;
      
      if (mirror){
	if ((j + intsyn) % 2 != 0){
	  b[i].thand = 1;
	  b[i].chand = 1;
	}
	else {
	  b[i].thand = 0;
	  b[i].chand = 0;
	}
      }
      else{
	if ((j + intsyn) % 2 != 0){
	  b[i].thand = 0;
	  b[i].chand = 0;
	}
	else {
	  b[i].thand = 1;
	  b[i].chand = 1;
	}
      }
      if (syn){
	b[i].c = -((int)(j / 2)) * 2;
      }
      else {
	b[i].c = -j;
      }
      while (j < pattw + pmax){
	if (r[j] == patts[j % pattw]){
	  return false;
	}
	else {
	  r[j]++;
	}
	k = patt[j % pattw][patts[j % pattw] - r[j]];
	if (syn && k < 0){
	  if (j % 2 == 0){
	    j += -k + 1;
	  }
	  else {
	    j += -k - 1;
	  }
	}
	else {
	  j+=k;
	}
      }
    }
    if (pmax < 3){
      pmax = 3;
    }
    tw0 = (float)Math.sqrt(2 / ga * pmax * height) * 2
      / (pmax - dwell * 2) * redrawrate / speed;
    tw = (int)fadd(tw0, 0);
    if (tw == 0){
      System.out.println("tw = 0");
      return false;
    }
    aw0 = tw0 * dwell * 2;
    aw = (int)fadd(aw0, 0);
    if (aw < 1){
      aw = 1;
    }
    if (aw > tw * 2 - 1){
      aw = tw * 2 - 1;
    }
    patt_x = HOR_CENTER / 8 - siteswap.length() / 2;
    
    kw0 = (int)((float)XR / KW);
    
    high[0] = -.2f * dpm;
    high[1] = (int)(ga * square(tw0 / redrawrate * speed) / 8 * dpm);
    for (i = 2; i <= pmax; i++){
      high[i] = (int)(ga * square((tw0 * i - aw0) / redrawrate * speed)
		      / 8 * dpm);
    }

    for (i = 0;i < ballno; i++){
      b[i].bh = 0;
      b[i].gx = HOR_CENTER;
      b[i].gy = VER_CENTER;
      b[i].gx0 = HOR_CENTER;
      b[i].gy0 = VER_CENTER;
      b[i].gx1 = HOR_CENTER;
      b[i].gy1 = VER_CENTER;
    }
    if (mirror){
      lhand.c=0;
      if (syn){
	rhand.c = 0;
      }
      else {
	rhand.c = -1;
      }
    }
    else {
      rhand.c = 0;
      if (syn){
	lhand.c = 0;
      }
      else {
	lhand.c = -1;
      }
    }
    rhand.bh = 2;
    rhand.st = Ball.OBJECT_HAND;
    rhand.thand = 1;
    rhand.chand = 1;
    rhand.gx  = HOR_CENTER;
    rhand.gy  = VER_CENTER;
    rhand.gx0 = HOR_CENTER;
    rhand.gy0 = VER_CENTER;
    rhand.gx1 = HOR_CENTER;
    rhand.gy1 = VER_CENTER;
    
    lhand.bh = 2;
    lhand.st = Ball.OBJECT_HAND;
    lhand.thand = 0;
    lhand.chand = 0;
    lhand.gx  = HOR_CENTER;
    lhand.gy  = VER_CENTER;
    lhand.gx0 = HOR_CENTER;
    lhand.gy0 = VER_CENTER;
    lhand.gx1 = HOR_CENTER;
    lhand.gy1 = VER_CENTER;
    
    for (i = 0; i < LMAX*2; i++){
      r[i] = 0;
    }
    return true;
  }
  final float square(float x){
    return x * x;
  }
  String getSingless(int i){
    char p[] = new char[256];
    int j,t;
    int index = 0;
    
    if (syn){
      p[index] = '(';
      index++;
    }
    for (t = 0; t <= intsyn; t++){
      if(t != 0){
	p[index] = ',';
	index++;
      }
      if (patts[i] == 0){
	p[index] = '0';
	index++;
      }
      else {
	if (patts[i] > 1){
	  p[index] = '[';
	  index++;
	}
	for (j = 0; j < patts[i]; j++){
	  if (Math.abs(patt[i][j]) < 10){
	    p[index] = (char)('0' + Math.abs(patt[i][j]));
	  }
	  else {
	    p[index] = (char)('a' + Math.abs(patt[i][j]) - 10);
	  }
	  index++;
	  if (patt[i][j] < 0){
	    p[index] = 'x';
	    index++;
	  }
	}
	if(patts[i] > 1){
	  p[index] = ']';
	  index++;
	}
      }
      i++;
    }
    if(syn){
      p[index] = ')';
      index++;
    }
    return new String(p ,0, index);
  }
  
  void patt_print(int mode){
    int i, c;
    
    if(mode == 1){
      if(c0 < pattw && pattw > 1 + intsyn){
	drawSiteswap(patt_x + tx, singless[c0], false);
	tx += singless[c0].length();
      }
      c = time_period;
      if(c <= c0){
	tx = 0;
      }
      drawSiteswap(patt_x + tx, singless[c], true);
      c0 = c;
      return;
    }
    if (mode == 0){
      tx = 0;
      for(i = 0;i < pattw; i += intsyn + 1){
	drawSiteswap(patt_x + tx, singless[i], false);
	tx += singless[i].length();
      }
      c0 = pattw;
    }
  }
  public void chooseTrickByName(String trickName){
    int i;
    if(trickName == null || trickName.length() == 0){
      return;
    }
    if(holder != null){
      i = holder.chooseTrickByName(trickName);
      if(i != -1){
	startJuggling(i, trickName);
      }
    }
  }
  
  void startJuggling(int index){
    startJuggling(index, null);
  }
  boolean startJuggling(int index, String s){
    int i;

    stopJuggling();
    clearImage();

    speed = controller.getSpeed();
    redrawrate = controller.getRedrawrate();
    mirror = controller.ifMirror();
    show_ss = controller.ifShowSiteSwap();
    hand_on = controller.ifShowBody();
    if (controller.isNewChoice() || index == SITESWAP_MODE){
      if(index == SITESWAP_MODE){
	holder.getPattern(s);
	height = controller.getHeight();
	dwell = controller.getDwell();
      }
      else{
	if (index == -1 || !holder.isPattern(index))
	  return false;
	holder.getPattern(index);
      }
      holder.getMotion(motion);
      if (syn){
	intsyn = 1;
      }
      else{
	intsyn = 0;
      }
      siteswap = new String();
      for(i = 0;i < pattw ; i += intsyn +1){
	singless[i] = getSingless(i);
	siteswap = siteswap + singless[i];
      }
      set_dpm();
      speed = controller.getSpeed();
      if (!pattInitialize()){
	if (index != SITESWAP_MODE)
	  holder.invalidate(index);
	putError("wrong siteswap" , pattern);
	return false;
      }
      controller.enableSwitches();
      controller.setHeight(height);
      controller.setDwell(dwell);
    }
    else{
      height = controller.getHeight();
      dwell = controller.getDwell();
      if (index == MOTION_MODE){
	motion = s;
	holder.getMotion(s);
      }
      set_dpm();
      speed = controller.getSpeed();
      pattInitialize();
    }
    
    if(show_ss){
      patt_print(0);
    }
    controller.setLabels();
    initBallGraphics();
    initGraphics();
    time_count = 0;
    time_period = 0;
    
    kicker = new Thread(this);
    kicker.start();
    status = JUGGLING;

    return true;
  }
  public void run(){
    while (kicker != null){
      do_juggle();
      count_up_timer();
      try {
	kicker.sleep((long)(1000 / redrawrate));
      }
      catch (InterruptedException e){
      }
    }
  }
  void do_juggle(){
    int i;
    
    if(status == PAUSE || status == IDLE){
      vsync_Count1 = 0;
      return;
    }
    time_count += vsync_Count1;
    vsync_Count1 = 0;
    
    if (time_count < aw){
      time_count = aw;
    }
    time_period = (int)((time_count - aw) / tw);
    time_period %= pattw;
    
    for(i = 0; i < ballno; i++){
      b[i].juggle();
    }
    if(rhand.juggle() + lhand.juggle() > 0){
      if(show_ss){
	patt_print(1);
      }
    }
    
    eraseBalls();

    ap.rx[0] = rhand.gx + 11 + arm_x;
    ap.ry[0] = rhand.gy + 11 + arm_y;
    ap.lx[0] = lhand.gx + 11 - arm_x;
    ap.ly[0] = lhand.gy + 11 + arm_y;
    arm_line();
    putBalls();
    if(imf != null){
      imf.repaint();
    }
    else{
      this.repaint();
    }
  }
  void count_up_timer(){
    vsync_Count1++;
  }
  void putError(String s1, String s2){
    controller.putMessage(s1, s2);
    System.err.println(s1);
    System.err.println(s2);
  }
  float fadd(float t, int x){
    return (float)(Math.floor(t * Math.pow(10, x) + .5f)/Math.pow(10, x));
  }

  // class ImageFrame extends Frame{

    public void update(Graphics g){
      paint(g);
    }
    public void paint(Graphics g){
      if (gg1 != null){
	gg1.drawImage(image_pixmap, 0, 0, null);
      }
      if (gg2 != null){
	gg2.drawImage(image_pixmap, -gx_min, -20, null);
      }
    }
    void initGraphics(){
      Graphics g;
      if(imf != null){
	g = imf.getGraphics();
      }
      else{
	g = this.getGraphics();
      }
      if (gg1 != null){
	gg1.dispose();
      }
      gg1 = g.create(0, Y_OFFSET, IMAGE_WIDTH, 20);
      if (gg2 != null){
	gg2.dispose();
      }
      gg2 = g.create(gx_min, 20 + Y_OFFSET,
		     gx_max - gx_min + 1, IMAGE_HEIGHT - 20);
      g.dispose();
  }
    void disposeGraphics(){
      if (image_gc != null){
	image_gc.dispose();
      }
      try{
	for(int i = 0;i < NCOLOR ;i++){
	  bm_gc[i].dispose();
	  r_bm_gc[i].dispose();
	  l_bm_gc[i].dispose();
	}
      }
      catch(Throwable e){}
      if (gg1 != null)
	gg1.dispose();
      if (gg2 != null){
	gg2.dispose();
      }
    }
    void drawSiteswap(int x, String str, boolean red){
      if (image_gc == null) return;
      if (red){
	image_gc.setColor(Color.red);
      }
      else {
	image_gc.setColor(Color.black);
      }
      image_gc.drawString(str, x * 8, 20);
    }
  /*
   *    void start(){
   *      if(image_gc == null){
   *	image_gc = image_pixmap.getGraphics();
   *      }
   *    }
   */
    void drawBall(Image bm, int x, int y){
      if(x < 0 || x > IMAGE_WIDTH - 32 || y < 0 || y > IMAGE_HEIGHT - 24){
	return;
      }
      Graphics g = image_gc.create(x + bm1,
				   y + bm1,
				   bm2 - bm1 + 1,
				   bm2 - bm1 + 1);

      g.drawImage(bm, -bm1, -bm1, null);
      g.dispose();
    }
    void drawLine(int x1, int y1, int x2, int y2){
      image_gc.drawLine(x1, y1, x2, y2);
    }
    void drawCircle(int x, int y, int r){
      image_gc.drawOval(x - r, y - r, 2 * r, 2 * r);
    }
    void fillBox(int x1_b, int y1, int x2_b, int y2){
      image_gc.fillRect(x1_b * 8, y1, (x2_b - x1_b + 1) * 8, y2 - y1 + 1);
    }
    void initBallGraphics(){
      int i;
      int data[] = {
	0,18, 0,23,17,23,20,22,
	22,20,23,17,23,12,18,12,
	18,16,16,18, 0,18,
	12,15,23,17
      };

      if (bm[0] == null){
	if( imf != null){
	  l_bm[1] = imf.createImage(PM_W, PM_H);
	  r_bm[1] = imf.createImage(PM_W, PM_H);
	}
	else{
	  l_bm[1] = this.createImage(PM_W, PM_H);
	  r_bm[1] = this.createImage(PM_W, PM_H);
	}
	l_bm_gc[1] = l_bm[1].getGraphics();
	r_bm_gc[1] = r_bm[1].getGraphics();
	for (i = 0; i < NCOLOR; i++){
	  if(imf != null){
	    bm[i] = imf.createImage(PM_W, PM_H);
	  }
	  else{
	    bm[i] = this.createImage(PM_W, PM_H);
	  }
	  bm_gc[i] = bm[i].getGraphics();
	  l_bm[i] = l_bm[1];
	  l_bm_gc[i] = l_bm_gc[1];
	  r_bm[i] = r_bm[1];
	  r_bm_gc[i] = r_bm_gc[1];
	}
      }
      for (i = 0; i < NCOLOR; i++){
	bm_gc[i].setColor(Color.white);
	bm_gc[i].fillRect(0, 0, PM_W, PM_H);
      }
      l_bm_gc[1].setColor(Color.white);
      l_bm_gc[1].fillRect(0, 0, PM_W, PM_H);
      r_bm_gc[1].setColor(Color.white);
      r_bm_gc[1].fillRect(0, 0, PM_W, PM_H);

      for (i = 0; i < data.length; i++){
	data[i] = (data[i] - 11) * dpm / DW;
      }
      hand_x = data[i - 4] + 2;
      hand_y = data[i - 3] + 2;
      arm_x = data[i - 2];
      arm_y = data[i - 1];
      for (i = 0; i+6 < data.length; i += 2){
	r_bm_gc[1].setColor(color[1]);
	r_bm_gc[1].drawLine(
			    11 + data[i], 10 + data[i+1],
			    11 + data[i + 2],10 + data[i + 3]);
	l_bm_gc[1].setColor(color[1]);
	l_bm_gc[1].drawLine(
			    12 - data[i], 10 + data[i + 1],
			    12 - data[i + 2],10 + data[i + 3]);
      }
      int r = 11 * dpm / DW;
      for (i = 0; i < NCOLOR; i++){
	bm_gc[i].setColor(color[i]);
	bm_gc[i].fillOval(
			  11 - r, 11 - r, 2 * r, 2 * r);
      }
      bm1 = 11 - 11 * dpm / DW;
      bm2 = 11 + 11 * dpm / DW + 1;
    }
    void clearImage(){
      image_gc.setColor(color[0]);
      image_gc.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
      Graphics g;
      if(imf != null){
	g = imf.getGraphics();
      }
      else{
	g = this.getGraphics();
      }
      g.drawImage(image_pixmap, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
      g.dispose();
    }
    void eraseBalls(){
      int i;
      image_gc.setColor(color[0]);
      
      if(hand_on){
	
	for(i = 0;i < 5;i++){
	  drawLine(ap.rx[i], ap.ry[i], ap.rx[i + 1], ap.ry[i + 1]);
	  drawLine(ap.lx[i], ap.ly[i], ap.lx[i + 1], ap.ly[i + 1]);
	}
	drawCircle(ap.hx, ap.hy, ap.hr);
	fillBox(
		rhand.gx0 / 8, rhand.gy0 + bm1,
		rhand.gx0 / 8 + 3, rhand.gy0 + bm2);
	fillBox(
		lhand.gx0 / 8, lhand.gy0 + bm1,
		lhand.gx0 / 8 + 3, lhand.gy0 + bm2);
      }
      for(i = ballno - 1;i >= 0;i--){
	fillBox(
		b[i].gx0 / 8, b[i].gy0 + bm1,
		b[i].gx0 / 8 + 3, b[i].gy0 + bm2);
      }
      return;
    }
    void putBalls(){
      int i;
      if(hand_on){

	image_gc.setColor(color[1]);
      
	drawBall(r_bm[1], rhand.gx, rhand.gy);
	drawBall(l_bm[1], lhand.gx, lhand.gy);
	for(i = 0; i < 5; i++){
	  drawLine(ap.rx[i], ap.ry[i], ap.rx[i + 1], ap.ry[i + 1]);
	  drawLine(ap.lx[i], ap.ly[i], ap.lx[i + 1], ap.ly[i + 1]);
	}
	drawCircle(ap.hx, ap.hy, ap.hr);
      }
      for(i = ballno - 1;i >= 0;i--){
	drawBall(bm[15 - i % 13], b[i].gx, b[i].gy);
      }
    }
  //  }


  public class ImageFrame extends Frame{
    Jmj jmj;
    public ImageFrame(Jmj j){
      super("Juggle Master Java");
      jmj = j;
    }
    
    final public void update(Graphics g){
      paint(g);
    }
    final public void paint(Graphics g){
      jmj.paint(g);
    }
  }
}
