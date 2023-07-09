import Filters.Filter1;
import Filters.Filter2;
import Filters.Filter3;

public class Main {
	
	public static void main(String[] args) {
		
		ChainConfiguration config = new ChainConfiguration();
		config.configFilters();
		
		//Filter1 Ω√¿€
		config.getFilter1().process();
		
		
	}
	
}


class ChainConfiguration {
	
	private Filter1 filter1;
	private Filter2 filter2;
	private Filter3 filter3;
	
	public void configFilters(){
		filter1 = new Filter1();
		filter2 = new Filter2();
		filter3 = new Filter3();
		
		filter1.setNextFilter(filter2);
		filter2.setNextFilter(filter3);
	}
	
	public Filter1 getFilter1() {
		return this.filter1;
	}
	
}

