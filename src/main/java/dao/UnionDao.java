package dao;

/**
 * Created by li on 6/2/17.
 */
public class UnionDao extends BaseDao {
    public UnionDao(String sql) {
        this.sql = sql;
    }

    private String sql;

    @Override
    protected <T> T query() throws Exception {
        System.out.println(sql);
        T t = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                t = (T) resultSet.getObject(1);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
        return t;
    }

    public <T> T unionQuery(String sql) throws Exception {
        this.sql = sql;
        System.out.println(sql);
        return doSomeQuery();
    }

    public static void main(String[] args) {
        UnionDao baseDao = new UnionDao("SELECT id FROM user WHERE id = 10");
        int username = 0;
        try {
            username = baseDao.doSomeQuery();
        } catch (Exception e) {
        }
        System.out.println(username);
    }
}
