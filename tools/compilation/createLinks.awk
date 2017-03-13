########################################################################################################
#
# createLinks.awk
#
########################################################################################################

function trim(strP) {

    # Left-trim :
    while (length(strP) > 0 && substr(strP, 1, 1) == " ") {
        strP = substr(strP, 2);
    }

    # Right-trim :
    while (length(strP) > 0 && substr(strP, length(strP), 1) == " ") {
        strP = substr(strP, 1, length(strP) - 1);
    }

    return strP;
}

########################################################################################################

function spaces(intPspacesNumber, strLspaces, intLspaceIndex) {

        strLspaces = "";
        for (intLspaceIndex = 0; intLspaceIndex < intPspacesNumber; intLspaceIndex++) {
                strLspaces = sprintf("%s ", strLspaces);
        }

        return strLspaces;
}

########################################################################################################

function dequote(strP, intLstringLength, intLstringIndex, strLindexedChar, strLdequoted) {

    intLstringLength = length(strP);
    strLdequoted = "";
    for (intLstringIndex = 1; intLstringIndex <= intLstringLength; intLstringIndex++) {
        strLindexedChar = substr(strP, intLstringIndex, 1);
        if (strLindexedChar == "'") {
            strLindexedChar = "";
        }
        strLdequoted = sprintf("%s%s", strLdequoted, strLindexedChar);
    }

    return strLdequoted;
}

########################################################################################################

function boldSite(strPuRL, intLstartIndex, intLendIndex) {
    
    intLstartIndex = 1;
    intLendIndex = -1;
    if (substr(strPuRL, 1, 11) == "http://www.") {
        intLstartIndex = 12;
    } else if (substr(strPuRL, 1, 7) == "http://") {
        intLstartIndex = 8;
    } else {
        intLstartIndex = 1;
    }
    
    intLendIndex = intLstartIndex + index(substr(strPuRL, intLstartIndex + 1), "/");
    if (intLendIndex == intLstartIndex) {
		intLendIndex = length(strPuRL) + 1;
    }

    return sprintf("%s<b>%s</b>%s", substr(strPuRL, 1, intLstartIndex - 1), substr(strPuRL, intLstartIndex, intLendIndex - intLstartIndex), substr(strPuRL, intLendIndex));
}

########################################################################################################

