package dao;

/**
 * Created by li on 6/2/17.
 */
public class UserDao extends BaseDao{

    @Override
    protected <T> T query() throws Exception {
        T t = null;
        preparedStatement = connection.prepareStatement("SELECT username FROM user WHERE id = 10");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            t = (T) resultSet.getObject(1);
        }
        return t;
    }

    public static void main(String[] args) {
        BaseDao baseDao = new UserDao();
        String username = null;
        try {
            username = baseDao.doSomeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(username);
    }
}
