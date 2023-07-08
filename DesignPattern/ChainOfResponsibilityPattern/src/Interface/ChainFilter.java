package Interface;

public interface ChainFilter {
	public void setNextFilter(ChainFilter chainFilter);
	public void process();
}
