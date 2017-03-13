<?PHP

	function getSize($strPdescriptor) {
		if (is_dir($strPdescriptor)) {
			$dirL = opendir($strPdescriptor);
			if ($dirL === false) {
				return 0;
			}
			
			$intLsize = 0;
			while (($strLdescriptor = readdir($dirL)) !== false) {
			    if ($strLdescriptor != "." && $strLdescriptor != "..") {
        			$intLsize += getSize($strPdescriptor . "/" . $strLdescriptor);
			    }
        	}
    		closedir($dirL);
		}
		if (is_file($strPdescriptor)) {
			$intLsize = filesize($strPdescriptor);
			return ($intLsize === false ? 0 : $intLsize);
		}
		return 0;
	}

	echo "Taille vidéo finale en fr (Go) : " . substr(getSize("/video") / (1024 * 1024), 0, 3) . "\n";
?>