package Filters;

import Interface.ChainFilter;

public class Filter2 implements ChainFilter{
	
	private ChainFilter nextFilter;

	@Override
	public void setNextFilter(ChainFilter chainFilter) {
		this.nextFilter = chainFilter;
		
	}

	@Override
	public void process() {
		
		System.out.println("Filter2 ¿‘¥œ¥Ÿ");
		
		nextFilter.process();
	}

}
