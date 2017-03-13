package jugglemasterpro.control.siteswap;

import java.util.Arrays;

import jugglemasterpro.control.ControlJFrame;
import jugglemasterpro.control.util.ThreeStatesButtonModel;
import jugglemasterpro.pattern.Pattern;
import jugglemasterpro.pattern.Siteswap;
import jugglemasterpro.util.Constants;
import jugglemasterpro.util.Tools;

final public class SiteswapActions {

	@SuppressWarnings("unused")
	final private static long	serialVersionUID	= Constants.lngS_ENGINE_VERSION_NUMBER;

	/**
	 * Method description
	 * 
	 * @see
	 * @param strPsiteswap
	 */
	final public static void doApplySiteswap(final ControlJFrame objPcontrolJFrame, final String strPsiteswap) {

		synchronized (Constants.objS_ENGINE_START_PATTERN_LOCK_OBJECT) {
			// JugglePanel.log("SiteswapJTextPane changed...");

			final Siteswap objLnewJuggleSiteswap = new Siteswap(strPsiteswap);
			final boolean bolLreverse = objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP);
			objPcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP : Constants.bytS_STRING_LOCAL_SITESWAP,
												strPsiteswap);
			objPcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_SITESWAP : Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP,
												objLnewJuggleSiteswap.getReverseSiteswapString());
			objPcontrolJFrame.getPatternsManager().getPattern(objPcontrolJFrame.getJuggleMasterPro().intGobjectIndex).intGballsNumberA[Constants.bytS_UNCLASS_CURRENT] =
																																												objLnewJuggleSiteswap.intGballsNumber;
			objPcontrolJFrame.getPatternsManager().getPattern(objPcontrolJFrame.getJuggleMasterPro().intGobjectIndex).bolGplayableA[Constants.bytS_UNCLASS_CURRENT] =
																																											objLnewJuggleSiteswap.bytGstatus == Constants.bytS_STATE_SITESWAP_PLAYABLE;

			objPcontrolJFrame.saveControlString(bolLreverse ? Constants.bytS_STRING_LOCAL_COLORS : Constants.bytS_STRING_LOCAL_REVERSE_COLORS,
												objLnewJuggleSiteswap.getReverseColorsString(objPcontrolJFrame.getControlString(bolLreverse
																																			? Constants.bytS_STRING_LOCAL_REVERSE_COLORS
																																			: Constants.bytS_STRING_LOCAL_COLORS)));

			switch (objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.compare(objLnewJuggleSiteswap)) {
				case Constants.bytS_STATE_SITESWAPS_IDENTICAL:
					objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.strGsiteswap = strPsiteswap;
					objPcontrolJFrame.setSiteswapControls();
					break;
				case Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL_SPACES:
					objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.strGsiteswap = strPsiteswap;
					objPcontrolJFrame.getJuggleMasterPro().getSiteswap().setSpaceAfterA(objLnewJuggleSiteswap);
					objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.setSymmetric();
					objPcontrolJFrame.doAddAction(Constants.intS_ACTION_DRAW_SITESWAP | Constants.intS_ACTION_REFRESH_DRAWING);
					objPcontrolJFrame.setSiteswapControls();
					break;
				case Constants.bytS_STATE_SITESWAPS_NON_IDENTICAL:
					objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.strGsiteswap = strPsiteswap;
					objPcontrolJFrame.doRestartJuggling();
			}
			objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
			objPcontrolJFrame.objGobjectsJList.validate();
		}
	}

	/**
	 * Method description
	 * 
	 * @see
	 * @param bolPenabled
	 */
	final public static void doDisplaySiteswap(final ControlJFrame objPcontrolJFrame, final Boolean bolPstateBoolean) {
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_SITESWAP,
												bolPstateBoolean != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN);
		objPcontrolJFrame.saveControlSelected(	Constants.bytS_BOOLEAN_LOCAL_SITESWAP_THROWS,
												bolPstateBoolean == ThreeStatesButtonModel.bolS_STRONGLY_SELECTED_BOOLEAN);

		if (bolPstateBoolean != ThreeStatesButtonModel.bolS_UNSELECTED_BOOLEAN) {
			objPcontrolJFrame.getJuggleMasterPro().bolGdisplayedThrowA[objPcontrolJFrame.getJuggleMasterPro().intGcurrentThrowIndex] = true;
			if (objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.bolGsynchro) {
				objPcontrolJFrame.getJuggleMasterPro().bolGdisplayedThrowA[objPcontrolJFrame.getJuggleMasterPro().intGcurrentThrowIndex
																			+ Tools.getSignedBoolean(objPcontrolJFrame.getJuggleMasterPro().intGcurrentThrowIndex % 2 == 0)] =
																																												true;
			}
		} else {
			Arrays.fill(objPcontrolJFrame.getJuggleMasterPro().bolGdisplayedThrowA, false);
		}

		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES | Constants.intS_ACTION_DRAW_SITESWAP | Constants.intS_ACTION_REFRESH_DRAWING);
		objPcontrolJFrame.setSiteswapControls();
	}

	/**
	 * Method description
	 * 
	 * @see
	 */
	final public static void doRefreshSiteswap(final ControlJFrame objPcontrolJFrame) {
		final Pattern objLpattern = objPcontrolJFrame.getPatternsManager().getPattern(objPcontrolJFrame.getJuggleMasterPro().intGobjectIndex);
		if (objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.bolGsymmetric) {
			// objPcontrolJFrame.objGjuggleMasterPro.objGsiteswap.bolGstarred = !objPcontrolJFrame.objGjuggleMasterPro.objGsiteswap.bolGstarred;
			objLpattern.bolGstarredSiteswapA[Constants.bytS_UNCLASS_CURRENT] = !objLpattern.bolGstarredSiteswapA[Constants.bytS_UNCLASS_CURRENT];
		}
		final String strLrefreshedSiteswap =
												objPcontrolJFrame.getJuggleMasterPro().objGsiteswap.toString(	true,
																												false,
																												objLpattern.bolGstarredSiteswapA[Constants.bytS_UNCLASS_CURRENT]);
		objPcontrolJFrame.saveControlString(objPcontrolJFrame.isControlSelected(Constants.bytS_BOOLEAN_LOCAL_REVERSE_SITESWAP)
																																? Constants.bytS_STRING_LOCAL_REVERSE_SITESWAP
																																: Constants.bytS_STRING_LOCAL_SITESWAP,
											strLrefreshedSiteswap);
		objPcontrolJFrame.objGsiteswapJTextPane.selectText(strLrefreshedSiteswap);
		objPcontrolJFrame.setSiteswapControls();
		objPcontrolJFrame.doAddAction(Constants.intS_ACTION_INIT_TITLES);
	}

}
