package com.chinese.clc.tocfl;

import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.chinese.clc.dict.ccedict.CCEDict;

public class DeckWriter {

	public void dumpToFile(List<Term> terms) {
		
		try {
			URL url = CCEDict.class.getResource("/");
			Path root = Paths.get(url.toURI());
			Path filename = root.resolve("tocfl.txt");
			
			try (PrintWriter pvout = new PrintWriter(filename.toString(), "UTF-8")) {
				for (Term term : terms) {
					pvout.println(makeLine(term));
				}
			} catch (Exception e) {
				//TODO log
				e.printStackTrace();
			}
		} catch (URISyntaxException e) {
			// TODO log
			e.printStackTrace();
		}
	}

	private String getTermId(Term term) {
		StringBuilder id = new StringBuilder();

		id.append(term.getLevel().replace(" ", ""));
		id.append("_");
		id.append(String.format("%04d", term.getIndex()));

		return id.toString();
	}

	private String makeLine(Term term) {
		return String.join(";", getTermId(term), term.getZhTrad(), term.getZhSimpl(), term.getType(), term.getPinyin(),
				term.getDefinition().replace(";", ","), term.getLevel());
	}
}
