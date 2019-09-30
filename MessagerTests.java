import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MessagerTests {

	@Test
	public void substring() {
		String longString = "NAMEaaENDNAME NAMEbbENDNAME";
		String[] strArray1 = longString.split(" ");
		for (String item : strArray1) { // Cycle through all the pieces
			if (item.startsWith("NAME")) {
				String target = item.substring(item.indexOf("NAME") + 4, item.indexOf("ENDNAME")); // Your desired String
				System.out.println(target);
			}
		}
		
		
	}

}
