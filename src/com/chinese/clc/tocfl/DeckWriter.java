package com.chinese.clc.tocfl;

import java.io.PrintWriter;
import java.util.List;

import com.chinese.clc.tocfl.definitions.Definitions;
import com.github.pffy.chinese.HanyuPinyin;

public class DeckWriter {

	private String path;
	private Definitions definitions = new Definitions();

	public DeckWriter(String path) {
		this.path = path;
	}
	
	public void dump(List<Term> terms) {

		try (PrintWriter pvout = new PrintWriter(path, "UTF-8")) {
			for (Term term : terms) {
				pvout.println(makeLine(term));
			}
		} catch (Exception e) {
			// TODO use log
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
		String zhuyin = definitions.getZhuyin(term);
		String definition = cleanSeparator(definitions.getDefinition(term));

		return String.join(";", getTermId(term), String.valueOf(term.getIndex()), term.getZh(), term.getPinyin(),
				zhuyin, term.getType(), term.getDomain(), definition, term.getLevel());
	}

	private String cleanSeparator(String definition) {
		return definition.replace(';', ',');
	}
}
