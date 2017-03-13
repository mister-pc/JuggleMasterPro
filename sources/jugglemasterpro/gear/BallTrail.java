/*
 * @(#)JuggleBallTrail.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */

package jugglemasterpro.gear;




import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Strings;




/**
 * Description
 * 
 * @version 4.3.0
 * @author Arnaud BeLO.
 */
final public class BallTrail {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;




	public boolean				bolGarmsBallZOrder;

	public boolean				bolGbodyBallZOrder;
	public boolean				bolGvisible;
	public byte					bytGthrow;
	public float				fltGposZ;
	public int					intGballIndex;
	public int					intGcolor;
	public int					intGlastCatchTimer;
	public int					intGposX;
	public int					intGposY;




	/**
	 * Constructs
	 * 
	 * @param intPballIndex
	 * @param intPposX
	 * @param intPposY
	 * @param fltPposZ
	 * @param bolPzOrder
	 * @param bytPthrow
	 * @param bytPcolor
	 */
	public BallTrail(	int intPballIndex,
							int intPlastCatchTimer,
							int intPposX,
							int intPposY,
							float fltPposZ,
							boolean bolPbodyBallZOrder,
							boolean bolParmsBallZOrder,
							boolean bolPvisible,
							byte bytPthrow,
							int intPcolor) {
		this.intGballIndex = intPballIndex;
		this.intGlastCatchTimer = intPlastCatchTimer;
		this.intGposX = intPposX;
		this.intGposY = intPposY;
		this.fltGposZ = fltPposZ;
		this.bolGbodyBallZOrder = bolPbodyBallZOrder;
		this.bolGarmsBallZOrder = bolParmsBallZOrder;
		this.bolGvisible = bolPvisible;
		this.bytGthrow = bytPthrow;
		this.intGcolor = intPcolor;
	}




	/**
	 * Method description
	 * 
	 * @see
	 * @return
	 */
	@Override final public String toString() {
		return Strings.doConcat(	"Ball[",
										this.intGballIndex,
										"] = ",
										this.bytGthrow,
										' ',
										this.intGcolor,
										" {",
										this.intGposX,
										", ",
										this.intGposY,
										", ",
										this.fltGposZ,
										'(',
										this.bolGbodyBallZOrder,
										", ",
										this.bolGarmsBallZOrder,
										", ",
										this.bolGvisible,
										")}");
	}
}

/*
 * @(#)JuggleBallTrail.java 4.3.0
 * Copyleft (c) 2010 Arnaud BeLO.
 */
