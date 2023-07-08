package Filters;

import Interface.ChainFilter;

public class Filter3 implements ChainFilter{
	
	private ChainFilter nextFilter;

	@Override
	public void setNextFilter(ChainFilter chainFilter) {
		this.nextFilter = chainFilter;
		
	}

	@Override
	public void process() {
		
		System.out.println("Filter3 ¿‘¥œ¥Ÿ");
		
		//nextFilter.process();
	}

}