function html(strP, strL, intLstringLength, intLstringIndex, strLhtmlChar, strLhtml) {

    strL = strP;
    sub(/&&/, "£µ§#@¤", strL);
    sub(/&/, "", strL);
    sub(/£µ§#@¤/, "&", strL);
    intLstringLength = length(strL);
    strLhtml = "";
    for (intLstringIndex = 1; intLstringIndex <= intLstringLength; intLstringIndex++) {
        strLhtmlChar = substr(strL, intLstringIndex, 1);
        if (strLhtmlChar == "à") {
            strLhtmlChar = "&agrave;";
        } else

        if (strLhtmlChar == "À") {
            strLhtmlChar = "&Agrave;";
        } else

        if (strLhtmlChar == "á") {
            strLhtmlChar = "&aacute;";
        } else

        if (strLhtmlChar == "Á") {
            strLhtmlChar = "&Aacute;";
        } else

        if (strLhtmlChar == "â") {
            strLhtmlChar = "&acirc;";
        } else

        if (strLhtmlChar == "Â") {
            strLhtmlChar = "&Acirc;";
        } else

        if (strLhtmlChar == "ã") {
            strLhtmlChar = "&atilde;";
        } else

        if (strLhtmlChar == "Ã") {
            strLhtmlChar = "&Atilde;";
        } else

        if (strLhtmlChar == "ä") {
            strLhtmlChar = "&auml;";
        } else

        if (strLhtmlChar == "Ä") {
            strLhtmlChar = "&Auml;";
        } else

        if (strLhtmlChar == "æ") {
            strLhtmlChar = "&aelig;";
        } else

        if (strLhtmlChar == "Æ") {
            strLhtmlChar = "&Aelig;";
        } else

        if (strLhtmlChar == "ç") {
            strLhtmlChar = "&ccedil;";
        } else

        if (strLhtmlChar == "Ç") {
            strLhtmlChar = "&Ccedil;";
        } else

        if (strLhtmlChar == "é") {
            strLhtmlChar = "&eacute;";
        } else

        if (strLhtmlChar == "É") {
            strLhtmlChar = "&Eacute;";
        } else

        if (strLhtmlChar == "è") {
            strLhtmlChar = "&egrave;";
        } else

        if (strLhtmlChar == "È") {
            strLhtmlChar = "&Egrave;";
        } else

        if (strLhtmlChar == "ê") {
            strLhtmlChar = "&ecirc;";
        } else

        if (strLhtmlChar == "Ê") {
            strLhtmlChar = "&Ecirc;";
        } else

        if (strLhtmlChar == "ë") {
            strLhtmlChar = "&euml;";
        } else

        if (strLhtmlChar == "Ë") {
            strLhtmlChar = "&Euml;";
        } else

        if (strLhtmlChar == "ì") {
            strLhtmlChar = "&igrave;";
        } else

        if (strLhtmlChar == "Ì") {
            strLhtmlChar = "&Igrave;";
        } else

        if (strLhtmlChar == "í") {
            strLhtmlChar = "&iacute;";
        } else

        if (strLhtmlChar == "Í") {
            strLhtmlChar = "&Iacute;";
        } else

        if (strLhtmlChar == "î") {
            strLhtmlChar = "&icirc;";
        } else

        if (strLhtmlChar == "Î") {
            strLhtmlChar = "&Icirc;";
        } else

        if (strLhtmlChar == "µ") {
            strLhtmlChar = "&micro;";
        } else

        if (strLhtmlChar == "ñ") {
            strLhtmlChar = "&ntilde;";
        } else

        if (strLhtmlChar == "Ñ") {
            strLhtmlChar = "&Ntilde;";
        } else

        if (strLhtmlChar == "ò") {
            strLhtmlChar = "&ograve;";
        } else

        if (strLhtmlChar == "Ò") {
            strLhtmlChar = "&Ograve;";
        } else

        if (strLhtmlChar == "ó") {
            strLhtmlChar = "&oacute;";
        } else

        if (strLhtmlChar == "Ó") {
            strLhtmlChar = "&Oacute;";
        } else

        if (strLhtmlChar == "ô") {
            strLhtmlChar = "&ocirc;";
        } else

        if (strLhtmlChar == "Ô") {
            strLhtmlChar = "&Ocirc;";
        } else

        if (strLhtmlChar == "ö") {
            strLhtmlChar = "&ouml;";
        } else

        if (strLhtmlChar == "Ö") {
            strLhtmlChar = "&Ouml;";
        } else

        if (strLhtmlChar == "õ") {
            strLhtmlChar = "&otilde;";
        } else

        if (strLhtmlChar == "Õ") {
            strLhtmlChar = "&Otilde;";
        } else

        if (strLhtmlChar == "œ") {
            strLhtmlChar = "&oelig;";
        } else

        if (strLhtmlChar == "ß") {
            strLhtmlChar = "&szlig;";
        } else

        if (strLhtmlChar == "ú") {
            strLhtmlChar = "&uacute;";
        } else

        if (strLhtmlChar == "Ú") {
            strLhtmlChar = "&Uacute;";
        } else

        if (strLhtmlChar == "ù") {
            strLhtmlChar = "&ugrave;";
        } else

        if (strLhtmlChar == "Ù") {
            strLhtmlChar = "&Ugrave;";
        } else

        if (strLhtmlChar == "û") {
            strLhtmlChar = "&ucirc;";
        } else

        if (strLhtmlChar == "Û") {
            strLhtmlChar = "&Ucirc;";
        } else

        if (strLhtmlChar == "ü") {
            strLhtmlChar = "&uuml;";
        } else

        if (strLhtmlChar == "Ü") {
            strLhtmlChar = "&Uuml;";
        } else

        if (strLhtmlChar == "ý") {
            strLhtmlChar = "&yacute;";
        } else

        if (strLhtmlChar == "Ý") {
            strLhtmlChar = "&Yacute;";
        } else

        if (strLhtmlChar == "ÿ") {
            strLhtmlChar = "&yuml;";
        } else

        if (strLhtmlChar == "Ÿ") {
            strLhtmlChar = "&Yuml;";
        } else

        if (strLhtmlChar == "\"") {
            strLhtmlChar = "&quot;";
        } else

        if (strLhtmlChar == "&") {
            strLhtmlChar = "&amp;";
        } else

        if (strLhtmlChar == "¿") {
            strLhtmlChar = "&iquest;";
        } else

        if (strLhtmlChar == "<") {
            strLhtmlChar = "&lt;";
        } else

        if (strLhtmlChar == ">") {
            strLhtmlChar = "&gt;";
        } else

        if (strLhtmlChar == " ") {
            strLhtmlChar = "&nbsp;";
        }

        strLhtml = sprintf("%s%s", strLhtml, strLhtmlChar);
    }
    return strLhtml;
}

########################################################################################################

BEGIN {

    # Quote constants :
    bytG_NO_QUOTES     = 0;
    bytG_SIMPLE_QUOTES = 1;
    bytG_DOUBLE_QUOTES = 2;

    # Status constants :
    bytG_BEGINNING_SPACES  = 0;
    bytG_LINK              = 1;
    bytG_SPACES_AFTER_LINK = 2;
    bytG_ICON              = 3;
    bytG_SPACES_AFTER_ICON = 4;
    bytG_TEXT              = 5;

    # Other constants :
    intGlinesNumber        = 0;
    strG_INDENT            = "      ";

    # Print HTML header :
    printf("<?PHP\n");
    printf("    require_once('../inc/juggleMasterPro.inc');\n");
    printf("    printHTMLHeader('css/links.css', true, false, true);\n");
    printf("    echo '  <BODY class=\"NoWrap\" onLoad=\"javascript:initDefaultHTMLProperties(false); javascript:setReverseColors(); javascript:setTitle(\\'' . $strGlinksTitleA[$strGjuggleLanguage] . '\\'); \" onKeyDown=\"javascript:catchKeyPressed(event, false, false); \">\n';\n");
    printf("    echo '    <DIV id=\"tooltip\"></DIV>\n';");
    printf("    printBackground('links.jpg');\n");
    printf("?>\n");
    printf("      <DIV class=\"BigTitles centeredText\">\n");
    printf("        <?PHP printJMPTitle(1, true); ?>\n");
    printf("      </DIV>\n");
    printf("      <DIV style=\"margin-top: 10px; \" class=\"BigTitles centeredText\">\n");
    printf("        <IMG src=\"%sbigLinks.png\" style=\"vertical-align : -45%;\" alt=\"\">\n", strPimagesDirectory);
    printf("        <?PHP printLink($strGlinksTitleA[$strGjuggleLanguage]); ?>\n");
    printf("        <?PHP printFlags('/scripts/links.php'); ?>\n");
    printf("      </DIV>\n");
    printf("      <DIV style=\"margin-top: 50px; font-weight : bolder;\" >\n");
}

########################################################################################################

{
    # Init dynamic constants :
    strGiconA[intGlinesNumber] = "";
    strGlinkA[intGlinesNumber] = "";
    strGtextA[intGlinesNumber] = "";
    bytGstatus             = bytG_BEGINNING_SPACES;
    bytGquotes             = bytG_NO_QUOTES;
    intGposition           = 1;

    # Parse the entire line :
    while (intGposition <= length($0)) {
        chrG = substr($0, intGposition, 1);

        # ';' :
        if (chrG == ";") {
            break;
        } else

        # ' ', '\t' :
        if (chrG == " " || chrG == "\t") {
            if (bytGstatus == bytG_LINK) {
                if (bytGquotes == bytG_NO_QUOTES) {
                    bytGstatus = bytG_SPACES_AFTER_LINK;
                } else {
                    strGlinkA[intGlinesNumber] = sprintf("%s ", strGlinkA[intGlinesNumber]);
                }
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_ICON) {
                if (bytGquotes == bytG_NO_QUOTES) {
                    bytGstatus = bytG_SPACES_AFTER_ICON;
                } else {
                    strGiconA[intGlinesNumber] = sprintf("%s ", strGiconA[intGlinesNumber]);
                }
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_TEXT) {
                strGtextA[intGlinesNumber] = sprintf("%s ", strGtextA[intGlinesNumber]);
            } else {

                intGposition++;
                continue;
            }
        } else

        # "'", '"' :
        if (chrG == "\"" || chrG == "'") {
            if (bytGstatus == bytG_BEGINNING_SPACES) {
                bytGquotes = (chrG == "'" ? bytG_SIMPLE_QUOTES : bytG_DOUBLE_QUOTES);
                bytGstatus = bytG_LINK;
            } else

            if (bytGstatus == bytG_LINK) {
                if (bytGquotes == bytG_NO_QUOTES ||
                    bytGquotes == bytG_SIMPLE_QUOTES && chrG == "\"" ||
                    bytGquotes == bytG_DOUBLE_QUOTES && chrG == "'") {
                    strGlinkA[intGlinesNumber] = sprintf("%s%s", strGlinkA[intGlinesNumber], chrG);
                } else {
                    bytGstatus = bytG_SPACES_AFTER_LINK;
                    bytGquotes = bytG_NO_QUOTES;
                }
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_SPACES_AFTER_LINK) {
                bytGquotes = (chrG == "'" ? bytG_SIMPLE_QUOTES : bytG_DOUBLE_QUOTES);
                bytGstatus = bytG_ICON;
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_ICON) {
                if (bytGquotes == bytG_NO_QUOTES ||
                    bytGquotes == bytG_SIMPLE_QUOTES && chrG == "\"" ||
                    bytGquotes == bytG_DOUBLE_QUOTES && chrG == "'") {
                    strGiconA[intGlinesNumber] = sprintf("%s%s", strGiconA[intGlinesNumber], chrG);
                } else {
                    bytGstatus = bytG_SPACES_AFTER_ICON;
                    bytGquotes = bytG_NO_QUOTES;
                }
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_SPACES_AFTER_ICON) {
                bytGquotes = (chrG == "'" ? bytG_SIMPLE_QUOTES : bytG_DOUBLE_QUOTES);
                bytGstatus = bytG_TEXT;
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_TEXT) {
                if (bytGquotes == bytG_NO_QUOTES ||
                    bytGquotes == bytG_SIMPLE_QUOTES && chrG == "\"" ||
                    bytGquotes == bytG_DOUBLE_QUOTES && chrG == "'") {
                    strGtextA[intGlinesNumber] = sprintf("%s%s", strGtextA[intGlinesNumber], chrG);
                } else {
                    strGtextA[intGlinesNumber] = sprintf("%s ", strGtextA[intGlinesNumber]);
                    bytGquotes = bytG_NO_QUOTES;
                }
                intGposition++;
                continue;
            }

        } else {

            /* 'x' : */
            if (bytGstatus == bytG_BEGINNING_SPACES || bytGstatus == bytG_LINK) {
                strGlinkA[intGlinesNumber] = sprintf("%s%s", strGlinkA[intGlinesNumber], chrG);
                bytGstatus = bytG_LINK;
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_SPACES_AFTER_LINK || bytGstatus == bytG_ICON) {
                strGiconA[intGlinesNumber] = sprintf("%s%s", strGiconA[intGlinesNumber], chrG);
                bytGstatus = bytG_ICON;
                intGposition++;
                continue;
            } else

            if (bytGstatus == bytG_SPACES_AFTER_ICON || bytGstatus == bytG_TEXT) {
                strGtextA[intGlinesNumber] = sprintf("%s%s", strGtextA[intGlinesNumber], chrG);
                bytGstatus = bytG_TEXT;
                intGposition++;
                continue;
            }
        }
        intGposition++;
    }

    # Complete image with path :
    if (length(strGiconA[intGlinesNumber]) > 0) {
        strGiconA[intGlinesNumber] = sprintf("%s%s", strPdataDirectory, strGiconA[intGlinesNumber]);
    }

    # Trim text :
    strGtextA[intGlinesNumber] = trim(strGtextA[intGlinesNumber]);

    # Memorize information :
    if (strGtextA[intGlinesNumber] != "" || strGlinkA[intGlinesNumber] != "" || strGiconA[intGlinesNumber] != "") {
        if (strGtextA[intGlinesNumber] == "") strGtextA[intGlinesNumber] = strGlinkA[intGlinesNumber];
#        print "Link[" intGlinesNumber "] : " strGlinkA[intGlinesNumber];
#        print "Icon[" intGlinesNumber "] : " strGiconA[intGlinesNumber];
#        print "Text[" intGlinesNumber "] : " strGtextA[intGlinesNumber];
#        print " ";
        intGlinesNumber++;
    }
}

########################################################################################################

END {

    # Print table :
    if (intGlinesNumber > 0) {

        intLpreviousTokensNumber = 0;

        for (intLlineIndex = 0; intLlineIndex < intGlinesNumber; ++intLlineIndex) {

            # Split text into tokens :
            intLsplitsNumber = split(strGtextA[intLlineIndex], strLsplitA, "->");
            intLtokensNumber = 0;
            for (intLtokenIndex in strGtokenA) {
                delete strGtokenA[intLtokenIndex];
            }
            for (intLsplitIndex in strLsplitA) {
                strLsplitA[intLsplitIndex] = trim(strLsplitA[intLsplitIndex]);
                if (length(strLsplitA[intLsplitIndex]) > 0) {
                    strGtokenA[intLtokensNumber] = html(strLsplitA[intLsplitIndex]);
                    intLtokensNumber++;
                }
                delete strLsplitA[intLsplitIndex];
            }

            # Detect separator :
            if (strGtokenA[intLtokensNumber - 1] == "--" || strGlinkA[intLlineIndex] == "--") {
                strGtokenA[intLtokensNumber - 1] = "--";
                strGlinkA[intLlineIndex] = "--";
            }

            # Calculate depth of the tree to add to the current branch :
            intLinsertIndex = 0;
            while (intLinsertIndex < intLtokensNumber - 1 && intLinsertIndex < intLpreviousTokensNumber && strGtokenA[intLinsertIndex] == strLpreviousTokenA[intLinsertIndex]) {
                intLinsertIndex++;
            }

            # Close previous list tags :
            if (intLinsertIndex == intLpreviousTokensNumber - 1) {
                printf("%s%s    </LI>\n", strG_INDENT, spaces(4 * intLinsertIndex));
            } else {
	            for (intLintermediaryTokenIndex = intLpreviousTokensNumber - 1; intLintermediaryTokenIndex >= intLinsertIndex; intLintermediaryTokenIndex--) {
	                printf("%s%s    </LI>\n", strG_INDENT, spaces(4 * intLintermediaryTokenIndex));
	                if (intLintermediaryTokenIndex > intLinsertIndex) printf("%s%s  </UL>\n", strG_INDENT, spaces(4 * intLintermediaryTokenIndex));
	            }
	        }

            # Add new item tags :
            for (intLintermediaryTokenIndex = intLinsertIndex; intLintermediaryTokenIndex < intLtokensNumber; ++intLintermediaryTokenIndex) {
                strLindent = sprintf("%s%s", strG_INDENT, spaces(4 * intLintermediaryTokenIndex + 2));
                if (intLintermediaryTokenIndex >= intLpreviousTokensNumber) {
                    printf("%s<UL>\n", strLindent);
                }
	            printf("%s  <LI>\n", strLindent);
	            printf("%s    ", strLindent);
	            if (intLintermediaryTokenIndex == intLtokensNumber - 1) {
	                if (length(strGlinkA[intLlineIndex]) > 0) {
	                    if (strGlinkA[intLlineIndex] != "--") {
	                        printf("<A ");
	                        printf("href=\"%s\" ", strGlinkA[intLlineIndex]);
	                        printf("target=\"_blank\" title=\"%s\" ", boldSite(strGlinkA[intLlineIndex]));
	                        printf("onclick='javascript:doPlaySound(intGouterPageSoundIndex); ' ");
	                        printf("onmouseover='javascript:setStatusBarText(\"%s\"); javascript:tooltip.show(this); ' ", dequote(strGtokenA[intLintermediaryTokenIndex]));
	                        printf("onmouseout='javascript:setStatusBarText(\"\"); javascript:tooltip.hide(this); '>\n");
	                        printf("%s      ", strLindent);
	                    }
	                } else {
	                    printf("<BR>\n");
			            printf("%s    ", strLindent);
	                }
	                if (length(strGiconA[intLlineIndex]) == 0) {
	                    strGiconA[intLlineIndex] = sprintf("%sblankPoint.png", strPimagesDirectory);
	                }
	                if (intLintermediaryTokenIndex == 0 || strGtokenA[intLintermediaryTokenIndex] != "--") {
	                    printf("<IMG src=\"%s\" width=\"16\" height=\"16\" alt=\"\">\n", strGiconA[intLlineIndex]);
			            printf("%s    %s", strLindent, (strGlinkA[intLlineIndex] != "--" ? "  " : ""));
			        }
	            }
	            if (intLintermediaryTokenIndex == 0) {
	                printf("<SPAN class=\"linksTitle\">");
	            } else {
	                printf("<SPAN style=\"font-size: %ipx;\">", 22 - 10 * intLintermediaryTokenIndex);
	            }
	            if (strGtokenA[intLintermediaryTokenIndex] == "--") {
	                if (intLintermediaryTokenIndex == 0) {
	                    printf("<BR><BR>");
	                } else {
	                    printf("&nbsp;");
	                }
	            } else {
	                if (intLintermediaryTokenIndex == 0 && length(strGtokenA[intLintermediaryTokenIndex]) > 2) {
	                    printf("<SPAN class='redLetters'>%c</SPAN><SPAN class='whiteLetters'>%s</SPAN>%c", substr(strGtokenA[intLintermediaryTokenIndex], 1, 1), substr(strGtokenA[intLintermediaryTokenIndex], 2, length(strGtokenA[intLintermediaryTokenIndex]) - 2), substr(strGtokenA[intLintermediaryTokenIndex], length(strGtokenA[intLintermediaryTokenIndex]), 1));
	                } else {
	                    printf("%s", strGtokenA[intLintermediaryTokenIndex]);
	                }
	            }
	            printf("</SPAN>");
	            if (intLintermediaryTokenIndex == intLtokensNumber - 1 && length(strGlinkA[intLlineIndex]) > 0 && strGlinkA[intLlineIndex] != "--") {
	            	printf("\n%s    </A>", strLindent);
	            }
	            printf("\n");
            }

            # Set previous values :
            for (intLpreviousTokenIndex in strLpreviousTokenA) {
                delete strLpreviousTokenA[intLpreviousTokenIndex];
            }
            for (intLtokenIndex in strGtokenA) {
                strLpreviousTokenA[intLtokenIndex] = strGtokenA[intLtokenIndex];
            }
            intLpreviousTokensNumber = intLtokensNumber;
        }

        # Close last item tags :
        for (intLintermediaryTokenIndex = intLpreviousTokensNumber - 1; intLintermediaryTokenIndex >= 0; intLintermediaryTokenIndex--) {
            printf("%s%s    </LI>\n", strG_INDENT, spaces(4 * intLintermediaryTokenIndex));
            printf("%s%s  </UL>\n", strG_INDENT, spaces(4 * intLintermediaryTokenIndex));
        }
    }

    # Print HTML footer :
    printf("      </DIV>\n");
	printf("      <SCRIPT type='text/javascript'>\n");
	printf("        <!--\n");
	printf("          javascript:printFooter(95, 50, true, false, true, false,\n"); 
	printf("                                 'http%%3A%%2F%%2Fjugglemaster.free.fr%%2Fscripts%%2Flinks.php?lang=' + strGjuggleLanguage + '&amp;charset=%%28detect+automatically%%29&amp;doctype=Inline&amp;ss=1&amp;group=0&amp;No200=1&amp;verbose=1&amp;st=1',\n");
	printf("                                 'http%%3A%%2F%%2Fjugglemaster.free.fr%%2Fscripts%%2Flinks.php?lang=' + strGjuggleLanguage + '&amp;warning=1&amp;profile=css3&amp;usermedium=all',\n");
	printf("                                 true, false, true, true, false, true, true);\n");
	printf("        -->\n");
	printf("      </SCRIPT>\n");
    printf("      <DIV class='BigTitles centeredText' style='margin-bottom: 30px; '>\n");
    printf("        <?PHP printJMPTitle(2, true); ?>\n");
    printf("      </DIV>\n");
    printf("    </DIV>\n");
    printf("  </BODY>\n");
    printf("</HTML>\n");
}