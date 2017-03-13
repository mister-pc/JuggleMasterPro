public class Arm {
    int rx[] = new int[6], ry[] = new int[6];
    int lx[] = new int[6], ly[] = new int[6];
    int hx, hy, hr;
    void printOn(){
	int i;

	System.out.print("rx[]: ");
	for (i = 0; i < 5; i++)
	    System.out.print(rx[i] + ", ");
	System.out.println(rx[i]);
 	System.out.print("ry[]: ");
	for (i = 0; i < 5; i++)
	    System.out.print(ry[i] + ", ");
	System.out.println(ry[i]);
	System.out.print("lx[]: ");
	for (i = 0; i < 5; i++)
	    System.out.print(lx[i] + ", ");
	System.out.println(lx[i]);
 	System.out.print("ly[]: ");
	for (i = 0; i < 5; i++)
	    System.out.print(ly[i] + ", ");
	System.out.println(ly[i]);
	System.out.println("hx: " + hx);
	System.out.println("hy: " + hy);
	System.out.println("hr: " + hr);
    }
}
