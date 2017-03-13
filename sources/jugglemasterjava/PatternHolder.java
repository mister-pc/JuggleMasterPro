import java.lang.*;
import java.io.*;
import java.util.*;

public class PatternHolder{

  private final byte CROSS = (byte)'x' - 'a' + 10;
  private final byte COMMA = -1;  
  private final byte BRA = -2;
  private final byte KET = -3;
  private final byte PAR = -4;
  private final byte ENTHESIS = -5;

  private Hashtable motiontable = new Hashtable();
  private Vector patvec;
  Enumeration en = motiontable.keys();

  Jmj jmj;
  private int fline, tailindex;
  boolean readflag;
  float dwell, height;
  char cbuf[] = new char[256];
  byte bbuf[] = new byte[Jmj.LMAX];
  byte pattbarr[];
  String motion = Jmj.NORMAL;
  String s;
  int next;

  PatternHolder(Jmj j){
    this.jmj = j;
    motiontable.put(motion , new byte[]{ 13, 0, 4, 0 });
  }

  public boolean setHolder(BufferedReader fp){
    readflag = true;
    dwell = 0.5f;
    patvec = new Vector(210, 20);
    height = .2f;
    fline = 0;
    jmj.controller.isCanceled();
    try{
      while (true){
	if (readflag){
	  s = fp.readLine();
	  fline++;
	}
	readflag = true;
	tailindex = getTail(s);
	if (tailindex == 0){
	  continue;
	}
	switch(s.charAt(0)){
	case '/':
	  patvec.addElement(s.substring(1, ++tailindex));
	  if (jmj.controller.isCanceled()){
	    throw new IOException();
	  }
	case ';':
	  continue;
	case '#':
	  wasParam();
	  continue;
	case '%':
	  wasMotion(fp);
	  continue;
	default:
	  pattbarr = parsePattern(s);
	  if(pattbarr != null){
	    Piece p = new Piece( s.substring( next, ++tailindex), motion,
				pattbarr, height, dwell);
	    patvec.addElement(p);
	    if (p.name.equals(jmj.startpattern)){
	      jmj.startindex = patvec.size() - 1;
	    }
	  }
	  else{
	    jmj.putError("Bad Pattern Definition in line :"+fline, s);
	  }
	}
      }
    }
    catch(IOException e){
    }
    catch(NullPointerException e){
    }
    return !patvec.isEmpty();
  }

