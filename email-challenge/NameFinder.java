import java.io.*;
import java.net.*;
import java.util.regex.*;

/**
 * @author James Phillips King <jpk@zealous.digital>
 * @version 1.0
 */
public class NameFinder {
	/**
	 * Take Uni of Southampton user email ID and outputs their name (if available).
	 */
	public static void main (String[] args) {
		NameFinder nameFinder = new NameFinder();
		String id = nameFinder.readStringFromCmd("Enter user email ID: ");

		String pageAddress = "https://www.ecs.soton.ac.uk/people/" + id;
		BufferedReader page = nameFinder.openPageFromAddress(pageAddress);
		String name = nameFinder.parsePageForName(page);
		System.out.println(name);
	}

	/**
	 * Read string from terminal.
	 *
	 * @param 	promt the promt shown to the user
	 * @return	String input by user
	 */
	private String readStringFromCmd(String prompt) {
		System.out.print(prompt);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = null;

		try {
			input = br.readLine();
		} catch (IOException ioe) {
			System.err.println("Input error");
		}

		return input;
	}

	/**
	 * Read webpage source into a BufferedReader.
	 *
	 * @param	pageAddress the address of the webpage
	 * @return	BufferedReader containing webpage
	 */
	private BufferedReader openPageFromAddress(String pageAddress) {
		URL pageURL = null;
		try {
			pageURL = new URL(pageAddress);
		} catch (MalformedURLException e) {
			System.err.println("URL malformed");
		}


		BufferedReader page = null;
		try {
			page = new BufferedReader(new InputStreamReader(pageURL.openStream()));	
		} catch (IOException e) {
			System.out.println("Error opening URL stream");
		}

		return page;
	}
	
	/**
	 * Processes webpage source to find name.
	 *
	 * @param 	page the BufferedReader containing the webpage source
	 * @return 	String with the name
	 */
	private String parsePageForName(BufferedReader page) {
		// TODO speed up page parsing
		Pattern namePattern = Pattern.compile("property=\"name\">(.+?)<");
		String pageLine;
		Matcher matcher = null;
		boolean foundMatch = false;

		try {
			while ((pageLine = page.readLine()) != null) {
				matcher = namePattern.matcher(pageLine);
				if (matcher.find()) {
					foundMatch = true;
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading page");
		}

		if (foundMatch) {
			return(matcher.group(1));
		} else {
			return("User not found");
		}
	}
}
