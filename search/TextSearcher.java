package search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextSearcher {

	/**
	 * Initializes the text searcher with the contents of a text file.
	 * The current implementation just reads the contents into a string 
	 * and passes them to #init().  You may modify this implementation if you need to.
	 * 
	 * @param f Input file.
	 * @throws IOException
	 */

	private String fileString;

	public TextSearcher(File f) throws IOException {
		FileReader r = new FileReader(f);
		StringWriter w = new StringWriter();
		char[] buf = new char[4096];
		int readCount;

		while ((readCount = r.read(buf)) > 0) {
			w.write(buf, 0, readCount);
		}

		init(w.toString());
	}

	/**
	 *  Initializes any internal data structures that are needed for
	 *  this class to implement search efficiently.
	 */
	protected void init(String fileContents) {
		this.fileString = fileContents;
	}

	/**
	 * 
	 * @param queryWord The word to search for in the file contents.
	 * @param contextWords The number of words of context to provide on
	 *                     each side of the query word.
	 * @return One context string for each time the query word appears in the file.
	 */
	public String[] search(String queryWord,int contextWords) {
		ArrayList<String> result = new ArrayList<>();
		String output = String.format("((?:[a-zA-Z']+[^a-zA-Z']+[.]*){0,%d}\\b)%s(\\b(?:[^a-zA-Z']+[a-zA-Z']+[.]*){0,%d})", contextWords, queryWord, contextWords);
		TextTokenizer tokenizer = new TextTokenizer(this.fileString, output);
		while (tokenizer.hasNext()) {
			String word = tokenizer.next();

			if (tokenizer.isWord(word)) {
				result.add(word);
			}
		}

		return result.toArray(new String[result.size()]);
	}

	public static void main(String[] args) {
		try {
			TextSearcher searcher = new TextSearcher(new File("/Users/robertjewell/Downloads/TextSearchProblemSol/out/production/TextSearchProblem/files/long_excerpt.txt"));
			searcher.search("naturalists", 3);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}



