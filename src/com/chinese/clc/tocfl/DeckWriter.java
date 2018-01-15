package com.chinese.clc.tocfl;

import java.io.PrintWriter;
import java.util.List;

public class DeckWriter {

	private String cleanDefinition(String definition) {
		return definition.replaceAll("/", ",");
	}

	private void dumpToFile(String file, List<Term> terms) {
		
		try (PrintWriter pvout = new PrintWriter(file, "UTF-8")) {
			for (Term term : terms) {
				pvout.println(makeLine(term));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getTermId(Term term) {
		StringBuilder id = new StringBuilder();

		id.append(term.getLevel());
		id.append(" ");
		id.append(String.format("%04d", term.getIndex()));

		return id.toString();
	}

	private String makeLine(Term term) {
		return String.join(";", getTermId(term), String.valueOf(term.getIndex()), term.getZh(), term.getType(),
				term.getDomain(), term.getLevel());
	}
}
