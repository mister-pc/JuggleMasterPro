public class Ball {
  static Jmj jmj;
  int bh;
  int gx,gy;
  int gx0,gy0;
  int gx1,gy1;
  int c;
  int c0;
  int chand;
  int thand;
  int st;

  int hand_x, hand_y;

  final static int OBJECT_HAND = 0x01;
  final static int OBJECT_UNDER = 0x02;
  final static int OBJECT_MOVE = 0x04;
  final static int OBJECT_MOVE2 = 0x08;

  int juggle(){
    int tp, flag = 0, i, tpox = 0, rpox = 0, tpoz = 0, rpoz = 0;
    long x, y;
    float fx;
    
    gx0 = gx;
    gy0 = gy;
    
    if(c < 0){
      if(jmj.time_count >= -c * jmj.tw){
	c = -c;
      }
    }
    while(true){
      tp = (int)(jmj.time_count - jmj.tw * Math.abs(c));
      if(tp <jmj.aw){
	break;
      }
      st &= ~OBJECT_UNDER;
      c0 = c;
      
      if((st & OBJECT_HAND) != 0){
	c += 2;
	flag = 1;
      }
      else{
	int t;
	
	t = c;
	
	if(jmj.syn){
	  if(jmj.mirror && chand == 0){
	    t++;
	  }
	  else if (!jmj.mirror && chand != 0){
	    t++;
	  }
	}
	
	t %= jmj.pattw;
	bh = jmj.patt[t][jmj.r[t]];
	c+= Math.abs(bh);
	if(++jmj.r[t] >= jmj.patts[t]){
	  jmj.r[t] = 0;
	}
	
	thand = chand;
	if(((bh & 1) != 0)|| bh < 0){
	  chand = 1 - chand;
	}
	flag = 1;
      }
    }
    
    if( c >= 0 && tp >= 0 && (st & OBJECT_UNDER) == 0){
      st |= OBJECT_UNDER;
      
      if((st & OBJECT_HAND) != 0){
	if((st & OBJECT_MOVE2) != 0){
	  st |= OBJECT_MOVE;
	  st &= ~OBJECT_MOVE2;
	}
	else{
	  st &= ~OBJECT_MOVE;
	}
      }
      else{
	int h;
	int t;
	
	t = c;
	
	if(jmj.syn){
	  if(jmj.mirror && chand == 0){
	    t++;
	  }
	  else if(!jmj.mirror && chand != 0) {
	    t++;
	  }
	}
	
	t %= jmj.pattw;
	
	if (bh == 1){
	  st |= OBJECT_MOVE;
	}
	else {
	  st &= ~OBJECT_MOVE;
	}
	for(i = 0; i < jmj.patts[t]; i++){
	  h = jmj.patt[t][i];
	  if(h == 1){
	    if (chand != 0){
	      jmj.lhand.st |= OBJECT_MOVE2;
	    }
	    else {
	      jmj.rhand.st |= OBJECT_MOVE2;
	    }
	  }
	  if(h != 2){
	    if (chand != 0){
	      jmj.rhand.st |= OBJECT_MOVE2;
	    }
	    else {
	      jmj.lhand.st |= OBJECT_MOVE2;
	    }
	    st |= OBJECT_MOVE;
	  }
	}
      }
    }
    if((st & OBJECT_MOVE) == 0){
      if(c < 0){
	hand_pos(-c, chand);
	tpox = hand_x;
	tpoz = hand_y;
	rpox = tpox;
	rpoz = tpoz;
      }
      else{
	if((st & OBJECT_UNDER) != 0){
	  hand_pos(c, chand);
	  tpox = hand_x;
	  tpoz = hand_y;
	  hand_pos(c + 2, chand);
	  rpox = hand_x;
	  rpoz = hand_y;
	  if(tpox != rpox || tpoz != rpoz){
	    hand_pos(c + 1, chand);
	    rpox = hand_x;
	    rpoz = hand_y;
	    if(tpox != rpox || tpoz != rpoz){
	      st |= OBJECT_MOVE;
	    }
	  }
	}
	else{
	  hand_pos(c - 2, chand);
	  tpox = hand_x;
	  tpoz = hand_y;
	  hand_pos(c  ,chand);
	  rpox = hand_x;
	  rpoz = hand_y;
	  if(tpox != rpox || tpoz != rpoz){
	    hand_pos(c - 1, chand);
	    tpox = hand_x;
	    tpoz = hand_y;
	    if(tpox != rpox || tpoz != rpoz){
	      st |= OBJECT_MOVE;
	    }
	  }
	}
      }
    }
    if((st & OBJECT_MOVE) != 0){
      if(bh == 1){
	hand_pos(c0 + 1, thand);
	tpox = hand_x;
	tpoz = hand_y;
	hand_pos(c + 1, chand);
	rpox = hand_x;
	rpoz = hand_y;
      }
      else if((st & OBJECT_UNDER) != 0){
	hand_pos(c ,chand);
	tpox = hand_x;
	tpoz = hand_y;
	hand_pos(c + 1 ,chand);
	rpox = hand_x;
	rpoz = hand_y;
      }
      else{
	hand_pos(c0 + 1, thand);
	tpox = hand_x;
	tpoz = hand_y;
	hand_pos(c, chand);
	rpox = hand_x;
	rpoz = hand_y;
      }
    }
    
    if( (st & OBJECT_HAND) == 0 && c < 0 ){
      if(tpox == 0){
	fx = 0;
	y = (long)((float)tpoz * jmj.dpm / 20
		   - (long)tp * jmj.dpm / 12 / jmj.tw);
      }
      else{
	if(tpox > 0){
	  fx = (float)tpox / 10 - (float)tp / 6 / jmj.tw;
	}
	  else {
	    fx = (float)tpox / 10 + (float)tp / 6 / jmj.tw;
	  }
	y = (int)((float)tpoz * jmj.dpm / 20);
      }
    }
    else if((st & OBJECT_MOVE) == 0){
      fx = (float)tpox / 10;
      y = (int)((float)tpoz * jmj.dpm / 20);
    }
    else{
      if(bh==1){
	fx = (float)(tp - jmj.aw) / jmj.tw * 2 + 1;
	y = (long)((float)jmj.high[1] * (1 - square(fx)));
      }
      else if((st & OBJECT_UNDER) != 0){
	fx = (float)tp / jmj.aw * 2 - 1;
	y = (long)((float)jmj.high[0] * (1 - square(fx)));
      }
      else{
	fx = (float)tp / (jmj.tw * Math.abs(bh) - jmj.aw) * 2 + 1;
	y = (long)((float)jmj.high[Math.abs(bh)] * (1 - square(fx)));
      }
	
      y += (float)(fx * (rpoz - tpoz) + rpoz + tpoz)
	* jmj.dpm / 40;
      fx = (fx * (rpox - tpox) + rpox + tpox) / 20;
    }
      
    x = (long)(fx * jmj.dpm * jmj.KW);
    gx = (int)(jmj.HOR_CENTER + x - 11);
    
    if((st & OBJECT_HAND) != 0){
      if(chand != 0){
	gx += jmj.hand_x;
      }
      else {
	gx -= jmj.hand_x;
      }
      y -= jmj.hand_y;
    }
    
    gy = (int)(jmj.base - y - 11);
    
    return flag;
  }
  void hand_pos(int c ,int h){
    int a;
    
    if(jmj.mirror){
      if(!jmj.syn &&  h != 0){
	c--;
      }
      if((c & 1) != 0){
	a = ((--c + h) % (jmj.motionarray.length / 4)) * 4 + 2;
      }
      else {
	a = ((c + h) % (jmj.motionarray.length / 4)) * 4;
      }
    }
    else{
      if(!jmj.syn && h == 0){
	c--;
      }
      if ((c & 1) != 0){
	a = ((c - h) % (jmj.motionarray.length / 4)) * 4 + 2;
      }
      else {
	a = ((c + 1 - h) % (jmj.motionarray.length / 4)) * 4;
      }
    }
    if(h != 0){
      hand_x = jmj.motionarray[a];
    }
    else {
      hand_x = -jmj.motionarray[a];
    }
    hand_y = jmj.motionarray[a + 1];
  }
  void printOn(){
    System.out.println("bh: " + bh);
    System.out.println("gx, gy: " + gx + ", " + gy);
    System.out.println("gx0, gy0: " + gx0 + ", " + gy0);
    System.out.println("gx1, gy1: " + gx1 + ", " + gy1);
    System.out.println("c: " + c);
    System.out.println("c0: " + c0);
    System.out.println("chand: " + chand);
    System.out.println("thand: " + thand);
    System.out.println("st: " + st);
  }
  float square(float x){
    return x * x;
  }
}
