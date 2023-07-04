package db.partition.partitioning.Interface;

import java.sql.SQLException;

public interface Partition {

    public void generatePartition() throws SQLException;

    public void dropPartition();

}
