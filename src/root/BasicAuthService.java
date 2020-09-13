package root;

import java.util.Set;

public class BasicAuthService implements AuthService {
    private final DataBaseHandler records = new DataBaseHandler();

    @Override
    public Record findRecord(String login, String password) {
        Record currentRecord = records.getUser(login,password);
               if (currentRecord != null) return currentRecord;
        return null;
    }

    @Override
    public void setRecord(String name,String login,String password){
        records.setUser(name,login,password,"avatars/pic5.png");
    }

    @Override
    public Set<Record> getRecords() {
            return records.getRecords();
    }
}