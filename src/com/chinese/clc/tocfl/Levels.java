package com.chinese.clc.tocfl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinese.clc.tocfl.bands.WordsBand;
import com.chinese.clc.tocfl.bands.WordsBand0;
import com.chinese.clc.tocfl.bands.WordsBand1;
import com.chinese.clc.tocfl.bands.WordsBand2_3;

public class Levels {

	// in a more "pro" setting, this should be IOC'ed

	public enum Band {
		Band0(Level.L0), Band1(Level.L1, Level.L2), Band2(Level.L3, Level.L4), Band3(Level.L5_6);

		Level[] levels;

		private Band(final Level... levels) {
			this.levels = levels;
		}

		public Level[] getLevels() {
			return levels;
		}
	}

	public enum Level {
		L0("準備一級"), L1("入門級"), L2("基礎級"), L3("進階級"), L4("高階級"), L5_6("流利級");

		private String description;

		private Level(final String description) {
			this.description = description;
		}

		public String description() {
			return description;
		}
	}

	private Map<Level, List<String>> paths = new HashMap<>();
	private Map<Band, Class<? extends WordsBand>> implementations = new HashMap<>();

	public Levels() {
		initializePaths();
		initializeImpls();
	}

	private String getBandLevelName(final Band band, final Level level) {
		StringBuilder name = new StringBuilder();
		name.append(band.name());
		name.append(" ");
		name.append(level.name());
		return name.toString();
	}

	public List<String> getPaths(final Level level) {
		return paths.get(level);
	}

	private void initializeImpls() {
		implementations.put(Band.Band0, WordsBand0.class);
		implementations.put(Band.Band1, WordsBand1.class);
		implementations.put(Band.Band2, WordsBand2_3.class);
		implementations.put(Band.Band3, WordsBand2_3.class);
	}

	private void initializePaths() {
		paths.put(Level.L0, Arrays.asList("/tocfl/tocfl1.csv", "/tocfl/tocfl2.csv"));
		paths.put(Level.L1, Arrays.asList("/tocfl/tocfl3.csv"));
		paths.put(Level.L2, Arrays.asList("/tocfl/tocfl4.csv"));
		paths.put(Level.L3, Arrays.asList("/tocfl/tocfl5.csv"));
		paths.put(Level.L4, Arrays.asList("/tocfl/tocfl6.csv"));
		paths.put(Level.L5_6, Arrays.asList("/tocfl/tocfl7.csv"));
	}

	public WordsBand make(final Band band, final Level level) {
		WordsBand tocfl = null;

		Class<? extends WordsBand> clazz = implementations.get(band);
		if (clazz != null) {
			try {
				Constructor<? extends WordsBand> constructor = clazz.getConstructor(String.class);
				tocfl = constructor.newInstance(getBandLevelName(band, level));

			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO log
				e.printStackTrace();
			}
		}

		return tocfl;
	}
}
