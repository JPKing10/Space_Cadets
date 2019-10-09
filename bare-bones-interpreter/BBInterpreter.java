import java.io.*;
import java.util.*;

/**
 * @author	James Phillips King <jpk@zealous.digital>
 * @version	0.1
 */

/**
 * Exception class for errors
 */
class InterpreterException extends Exception {
	String errDescription;

	public InterpreterException(String description) {
		errDescription = description;
	}

	public String toString() {
		return errDescription;
	}
}

/**
 * The Bare Bones Interpreter
 */
public class BBInterpreter {
	/**
	 * Max size of source to be interpreted
	 */
	final int MAX_PROG_SIZE = 10000;

	/**
	 * Token types
	 */
	final int NONE = 0;
	final int DELIMITER = 1;
	final int VARIABLE = 2;
	final int COMMAND = 3;

	/**
	 * End of program token
	 */

	final String EOP = "\0";

	/**
	 * Command internal representation
	 */
	final int UNKNOWN_COMMAND = 0;
	final int CLEAR = 1;
	final int INCR = 2;
	final int DECR = 3;
	final int EOL = 100;
	/**
	 * Error codes
	 */
	final int FILE_NOT_FOUND = 0;
	final int IO_FILE_READ = 1;

	/**
	 * Program source code to be interpreted
	 */
	private char[] source;

	/**
	 * Current source location
	 */
	private int sourceLocation;

	/**
	 * Current token state
	 */
	int tokenType;
	String token;
	int tokenCommand;

	/**
	 * Program integer variable storage
	 */
	private HashMap<String, int> variables;

	/**
	 * Structure to link commands with their tokens
	 */
	class Command {
		String command;
		int token;

		Command(String command, int token) {
			this.command = command;
			this.token = token;
		}
	}

	/**
	 * Table of commands with their tokens
	 */
	Command commandTable[] = {
		new Command("clear", CLEAR),
		new Command("incr", INCR),
		new Command("decr", DECR),
		new Command(";", EOL)
	};

	/**
	 * Converts command string to its token
	 *
	 * @param	command string
	 * @return	command token
	 */
	private int commandLookup(String command) {
		int token;
		for (i = 0; i < commandTable.length; i++) {
			if (commandTable[i].command.equals(command)) {
				return commandTable[i].token;
			}
		}
		return UNKNOWN_COMMAND;
	}

	/**
	 * Constructor for Bare Bones Interpreter
	 * <p>
	 * Loads program source into char array (more efficient than using String)
	 *
	 * @param	name of program
	 */
	public void BBInterpreter(String programName) throws InterpreterException {
		char temporaryBuffer[] = new char[MAX_PROG_SIZE];
		int size;

		size = loadSource(temporaryBuffer, programName);

		if (size != -1) {
			source = new char[size]; //properly sized source array
			System.arraycopy(temporaryBuffer, 0, source, 0, size); //copy source from temporary buffer
		}
	}

	/**
	 * Loads source from file
	 *
	 * @param	char array buffer to hold source
	 * @param	name of program to load
	 * @return 	size of the source
	 */
	private int loadSource(char[] temporaryBuffer, String programName) throws InterpreterException {
		int size = 0;

		try {
			FileReader fr = new FileReader(programName);
			BufferedReader br = new BufferedReader(fr);
			size = br.read(temporaryBuffer, 0, MAX_PROG_SIZE);
			fr.close();
		} catch(FileNotFoundException except) {
			errorHandler(FILE_NOT_FOUND);
		} catch (IOException except) {
			errorHandler(IO_FILE_READ);
		}

		if (temporaryBuffer[size - 1] == (char) 26) { //(char) 26 is end of file mark which we don't need to process during execution
			size--; //EOF wont be copied into source
		}

		return size;
	}

	/**
	 * Start source execution
	 */
	public void run() throws InterpreterException {
		/**
		 * Initilise program 
		 */
		variables = new HashMap<String, int>();
		sourceLocation = 0;

		/**
		 * Start parser
		 */
		bbParser();
	}

	/**
	 * Parser for Bare Bones
	 */
	private void bbParser() throws InterpreterException {
		/**
		 * Main loop: get token, interpret token
		 */
		do {
			readToken();
		} while (//TODO);	
	}

	private void readToken() throws InterpreterException {
		char c;
		tokenType = NONE;
		token = "";
		tokenCommand = UNKNOWN_COMMAND;

		/**
		 * Ignore white space
		 */
		while (sourceLocation < source.length && isWhitespace(source[sourceLocation]) {
			sourceLocation++;
		}

		/**
		 * Check for end of source
		 */
		if (sourceLocation == source.length) {
			tokenType = DELIMITER;
			token = EOP;
			return;
		}

		/**
		 * Line ends at semicolon. TODO: Windows uses \r\n
		 */
		if (source[sourceLocation] == ';') {
			tokenCommand = EOL;
			token = "\n";

			/**
			 * Move sourceLocation to beginning of next line
			 */
			do {
				sourceLocation++;
			} while (sourceLocation < source.length && source[sourceLocation] != '\n');
			sourceLocation++; //move to next line
			return;
		}

		if (Character.isLetter(source[sourceLocation])) {
			while(!isDelimiter(source[sourceLocation]) && sourceLocation <= source.length) {
				token += source[sourceLocation];
				sourceLocation++;

		




	}

	/**
	 * Check if char is delimiter
	 *
	 * @param 	char to check
	 * @return 	true iff char is space or semicolon
	 */
	private boolean isDelimiter(char c) {
		//TODO this
	}

	/**
	 * Check if char is either a space or a tab
	 *
	 * @param	character to check
	 * @return	true iff char is space or tab
	 */
	private boolean isWhitespace(char c) {
		return (c == " " || c == "\t");
	}

	}
	/**
	 * Handles errors
	 *
	 * @param	error code
	 */
	private void errorHandler(int error) throws InterpreterException {
		String[] errors = {
			"File not found",
			"IO error while loading program file"
		};

		throw new InterpreterException(errors[error]);
	}
