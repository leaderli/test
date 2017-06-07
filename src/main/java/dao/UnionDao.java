package dao;

/**
 * Created by li on 6/2/17.
 */
public class UnionDao extends BaseDao {
    public UnionDao() {

    }

    public UnionDao(String sql) {
        this.sql = sql;
    }

    private String sql;

    @Override
    protected <T> T query() throws Exception {
        T t = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = (T) resultSet.getObject(1);
            }
        } catch (Exception e) {
            throw e;
        }
        return t;
    }

    public <T> T unionQuery(String sql) throws Exception {
        this.sql = sql;
        return doSomeQuery();
    }
}