  void wasParam(){
    StringTokenizer st = new StringTokenizer(s, "= ;\t");
    float t;
    int i;
    try{
      st.nextToken();
      t = Float.valueOf(st.nextToken()).floatValue();
    }
    catch(NumberFormatException e){
      jmj.putError("Not Number in line:"+fline , s);
      return;
    }
    cbuf[0] = s.charAt(1);cbuf[1] = s.charAt(2);
    if(cbuf[0] =='D' && cbuf[1] =='R'&&
       t >= .1f && t <= .9f){
      dwell = fadd(t,2);
    }
    else if(cbuf[0] =='H' && cbuf[1] =='R' &&
	    t >= 0.01f && t <= 1.00f){
      height = fadd(t,2);
    }
    else if(cbuf[0] =='G' && cbuf[1] =='A' &&
	    t > 0f && t <= 98f){
      jmj.ga = t;
    }
    else if(cbuf[0] =='S' && cbuf[1] =='P' &&
	    t >= .1f && t <= 2f){
      jmj.speed = fadd(t,1);
    }
    else{
      i = (int) t;
      if(cbuf[0] =='M' && cbuf[1] =='R' &&
	    ((i == 0) || (i == 1))){
      jmj.mirror = (i == 1)? true:false;
    }
    else if(cbuf[0] =='B' && cbuf[1] =='P'){
    }
    else if(cbuf[0] =='H' && cbuf[1] =='D' &&
	    ((i == 0) || (i == 1))){
      jmj.hand_on = (i == 1)? true:false;
    }
    else if(cbuf[0] =='P' && cbuf[1] =='D' &&
	    ((i == 0) || (i == 1))){
      jmj.show_ss = (i == 1)? true:false;
    }
    else if(cbuf[0] =='B' && cbuf[1] =='C'){
    }
    else jmj.putError("Invalid Parameter Value in line:" + fline, s);
    return;
    }
  }
  void wasMotion(BufferedReader fp) throws IOException{
    String tmp = s.substring( 1, ++tailindex );
    s = fp.readLine();
    fline++;
    readflag = false;
    if (s.length() == 0  || s.charAt(0) != '{'){
      if(motiontable.containsKey(tmp)){
	motion = tmp;
      }
      else {
	jmj.putError("Undefined Pattern in line:" + fline , s);
      }
      return;
    }
    int bindex = 0;
    try{
      String s1;
      while(s.length() != 0 && s.charAt(0) == '{'){
	StringTokenizer st = new StringTokenizer(s, " {},\t");
	for(int i = 0; i < 4; i++, bindex++){
	  try{
	    s1 = st.nextToken();
	  }
	  catch(NoSuchElementException e){
	    throw new NumberFormatException();
	  }
	  bbuf[bindex] = Byte.parseByte(s1);
	  if ( (bbuf[bindex] < -10 || bbuf[bindex] > 20) &&
	      ((bindex & 1) == 1) && 
	      (bbuf[bindex] < -30 || bbuf[bindex] > 30)){
	    throw new NumberFormatException();
	  }
	}
	s = fp.readLine();
	fline++;
      }

      motion = tmp;
      byte b[] = new byte[bindex];
      for (int i = 0 ; i < bindex ; i++){
	b[i] = bbuf[i];
      }
      motiontable.put(motion, b);
    }
    catch(NumberFormatException e){
      jmj.putError("Bad motion definition in " + tmp +
		   " in line:" + fline, s);
      while(s.length() != 0 && s.charAt(0) == '{'){
	s = fp.readLine();
	fline++;
      }
      readflag = false;
    }
  }
  byte []parsePattern(String s){
    int bindex = 0;
    char c;
    for (next = 0 ; next <= tailindex ;next++ ){
      if ( (c =s.charAt(next)) == ' ' || c == '\t') break;
    }
    for (int i = 0; i < next ; i++ , bindex++){
      switch((c = s.charAt(i))){
      case '(':
	bbuf[bindex] = PAR;
	break;
      case ')':
	bbuf[bindex] = ENTHESIS;
	break;
      case ',':
	bbuf[bindex] = COMMA;
	break;
      case '[':
	bbuf[bindex] = BRA;
	break;
      case ']':
	bbuf[bindex] = KET;
	break;
      default:
	if (c >= '0' && c <= '9')
	  bbuf[bindex] = (byte)(c - '0');
	else if (c >= 'a' && c <= 'z')
	  bbuf[bindex] = (byte)(c - 'a' + 10);
	else if (c >= 'A' && c <= 'Z')
	  bbuf[bindex] = (byte)(c - 'A' + 10);
	else {
	  return null;
	}
      }
    }
    if (next > Jmj.LMAX ){
      jmj.putError("Too Long Pattern in line :"+fline, s);
      return null;
    }
    byte patt[] = new byte[bindex];
    for (int i = 0 ; i < bindex ; i++)
      patt[i] = bbuf[i];
    for(; next < tailindex ; next++)
      if ( s.charAt(next) != ' ' && s.charAt(next) != '\t')
	break;
    if (next >= tailindex)
      next = 0;
    return patt;
  }

  boolean isPattern(int index){
    if (patvec.elementAt(index) instanceof Piece){
      return true;
    }
    return false;
  }
  boolean getPattern(int index){
    return getPattern(index, null);
  }
  boolean getPattern(String s){
    tailindex = getTail(s);
    return getPattern(-1, s);
  }
  boolean getPattern(int index,String s){
    int flag = 0, flag2 = 0, a = 0;
    int j = 0;
    int pattw = 0;

    if (s != null){
      pattbarr = parsePattern(s);
      if (pattbarr == null){
	return false;
      }
      jmj.pattern = s;
    }
    else{
      if ( !isPattern(index)){
	return false;
      }
      Piece p = (Piece) patvec.elementAt(index);
      jmj.pattern = p.name;
      jmj.motion = p.motion;
      jmj.height = p.height;
      jmj.dwell = p.dwell;
      pattbarr = p.siteswap;
    }

    if (pattbarr[0] == PAR){
      jmj.syn = true;
    }
    else {
      jmj.syn = false;
    }
    while(j < pattbarr.length){
      if (pattbarr[j] == BRA){
	flag2 = 1;
	jmj.patts[pattw] = 0;
	j++;
	continue;
      }
      if (pattbarr[j] == KET){
	if (flag2 == 0){
	  return false;
	}
	flag2 = 0;
	pattw++;
	j++;
	continue;
      }
      if (jmj.syn){
	switch(pattbarr[j]){
	case PAR:
	  if (flag != 0){
	    return false;
	  }
	  flag = 1;
	  j++;
	  continue;
	case ENTHESIS:
	  if (flag != 5){
	    return false;
	  }
	  flag = 0;
	  j++;
	  continue;
	case COMMA:
	  if (flag != 2){
	    return false;
	  }
	  flag = 4;
	  j++;
	  continue;
	case CROSS:
	  if (flag != 2 && flag != 5){
	    return false;
	  }
	  if (flag2 != 0){
	    jmj.patt[pattw][jmj.patts[pattw] - 1] = -a;
	  }
	  else {
	    jmj.patt[pattw - 1][0] = -a;
	  }
	  j++;
	  continue;
	}
      }
      a = pattbarr[j];
      if (jmj.syn){
	if (a % 2 != 0){
	  return false;
	}
	if (flag2 == 0 && flag != 1 && flag != 4){
	  return false;
	}
	if (flag == 1){
	  flag = 2;
	}
	if (flag == 4){
	  flag = 5;
	}
      }
      if (flag2 != 0){
	if (a == 0){
	  return false;
	}
	jmj.patt[pattw][jmj.patts[pattw]++] = a;
	if (jmj.patts[pattw] >Jmj.MMAX){
	  return false;
	}
      }
      else{
	jmj.patt[pattw][0] = a;
	if (a == 0){
	  jmj.patts[pattw++] = 0;
	}
	else {
	  jmj.patts[pattw++] = 1;
	}
      }
      j++;
    }
    if (flag != 0 || flag2 != 0 || pattw == 0){
      return false;
    }
    jmj.pattw = pattw;
    return true;
  }
  String nameAt(int index){
    try{
      Object o = patvec.elementAt(index);
      if ( o instanceof String)
	return (String) o;
      if (o instanceof Piece){
	Piece p = (Piece) o;
	return p.name;
      }
      return new String();
    }
    catch(ArrayIndexOutOfBoundsException e){
      return new String();
    }
  }
  int chooseTrickByName(String trickName){
    int n = patvec.size();
    for(int i = 0; i < n; i++){
      if(patvec.elementAt(i) instanceof Piece){
	if( ((Piece)patvec.elementAt(i)).name.equals(trickName)){
	  if(isPattern(i)){
	    jmj.controller.list.select(i);
	    return i;
	  }
	}
      }
    }
    return -1;
  }

  void invalidate(int index){
    patvec.setElementAt( nameAt(index) , index);
  }
  int getTail(String str){
    if (str.length() == 0){
      return 0;
    }
    for(int i = str.length() - 1; i > -1 ; i--){
      if (str.charAt(i) != ' ' && str.charAt(i) != '\t'){
	return i;
      }
    }
    return 0;
  }
  float fadd(float t, int x){
    return (float)(Math.floor(t * Math.pow(10, x) + .5f)/Math.pow(10, x));
  }
  String nextMotion(){
    if(en.hasMoreElements())
      return (String) en.nextElement();
    en = motiontable.keys();
    return new String();
  }
  void getMotion(String motion){
    jmj.motionarray = (byte [])motiontable.get(motion);
  }
  int countMotions(){
    return motiontable.size();
  }
  static public class Piece{

    String name;
    String motion;
    byte siteswap[];
    float height;
    float dwell;

    Piece(){
    }
    Piece(String nm, String mt, byte []ss, float hght, float ht){
      name = nm;
      motion = mt;
      siteswap = ss;
      height = hght;
      dwell = ht;
    }
  }
}
